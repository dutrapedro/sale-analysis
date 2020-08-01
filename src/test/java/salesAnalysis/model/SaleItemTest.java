package salesAnalysis.model;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import salesAnalysis.models.SaleItem;


public class SaleItemTest {
    @Test
    public void assertsThatSaleItemTotalIsCorrect() {
        String rawData = "[1-34-2.19]";

        SaleItem item = new SaleItem(rawData.split("-"));

        assertEquals("74.46", item.getTotal().toString());
    }

    @Test
    public void assertsThatSaleItemIsCreatedCorrectlyEvenWhenRawDataHasBrackets() {
        String rawData = "[1-34-2.19]";

        SaleItem item = new SaleItem(rawData.split("-"));

        assertEquals("34", item.getQuantity().toString());
        assertEquals("2.19", item.getPrice().toString());
    }
}
