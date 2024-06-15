package com.bottle_app.repository;

import com.bottle_app.model.Bottle;
import com.bottle_app.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BottleRepository extends JpaRepository<Bottle, Long> {
    long countByCreatorNot(User user);

    Page<Bottle> findByReceiver(User user, Pageable pageable);
}
