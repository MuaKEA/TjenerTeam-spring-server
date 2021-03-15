package com.example.demo.facebook.repository;

import com.example.demo.facebook.model.PostCodes;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IpostCodes extends CrudRepository<PostCodes,Long> {
  Optional<PostCodes> findByPostCode(Integer post_code);

}
