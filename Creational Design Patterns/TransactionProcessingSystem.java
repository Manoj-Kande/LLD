import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

interface PaymentProcessor {
    void processPayment(double amount);
}

class CreditCardProcessor implements PaymentProcessor{

    @Override 
    public void processPayment(double amount){
        System.out.println("Payment is done " + amount +" via Credit Card");
    }

}


class UpiProcessor implements PaymentProcessor{
    @Override 
    public void processPayment(double amount){
        System.out.println("Payment is done " + amount +" via UPI");
    }
}


class NetBankingProcessor implements PaymentProcessor{
    @Override 
    public void processPayment(double amount){
        System.out.println("Payment is done " + amount +" via NET Banking");
    }
}

class PaytmProcessor implements PaymentProcessor{
    @Override
    public void processPayment(double amount){
        System.out.println("Payment is done " + amount +" via Paytm");
    }
}


class PaymentProcessorFactory{

    private static final Map<String,Supplier<PaymentProcessor>> processors = new HashMap<>();
    
    static{
        processors.put("UPI", UpiProcessor::new);
        processors.put("CREDIT_CARD", CreditCardProcessor::new);
        processors.put("NET_BANKING", NetBankingProcessor::new);
        processors.put("PAYTM", PaytmProcessor::new);
    }

    public static PaymentProcessor processPayment(String paymentType){
        Supplier<PaymentProcessor> supplier = 
                                    processors.get(paymentType.toUpperCase());
        if (supplier == null) {
            throw new IllegalArgumentException(
                    "Unsupported payment type: " + paymentType
            );
        }

        return supplier.get();

    }

}

public class TransactionProcessingSystem {
    public static void main(String[] args) {

        PaymentProcessor processor = PaymentProcessorFactory.processPayment("UPI");

    Transaction txn = new Transaction.TransactionBuilder("txn1", "cust1", 500)
                        .currency("INR")
                        .build();

    processor.processPayment(txn.getAmount());

    TransactionLogger.getInstance().log("Transaction Success");



    }
}



class Transaction {

    //mandatory

    private final String transactionId;
    private final String customerId ;
    private final double amount ;
    
    // optional
    private final String currency;
    private final String notes;
    private final String timestamp;

    public Transaction(TransactionBuilder builder){
        this.transactionId = builder.transactionId;
        this.customerId = builder.customerId;
        this.amount = builder.amount;
        this.currency = builder.currency;
        this.notes = builder.notes;
        this.timestamp = builder.timestamp;
    }
    
    public String getCurrency(){
        return this.currency;
    }

    public String getNotes(){
        return this.notes;
    }

    public double getAmount(){
        return this.amount;
    }
    
    public static class TransactionBuilder{

        private final String transactionId;
        private final String customerId ;
        private final double amount;
        
        // optional
        private  String currency;
        private  String notes;
        private  String timestamp;

        public TransactionBuilder(String transactionId, String customerId, double amount){

            this.transactionId = transactionId;
            this.customerId = customerId;
            this.amount = amount;
        
        }

       

        public TransactionBuilder currency(String currency){
            this.currency = currency;
            return this;
        }

        public TransactionBuilder notes(String notes){
            this.notes = notes;
            return this;
        }

        public TransactionBuilder timestamp(String timestamp){
            this.timestamp = timestamp;
            return this;
        }

        public Transaction build(){
            return new Transaction(this);
        }

    }

}


interface ILogger{
    void log(String message);
}


class TransactionLogger  implements ILogger{
    private static  volatile TransactionLogger logger;

    // private constructor
    private TransactionLogger(){
        // empty constructor for the singleton purposes..
    }

    public static TransactionLogger getInstance(){
        if(logger == null){
            synchronized(TransactionLogger.class){
                if(logger == null){
                    logger = new TransactionLogger();
                }
            }
        }
        return logger;
    }

    @Override 
    public void log(String message){
        System.out.println("Logger is logging the tansaction...." + message);
    }
}