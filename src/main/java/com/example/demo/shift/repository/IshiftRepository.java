package com.example.demo.shift.repository;

import com.example.demo.shift.model.Shift;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IshiftRepository extends CrudRepository<Shift,Long>{
     List<Shift> findAll();

}
