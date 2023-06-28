package com.example.springboot.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.models.ProductModel;

//Interface criada para que os métodos do JPA sejam utilizados exemplo: JpaRepository<"Objeto fornecido", "tipo da chave primária fornecida">
@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
    
}