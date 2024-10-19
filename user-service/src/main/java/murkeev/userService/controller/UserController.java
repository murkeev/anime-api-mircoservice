package murkeev.userService.controller;

import lombok.AllArgsConstructor;
import murkeev.userService.dto.LoginRequestDto;
import murkeev.userService.dto.UserDto;
import murkeev.userService.dto.UserVO;
import murkeev.userService.security.UserPrincipal;
import murkeev.userService.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @GetMapping("/info-unsecured")
    public String infoUnsecured() {
        return "Info unsecured";
    }

    @GetMapping("/info-secured")
    public String infoSecured() {
        return "Info secured";
    }

    @PostMapping("/login")
    public UserVO login(@RequestBody LoginRequestDto loginRequestDto) {
         Authentication authenticate = authenticationManager
                 .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        UserPrincipal userPrincipal = (UserPrincipal) authenticate.getPrincipal();
        return new UserVO(userPrincipal.getId(), userPrincipal.getAuthorities().iterator().next().getAuthority());
    }

    @GetMapping
    public Page<UserDto> getAllUsers(@RequestParam(value = "page_number") int pageNumber,
                                     @RequestParam(value = "page_size") int pageSize) {
        return userService.getAllUsers(PageRequest.of(pageNumber, pageSize));
    }

    @GetMapping("/find-by-username/{username}")
    public UserDto getUserByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeUserById(@PathVariable Long id) {
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/remove/{username}")
    public ResponseEntity<Void> removeUserByUsername(@PathVariable String username) {
        userService.removeUserByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
