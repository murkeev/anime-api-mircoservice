package murkeev.authService.controller;

import lombok.AllArgsConstructor;
import murkeev.authService.model.AuthRequest;
import murkeev.authService.model.AuthResponse;
import murkeev.authService.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register (@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
