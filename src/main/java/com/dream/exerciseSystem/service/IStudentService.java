package com.dream.exerciseSystem.service;

import com.dream.exerciseSystem.domain.Student;
import com.dream.exerciseSystem.service.exception.NonexistentUserException;
import com.dream.exerciseSystem.service.exception.PasswordErrorException;
import com.dream.exerciseSystem.utils.DataWrapper;

public interface IStudentService {
    DataWrapper login(String email, String password);
    DataWrapper register(Student student);
}
