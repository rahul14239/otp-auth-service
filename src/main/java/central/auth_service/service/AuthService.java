package central.auth_service.service;

import central.auth_service.dto.AuthResponse;
import central.auth_service.entity.UserSession;
import central.auth_service.repository.UserSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    private final UserSessionRepository sessionRepository;

    private static final int MAX_OTP_ATTEMPTS = 5;

    public void sendOtp(String user_id){
        String otp = generateOtp();

        System.out.println(otp);

        String hashedOtp = passwordEncoder.encode(otp);

        redisTemplate.opsForValue().set(
                "Otp:"+user_id,
                hashedOtp,
                Duration.ofMinutes(3)
        );


    }

    public String generateOtp(){
        Random random = new Random();
        int otp = 100000+random.nextInt(900000);

        return String.valueOf(otp);
    }


    public AuthResponse verifyOtp(String user_id, String otp,String deviceId) {

        String attemptsKey = "otp_attempts:" + user_id;

        String attemptsValue = redisTemplate.opsForValue().get(attemptsKey);
        int attempts;

        if (attemptsValue == null) {
            attempts = 0;
        } else {
            attempts = Integer.parseInt(attemptsValue);
        }

        if (attempts >= MAX_OTP_ATTEMPTS) {
            throw new RuntimeException(
                    "Too many failed attempts. Try again later."
            );
        }
            String storedOtp = redisTemplate.opsForValue().get("Otp:" + user_id);

            if (storedOtp == null) {
                throw new RuntimeException(
                        "OTP expired"
                );
            }

            boolean isValid = passwordEncoder.matches(otp, storedOtp);

            if (!isValid) {
                redisTemplate.opsForValue().increment(attemptsKey);

                redisTemplate.expire(
                        attemptsKey,
                        Duration.ofMinutes(5)
                );

                throw new RuntimeException(
                        "Invalid OTP"
                );
            }

            redisTemplate.delete("Otp:" + user_id);
            redisTemplate.delete(attemptsKey);

            System.out.println("Fetching key = OTP:" + user_id);

            System.out.println("Stored OTP = " + storedOtp);

            String accessToken = jwtService.generateAccessToken(user_id);
            String refreshToken = jwtService.generateRefreshToken(user_id);

            UserSession session = new UserSession();

            session.setUserId(user_id);
            session.setDeviceId(deviceId);
            session.setRefreshToken(refreshToken);
            session.setLoginTime(LocalDateTime.now());
            session.setExpiryTime(LocalDateTime.now().plusDays(7));
            session.setActive(true);

            sessionRepository.save(session);

            return new AuthResponse(accessToken, refreshToken);
        }
    }
