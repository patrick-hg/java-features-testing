package generalities.concurrency.blockingAndNonBlockingIO;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OnlineStoreExample {

    public static void main(String[] args) {

        OnlineStoreWebApplication onlineStoreWebApplication = new OnlineStoreWebApplication();
        onlineStoreWebApplication.startHttpServer();
        onlineStoreWebApplication.startHttpClient();
    }

    public static class OnlineStoreWebApplication {

        private HttpServer httpServer;
        private HttpClient httpClient;

        @SneakyThrows
        public void startHttpServer() {
            this.httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
            HttpContext context = this.httpServer.createContext("/");
            context.setHandler(this::handleHttpRequest);
            ExecutorService executorService = Executors.newFixedThreadPool(8);
            this.httpClient = HttpClient.newBuilder().executor(executorService).build();
            this.httpServer.setExecutor(executorService);

            this.httpServer.start();

        }

        public void startHttpClient() {
            this.httpClient = HttpClient.newHttpClient();
        }

        /**
         * handles an incoming http request from a user
         * @param httpExchange
         */
        private void handleHttpRequest(HttpExchange httpExchange) {
            try {
                System.out.println("httpExchange");

                int numberOfProducts = 2;

                URI requestUri = URI.create("http://google.com");

                HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder()
                        .GET()
                        .uri(requestUri)
                        .build(), HttpResponse.BodyHandlers.ofString());

                System.out.println("will send response http to user");

//                parseRequest(httpExchange);
            } catch (InterruptedException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

}
