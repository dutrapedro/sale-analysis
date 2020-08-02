package salesAnalysis.models;

import org.apache.commons.csv.CSVRecord;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Sale implements Serializable {
    private static final int SALE_ID = 1;
    private static final int ITEMS = 2;
    private static final int SELLER = 3;

    private Integer saleId;
    private List<SaleItem> items;
    private Seller seller;

    public Sale(CSVRecord row, List<Seller> sellers) {
        saleId = Integer.parseInt(row.get(SALE_ID));
        items = fetchRawSaleItems(row.get(ITEMS));
        seller = sellers.stream().filter(s -> s.getName().equals(row.get(SELLER))).findFirst().get();
    }

    public Integer getSaleId() {
        return saleId;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public Seller getSeller() {
        return seller;
    }

    public BigDecimal getTotalSold() {
        return items
                .stream()
                .map(SaleItem::getTotal)
                .reduce(BigDecimal::add)
                .get();
    }

    private List<SaleItem> fetchRawSaleItems(String rawData) {
        List<String> data = Arrays.asList(rawData.split(","));
        return data
                .stream()
                .map(item -> new SaleItem(item.split("-")))
                .collect(Collectors.toList());
    }
}
