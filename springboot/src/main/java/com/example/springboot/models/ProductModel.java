package com.example.springboot.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

//@Entity = Define a classe modelo como um Entidade do banco de dados.
@Entity
//@Table = Indica o nome dessa entidade no banco de dados.
@Table(name = "TB_PRODUCTS")
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable  {
//  Interface Serializable = Ao implementa-la, indico que meus objetos podem ser convertidos, armazenados, etc. É utilizada
//  para armazenar objetos, transmiti-los em rede, Criar cópias de objetos ou compartilhar objetos em ambientes distribuídos.

    private static final long serialVersionUID = 1L;

//@ID e @GeneratedValue = Indica que o idProduct será a chave primária da tabela e será gerada automaticamente a cada objeto criado.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//  UUID = tipo utilizado para criação de chaves aleatórias
    private UUID idProduct;
    private String name;
    private BigDecimal value;

    // Getters and Setters
    public UUID getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(UUID idProduct) {
        this.idProduct = idProduct;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getValue() {
        return value;
    }
    public void setValue(BigDecimal value) {
        this.value = value;
    }

   
}
