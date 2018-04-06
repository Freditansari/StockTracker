package com.fredy.repository;

import com.fredy.domain.StockList;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StockList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockListRepository extends JpaRepository<StockList, Long> {

}
