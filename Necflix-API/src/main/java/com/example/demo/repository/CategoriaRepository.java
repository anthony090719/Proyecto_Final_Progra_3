package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entities.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

}
