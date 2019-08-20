package com.mkt.uzaktanelemanapi.daoImplementation;

import com.mkt.uzaktanelemanapi.dao.IDao;
import com.mkt.uzaktanelemanapi.entity.Project;
import com.mkt.uzaktanelemanapi.repository.ProjectRepository;
import com.mkt.uzaktanelemanapi.tools.BEAR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ProjectDaoImplementation implements IDao<Project> {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ResponseEntity get(String id) {
        Project project = projectRepository.findById(id).orElse(null);
        if(project == null) {
           return BEAR.notAcceptableErrorResponse("project cannot found");
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", project.getId());
        resultMap.put("projectOwner", project.getProjectOwner());
        resultMap.put("projectCategory", project.getProjectCategory());
        resultMap.put("imagePath", project.getImagePath());
        resultMap.put("smallDesctiption", project.getSmallDescription());
        resultMap.put("description", project.getDescription());
        resultMap.put("date", project.getDate());
        return BEAR.bearJSON(resultMap);
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public ResponseEntity save(Project project) {
        Project p = projectRepository.save(project);
        if( p != null) {
            return new ResponseEntity<Project>(p,HttpStatus.CREATED);
        }
        return BEAR.notAcceptableErrorResponse("Project cannot be saved");
    }

    @Override
    public void delete(Project project) {

    }

    public Project getEntity(String id) {
        return projectRepository.findById(id).orElse(null);
    }
}
