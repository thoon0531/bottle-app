package com.bottle_app.repository;

import com.bottle_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

    Optional<User> findByUsername(String username);

    long countByIdNot(Long id);

    @Query(value = "select * from USER where rand_id > FLOOR((RAND()*1000000)) and not USER.ID in(:id) order by rand_id limit 1", nativeQuery = true)
    Optional<User> findRandByIdNot(@Param("id") long id);
}
