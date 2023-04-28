package com.example.edubin.controller;

import com.example.edubin.dto.request.EventRequest;
import com.example.edubin.dto.request.OrderEvent;
import com.example.edubin.dto.response.ApiResponse;
import com.example.edubin.enitity.EventEntity;
import com.example.edubin.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event/")
@RequiredArgsConstructor
public class EventsController {

    private final EventService eventService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    private ApiResponse<Void> addEvent(@ModelAttribute EventRequest eventRequest){
        eventService.addEvent(eventRequest);
        return new ApiResponse<>("event was saved successfully in database");
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    private ApiResponse<Void> deleteEvent(@PathVariable("id") int id){
        eventService.deleteEvent(id);
        return new ApiResponse<>("event was deleted successfully in database");
    }
    @PutMapping("/update/{id}")// event id
    @ResponseStatus(HttpStatus.OK)
    private ApiResponse<Void> updateEvent(@PathVariable("id") int id, @ModelAttribute EventRequest eventRequest){
        eventService.updateEvent(id,eventRequest);
        return new ApiResponse<>("event was updated successfully in database");
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    private ApiResponse<EventEntity> getEventById(@PathVariable("id") int id){
        EventEntity eventById = eventService.getEventById(id);
        return new ApiResponse<>("event was token successfully in database",eventById);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    private ApiResponse<List<EventEntity>> getListOfEvent(){
       return new ApiResponse<>("event List was token successfully in database",eventService.getList());
    }

}
