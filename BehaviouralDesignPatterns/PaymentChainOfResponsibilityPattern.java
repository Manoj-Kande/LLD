

import java.util.List;

class PaymentRequest{
    String userId;
    int amount;
    String paymentMethod;
    String authenticationToken;
    String trasnactionId;

    public PaymentRequest(
            String userId,
            int amount,
            String paymentMethod,
            String authenticationToken,
            String trasnactionId
    ) {
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.authenticationToken = authenticationToken;
        this.trasnactionId = trasnactionId;

    }
}

abstract class PaymentHandler{
    PaymentHandler nextHandler;

    PaymentHandler(PaymentHandler nextHandler){
        this.nextHandler = nextHandler;
    }

    PaymentHandler getNextHandler(){
        return nextHandler;
    }

    abstract void handleRequest(PaymentRequest request);
}

class AuthenticationHandler extends  PaymentHandler{

    AuthenticationHandler(PaymentHandler nextHandler){
        super(nextHandler);
    }

    @Override
    void handleRequest(PaymentRequest request) {
        PaymentHandler nextHandler = getNextHandler();
        if(request.authenticationToken != null){
            if(nextHandler != null){
                System.out.println("Authentication Token is valid so deligating the request to the FraudDetection Handler");
                nextHandler.handleRequest(request);
            }
        }else{
            System.out.println("Authentication Failed so exiting from the chain");
        }
    }
}

class FraudDetectionHandler extends  PaymentHandler{

    public FraudDetectionHandler(PaymentHandler nextHandler){
        super(nextHandler);
    }

    @Override
    void handleRequest(PaymentRequest request) {
        PaymentHandler nextHandler = getNextHandler();

        if(request.trasnactionId != null){
            if(nextHandler != null){
                System.out.println("FraudChecking is also done deligating the request to the PaymentLimitHandler");
                nextHandler.handleRequest(request);
            }
        }else{
            System.out.println("Authentication Failed so exiting from the chain");

        }
    }
}


class paymentLimitHandler extends  PaymentHandler{
    public paymentLimitHandler(PaymentHandler nextHandler){
        super(nextHandler);

    }
    @Override
    void handleRequest(PaymentRequest request) {
        PaymentHandler nextHandler = getNextHandler();

        if(request.amount <= 5000){
            if(nextHandler != null){
                System.out.println("PaymentLimitHandler check is done deligating the request to the PaymentMethodHandler");
                nextHandler.handleRequest(request);
            }
        }else{
            System.out.println("PaymentLimitHandler failed so exiting from the chain");
        }


    }
}

class PaymentMethodHandler  extends PaymentHandler{
    public PaymentMethodHandler(PaymentHandler nextHandler){
        super(nextHandler);
    }
    @Override
    void handleRequest(PaymentRequest request) {
        PaymentHandler nextHandler = getNextHandler();

        if(checkValidTypes(request)){

            System.out.println("Payment is Sucessfull and chain is completed");
            if(nextHandler != null){
                nextHandler.handleRequest(request);
            }
        }else{
            System.out.println("PaymentMethodHandler failed so exiting from the chain");
        }
    }

    public boolean checkValidTypes(PaymentRequest paymentRequest){
        String paymentMethod = paymentRequest.paymentMethod;
        List<String> paymentTypes = List.of("UPI","CREDIT_CARD","DEBIT_CARD","CASH_ON_DELIVERY","NET_BANKING");
        if(!paymentMethod.isEmpty()){
           for(String paymentType:paymentTypes){
               if(paymentType.equals(paymentRequest.paymentMethod)){
                   return true;
               }
           }

        }
        return false;
    }
}
public class PaymentChainOfResponsibilityPattern {

    public static void main(String[] args) {

        // Build the chain from the last handler backwards
        PaymentHandler paymentMethodHandler =
                new PaymentMethodHandler(null);

        PaymentHandler paymentLimitHandler =
                new paymentLimitHandler(paymentMethodHandler);

        PaymentHandler fraudDetectionHandler =
                new FraudDetectionHandler(paymentLimitHandler);

        PaymentHandler authenticationHandler =
                new AuthenticationHandler(fraudDetectionHandler);


        // -------------------------------
        // Test Case 1: Successful Payment
        // -------------------------------
        PaymentRequest request1 = new PaymentRequest(
                "USER101",
                5000,
                "CREDIT_CARD",
                "TOKEN123",
                "TXN001"
        );

        System.out.println("===== TEST CASE 1 =====");
        authenticationHandler.handleRequest(request1);


        // -------------------------------
        // Test Case 2: Authentication fails
        // -------------------------------
        PaymentRequest request2 = new PaymentRequest(
                "USER102",
                2000,
                "UPI",
                null,
                "TXN002"
        );

        System.out.println("\n===== TEST CASE 2 =====");
        authenticationHandler.handleRequest(request2);


        // -------------------------------
        // Test Case 3: Payment limit fails
        // -------------------------------
        PaymentRequest request3 = new PaymentRequest(
                "USER103",
                10000,
                "UPI",
                "TOKEN456",
                "TXN003"
        );

        System.out.println("\n===== TEST CASE 3 =====");
        authenticationHandler.handleRequest(request3);


        // -------------------------------
        // Test Case 4: Invalid payment method
        // -------------------------------
        PaymentRequest request4 = new PaymentRequest(
                "USER104",
                3000,
                "BITCOIN",
                "TOKEN789",
                "TXN004"
        );

        System.out.println("\n===== TEST CASE 4 =====");
        authenticationHandler.handleRequest(request4);
    }
}