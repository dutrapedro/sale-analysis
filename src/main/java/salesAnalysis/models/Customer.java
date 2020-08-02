package salesAnalysis.models;

import org.apache.commons.csv.CSVRecord;

import java.io.Serializable;

public class Customer implements Serializable {
    private static final int CNPJ = 1;
    private static final int NAME = 2;
    private static final int BUSINESS_AREA = 3;

    private String cnpj;
    private String name;
    private String businessArea;

    public Customer(CSVRecord row) {
        cnpj = row.get(CNPJ);
        name = row.get(NAME);
        businessArea = row.get(BUSINESS_AREA);
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getName() {
        return name;
    }

    public String getBusinessArea() {
        return businessArea;
    }

}
