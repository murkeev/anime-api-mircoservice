package murkeev.userService.security;

import murkeev.userService.model.User;

import java.util.Set;


public class UserPrincipal extends org.springframework.security.core.userdetails.User {

    private Long id;

    public UserPrincipal(User user) {
        super(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                Set.of(user.getRole()));
        this.id = user.getId();

    }

    public Long getId() {
        return id;
    }
}
