package com.example.demo.controllers;

import com.example.demo.entities.ConferenceRoomEntity;
import com.example.demo.services.ConferenceRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/conferenceRoom")
@CrossOrigin(origins = "http://localhost:4200")

public class ConferenceRoomController {
    @Autowired
    ConferenceRoomService service;

    @PostMapping("/add")
    public void addConferenceRoom(@RequestBody final ConferenceRoomEntity conferenceRoomEntity, @RequestParam(name = "corporate_id") final long corporate_id) {
        service.createRoom(conferenceRoomEntity, corporate_id);
    }

    @GetMapping("/all")
    public List<ConferenceRoomEntity> getAllRooms() {
        return service.getAllRooms();
    }

    @GetMapping("/byCorpoId/{corporate_id}")
    public List<ConferenceRoomEntity> getRoomsByCorpoId(@PathVariable final long corporate_id) {
        return service.getRoomsByCorpoId(corporate_id);
    }

    @DeleteMapping("/delete/{room_id}")
    public void deleteRoom(@PathVariable final long room_id) {
        service.deleteRoom(room_id);
    }

    @PatchMapping("/update/{room_id}")
    public void updateRoom(@PathVariable final long room_id, @RequestBody final ConferenceRoomEntity conferenceRoomEntity) {
        service.updateRoom(room_id, conferenceRoomEntity);
    }


}
