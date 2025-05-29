package com.example.animal_api.controller;

import com.example.animal_api.DTO.AnimalComPessoaDTO;
import com.example.animal_api.model.Animal;
import com.example.animal_api.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/animais")
public class AnimalController {

    @Autowired
    private AnimalService service;

    @PostMapping
    public ResponseEntity<Animal> salvar(@RequestBody Animal animal) {
        return ResponseEntity.ok(service.salvar(animal));
    }

    @GetMapping
    public ResponseEntity<List<Animal>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/com-pessoa")
    public ResponseEntity<AnimalComPessoaDTO> buscarAnimalComPessoa(@PathVariable String id) {
        AnimalComPessoaDTO dto = service.buscarAnimalComPessoa(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> atualizar(@PathVariable String id, @RequestBody Animal animal) {
        return ResponseEntity.ok(service.atualizar(id, animal));
    }

    @PatchMapping("/{id}/associar-pessoa/{idPessoa}")
    public ResponseEntity<Animal> associarPessoaAoAnimal(@PathVariable String id, @PathVariable String idPessoa) {
        return ResponseEntity.ok(service.associarPessoa(id, idPessoa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
