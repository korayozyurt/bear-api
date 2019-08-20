package com.mkt.uzaktanelemanapi.controller;

import com.mkt.uzaktanelemanapi.entity.Project;
import com.mkt.uzaktanelemanapi.service.ProjectService;
import com.mkt.uzaktanelemanapi.tools.BEAR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BEAR.version + "/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Project> getProjectList(){
        return projectService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{projectId}")
    public ResponseEntity getProjectById(@PathVariable("projectId") String projectId){
        return projectService.get(projectId);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addProject(@RequestBody Project project){
        return projectService.addProject(project);
    }

}
