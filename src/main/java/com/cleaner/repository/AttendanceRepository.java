package com.cleaner.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cleaner.model.Attendance;

public interface AttendanceRepository extends MongoRepository<Attendance,String>{
   
}
