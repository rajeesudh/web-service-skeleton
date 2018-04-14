package uk.co.mruoc.client;

import uk.co.mruoc.api.CustomerDto;
import uk.co.mruoc.api.CustomerDtoConverter;
import uk.co.mruoc.http.client.HttpClient;
import uk.co.mruoc.http.client.Response;
import uk.co.mruoc.http.client.SimpleHttpClient;

import java.util.Optional;


public class CustomerClient {

    private final CustomerDtoConverter customerConverter = new CustomerDtoConverter();
    private final HttpClient httpClient;
    private final String baseUrl;

    public CustomerClient(String baseUrl) {
        this(baseUrl, new SimpleHttpClient());
    }

    public CustomerClient(String baseUrl, HttpClient httpClient) {
        this.baseUrl = baseUrl;
        this.httpClient = httpClient;
    }

    public Optional<CustomerDto> getCustomer(String accountNumber) {
        String url = String.format("%s/customers/%s", baseUrl, accountNumber);
        Response response = httpClient.get(url);
        if (response.is2xx()) {
            return Optional.of(customerConverter.toDto(response.getBody()));
        }
        return Optional.empty();
    }

}