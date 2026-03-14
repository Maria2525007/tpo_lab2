package functions.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LnRestClient {

    private final String baseUrl;
    private final HttpClient httpClient;

    public LnRestClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.httpClient = HttpClient.newHttpClient();
    }

    public double calculateLn(double x, double eps) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + "/ln?x=" + x + "&eps=" + eps))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "ln-provider returned HTTP " + response.statusCode()
                                + ": " + response.body());
            }

            return parseResult(response.body());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to call ln-provider", e);
        }
    }

    private double parseResult(String json) {
        String resultStr = json.split("\"result\"\\s*:\\s*")[1]
                .split("[,}]")[0]
                .trim();
        return Double.parseDouble(resultStr);
    }
}
