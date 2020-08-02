package salesAnalysis.transforms;

import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import salesAnalysis.Report;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

public class ContentParser extends SimpleFunction<KV<String, String>, Report> {
    private static final Logger logger = LogManager.getLogger(ContentParser.class);

    @Override
    public Report apply(KV<String, String> file) {
        Report report = new Report(file.getKey());
        Reader in = new StringReader(file.getValue());

        try {
            CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.withDelimiter('ç'));
            List<CSVRecord> rows = parser.getRecords();

            rows.stream().forEach(r -> report.addEntry(r));

        } catch (IOException e) {
            logger
                    .error(String.format("Error in Content Parsing Filename=%s Error=%s",
                            file.getKey(),
                            e.getMessage()));
        }

        return report;
    }
}
