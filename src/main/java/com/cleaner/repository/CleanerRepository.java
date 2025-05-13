package com.cleaner.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.cleaner.model.Cleaner;
import java.util.Optional;

@Repository
public interface CleanerRepository extends MongoRepository<Cleaner, String> {
    Optional<Cleaner> findByEmail(String email);
}
