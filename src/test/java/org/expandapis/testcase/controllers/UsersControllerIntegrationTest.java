package org.expandapis.testcase.controllers;

import org.expandapis.testcase.models.User;
import org.expandapis.testcase.services.UsersService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerIntegrationTest {
    private String authToken;
    private User testUser;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsersService usersService;

    @Before
    public void preSetup() {
        testUser = new User("testUser", "password123");

        ResponseEntity<String> addUserResponse = restTemplate.postForEntity(
                "/user/add", testUser, String.class);

        assertEquals(HttpStatus.OK, addUserResponse.getStatusCode());

        ResponseEntity<String> authenticateUserResponse = restTemplate.postForEntity(
                "/user/authenticate", testUser, String.class);

        assertEquals(HttpStatus.OK, authenticateUserResponse.getStatusCode());
        authToken = authenticateUserResponse.getBody();
    }

    @Test
    public void testAuthenticatedEndpoints() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + authToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "/products/all",
                HttpMethod.GET,
                entity,
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @After
    public void postTeardown() {
        usersService.deleteByUsername(testUser.getUsername());
    }
}
