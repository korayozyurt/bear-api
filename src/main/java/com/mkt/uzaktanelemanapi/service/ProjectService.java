package com.mkt.uzaktanelemanapi.service;

import com.mkt.uzaktanelemanapi.daoImplementation.ProjectDaoImplementation;
import com.mkt.uzaktanelemanapi.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService implements IBearService<Project> {

    @Autowired
    private ProjectDaoImplementation projectDaoImplementation;

    public ResponseEntity addProject(Project project){
        return projectDaoImplementation.save(project);
    }

    @Override
    public List<Project> getAll() {
        return (List<Project>) projectDaoImplementation.getAll();
    }

    @Override
    public ResponseEntity get(String id) {
        return projectDaoImplementation.get(id);
    }

    @Override
    public ResponseEntity<Project> save(Project project) {
        return null;
    }

    @Override
    public void delete() {

    }

    public Project getEntity(String id) {
        return projectDaoImplementation.getEntity(id);
    }

    // Melisa
    public boolean tcNumberValidation(){
        int number1 = 2;
        int number2 = 4;
        int number3 = 8;
        int number4 = 3;
        int number5 = 2;
        int number6 = 2;
        int number7 = 1;
        int number8 = 8;
        int number9 = 2;
        int number10 = 8;
        int number11 = 0;

        int numb10 = ((((number1 + number3 + number5 + number7 + number9) * 7) - (number2 + number4 + number6 + number8)) % 10);

        int numb11 = ((number1+number2+number3+number4+number5+number6+number7+number8+number9+number10) % 10);

        return numb10 == number10 && numb11 == number11;
    }
}
