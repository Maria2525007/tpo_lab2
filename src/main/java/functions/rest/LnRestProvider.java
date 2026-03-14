package functions.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import functions.log.Ln;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LnRestProvider {

    private final Ln ln;
    private final boolean requirePrecisionMode;
    private HttpServer server;
    private int port;

    public LnRestProvider(Ln ln, int port) {
        this(ln, port, false);
    }

    public LnRestProvider(Ln ln, int port, boolean requirePrecisionMode) {
        this.ln = ln;
        this.port = port;
        this.requirePrecisionMode = requirePrecisionMode;
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/ln", this::handleLn);
        server.start();
        this.port = server.getAddress().getPort();
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    public int getPort() {
        return port;
    }

    private void handleLn(HttpExchange exchange) throws IOException {
        Map<String, String> params = parseQuery(exchange.getRequestURI().getQuery());

        if (requirePrecisionMode && !params.containsKey("precision_mode")) {
            sendResponse(exchange, 400,
                    "{\"error\": \"missing required parameter: precision_mode\"}");
            return;
        }

        if (!params.containsKey("x") || !params.containsKey("eps")) {
            sendResponse(exchange, 400,
                    "{\"error\": \"missing required parameters: x and eps\"}");
            return;
        }

        try {
            double x = Double.parseDouble(params.get("x"));
            double eps = Double.parseDouble(params.get("eps"));
            double result = ln.calculate(x, eps);

            String json = "{\"x\": " + x + ", \"eps\": " + eps + ", \"result\": " + result + "}";
            sendResponse(exchange, 200, json);
        } catch (IllegalArgumentException e) {
            sendResponse(exchange, 400, "{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String body)
            throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null) {
            for (String pair : query.split("&")) {
                String[] kv = pair.split("=", 2);
                if (kv.length == 2) {
                    params.put(kv[0], kv[1]);
                }
            }
        }
        return params;
    }
}
