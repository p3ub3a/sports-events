package com.sportsevents.entity;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventsRepo extends CrudRepository<Event, Long>{
    List<Event> findByType(String type);

    @Modifying
    @Query("update Event e set e.winner = :winner, e.closedBy = :closedBy, e.status = :status, e.closedDate = :closedDate where e.id = :id")
    int closeEvent(@Param("winner") String winner, 
        @Param("closedBy") String closedBy, 
        @Param("status") String status,
        @Param("closedDate") LocalDateTime closedDate,
        @Param("id") Long id);
    
    @Modifying
    @Query("update Event e set e.players = :players where e.id = :id")
    int updatePlayers(@Param("players") String[] players,
        @Param("id") Long id);

    @Query("select e.winner, count(e.winner) as wins from Event e where status = 'CLOSED' group by e.winner order by 2 desc")
    List<Object[]> getLeaderboard(Pageable pageable);
}

