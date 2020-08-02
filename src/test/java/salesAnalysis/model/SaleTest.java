package salesAnalysis.model;

import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import salesAnalysis.TestUtils;
import salesAnalysis.models.Sale;
import salesAnalysis.models.Seller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class SaleTest {
    @Test
    public void assertsThatSaleTotalSoldIsCorrect() throws IOException {
        List<CSVRecord> rows = TestUtils.getCsvRecords();
        List<Seller> sellers = new ArrayList<>();
        sellers.add(new Seller(rows.get(TestUtils.SELLER_ROW_INDEX)));

        Sale sale = new Sale(rows.get(TestUtils.SALE_ROW_INDEX), sellers);

        assertEquals("1199.00", sale.getTotalSold().toString());
    }

    @Test
    public void assertsThatSaleIsCreatedCorrectly() throws IOException {
        List<CSVRecord> rows = TestUtils.getCsvRecords();
        List<Seller> sellers = new ArrayList<>();
        sellers.add(new Seller(rows.get(TestUtils.SELLER_ROW_INDEX)));

        Sale sale = new Sale(rows.get(TestUtils.SALE_ROW_INDEX), sellers);

        assertEquals("10", sale.getSaleId().toString());
        assertEquals(3, sale.getItems().size());
        assertEquals("Pedro", sale.getSeller().getName());
    }
}
