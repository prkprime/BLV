package dev.prkprime.streamdemo;

import lombok.Getter;
import org.apache.commons.csv.CSVFormat;

import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Comparator;
import java.util.List;

public class FileMonitor implements Closeable {
    @Getter
    private final String fileName;
    private final Reader reader;
    private final CSVFormat csvFormat;

    public FileMonitor(String fileName) throws IOException {
        this.fileName = fileName;
        reader = new FileReader(fileName);
        csvFormat = CSVFormat.DEFAULT.builder().build();
    }

    public List<DataRecord> getAllRecords() throws IOException {
        return csvFormat.parse(reader)
                .stream()
                .map(y -> new DataRecord(Integer.parseInt(y.get(0)), y.get(1)))
                .sorted(Comparator.comparingInt(DataRecord::col1))
                .toList();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
