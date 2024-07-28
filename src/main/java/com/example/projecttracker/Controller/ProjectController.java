package com.example.projecttracker.Controller;


import com.example.projecttracker.Api.ApiResponse;
import com.example.projecttracker.Model.Project;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/project/")

public class ProjectController {

    ArrayList<Project> projects = new ArrayList<Project>();


    @GetMapping("/get")
    public ArrayList<Project> getProjects() {
        return projects;
    }





    @PostMapping("/add")
    public ApiResponse addProject(@RequestBody Project project) {
        Random randa = new Random();
        String randomID;
        boolean idExists;

        do {
            randomID = Integer.toString(randa.nextInt(1000000));
            idExists = false;
            for (Project i : projects) {
                if (i.getID().equals(randomID)) {
                    idExists = true;
                    break;
                }
            }
        } while (idExists);

        project.setID(randomID);
        projects.add(project);

        return new ApiResponse("Project added successfully", "202");
    }


    @PutMapping("/update-id/{id}")
    public ApiResponse updateIdProject(@PathVariable String id, @RequestBody Project project) {
        Project existingProject = null;
        int index = -1;

        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getID().equals(id)) {
                existingProject = projects.get(i);
                index = i;
                break;
            }
        }

        if (existingProject == null) {
            new ApiResponse("ID not found ", "400");
        }

        if (project.getDescription() != null) {
            existingProject.setDescription(project.getDescription());
        }
        if (project.getCompanyName()!= null) {
            existingProject.setCompanyName(project.getCompanyName());
        }

        if (project.getTitle() != null) {
            existingProject.setTitle(project.getTitle());
        }

        if (project.getStatus() != null) {
            existingProject.setStatus(project.getStatus());
        }

        projects.set(index, existingProject);
        return new ApiResponse("Project updated", "202");
    }

    @DeleteMapping ("/delete-id/{id}")
    public ApiResponse deleteProjectID(@PathVariable String id) {

        for (Project project : projects) {
            if (id.equals(project.getID())) {
                projects.remove(project);
                return new ApiResponse("customer deleted", "202");
            }
        }
       return new ApiResponse("customer delete by ID not found", "404");


    }
    @GetMapping("/search-title/{title}")
    public Project searchTitleProject(@PathVariable String title ) {

        for (Project project : projects) {
            if (project.getTitle().equals(title)) {
                return project;

            }
        }

        return null;

    }

    @GetMapping("/search-company-name/{companyName}")
    public ArrayList<Project> searchProject(@PathVariable String companyName ) {

            ArrayList<Project> projectsNew = new ArrayList<>();
        for (Project project : projects) {
            if (project.getCompanyName().equals(companyName)) {
                projectsNew.add(project);
            }
        }
        return projectsNew;

    }

    @PutMapping("/update-id-status/{id}/{status}")
    public ApiResponse updateStatusId(@PathVariable String id , @PathVariable String status) {

        String statusRaw = status.trim();
        int index = -1;

        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getID().equals(id)) {
                index = i;
                break;
            }
        }

        if(index == -1){
            return new ApiResponse("project id not found", "404");
        }

        if (statusRaw.equalsIgnoreCase("done") || statusRaw.equalsIgnoreCase("not done")) {
            projects.get(index).setStatus(statusRaw);
            return new ApiResponse("project status updated", "202");
        }

        return new ApiResponse("project not updated, check your status typing", "404");
    }


}
