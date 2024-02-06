package com.booking.recruitment.hotel.service.impl;

import com.booking.recruitment.hotel.model.Hotel;
import com.booking.recruitment.hotel.repository.CityRepository;
import com.booking.recruitment.hotel.repository.HotelRepository;
import com.booking.recruitment.hotel.service.CityService;
import com.booking.recruitment.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookingService{


    @Autowired
    private final HotelService hs;

    @Autowired private final CityService cs;

    @Autowired private final DefaultHotelService ds;

    private List<Hotel> allHotels;

    public static final double R = 6372.8; // In kilometers

    public BookingService(HotelService hs, CityService cs, CityRepository cr, HotelRepository hr, DefaultHotelService ds) {
        this.hs = hs;
        this.cs = cs;
        this.ds = ds;
        this.allHotels = new ArrayList<>();
    }

    public List<Hotel> getAllHotelsForBooking(){
        allHotels = hs.getAllHotels();
        return allHotels;
    }
    public Hotel getHotelBookingService(String id){

        for(Hotel hotel:getAllHotelsForBooking()){
           if(hotel.getId()==Long.parseLong(id)){
               return hotel;
           }
       }
        return null;
    }


    public Hotel DeleteHotelBookingService(String id){

        for(Hotel hotel:getAllHotelsForBooking()){
            if(hotel.getId()==Long.parseLong(id)){
               allHotels.remove(hotel);
            }
        }
        return null;
    }


    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

    public List<Hotel> getNearestHotelBookingService(String id){
        Map<Double,Long> distances=new TreeMap<>();
        ArrayList<Hotel> hotels=new ArrayList<>();
        List<Hotel> cityhotels=ds.getHotelsByCity(Long.valueOf(id));
        for(Hotel hotel:cityhotels){
           distances.put(haversine(hotel.getLatitude(), hotel.getLongitude(), hotel.getCity().getCityCentreLatitude(),hotel.getCity().getCityCentreLongitude()),hotel.getId());
        }
        distances.values().forEach(t->hotels.add(getHotelBookingService(String.valueOf(t))));
        return hotels;
    }

}
