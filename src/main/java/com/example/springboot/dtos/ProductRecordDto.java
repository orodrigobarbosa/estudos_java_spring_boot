package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

//DTO =  DATA TRANSFER OBJECTS - OBJETO PARA TRANSFERÊNCIA DE DADOS

//RECORD TRAZ VÁRIOS MÉTODOS PRONTOS, COMO TOSTRING, GETTERS, EQUAILS.. SAO IMUTÁVEIS, POR ISSO, NAO POSSUEM SETTERS, APENAS GETTERS
public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value) { //@NotBlank e @NotNull - valor de validação... nao podem ser vazios ou nulos respectivamente



}
