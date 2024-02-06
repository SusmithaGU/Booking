package com.booking.recruitment.hotel.controller;

import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.service.impl.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class BookingController {
    private final BookingService bs;

    public BookingController(BookingService bs) {
        this.bs = bs;
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<Object> get(@PathVariable String id){
        Hotel hotel =bs.getHotelBookingService(id);
        if(hotel!=null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    bs.getHotelBookingService(id));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hotel with that id does not exist");
        }
    }

    @DeleteMapping("/hotel/{id}")
    public ResponseEntity<Hotel> delete(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(
                bs.DeleteHotelBookingService(id));
    }

    @GetMapping("/search/{cityId}")
    public ResponseEntity<List<Hotel>> getNearestHotels(@PathVariable String cityId,@RequestParam(value = "distance",required = false) String sortBy){
        return ResponseEntity.status(HttpStatus.OK).body(
                bs.getNearestHotelBookingService(cityId));
    }

}
