package com.mkt.uzaktanelemanapi.service;

import com.mkt.uzaktanelemanapi.daoImplementation.BidDaoIMplementation;
import com.mkt.uzaktanelemanapi.entity.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService implements IBearService<Bid>{

    @Autowired
    private BidDaoIMplementation bidDaoIMplementation;

    @Override
    public List<Bid> getAll() {
        return bidDaoIMplementation.getAll();
    }

    @Override
    public ResponseEntity get(String id) {
        return bidDaoIMplementation.get(id);
    }

    @Override
    public ResponseEntity save(Bid bid) {
        return bidDaoIMplementation.save(bid);
    }

    @Override
    public void delete() {

    }

    public List<Bid> findAllBidByProject(String projectId) {
        return bidDaoIMplementation.findAllBidByProject(projectId);
    }
}
