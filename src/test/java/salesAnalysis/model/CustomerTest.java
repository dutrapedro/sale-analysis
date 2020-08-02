package salesAnalysis.model;

import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import salesAnalysis.TestUtils;
import salesAnalysis.models.Customer;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class CustomerTest {
    @Test
    public void assertsThatCustomerIsCreatedCorrectly() throws IOException {
        List<CSVRecord> rows = TestUtils.getCsvRecords();

        Customer customer = new Customer(rows.get(TestUtils.CUSTOMER_ROW_INDEX));

        assertEquals("2345675434544345", customer.getCnpj());
        assertEquals("Jose da Silva", customer.getName());
        assertEquals("Rural", customer.getBusinessArea());
    }
}
