package com.example.animal_api.service;

import com.example.animal_api.DTO.AnimalComPessoaDTO;
import com.example.animal_api.DTO.PessoaDTO;
import com.example.animal_api.model.Animal;
import com.example.animal_api.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private RestTemplate restTemplate; // injeta o Bean criado

    public Animal salvar(Animal animal) {
        return animalRepository.save(animal);
    }

    public List<Animal> listarTodos() {
        return animalRepository.findAll();
    }

    public Optional<Animal> buscarPorId(String id) {
        return animalRepository.findById(id);
    }

    public void deletar(String id) {
        animalRepository.deleteById(id);
    }

    public Animal atualizar(String id, Animal animal) {
        Optional<Animal> existente = animalRepository.findById(id);

        if (existente.isPresent()) {
            Animal existenteAnimal = existente.get();
            existenteAnimal.setNome(animal.getNome());
            existenteAnimal.setEspecie(animal.getEspecie());
            existenteAnimal.setRaca(animal.getRaca());
            existenteAnimal.setIdade(animal.getIdade());

            return animalRepository.save(existenteAnimal);
        } else {
            throw new RuntimeException("Animal não encontrado com id: " + id);
        }
    }

    public Animal associarPessoa(String idAnimal, String idPessoa) {
        Optional<Animal> animalOptional = animalRepository.findById(idAnimal);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            animal.setIdPessoa(idPessoa);
            return animalRepository.save(animal);
        } else {
            throw new RuntimeException("Animal não encontrado com id: " + idAnimal);
        }
    }
    public AnimalComPessoaDTO buscarAnimalComPessoa(String idAnimal) {
        Optional<Animal> optionalAnimal = animalRepository.findById(idAnimal);

        if (optionalAnimal.isPresent()) {
            Animal animal = optionalAnimal.get();

            PessoaDTO pessoaDTO = null;

            if (animal.getIdPessoa() != null && !animal.getIdPessoa().isEmpty()) {
                String url = "http://localhost:8086/api/pessoas/" + animal.getIdPessoa();

                try {
                    pessoaDTO = restTemplate.getForObject(url, PessoaDTO.class); // aqui usamos o RestTemplate injetado
                } catch (Exception e) {
                    System.out.println("Erro ao buscar pessoa: " + e.getMessage());
                }
            }

            return new AnimalComPessoaDTO(animal, pessoaDTO);
        } else {
            throw new RuntimeException("Animal não encontrado com id: " + idAnimal);
        }
    }
}