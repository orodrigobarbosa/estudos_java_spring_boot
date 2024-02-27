package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDto;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//CRIAÇÃO DO CONTROLLER
//NO CONTROLLER SERÁ IMPLEMENTADO O CRUD COMPLETO
@RestController

public class ProductController {

    @Autowired
    ProductRepository productRepository;
    /*ponto de injeção para a interface ProductRepository, para que tenhamos acesso aos metodos Jpa quando houver necessidade*/


 /////////////MÉTODO CRUD (CREAT = POST, READ = GET, UPDATE = PUT, DELETE = //////////////////////////////////////////////////

        /* O MODELO DE MATURIDADE DE LEONNARD RICHARDSON PARA QUE UMA API SEJA CONSIDERADA RESTFUL POSSUI 4 NÍVEIS:
       0 - A API UTILIZE DO PROTOCOLO HTTP
       1 - TENHA RECURSOS BEM DEFINIDOS E CORRETO USO DA URI
       2 - UTILIZE OS MÉTODOS HTTP DE FORMA SEMÂNTICA. POR EXEMPLO: UM MÉTODO PARA SALVAR UM DETERMINADO RECURSO UTILIZE O MÉTODO POST, PARA LISTAR LEITURA DE RECURSO USAR O MÉTODO GET... E ASSIM POR DIANTE
       3 -
            */


    //METODO POST - MÉTODO DE CRIAÇÃO
    @PostMapping("/products") //URI products
    //inicia o método
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel); //Bean converte o productDTo em ProductModel para que seja salvo na base de dados
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }


    //MÉTODO GET -  DE LEITURA
    // Método getAll vai listar todos os itens que estão no banco de dados
    @GetMapping("products") //uso do método getAll
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();
        if (!productsList.isEmpty()){
            for (ProductModel product: productsList){
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class) .getOneProduct(id)) .withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }




    //uso do métudo getOne, ou seja, ele vai obter apenas de 1 recurso
    @GetMapping("/products/{id}")
    //primeira coisa a ser feita é pesquisar um produto na base para ser utilizado. Se nao existir, enviar para o cliente que nao existe
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);

        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productO.get());
    }


    // uso do PUT (UPDATE) - necessario passar um parâmetro/recurso de onde se quer atualizar
    @PutMapping("/products/{id}")
    //primeira coisa a ser feita é pesquisar o produto na base para ser atualizado. Se nao existir, enviar para o cliente que "nao existe"
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }

        var productModel = productO.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }

    //uso do DELETE
    //necessario passar um recurso que se quer deletar

    @DeleteMapping("/products/{id}")
    //primeira coisa a ser feita é pesquisar o produto na base para ser deletado. Se nao existir, enviar para o cliente que nao existe
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }

        productRepository.delete(productO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado com sucesso");
    }
}


