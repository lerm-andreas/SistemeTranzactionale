import com.sisteme.tranzationale.generator.impl.TransactionHistoriesGenerator;
import com.sisteme.tranzationale.helper.TransactionHelper;
import com.sisteme.tranzationale.validator.TransactionValidator;

import java.util.Arrays;
import java.util.List;

public class Lab4 {
    private static final int NR_OF_READS = 2;
    private static final int NR_OF_WRITES = 2;

    private static final int NR_OF_DESIRED_TRANSACTIONS = 2;
    private static final List<String> DOMAIN = Arrays.asList("x", "y", "z");

    public static void main(String[] args) {
        TransactionHelper transactionHelper = new TransactionHelper();
        TransactionValidator transactionValidator = new TransactionValidator(NR_OF_READS, NR_OF_WRITES, transactionHelper);
        TransactionHistoriesGenerator transactionHistoriesGenerator = new TransactionHistoriesGenerator(transactionValidator);

        transactionHistoriesGenerator.generate(DOMAIN, NR_OF_DESIRED_TRANSACTIONS);
    }
}
