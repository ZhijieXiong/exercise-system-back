package com.dream.exerciseSystem.controller;

import com.dream.exerciseSystem.domain.exercise.ExerciseBasicInfo;
import com.dream.exerciseSystem.domain.exercise.SingleChoiceExercise;
import com.dream.exerciseSystem.service.IJavaProgramExerciseService;
import com.dream.exerciseSystem.service.ISingleChoiceExerciseService;
import com.dream.exerciseSystem.utils.DataWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/exercises")
public class ExerciseController {
    @Autowired
    private IJavaProgramExerciseService javaProgramExerciseService;

    @Autowired
    private ISingleChoiceExerciseService singleChoiceExerciseService;

    @GetMapping("/type")
    @ResponseBody
    public DataWrapper getJavaExerciseType() {
        HashMap<String, List<String>> data = new HashMap<>();
        List<String> types = new ArrayList<>();
        types.add("JAVA_PROGRAM_EXERCISE");
        types.add("SINGLE_CHOICE_EXERCISE");
        data.put("typeList", types);
        return new DataWrapper(true).msgBuilder("请求习题类型成功").dataBuilder(data);
    }

    @GetMapping("/java/program/all")
    @ResponseBody
    public DataWrapper getJavaProgramExerciseAll() {
        return javaProgramExerciseService.getExerciseAll();
    }

    @GetMapping("/java/program/one/{exerciseId}")
    @ResponseBody
    public DataWrapper getJavaProgramExerciseOneById(@PathVariable String exerciseId) {
        return javaProgramExerciseService.getExerciseById(exerciseId);
    }

    @GetMapping("/java/singleChoice/all")
    @ResponseBody
    public DataWrapper getJavaSingleChoiceExerciseAll() {
        return singleChoiceExerciseService.getExerciseAll();
    }

    @GetMapping("/java/singleChoice/one/{exerciseId}")
    @ResponseBody
    public DataWrapper getJavaSingleChoiceExerciseOneById(@PathVariable String exerciseId) {
        return singleChoiceExerciseService.getExerciseById(exerciseId);
    }

    @PostMapping("/java/program/check/{exerciseId}")
    @ResponseBody
    public DataWrapper checkJavaProgramExercise(@PathVariable String exerciseId, @RequestBody Map<String, String> requestBody) throws Exception {
        String submissionCode = "package org.dream.solution;\n" + requestBody.get("submissionCode");
        return javaProgramExerciseService.checkExercise(exerciseId, submissionCode);
    }

    @GetMapping("/java/basicInfo/all")
    @ResponseBody
    public DataWrapper getJavaExerciseBasicInfoAll() {
        DataWrapper singleChoiceExerciseBasicInfo = singleChoiceExerciseService.getExerciseAllBasicInfo();
        DataWrapper programExerciseBasicInfo = javaProgramExerciseService.getExerciseAllBasicInfo();
        if (!singleChoiceExerciseBasicInfo.isFlag() && !programExerciseBasicInfo.isFlag()) {
            return new DataWrapper(false).msgBuilder("请求失败");
        } else {
            Map<String, List<ExerciseBasicInfo>> data = new HashMap<>();
            List<ExerciseBasicInfo> exerciseBasicInfoList = new ArrayList<>();
            HashMap<String, Object> singleChoiceExerciseBasicInfoData = (HashMap<String, Object>) singleChoiceExerciseBasicInfo.getData();
            HashMap<String, Object> programExerciseBasicInfoData = (HashMap<String, Object>) programExerciseBasicInfo.getData();
            exerciseBasicInfoList.addAll((List<ExerciseBasicInfo>) singleChoiceExerciseBasicInfoData.get("exerciseBasicInfoList"));
            exerciseBasicInfoList.addAll((List<ExerciseBasicInfo>) programExerciseBasicInfoData.get("exerciseBasicInfoList"));
            data.put("exerciseBasicInfoList", exerciseBasicInfoList);
            return new DataWrapper(true).msgBuilder("请求成功").dataBuilder(data);
        }
    }
}
