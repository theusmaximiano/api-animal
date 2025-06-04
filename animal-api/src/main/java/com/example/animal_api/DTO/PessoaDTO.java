package com.example.animal_api.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PessoaDTO {
    private String id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String email;
}