package com.bottle_app.repository;

import com.bottle_app.model.Bottle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface BottleRepository extends CrudRepository<Bottle, Long>, PagingAndSortingRepository<Bottle, Long> {

    //To Do
    /*
    static Iterable<Bottle> findAllByReceiver() {

    }
    */

}
