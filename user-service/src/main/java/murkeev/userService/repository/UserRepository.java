package murkeev.userService.repository;

import murkeev.userService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

//    @Query("SELECT u FROM User u")
//    Page<User> getAll(Pageable pageable);
//
//    @Query("SELECT u FROM User u WHERE u.createdAt = :registrationDate")
//    Page<User> findByRegistrationDate(Instant registrationDate, Pageable pageable);
}
