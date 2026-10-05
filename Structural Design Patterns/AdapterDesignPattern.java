

// Applications Payment interface
interface InHousePaymentProcessor {
    void process(int  amount);
}

class YourApplication{
    InHousePaymentProcessor paymentProcessor;

    // setter for the payment processor (Dependency Injection)..
    void setPaymentProcessor(InHousePaymentProcessor paymentProcessor){
        this.paymentProcessor = paymentProcessor;
    }

    void processPayment(int amount){
        paymentProcessor.process(amount);
    }
}

class InHousePaymentLibrary implements InHousePaymentProcessor{
    @Override
    public void process(int amount) {
        // 10K lines of code to process the payment successfully
        System.out.println("In House Payment process successfull");
    }

}

// Now our application wants to support the razorpay and paytm third party payments also
// but dont want to change the inhourse interface due to the interfaces exposed by the thirdparty are different
// so we use the adapter classes.(we use adapter design pattern here..).

interface RazorpayPaymentProcessor {
    void processRazorPay(int amount);
}

interface PaytmPaymentProcessor {
    void processPaytm(int amount);
}

// adapter classes
// flow YourApplication.setPaymentProcessor(_razorpay or paytm).processPayment

class RazorpayAdapter implements InHousePaymentProcessor{

    RazorpayPaymentProcessor paymentProcessor;

    public RazorpayAdapter(RazorpayPaymentProcessor paymentProcessor){
        this.paymentProcessor = paymentProcessor;
    }

    @Override
    public void process(int amount) {
        paymentProcessor.processRazorPay(amount);
        System.out.println("Razorpay process successfull");
    }
}

class PaytmAdapter implements InHousePaymentProcessor{

    PaytmPaymentProcessor paymentProcessor;

    public PaytmAdapter(PaytmPaymentProcessor paymentProcessor){
        this.paymentProcessor = paymentProcessor;
    }

    @Override
    public void process(int amount) {
        paymentProcessor.processPaytm(amount);
        System.out.println("Paytm process successfull");
    }
}

public class AdapterDesignPattern {
    public static void main() {
        InHousePaymentLibrary paymentLibrary = new InHousePaymentLibrary();
//        RazorpayAdapter razorpayAdapter = new RazorpayAdapter();
//        PaytmAdapter paytmAdapter = new PaytmAdapter();

        YourApplication application = new YourApplication();
//        application.setPaymentProcessor(razorpayAdapter);
        application.processPayment(500);

    }
}
