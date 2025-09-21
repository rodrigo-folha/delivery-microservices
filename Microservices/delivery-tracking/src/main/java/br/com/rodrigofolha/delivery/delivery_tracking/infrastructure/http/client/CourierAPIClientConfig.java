package br.com.rodrigofolha.delivery.delivery_tracking.infrastructure.http.client;

import java.time.Duration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class CourierAPIClientConfig {

    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClienteBuilder() {
        return RestClient.builder();
    }

    @Bean
    public CourierAPIClient courierAPIClient(RestClient.Builder builder) {
        RestClient restClient = builder.baseUrl("http://courier-management")
            .requestFactory(generateClientRequestFactory())
            .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return proxyFactory.createClient(CourierAPIClient.class);
    }

    private ClientHttpRequestFactory generateClientRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(10));
        factory.setReadTimeout(Duration.ofMillis(200));
        return factory;
    }
}
