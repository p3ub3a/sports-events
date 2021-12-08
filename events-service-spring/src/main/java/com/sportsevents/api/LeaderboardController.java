package com.sportsevents.api;

import java.util.List;
import java.util.Optional;

import com.sportsevents.api.model.LeaderboardEntryModel;
import com.sportsevents.service.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.annotation.Timed;

@RequestMapping("leaderboard")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LeaderboardController {
    @Autowired
    EventService eventService;

    @GetMapping()
    @Timed(value="get_leaderboard.request", histogram=true)
    public Optional<List<LeaderboardEntryModel>> getLeaderboard(){
        return eventService.getLeaderboard();
    }
}
