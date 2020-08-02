package salesAnalysis;

import org.apache.commons.csv.CSVRecord;
import salesAnalysis.models.Customer;
import salesAnalysis.models.Sale;
import salesAnalysis.models.Seller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Report implements Serializable {
    private static final int ROW_ID_INDEX = 0;
    private String filename;

    private List<Seller> sellers;
    private List<Customer> customers;
    private List<Sale> sales;

    public Report(String filename) {
        sellers = new ArrayList<>();
        customers = new ArrayList<>();
        sales = new ArrayList<>();
        this.filename = getOnlyName(filename);
    }

    public void addEntry(CSVRecord row) {
        switch (row.get(ROW_ID_INDEX)) {
            case "001":
                sellers.add(new Seller(row));
                break;
            case "002":
                customers.add(new Customer(row));
                break;
            case "003":
                sales.add(new Sale(row, sellers));
                break;
        }
    }

    public Seller getWorstSeller() {
        return groupSaleBySeller()
                .entrySet()
                .stream()
                .min((entry1, entry2) -> compareSellerSales(entry1, entry2))
                .get()
                .getKey();
    }

    public String getFilename() {
        return filename;
    }

    public Integer getMostExpensiveSaleId() {
        return sales
                .stream()
                .max(Comparator.comparing(Sale::getTotalSold))
                .get()
                .getSaleId();
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Sale> getSales() {
        return sales;
    }

    private Map<Seller, List<Sale>> groupSaleBySeller() {
        return sales.stream().collect(Collectors.groupingBy(Sale::getSeller));
    }

    private int compareSellerSales(Map.Entry<Seller, List<Sale>> entry1, Map.Entry<Seller, List<Sale>> entry2) {
        return getTotalSoldFrom(entry1.getValue()).compareTo(getTotalSoldFrom(entry2.getValue()));
    }

    private BigDecimal getTotalSoldFrom(List<Sale> sales) {
        return sales.stream().map(Sale::getTotalSold).reduce(BigDecimal::add).get();
    }

    private String getOnlyName(String path) {
        String[] splittedPath = path.split("/");

        return splittedPath[splittedPath.length - 1];
    }
}
