package com.example.sfsalaries.genderize;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenderizeUtil {
    private static final String GENDERIZE_API_URL = "https://api.genderize.io";
    private static final String API_KEY = "9544cb69372d8313a2a94eec535cde2d";
    private final WebClient webClient;

    public GenderizeUtil() {
        this.webClient = WebClient.builder()
                .baseUrl(GENDERIZE_API_URL)
                .build();
    }

    public String getGender(String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("name", name)
                        .queryParam("apikey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) response.getOrDefault("gender", "unknown"))
                .block();
    }

    public Map<String, String> getGenders(List<String> names) {
        Map<String, String> result = new HashMap<>();
        webClient.get()
                .uri(uriBuilder -> {
                    for (int i = 0; i < names.size(); i++) {
                        uriBuilder.queryParam(String.format("name[%d]", i), names.get(i));
                    }
                    uriBuilder.queryParam("apikey", API_KEY);
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToFlux(Map.class)
                .toStream()
                .forEach(response -> {
                    String name = (String) response.get("name");
                    String gender = (String) response.getOrDefault("gender", "unknown");
                    result.put(name, gender);
                });

        return result;
    }
}
