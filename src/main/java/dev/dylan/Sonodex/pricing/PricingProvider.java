package dev.dylan.Sonodex.pricing;

import org.springframework.web.client.RestClient;

public final class PricingProvider {
    private PricingProvider() {}
    public static String getPrice(Long id) {
        RestClient client = RestClient.builder()
                .baseUrl("http://localhost:8081")
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();

        return client.get().uri("/price").retrieve().toEntity(String.class).getBody();
    }
}
