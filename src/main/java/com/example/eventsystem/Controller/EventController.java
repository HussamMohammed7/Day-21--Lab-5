package com.example.eventsystem.Controller;


import com.example.eventsystem.Api.ApiResponse;
import com.example.eventsystem.Model.Event;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/events")

public class EventController {


    private ArrayList<Event> events = new ArrayList<>();

    @PostMapping("/add")
    public ApiResponse addEvent(@RequestBody Event event) {
        Random random = new Random();
        String randomID;
        boolean idExists;

        do {
            randomID = Integer.toString(random.nextInt(1000000));
            idExists = false;
            for (Event e : events) {
                if (e.getId().equals(randomID)) {
                    idExists = true;
                    break;
                }
            }
        } while (idExists);

        event.setId(randomID);

        events.add(event);

        return new ApiResponse("Event added successfully", "202");
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateEvent(@PathVariable String id, @RequestBody Event event) {
        Event existingEvent = null;
        int index = -1;

        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getId().equals(id)) {
                existingEvent = events.get(i);
                index = i;
                break;
            }
        }

        if (existingEvent == null) {
            return new ApiResponse("Event ID not found", "400");
        }

        if (event.getDescription() != null) {
            existingEvent.setDescription(event.getDescription());
        }
        if (event.getCapacity() != 0) {
            existingEvent.setCapacity(event.getCapacity());
        }
        if (event.getStartDate() != null) {
            existingEvent.setStartDate(event.getStartDate());
        }
        if (event.getEndDate() != null) {
            existingEvent.setEndDate(event.getEndDate());
        }

        events.set(index, existingEvent);
        return new ApiResponse("Event updated", "202");
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteEvent(@PathVariable String id) {
        for (Event event : events) {
            if (event.getId().equals(id)) {
                events.remove(event);
                return new ApiResponse("Event deleted", "202");
            }
        }
        return new ApiResponse("Event ID not found", "404");
    }

    @GetMapping("/get")
    public ArrayList<Event> getEvents() {
        return events;
    }

    @GetMapping("/search/{id}")
    public Event searchEventById(@PathVariable String id) {
        for (Event event : events) {
            if (event.getId().equals(id)) {
                return event;
            }
        }
        return null;
    }

    @PutMapping("/update/capacity/{id}/{capacity}")
    public ApiResponse updateEventCapacity(@PathVariable String id, @PathVariable int capacity) {
        for (Event event : events) {
            if (event.getId().equals(id)) {
                event.setCapacity(capacity);
                return new ApiResponse("Event capacity updated", "202");
            }
        }
        return new ApiResponse("Event ID not found", "404");
    }



}
