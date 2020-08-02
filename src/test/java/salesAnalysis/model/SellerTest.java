package salesAnalysis.model;

import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import salesAnalysis.TestUtils;
import salesAnalysis.models.Seller;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class SellerTest {
    @Test
    public void assertsThatSellerIsCreatedCorrectly() throws IOException {
        List<CSVRecord> rows = TestUtils.getCsvRecords();

        Seller seller = new Seller(rows.get(TestUtils.SELLER_ROW_INDEX));

        assertEquals("1234567891234", seller.getCpf());
        assertEquals("Pedro", seller.getName());
        assertEquals("50000", seller.getSalary().toString());
    }
}
