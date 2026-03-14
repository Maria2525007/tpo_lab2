package functions.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import functions.rest.LnRestClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ln-provider")
public class LnConsumerPactTest {

    @Pact(provider = "ln-provider", consumer = "logbase-consumer")
    public V4Pact pactForLn2(PactDslWithProvider builder) {
        return builder
                .given("ln service is available")
                .uponReceiving("request to calculate ln(2.0)")
                    .path("/ln")
                    .method("GET")
                    .query("x=2.0&eps=1.0E-6")
                .willRespondWith()
                    .status(200)
                    .matchHeader("Content-Type", "application/json.*", "application/json")
                    .body(new PactDslJsonBody()
                            .decimalType("x", 2.0)
                            .decimalType("eps", 1.0E-6)
                            .decimalType("result", 0.6931471805599453))
                .toPact(V4Pact.class);
    }

    @Pact(provider = "ln-provider", consumer = "logbase-consumer")
    public V4Pact pactForLn05(PactDslWithProvider builder) {
        return builder
                .given("ln service is available")
                .uponReceiving("request to calculate ln(0.5)")
                    .path("/ln")
                    .method("GET")
                    .query("x=0.5&eps=1.0E-6")
                .willRespondWith()
                    .status(200)
                    .matchHeader("Content-Type", "application/json.*", "application/json")
                    .body(new PactDslJsonBody()
                            .decimalType("x", 0.5)
                            .decimalType("eps", 1.0E-6)
                            .decimalType("result", -0.6931471805599453))
                .toPact(V4Pact.class);
    }

    @Pact(provider = "ln-provider", consumer = "logbase-consumer")
    public V4Pact pactForLnInvalid(PactDslWithProvider builder) {
        return builder
                .given("ln service is available")
                .uponReceiving("request to calculate ln(-1) which is invalid")
                    .path("/ln")
                    .method("GET")
                    .query("x=-1.0&eps=1.0E-6")
                .willRespondWith()
                    .status(400)
                    .matchHeader("Content-Type", "application/json.*", "application/json")
                    .body(new PactDslJsonBody()
                            .stringType("error", "ln undefined"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "pactForLn2")
    void testCalculateLn2(MockServer mockServer) {
        LnRestClient client = new LnRestClient(mockServer.getUrl());
        double result = client.calculateLn(2.0, 1e-6);
        assertEquals(0.6931471805599453, result, 0.01);
    }

    @Test
    @PactTestFor(pactMethod = "pactForLn05")
    void testCalculateLn05(MockServer mockServer) {
        LnRestClient client = new LnRestClient(mockServer.getUrl());
        double result = client.calculateLn(0.5, 1e-6);
        assertEquals(-0.6931471805599453, result, 0.01);
    }

    @Test
    @PactTestFor(pactMethod = "pactForLnInvalid")
    void testCalculateLnInvalid(MockServer mockServer) {
        LnRestClient client = new LnRestClient(mockServer.getUrl());
        try {
            client.calculateLn(-1.0, 1e-6);
        } catch (RuntimeException e) {
            assertEquals(true, e.getMessage().contains("400"));
        }
    }
}
