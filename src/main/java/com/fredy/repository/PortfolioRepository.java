package com.fredy.repository;

import com.fredy.domain.Portfolio;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Portfolio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Query("select portfolio from Portfolio portfolio where portfolio.user.login = ?#{principal.username}")
    List<Portfolio> findByUserIsCurrentUser();

}
