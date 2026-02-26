package com.api.ems.users;

import com.api.ems.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            select u from User u
            where (:name is null or lower(u.fullName)
            like lower(concat('%', :name, '%' ) ) )
            """)
    Page<User> getPagedUser(Pageable pageable, @Param("name") String name);

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
