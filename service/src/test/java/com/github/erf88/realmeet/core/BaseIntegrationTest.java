package com.github.erf88.realmeet.core;

import static com.github.erf88.realmeet.utils.TestConstants.TEST_CLIENT_API_KEY;
import static com.github.erf88.realmeet.utils.TestDataCreator.newClientBuilder;
import static org.mockito.BDDMockito.given;

import com.github.erf88.realmeet.Application;
import com.github.erf88.realmeet.api.ApiClient;
import com.github.erf88.realmeet.api.facade.AllocationApi;
import com.github.erf88.realmeet.api.facade.RoomApi;
import com.github.erf88.realmeet.domain.repository.ClientRepository;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("integration-test")
@SpringBootTest(
    classes = { Application.class, RoomApi.class, AllocationApi.class },
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public abstract class BaseIntegrationTest {
    @Autowired
    private Flyway flyway;

    @MockitoBean
    private ClientRepository clientRepository;

    @LocalServerPort
    private int serverPort;

    @BeforeEach
    void setup() {
        setupFlyway();
        mockApiKey();
    }

    private void setupFlyway() {
        flyway.clean();
        flyway.migrate();
    }

    protected void setLocalHostBasePath(ApiClient apiClient, String path)
        throws URISyntaxException, MalformedURLException {
        URI uri = new URI("http", null, "localhost", serverPort, path, null, null);
        apiClient.setBasePath(uri.toURL().toString());
    }

    protected void setupEach() throws Exception {}

    private void mockApiKey() {
        given(clientRepository.findById(TEST_CLIENT_API_KEY)).willReturn(Optional.of(newClientBuilder().build()));
    }
}
