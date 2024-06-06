package com.ravijar.dinevote.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class FourSquareService {

    private final RestTemplate restTemplate;
    private final String fourSquareApiUrl;
    private final String fourSquareApiKey;

    public FourSquareService(
            RestTemplate restTemplate,
            String fourSquareApiUrl,
            String fourSquareApiKey
    ) {
        this.restTemplate = restTemplate;
        this.fourSquareApiUrl = fourSquareApiUrl;
        this.fourSquareApiKey = fourSquareApiKey;
    }

    public List<Map<String,Object>> getPlaces(
            double latitude,
            double longitude,
            int radius,
            String categories,
            String fields
    ){
        String url = UriComponentsBuilder.fromHttpUrl(this.fourSquareApiUrl)
                .queryParam("ll",latitude + "," + longitude)
                .queryParam("radius",radius)
                .queryParam("categories",categories)
                .queryParam("fields",fields)
                .toUriString();

        // set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", fourSquareApiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // make the http request
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        // extract the results part of response
        Map<String,Object> responseBody = response.getBody();
        return (List<Map<String, Object>>) responseBody.get("results");
    }
}
