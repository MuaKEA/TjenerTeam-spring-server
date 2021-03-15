package com.example.demo.facebook.repository;

import com.example.demo.facebook.model.FacebookUser;
import org.springframework.data.repository.CrudRepository;

public interface FacebookRepository extends CrudRepository<FacebookUser, Long> {


}
