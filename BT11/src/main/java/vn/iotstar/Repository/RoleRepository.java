package vn.iotstar.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.iotstar.Entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Custom JPQL query - tìm role theo tên
    @Query("SELECT r FROM Role r WHERE r.name = :name")
    Role getRoleByName(@Param("name") String name);

    // Tìm role theo tên (Spring Data JPA tự tạo query)
    Optional<Role> findByName(String name);
    
}
