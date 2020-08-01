package salesAnalysis;

import org.apache.commons.csv.CSVRecord;
import salesAnalysis.models.Customer;
import salesAnalysis.models.Sale;
import salesAnalysis.models.Seller;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Report {
    private static final int ROW_ID_INDEX = 0;

    private Map<String, Function> mapper = new HashMap<>();
    private List<Seller> sellers;
    private List<Customer> customers;
    private List<Sale> sales;

    public Report() {
        this.sellers = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.sales = new ArrayList<>();
        initMapper();
    }

    public void addEntry(CSVRecord row) {
        mapper.get(row.get(ROW_ID_INDEX)).apply(row);
    }

    public Seller getWorstSeller() {
        return groupSaleBySeller()
                .entrySet()
                .stream()
                .min((entry1, entry2) -> compareSellerSales(entry1, entry2))
                .get()
                .getKey();
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

    private void initMapper() {
        mapper.put("001", (Function) (row) -> sellers.add(new Seller((CSVRecord) row)));
        mapper.put("002", (Function) (row) -> customers.add(new Customer((CSVRecord) row)));
        mapper.put("003", (Function) (row) -> sales.add(new Sale((CSVRecord) row, sellers)));
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
}
