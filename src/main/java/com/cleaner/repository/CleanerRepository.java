package com.cleaner.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.cleaner.model.Clearner;
import java.util.Optional;

@Repository
public interface CleanerRepository extends MongoRepository<Clearner, String> {
    Optional<Clearner> findByEmail(String email);
}
