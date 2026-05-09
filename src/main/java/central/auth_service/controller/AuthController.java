package central.auth_service.controller;


import central.auth_service.dto.AuthResponse;
import central.auth_service.dto.OtpRequest;
import central.auth_service.dto.VerifyOtpRequest;
import central.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody OtpRequest request){
        authService.sendOtp(request.getUser_id());

        return "Otp sent Successfully";
    }

    @PostMapping("/verify-otp")
    public AuthResponse verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest){
        return authService.verifyOtp(verifyOtpRequest.getUser_id(),
                verifyOtpRequest.getOtp(),
                verifyOtpRequest.getDevice_id());
    }

}
