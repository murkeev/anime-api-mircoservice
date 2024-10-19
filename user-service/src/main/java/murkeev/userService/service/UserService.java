package murkeev.userService.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import murkeev.userService.dto.RegistrationUserDto;
import murkeev.userService.dto.UserDto;
import murkeev.userService.dto.UserVO;
import murkeev.userService.model.User;
import murkeev.userService.repository.UserRepository;
import murkeev.userService.security.UserPrincipal;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    private static final String USER_NOT_FOUND = "User not found";

    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        if (users.isEmpty()) {
            throw new EntityNotFoundException(USER_NOT_FOUND);
        }
        return users.map(user -> modelMapper.map(user, UserDto.class));
    }

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    public void createUser(RegistrationUserDto registrationUserDto) {
        User user = modelMapper.map(registrationUserDto, User.class);
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed in saving user");
        }
    }

    @Transactional
    public void removeUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        try {
            userRepository.deleteById(user.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error in remove user");
        }
    }

    @Transactional
    public void removeUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        try {
            userRepository.deleteById(user.getId());
        } catch (Exception e) {
            throw new RuntimeException("Error in remove user");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        return new UserPrincipal(user);
    }
}
