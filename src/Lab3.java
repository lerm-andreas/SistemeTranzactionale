import com.sisteme.tranzationale.generator.impl.AllTransactionsGenerator;
import com.sisteme.tranzationale.helper.TransactionHelper;
import com.sisteme.tranzationale.validator.TransactionValidator;

import java.util.Arrays;
import java.util.List;

public class Lab3 {
    private static final int NR_OF_READS = 2;
    private static final int NR_OF_WRITES = 2;

    private static final List<String> DOMAIN = Arrays.asList("x", "y", "z");

    public static void main(String[] args) {
        TransactionHelper transactionHelper = new TransactionHelper();
        TransactionValidator transactionValidator = new TransactionValidator(NR_OF_READS, NR_OF_WRITES, transactionHelper);
        AllTransactionsGenerator allTransactionsGenerator = new AllTransactionsGenerator(transactionValidator);

        allTransactionsGenerator.generate(DOMAIN, NR_OF_READS, NR_OF_WRITES);
    }
}
