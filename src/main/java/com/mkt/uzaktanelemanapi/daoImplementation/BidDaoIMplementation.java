package com.mkt.uzaktanelemanapi.daoImplementation;

import com.mkt.uzaktanelemanapi.dao.IDao;
import com.mkt.uzaktanelemanapi.entity.Bid;
import com.mkt.uzaktanelemanapi.repository.BidRepository;
import com.mkt.uzaktanelemanapi.tools.BEAR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BidDaoIMplementation implements IDao<Bid> {

    @Autowired
    private BidRepository bidRepository;

    @Override
    public ResponseEntity get(String id) {
        Bid bid = bidRepository.findById(id).orElse(null);
        if(bid == null) {
            return BEAR.notAcceptableErrorResponse("bid cannot found");
        }
        return new ResponseEntity<Bid>(bid, HttpStatus.OK);
    }

    @Override
    public List<Bid> getAll() {
        return bidRepository.findAll();
    }

    @Override
    public ResponseEntity<Bid> save(Bid bid) {
        Bid b = bidRepository.save(bid);
        if(b != null) {
            return new ResponseEntity<>(b, HttpStatus.CREATED);
        }
        return BEAR.notAcceptableErrorResponse("bid cannot be saved");
    }

    @Override
    public void delete(Bid bid) {
        bidRepository.delete(bid);
    }


    public List<Bid> findAllBidByProject(String projectId) {
        return bidRepository.findAllBidByProjectId(projectId);
    }
}
