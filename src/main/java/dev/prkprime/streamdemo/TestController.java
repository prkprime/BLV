package dev.prkprime.streamdemo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@RestController
public class TestController {
    @GetMapping(value = "/stream",produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<List<DataRecord>> getStream() throws IOException {
        FileMonitor monitor = new FileMonitor("src/main/resources/data.csv");
        return Flux.interval(Duration.ofSeconds(1))
                .mapNotNull(y -> {
                    try {
                        return monitor.getAllRecords();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

    }
}