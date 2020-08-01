package salesAnalysis;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ReportTest {
    @Test
    public void assertsThatWorstSellerIsCorrect() throws IOException {
        Report report = new Report();
        List<CSVRecord> rows = TestHelper.getCsvRecords();
        rows.stream().forEach( row -> report.addEntry(row));

        assertEquals("Paulo", report.getWorstSeller().getName());
    }

    @Test
    public void assertsThatMostExpensiveSaleIdIsCorrect() throws IOException{
        Report report = new Report();
        List<CSVRecord> rows = TestHelper.getCsvRecords();
        rows.stream().forEach( row -> report.addEntry(row));

        assertEquals("10", report.getMostExpensiveSaleId().toString());
    }

    @Test
    public void assertsThatAddEntryAddDataToCorrectCollection() throws IOException {
        Report report = new Report();
        List<CSVRecord> rows = TestHelper.getCsvRecords();

        report.addEntry(rows.get(TestHelper.SELLER_ROW_INDEX));
        report.addEntry(rows.get(TestHelper.CUSTOMER_ROW_INDEX));
        report.addEntry(rows.get(TestHelper.SALE_ROW_INDEX));

        assertEquals(1, report.getSellers().size());
        assertEquals(1, report.getCustomers().size());
        assertEquals(1, report.getSales().size());
    }
}
