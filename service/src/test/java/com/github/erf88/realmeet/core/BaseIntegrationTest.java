
package com.github.erf88.realmeet.core;

import com.github.erf88.realmeet.Application;
import com.github.erf88.realmeet.api.ApiClient;
import com.github.erf88.realmeet.api.facade.RoomApi;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("integration-test")
@SpringBootTest(
        classes = {Application.class, RoomApi.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

    @Autowired
    private Flyway flyway;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void setup() {
        setupFlyway();
    }

    private void setupFlyway() {
        flyway.clean();
        flyway.migrate();
    }

    protected void setLocalHostBasePath(ApiClient apiClient, String path) throws URISyntaxException, MalformedURLException {
        URI uri = new URI("http", null, "localhost", serverPort, path, null, null);
        apiClient.setBasePath(uri.toURL().toString());
    }

    protected void setupEach() throws Exception {
    }

}
