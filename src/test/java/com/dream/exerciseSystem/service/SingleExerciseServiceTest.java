package com.dream.exerciseSystem.service;

import com.dream.exerciseSystem.service.impl.SingleExerciseService;
import com.dream.exerciseSystem.utils.DataWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
public class SingleExerciseServiceTest {
    @Autowired
    SingleExerciseService singleExerciseService;

    @Test
    void TestGetExerciseById() {
        DataWrapper targetExercise = singleExerciseService.getExerciseById("1370052298");
        System.out.println(targetExercise);
    }

    @Test
    void TestGetExerciseAllBasicInfo() {
        DataWrapper exerciseBasicInfoList = singleExerciseService.getExerciseAllBasicInfo();
        System.out.println(exerciseBasicInfoList);
    }

    @Test
    void TestGetExerciseAll() {
        DataWrapper targetExercises = singleExerciseService.getExerciseAll();
        System.out.println(targetExercises);
    }
}
