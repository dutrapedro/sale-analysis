package salesAnalysis.models;

import org.apache.commons.csv.CSVRecord;

import java.io.Serializable;
import java.math.BigDecimal;

public class Seller implements Serializable {
    private static final int CPF = 1;
    private static final int NAME = 2;
    private static final int SALARY = 3;

    private String cpf;
    private String name;
    private BigDecimal salary;

    public Seller(CSVRecord row) {
        cpf = row.get(CPF);
        name = row.get(NAME);
        salary = new BigDecimal(row.get(SALARY));
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}
