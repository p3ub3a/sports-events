package com.sportsevents.external;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sportsevents.entity.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.annotation.Timed;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;

@ApplicationScoped
public class EventsFileReader {

    private static final Logger logger = LoggerFactory.getLogger(EventsFileReader.class);
    
    // cron = ss mm hh dd mm dayOfWeek
    @Scheduled(cron="0 30 10 * * ?") //every day at 10 30
    // @Scheduled(cron="0 * * ? * *") // every minute
    @Transactional
    @Timed(value="parse.external.file.job", histogram=true)
    void parseExternalFile(ScheduledExecution execution) {
        logger.info("Getting data from external service: {}", execution.getScheduledFireTime());

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            List<ExternalEvent.Root> events = Arrays.asList(mapper.readValue(Paths.get("E:\\diseratie\\sports-events\\new-data\\data.json").toFile(), ExternalEvent.Root[].class));

            for(ExternalEvent.Root rootEvent: events){
                ExternalEvent externalEvent = rootEvent.event;
                Event event = Event.findById(externalEvent.getId());
                
                if(event != null){
                    event = ExternalEventMapper.updateEvent(event, externalEvent);
                } else{
                    event = ExternalEventMapper.mapExternalEventToEvent(externalEvent);
                }
                logger.info("Persisting event: {}", event.toString());
                event.persist();
            }
        
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
