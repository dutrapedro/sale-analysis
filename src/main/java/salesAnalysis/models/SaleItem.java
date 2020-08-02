package salesAnalysis.models;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleItem implements Serializable {
    private static final int QUANTITY = 1;
    private static final int PRICE = 2;

    private BigDecimal quantity;
    private BigDecimal price;

    public SaleItem(String[] rawData) {
        quantity = new BigDecimal(removeBrackets(rawData[QUANTITY]));
        price = new BigDecimal(removeBrackets(rawData[PRICE]));
    }

    public BigDecimal getTotal() {
        return quantity.multiply(price);
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String removeBrackets(String data) {
        return data.replaceAll("\\[|\\]", "");
    }

    public BigDecimal getPrice() {
        return price;
    }
}
