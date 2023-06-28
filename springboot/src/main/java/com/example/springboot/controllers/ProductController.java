package com.example.springboot.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// Camada Controller, utilizada para o mapeamento de rotas, exite também uma camada de service que funciona para intermediar
// as camadas de repositório e controller, porém não foi utilizada netes projeto.
@RestController
public class ProductController {
//    Instância da Interface ProductRepositoy para poder utilizar os métodos JPA nessa classe controller.
    @Autowired
    ProductRepository productRepository;

//  Retorno de todos os produtos da lista.
    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts(){
        List<ProductModel> productList = productRepository.findAll();
        if(!productList.isEmpty()){
            for (ProductModel product : productList){
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getUniqueProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }
//  Retorno de um produto da lista.
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getUniqueProduct(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> productSelected = productRepository.findById(id);
        if (productSelected.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        productSelected.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Products List"));
        return ResponseEntity.status(HttpStatus.OK).body(productSelected.get());

    }
//  Insere um produto na lista.
    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

//  Altera um produto na lista com base no ID informado.
    @PutMapping("/products/{id}")
    public ResponseEntity<Object>updateProduct(@PathVariable(value = "id") UUID id,
                                               @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productSelected = productRepository.findById(id);
        if (productSelected.isEmpty()) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        var productModel = productSelected.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }
//  Deleta um produto na lista com base no ID informado.
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object>deleteProduct(@PathVariable(value="id") UUID id){
        Optional<ProductModel> productSelected = productRepository.findById(id);

        if(productSelected.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro de produto não encontrado.");
        }
        productRepository.delete(productSelected.get());
        return ResponseEntity.status(HttpStatus.OK).body("Registro de produto deletado.");
    }

}
