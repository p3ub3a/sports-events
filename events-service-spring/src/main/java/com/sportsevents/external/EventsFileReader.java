package com.sportsevents.external;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportsevents.entity.Event;
import com.sportsevents.entity.EventsRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.micrometer.core.annotation.Timed;

@Component
public class EventsFileReader {

    private static final Logger logger = LoggerFactory.getLogger(EventsFileReader.class);

    @Autowired
    private EventsRepo eventsRepo;
    
    // cron = ss mm hh dd mm dayOfWeek
    @Scheduled(cron="0 30 10 * * ?") //every day at 10 30
    // @Scheduled(cron="0 * * ? * *") // every minute
    @Transactional
    @Timed(value="parse.external.file.job", histogram=true)
    void parseExternalFile() {
        logger.info("Getting data from external service: {}");

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            List<ExternalEvent.Root> events = Arrays.asList(mapper.readValue(Paths.get("E:\\diseratie\\sports-events\\new-data\\data.json").toFile(), ExternalEvent.Root[].class));

            for(ExternalEvent.Root rootEvent: events){
                ExternalEvent externalEvent = rootEvent.event;
                Optional<Event> optEvent = eventsRepo.findById(externalEvent.getId());
                Event event;
                if(optEvent.isPresent()){
                    event = ExternalEventMapper.updateEvent(optEvent.get(), externalEvent);
                } else{
                    event = ExternalEventMapper.mapExternalEventToEvent(externalEvent);
                }
                logger.info("Persisting event: {}", event.toString());
                eventsRepo.save(event);
            }
        
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
