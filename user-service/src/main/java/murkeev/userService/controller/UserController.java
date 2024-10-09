package murkeev.userService.controller;

import lombok.AllArgsConstructor;
import murkeev.userService.dto.UserDto;
import murkeev.userService.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDto> getAllUsers(@RequestParam(value = "page_number") int pageNumber,
                                     @RequestParam(value = "page_size") int pageSize) {
        return userService.getAllUsers(PageRequest.of(pageNumber, pageSize));
    }

    @GetMapping("/find-by-username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto getUserByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public UserDto getUserProfile() {
        return userService.profile();
    }

    @DeleteMapping("/remove-account")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteAccount() {
        userService.deleteAccount();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeUserById(@PathVariable Long id) {
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/remove/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeUserByUsername(@PathVariable String username) {
        userService.removeUserByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
