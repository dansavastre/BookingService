package nl.tudelft.sem.template.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class Authorization {

    private static final transient RestTemplate restTemplate = new RestTemplate();
    public static final String ADMIN = "admin";
    public static final String EMPLOYEE = "employee";
    public static final String SECRETARY = "secretary";

    /** Prepares the request to the user microservice to authorize the token.
     * If the authorization was unsuccessful an exception is thrown.
     *
     * @param role          authority (role) with which the user tries to authorize
     * @param token         access token
     */
    public void authorize(String role, String token) {
        String uri = "http://localhost:8081/" + role + "/authorize";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        sendAuthorizationRequest(uri, entity);
    }

    /** Prepares the request to the user microservice to authorize the token with the username.
     * If the authorization was unsuccessful an exception is thrown.
     *
     * @param role          authority (role) with which the user tries to authorize
     * @param token         access token
     * @param username      username that should match the username in the token
     */
    public void authorizeWithUsername(String role, String token, String username) {
        String uri = "http://localhost:8081/" + role + "/authorizeWithUsername";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.add("Username", username);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        sendAuthorizationRequest(uri, entity);
    }

    /** Sends the request and throws the exceptions when the response was not 200.
     *
     * @param uri       address where to send the request
     * @param entity    request to be sent
     */
    private static void sendAuthorizationRequest(String uri, HttpEntity<String> entity) {
        try {
            restTemplate.exchange(uri, HttpMethod.GET, entity, Boolean.class);

        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.toString());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong while connecting to user microservice");
        }
    }
}
