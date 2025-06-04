package com.example.animal_api.DTO;

import com.example.animal_api.model.Animal;
import com.example.animal_api.DTO.PessoaDTO; // vamos criar também!

// DTO para representar um Animal junto com os dados da Pessoa associada
public class AnimalComPessoaDTO {

    private String id;
    private String nome;
    private String especie;
    private String raca;
    private Integer idade;

    private PessoaDTO pessoa; // dados da pessoa associada

    public AnimalComPessoaDTO() {}

    // Construtor que recebe um Animal e uma PessoaDTO para construir o DTO combinado
    public AnimalComPessoaDTO(Animal animal, PessoaDTO pessoa) {
        this.id = animal.getId();
        this.nome = animal.getNome();
        this.especie = animal.getEspecie();
        this.raca = animal.getRaca();
        this.idade = animal.getIdade();
        this.pessoa = pessoa;
    }

    // Getters e setters para todos os campos
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    // getters e setters
}