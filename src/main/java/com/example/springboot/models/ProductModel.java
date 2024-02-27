package com.example.springboot.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel; //dependencia hateoas, internamente terá métodos para construir links para navegabilidade, que redirecionará clientes para detalhes dos produtos

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;


/*Aqui sera criada a classe ProductModel e nela a  TABELA "TB PRODUCTS" COM AS COLUNAS IdProduto, Name e Value - no Postgre*/
@Entity
@Table(name = "TB PRODUCTS") /*Nome da tabela*/

/*Classe*/
public class ProductModel extends RepresentationModel<ProductModel> implements Serializable { /* Serializabre é uma interface que mostra a JVM que esta é uma classe que está habilitada a passar por serializazções */
    private static final long serialVersionUID = 1L; /*numero de controle de versao de classes serializadas quando houver necessidade*/

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO) /*nao sera necessario iniciar manualmente um valor do ID na base de dados. Será feito automaticamente*/
    /*3 Atributos, que serão cada coluna da tabela */
    private UUID idProduct; /*UUID identificador muito utilizado para base de dados microservices para evitar conflitos */
    private String name;
    private BigDecimal value;

    /*getters e setters*/
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
