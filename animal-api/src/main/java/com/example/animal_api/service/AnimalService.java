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
    private RestTemplate restTemplate; // Usado para fazer requisições HTTP para outras APIs (no caso, a API de Pessoas)

    // Salva um novo animal no banco de dados
    public Animal salvar(Animal animal) {
        return animalRepository.save(animal);
    }

    // Retorna a lista de todos os animais cadastrados
    public List<Animal> listarTodos() {
        return animalRepository.findAll();
    }

    // Busca um animal pelo seu ID
    public Optional<Animal> buscarPorId(String id) {
        return animalRepository.findById(id);
    }

    // Deleta um animal pelo ID
    public void deletar(String id) {
        animalRepository.deleteById(id);
    }

    // Atualiza os dados de um animal existente
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
            // Caso o animal não exista, lança exceção para ser tratada pelo controller ou globalmente
            throw new RuntimeException("Animal não encontrado com id: " + id);
        }
    }

    // Associa um animal a uma pessoa (tutor) pelo ID da pessoa
    public Animal associarPessoa(String idAnimal, String idPessoa) {
        Optional<Animal> animalOptional = animalRepository.findById(idAnimal);

        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            animal.setIdPessoa(idPessoa); // Define o ID da pessoa associada
            return animalRepository.save(animal);
        } else {
            throw new RuntimeException("Animal não encontrado com id: " + idAnimal);
        }
    }

    // Busca um animal junto com os dados da pessoa associada via API externa
    public AnimalComPessoaDTO buscarAnimalComPessoa(String idAnimal) {
        Optional<Animal> optionalAnimal = animalRepository.findById(idAnimal);

        if (optionalAnimal.isPresent()) {
            Animal animal = optionalAnimal.get();

            PessoaDTO pessoaDTO = null;

            // Se o animal possui uma pessoa associada, tenta buscar seus dados via API REST
            if (animal.getIdPessoa() != null && !animal.getIdPessoa().isEmpty()) {
                String url = "http://localhost:8086/api/pessoas/" + animal.getIdPessoa();

                try {
                    // Chamada HTTP GET para obter dados da pessoa via RestTemplate
                    pessoaDTO = restTemplate.getForObject(url, PessoaDTO.class);
                } catch (Exception e) {
                    System.out.println("Erro ao buscar pessoa: " + e.getMessage());
                    // Poderia também lançar exceção ou retornar algum objeto de erro customizado
                }
            }

            // Retorna um DTO combinando dados do animal e da pessoa (pode ser nulo)
            return new AnimalComPessoaDTO(animal, pessoaDTO);
        } else {
            throw new RuntimeException("Animal não encontrado com id: " + idAnimal);
        }
    }
    public void testeBuscarPessoa() {
        String idPessoa = "6839182c9f88457a451101da";  // use um ID válido que você sabe que existe
        String url = "http://localhost:8086/api/pessoas/" + idPessoa;

        try {
            PessoaDTO pessoaDTO = restTemplate.getForObject(url, PessoaDTO.class);
            System.out.println("Pessoa encontrada: " + pessoaDTO.getNome() + ", email: " + pessoaDTO.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}