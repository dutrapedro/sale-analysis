package salesAnalysis.transforms;

import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import salesAnalysis.Report;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVGenerator extends SimpleFunction<Report, String> {
    private static final Logger logger = LogManager.getLogger(CSVGenerator.class);

    private String outputPath;

    public CSVGenerator(String outputPath) {
        this.outputPath = outputPath.isBlank() ? String.format("%s/data/out", System.getenv("HOME")) : outputPath;
    }
    @Override
    public String apply(Report report) {
        try {
            String file = String.format("%s/%s.csv", outputPath, report.getFilename());
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(file));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("customers_quantity", "seller_quantity", "best_sale_id", "worst_seller_name"));

            csvPrinter.printRecord(
                    report.getCustomers().size(),
                    report.getSellers().size(),
                    report.getMostExpensiveSaleId(),
                    report.getWorstSeller().getName()
            );

            csvPrinter.flush();
        } catch (IOException e) {
            logger
                    .error(String.format("Error in CSV Generation Filename=%s Error=%s",
                            report.getFilename(),
                            e.getMessage()));
        }

        return report.getFilename();
    }
}
