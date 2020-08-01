package salesAnalysis;

import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class ContentParser extends SimpleFunction<String, Report> {
    private static final String CONTENT_SEPARATOR = "ç";

    @Override
    public Report apply(String  lines) {
        Report report = new Report();
        Reader in = new StringReader(lines);

        try {
            CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.withDelimiter('ç'));
            List<CSVRecord> records = parser.getRecords();

            records.stream().forEach(r -> report.addEntry(r));

        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        return report;
    }
}
