package com.example.animal_api.controller;

import com.example.animal_api.DTO.AnimalComPessoaDTO;
import com.example.animal_api.model.Animal;
import com.example.animal_api.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controlador REST que expõe endpoints para gerenciar animais
// Os endpoints permitem operações CRUD e consulta avançada para buscar animal com pessoa associada
@RestController
@RequestMapping("/api/animais")
@CrossOrigin(origins = "*") // Permite chamadas de qualquer origem (para testes / front-ends diversos)
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    // Endpoint para listar todos os animais
    @GetMapping
    public ResponseEntity<List<Animal>> listarTodos() {
        List<Animal> animais = animalService.listarTodos();
        return ResponseEntity.ok(animais);
    }

    // Endpoint para buscar animal pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Animal> buscarPorId(@PathVariable String id) {
        return animalService.buscarPorId(id)
                .map(ResponseEntity::ok) // Se encontrar, retorna 200 com o animal
                .orElse(ResponseEntity.notFound().build()); // Se não, retorna 404
    }

    // Endpoint para cadastrar um novo animal
    @PostMapping
    public ResponseEntity<Animal> cadastrar(@RequestBody Animal animal) {
        Animal salvo = animalService.salvar(animal);
        return ResponseEntity.ok(salvo); // Retorna o animal salvo
    }

    // Endpoint para atualizar um animal pelo ID
    @PutMapping("/{id}")
    public ResponseEntity<Animal> atualizar(@PathVariable String id, @RequestBody Animal animal) {
        try {
            Animal atualizado = animalService.atualizar(id, animal);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para deletar um animal pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        animalService.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna 204 sem conteúdo após deletar
    }

    // Endpoint para associar uma pessoa a um animal (atualização parcial)
    @PatchMapping("/{idAnimal}/associar-pessoa/{idPessoa}")
    public ResponseEntity<Animal> associarPessoa(
            @PathVariable String idAnimal,
            @PathVariable String idPessoa) {
        try {
            Animal atualizado = animalService.associarPessoa(idAnimal, idPessoa);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para buscar animal junto com a pessoa associada (via outra API)
    @GetMapping("/{id}/com-pessoa")
    public ResponseEntity<AnimalComPessoaDTO> buscarAnimalComPessoa(@PathVariable String id) {
        try {
            AnimalComPessoaDTO dto = animalService.buscarAnimalComPessoa(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint de teste para verificar a comunicação com API Pessoa (log no console)
    @GetMapping("/teste-pessoa")
    public ResponseEntity<String> testePessoa() {
        try {
            animalService.testeBuscarPessoa();
            return ResponseEntity.ok("Teste executado, veja o console");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro: " + e.getMessage());
        }
    }
}