package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
// Camada DTO, utilizada para gerenciar o objeto e utiliza-lo para exibir somente informações necessárias e fazer tratativas.
public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value) {

}
