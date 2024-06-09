package com.bottle_app.repository;

import com.bottle_app.model.Bottle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BottleRepository extends JpaRepository<Bottle, Long> {

    //To Do
    /*
    static Iterable<Bottle> findAllByReceiver() {

    }
    */

}
