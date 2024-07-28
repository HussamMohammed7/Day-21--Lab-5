package com.example.students.Controller;


import com.example.students.Api.ApiResponse;
import com.example.students.Model.Students;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/students/")


public class StudentsController {

    ArrayList<Students> students = new ArrayList<Students>();


    @PostMapping("/add")
    public ApiResponse addStudent(@RequestBody Students student) {
        Random randa = new Random();
        String randomID;
        boolean idExists;

        do {
            randomID = Integer.toString(randa.nextInt(1000000));
            idExists = false;
            for (Students i : students) {
                if (i.getId().equals(randomID)) {
                    idExists = true;
                    break;
                }
            }
        } while (idExists);

        student.setId(randomID);
        students.add(student);

       return new ApiResponse("Student added successfully", "202");
    }


    @PutMapping("/update-id/{id}")
    public ApiResponse updateIdStudent(@PathVariable String id, @RequestBody Students student) {
        Students existingStudent = null;
        int index = -1;

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                existingStudent = students.get(i);
                index = i;
                break;
            }
        }

        if (existingStudent == null) {
            return new ApiResponse("ID not found ", "400");
        }

        if (student.getAge() != 0) {
            existingStudent.setAge(student.getAge());
        }
        if (student.getName() != null) {
            existingStudent.setName(student.getName());
        }

        if (student.getStudy() != null) {
            existingStudent.setStudy(student.getStudy());
        }

        if (student.getCollege() != null) {
            existingStudent.setCollege(student.getCollege());
        }

        students.set(index, existingStudent);
        return new ApiResponse("customer updated", "202");
    }
    @DeleteMapping ("/delete-id/{id}")
    public ApiResponse deleteCustomersID(@PathVariable String id) {

        for (Students student : students) {
            if (id.equals(student.getId())) {
                students.remove(student);
                new ApiResponse("customer deleted", "202");
            }
        }
        return new ApiResponse("customer delete by ID not found", "404");

    }




    @GetMapping("/get")
    public ArrayList<Students> getStudents() {
        return students;
    }



    @GetMapping("/name/{index}")
    public ApiResponse getName(@PathVariable int index) {
        if (index < 0 || index >= students.size()) {
            return new ApiResponse("Index out of bounds, 400", "404");
        }

       return new ApiResponse("The name is : "+students.get(index).getName(), "200");
    }

    @GetMapping("/age/{index}")
    public ApiResponse getAge (@PathVariable int index) {
        if (index < 0 || index >= students.size()) {
            return new ApiResponse("Index out of bounds, 400", "404");
        }
        return new ApiResponse("The age is : "+Integer.toString(students.get(index).getAge()), "404");
    }

    @GetMapping("/college/degree/{index}")
    public ApiResponse getCollegeDegree(@PathVariable int index) {
        if (index < 0 || index >= students.size()) {
            return new ApiResponse("Index out of bounds, 400", "404");
        }
        
        if (students.get(index).getDegree().equalsIgnoreCase("bachelor")) {

            return new ApiResponse("the study degree is bachelor", "200");
            
        } else if (students.get(index).getDegree().equalsIgnoreCase("diploma")) {
            return new ApiResponse("the study degree is diploma", "200");
            
        } else if (students.get(index).getDegree().equalsIgnoreCase("master")) {
            return new ApiResponse("the study degree is master", "200");

        }
        return new ApiResponse("the degree is null", "404");

    }

    @GetMapping("/study/status/{index}")
    public ApiResponse  getDegree(@PathVariable int index) {
        boolean statusOfStudy ;
        if (index < 0 || index >= students.size()) {
            return new ApiResponse("Index out of bounds, 400", "404");
        }
        
        if (students.get(index).getStudy().equalsIgnoreCase("graduated")) {
            statusOfStudy = true;
            return new ApiResponse(Boolean.toString(statusOfStudy), "202");

        }
        if (students.get(index).getStudy().equalsIgnoreCase("ungraduated")) {
            statusOfStudy = false;
            return new ApiResponse(Boolean.toString(statusOfStudy), "202");
        }

        return new ApiResponse("the status is not as we excepted ", "404");

    }





}
