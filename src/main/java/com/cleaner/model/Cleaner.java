package com.cleaner.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "cleaners")
public class Cleaner {
    @Id
    private String id;  
    private String fullname;
    private String email;
    private String phone;
    private Gender gender; 
    private String address;
    private Long nationalId;
    private String dateOfBirth;
    private Role role = Role.CLEANER;
    private Status status = Status.Active;
    
    
    
}
