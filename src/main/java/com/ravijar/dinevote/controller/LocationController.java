package com.ravijar.dinevote.controller;

import com.ravijar.dinevote.enums.foursquare.Categories;
import com.ravijar.dinevote.enums.foursquare.Fields;
import com.ravijar.dinevote.service.FourSquareService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/restaurant")
public class LocationController {

    private final FourSquareService fourSquareService;

    public LocationController(FourSquareService fourSquareService) {
        this.fourSquareService = fourSquareService;
    }

    @GetMapping
    public List<Map<String,Object>> getAllLocations(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam int radius,
            @RequestParam String category
    ) {

        String categoryIds = switch (category.toLowerCase()) {
            case "restaurant" -> Categories.RESTAURANTS.getIds();
            case "bar" -> Categories.BARS.getIds();
            case "cafe" -> Categories.CAFES.getIds();
            case "dessertshop" -> Categories.DESSERT_SHOPS.getIds();
            default -> throw new IllegalArgumentException("Unsupported category: " + category);
        };

        return fourSquareService.getPlaces(latitude, longitude, radius, categoryIds, Fields.PLACE_SEARCH.getRequiredFields());
    }
}
