package murkeev.userService.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequestDto {

    @NotNull(message = "Login cannot be null")
    private String username;
    private String password;
}
