package functions.pact;

import functions.log.Ln;
import functions.rest.LnRestClient;
import functions.rest.LnRestProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LnProviderBreakingPactTest {

    @Test
    void originalProviderSatisfiesContract() throws Exception {
        LnRestProvider providerV1 = new LnRestProvider(new Ln(), 0, false);
        providerV1.start();

        try {
            LnRestClient client = new LnRestClient(
                    "http://localhost:" + providerV1.getPort());

            double result = client.calculateLn(2.0, 1e-6);
            assertEquals(Math.log(2), result, 0.001,
                    "V1 provider correctly returns ln(2)");

            double result2 = client.calculateLn(0.5, 1e-6);
            assertEquals(Math.log(0.5), result2, 0.001,
                    "V1 provider correctly returns ln(0.5)");
        } finally {
            providerV1.stop();
        }
    }

    @Test
    void breakingChangeDetectedByContractTest() throws Exception {
        LnRestProvider providerV2 = new LnRestProvider(new Ln(), 0, true);
        providerV2.start();

        try {
            LnRestClient client = new LnRestClient(
                    "http://localhost:" + providerV2.getPort());

            RuntimeException ex = assertThrows(RuntimeException.class,
                    () -> client.calculateLn(2.0, 1e-6),
                    "V2 provider should reject requests without precision_mode");

            assertTrue(ex.getMessage().contains("400"),
                    "Provider V2 returns HTTP 400: " + ex.getMessage());
            assertTrue(ex.getMessage().contains("precision_mode"),
                    "Error mentions the missing parameter: " + ex.getMessage());

            RuntimeException ex2 = assertThrows(RuntimeException.class,
                    () -> client.calculateLn(0.5, 1e-6),
                    "V2 provider should reject all requests without precision_mode");

            assertTrue(ex2.getMessage().contains("400"));
        } finally {
            providerV2.stop();
        }
    }


    @Test
    void updatedConsumerWorksWithV2Provider() throws Exception {
        LnRestProvider providerV2 = new LnRestProvider(new Ln(), 0, true);
        providerV2.start();

        try {
            java.net.http.HttpClient httpClient = java.net.http.HttpClient.newHttpClient();
            java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                    .uri(java.net.URI.create(
                            "http://localhost:" + providerV2.getPort()
                                    + "/ln?x=2.0&eps=1.0E-6&precision_mode=standard"))
                    .GET()
                    .build();

            java.net.http.HttpResponse<String> response =
                    httpClient.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

            assertEquals(200, response.statusCode(),
                    "V2 provider works when precision_mode is included");
            assertTrue(response.body().contains("result"),
                    "Response contains the result field");
        } finally {
            providerV2.stop();
        }
    }
}
