package com.mkt.uzaktanelemanapi.controller;

import com.mkt.uzaktanelemanapi.entity.Bid;
import com.mkt.uzaktanelemanapi.entity.Project;
import com.mkt.uzaktanelemanapi.entity.BearUser;
import com.mkt.uzaktanelemanapi.service.BidService;
import com.mkt.uzaktanelemanapi.service.ProjectService;
import com.mkt.uzaktanelemanapi.service.UserService;
import com.mkt.uzaktanelemanapi.tools.BEAR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BEAR.version + "/bid")
public class BidController {

    @Autowired
    private BidService bidService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Bid> getBidList() {
        return bidService.getAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{bidId}")
    public ResponseEntity getBidById(@PathVariable("bidId") String id) {
        return bidService.get(id);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            value = "/{projectId}/{userId}")
    public ResponseEntity saveBid(@RequestBody Bid bid, @PathVariable("projectId") String projectId, @PathVariable("userId") String userId) {
        Project project =  projectService.getEntity(projectId);
        if(project == null) {
            return BEAR.notAcceptableErrorResponse("proje bulunamadı");
        }
        BearUser bearUser = userService.getUser(userId);
        if(bearUser == null) {
            return BEAR.notAcceptableErrorResponse("Kullanıcı bulunamadı");
        }
        bid.setProject(project);
        bid.setBearUser(bearUser);
        return bidService.save(bid);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/project/{projectId}")
    public ResponseEntity findAllBidByProject(@PathVariable("projectId") String projectId) {
        return new ResponseEntity(bidService.findAllBidByProject(projectId), HttpStatus.OK);
    }

}





















































