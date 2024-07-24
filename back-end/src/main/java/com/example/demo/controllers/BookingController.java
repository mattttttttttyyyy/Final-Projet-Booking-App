package com.example.demo.controllers;

import com.example.demo.entities.BookingEntity;
import com.example.demo.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:4200")

public class BookingController {
    @Autowired
    BookingService service;

    @PostMapping("/add/{room_id}")
    public ResponseEntity<BookingEntity> addBooking(@RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final BookingEntity bookingEntity, @PathVariable final long room_id) {
        return new ResponseEntity<>(service.addBooking(bookingEntity, room_id), HttpStatus.OK);
    }


    @GetMapping("/all")
    public List<BookingEntity> getAllBookings() {
        return service.getAllBookings();
    }

    @GetMapping("/byRoom/{room_id}")
    public List<BookingEntity> getBookingsByRoom(@PathVariable final long room_id) {
        return service.getBookingsByRoom(room_id);
    }

    @GetMapping("/availableToday/{room_id}")
    public List<BookingEntity> getAvailableToday(@PathVariable final long room_id, @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final Timestamp timestamp) {
        return service.getAvailableToday(room_id, timestamp);
    }

    @DeleteMapping("/delete/{unique_id}")
    public ResponseEntity<String> deleteBooking(@PathVariable final String unique_id) {
        service.deleteBooking(unique_id);
        return new ResponseEntity<>("Booking deleted", HttpStatus.OK);
    }

    @GetMapping("/byUniqueID/{unique_id}")
    public BookingEntity getBookingByUniqueID(@PathVariable final String unique_id) {
        return service.getBookingByUniqueID(unique_id);
    }

    @GetMapping("/byDateAndRoom")
    public List<BookingEntity> getBookingsByDateAndRoom(@RequestParam("date") final String date, @RequestParam("roomId") final long roomId) {
        return service.getBookingsByDateAndRoom(date, roomId);
    }

    @GetMapping("/roomID/{unique_id}")
    public long getRoomIDByUniqueID(@PathVariable final String unique_id) {
        return service.getRoomIDByUniqueID(unique_id);
    }


    @PatchMapping("/update/{unique_id}")
    public ResponseEntity<BookingEntity> updateBooking(@PathVariable final String unique_id, @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final BookingEntity bookingEntity) {
        service.updateBooking(unique_id, bookingEntity);
        return new ResponseEntity<>((bookingEntity), HttpStatus.OK);
    }

    @DeleteMapping("/deleteByID/{id}")
    public void deleteBookingByID(@PathVariable final long id) {
        service.deleteBookingById(id);
    }

}
