package com.example.animal_api.repository;

import com.example.animal_api.model.Animal;
import org.springframework.data.mongodb.repository.MongoRepository;

// Repositório MongoDB para a entidade Animal.
// Extende MongoRepository, que já fornece métodos prontos para CRUD.
// Pode ser estendido futuramente com consultas customizadas se necessário.

public interface AnimalRepository extends MongoRepository<Animal, String> {
}
