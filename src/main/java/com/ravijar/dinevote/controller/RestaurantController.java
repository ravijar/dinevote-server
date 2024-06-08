package com.ravijar.dinevote.controller;

import com.ravijar.dinevote.enums.Categories;
import com.ravijar.dinevote.enums.Fields;
import com.ravijar.dinevote.service.FourSquareService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/restaurants")
public class RestaurantController {

    private final FourSquareService fourSquareService;

    public RestaurantController(FourSquareService fourSquareService) {
        this.fourSquareService = fourSquareService;
    }

    @GetMapping
    public List<Map<String,Object>> getAllRestaurants(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam int radius,
            @RequestParam String category
    ) {

        String categoryIds = switch (category.toLowerCase()) {
            case "restaurant" -> Categories.RESTAURANTS.getIds();
            case "bar" -> Categories.BARS.getIds();
            case "cafe" -> Categories.CAFES.getIds();
            case "dessertshop" -> Categories.DESSERTSHOPS.getIds();
            default -> throw new IllegalArgumentException("Unsupported category: " + category);
        };

        return fourSquareService.getPlaces(latitude, longitude, radius, categoryIds, Fields.PLACE_SEARCH.getRequiredFields());
    }
}
