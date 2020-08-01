package salesAnalysis;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.List;

public class TestHelper {
    public static final int SELLER_ROW_INDEX = 0;
    public static final int CUSTOMER_ROW_INDEX = 2;
    public static final int SALE_ROW_INDEX = 4;

    public static List<CSVRecord> getCsvRecords() throws IOException {
        Reader in = new FileReader(new File("src/test/resources/flatfile"));
        CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.withDelimiter('ç'));

        return parser.getRecords();
    }
}
