package com.example.animal_api.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "animais")
public class Animal {

    @Id
    private String id;

    private String nome;
    private String especie;
    private String raca;
    private Integer idade;

    private String idPessoa; // relação com a pessoa (tutor, responsável, etc.)
}