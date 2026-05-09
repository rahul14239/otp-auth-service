package central.auth_service.dto;

import lombok.Data;

@Data
public class VerifyOtpRequest {

    private String user_id;
    private String otp;
    private String device_id;
}
