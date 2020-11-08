import com.sisteme.tranzationale.generator.AllTransactionsGenerator;
import com.sisteme.tranzationale.generator.KRandomTransactionGenerator;
import com.sisteme.tranzationale.helper.TransactionHelper;
import com.sisteme.tranzationale.validator.OperationValidator;

import java.util.Arrays;
import java.util.List;

public class Main {

    private static final int NR_OF_READS = 2;
    private static final int NR_OF_WRITES = 2;

    private static final int NR_OF_DESIRED_TRANSACTIONS = 12;
    private static final List<String> DOMAIN = Arrays.asList("x", "y", "z");

    public static void main(String[] args) {
        //init app context
        TransactionHelper transactionHelper = new TransactionHelper();
        OperationValidator operationValidator = new OperationValidator(NR_OF_READS, NR_OF_WRITES, transactionHelper);
        KRandomTransactionGenerator kRandomTransactionGenerator = new KRandomTransactionGenerator(NR_OF_DESIRED_TRANSACTIONS, DOMAIN, operationValidator);
        AllTransactionsGenerator allTransactionsGenerator = new AllTransactionsGenerator(DOMAIN, operationValidator);

//        System.out.println("Starting K Random transaction generator");
//        kRandomTransactionGenerator.generate();

        System.out.println("Starting all generator");
        allTransactionsGenerator.generate();
    }
}
