package murkeev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginVO {
    String accessToken;
    String refreshToken;
}
