package vn.iotstar.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.iotstar.Entity.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    // Truy vấn theo username (sử dụng JPQL)
    @Query("SELECT u FROM Users u WHERE u.username = :username")
    Users getUserByUsername(@Param("username") String username);

    // Tìm theo email
    Optional<Users> findByEmail(String email);

    // Tìm theo username hoặc email (dùng cho đăng nhập)
    Optional<Users> findByUsernameOrEmail(String username, String email);

    // Tìm theo username (Spring Data JPA sẽ tự sinh query)
    Optional<Users> findByUsername(String username);

    // Kiểm tra username đã tồn tại chưa
    Boolean existsByUsername(String username);

    // Kiểm tra email đã tồn tại chưa
    Boolean existsByEmail(String email);
}
