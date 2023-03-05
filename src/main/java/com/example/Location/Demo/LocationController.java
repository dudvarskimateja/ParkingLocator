package com.example.Location.Demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/maps")
public class LocationController {

    @Value("${google.maps.api.key}")
    private String apiKey;

//    @GetMapping("/location")

//    public Map<String, Object> getLocation(@RequestParam("address") String address){
//
//        Map<String, Object> locationData = new HashMap<>();
//        locationData.put("latitude", latitude);
//        locationData.put("longitude", longitude);
//        locationData.put("address", formattedAddress);
//        return locationData;
//
//    }

//    public ResponseEntity<String> getCurrentLocation() {
//        navigator.geolocation.getCurrentPosition(
//                function(position) {
//            var lat = position.coords.latitude;
//            var lng = position.coords.longitude;
//            console.log("Latitude: " + lat + ", Longitude: " + lng);
//        },
//        function(e) {
//            console.log("Error: " + e.message);
//        }
//);
//
//    }

}
