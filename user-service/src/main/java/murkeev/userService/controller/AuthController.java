package murkeev.userService.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import murkeev.userService.dto.LoginRequestDto;
import murkeev.userService.dto.RegistrationUserDto;
import murkeev.userService.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationUserDto registrationUserDto) {
        authService.addUser(registrationUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody LoginRequestDto loginRequest) {
        String token = authService.authenticateAndGenerateToken(loginRequest.getLogin(), loginRequest.getPassword());
        return ResponseEntity.ok(token);
    }
}
