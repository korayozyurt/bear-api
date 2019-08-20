package com.mkt.uzaktanelemanapi.repository;

import com.mkt.uzaktanelemanapi.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, String> {

    @Query(value = "SELECT * FROM Bid b WHERE b.project_id = ?1", nativeQuery = true)
    List<Bid> findAllBidByProjectId(String projectId);

}
