
interface PaymentGateway{
    void processPayment(double amount);
}

class RazorpayGateway implements PaymentGateway{
    @Override 
    public void processPayment(double amount){
        System.out.println("Processing INR payment via Razorpay " + amount);
    }
}

class PayuGateway implements PaymentGateway{
    @Override 
    public void processPayment(double amount){
        System.out.println("Processing INR payment via Payu" + amount);
    }
}


class StripeGateway implements PaymentGateway{
    @Override 
    public void processPayment(double amount){
        System.out.println("Processing INR payment via Stripe" + amount);
    }
}

class PaypalGateway implements PaymentGateway{
    @Override 
    public void processPayment(double amount){
        System.out.println("Processing INR payment via Paypal" + amount);
    }
}
interface Invoice {
    void generateInvoice();
}

class GSTInvoice implements Invoice{
    @Override 
    public void generateInvoice(){
        System.out.println("Generating GST invoice for India");
    }
}

class USInvoice implements Invoice{
    @Override 
    public void generateInvoice(){
        System.out.println("Generating  invoice for US");
    }
}

class IndiaFactory implements RegionalFactory{
    public  PaymentGateway createPaymentGateway(String gatewayType){
        switch(gatewayType.toLowerCase()){
            case "razorpay":
                return new RazorpayGateway();
            case "payu":
                return new PayuGateway();
            default:
                throw new IllegalArgumentException("Unsupportable payment gateway in India" + gatewayType);
        }
    }

    public  Invoice createInvoice(){
        return new GSTInvoice();
    }
}

class USFactory implements RegionalFactory{

    @Override 
    public  PaymentGateway createPaymentGateway(String gatewayType){
        switch(gatewayType.toLowerCase()){
            case "paypal":
                return new PaypalGateway();
            case "stripe":
                return new StripeGateway();
            default:
                throw new IllegalArgumentException("Unsupportable payment gateway in US" + gatewayType);
        }
    }

    @Override 
    public  Invoice createInvoice(){
        return new USInvoice();
    }
}

interface RegionalFactory{
    PaymentGateway createPaymentGateway(String gatewayType);
    Invoice createInvoice();
}


class CheckoutService{
    
    private PaymentGateway paymentGateway;
    private Invoice invoice;
    private String gatewayType;

    public CheckoutService(RegionalFactory factory,String gatewayType){
        this.gatewayType = gatewayType;
        this.paymentGateway = factory.createPaymentGateway(gatewayType);
        this.invoice = factory.createInvoice();
    }

    public void checkout(double amount){
        paymentGateway.processPayment(amount);
        invoice.generateInvoice();
    }
}

class Main{
    public static void main(String[] args) {
        CheckoutService checkoutService = new CheckoutService(new IndiaFactory(), "razorpay");
        checkoutService.checkout(200);
    }
}


// Here we are voilating the srp principle coz we are handling creation of the objects of which country  related to invoice 
// and as well as the  invoice genration and this is not a clean code...

// This doesnt scale well when we add a new country we need to add one more if else then the code for the other countries needs to be
// tested again.



// class CheckoutService{
//     private String gatewayType;
//     private String countryCode;

//     public CheckoutService(String gatewayType, String countryCode){
        
//         this.gatewayType = gatewayType;
//         this.countryCode = countryCode;

//     }

//     public void checkout(double amount){
        
//         if(countryCode == "IN"){
//             PaymentGateway gateway = IndiaFactory.createPaymentGateway(gatewayType);
//             gateway.processPayment(amount);
//             Invoice invoice = IndiaFactory.createInvoice();
//             invoice.generateInvoice();

//         }else{
//             PaymentGateway gateway1 = USFactory.createPaymentGateway(gatewayType);
//             gateway1.processPayment(amount);
        
//             Invoice invoice1 = USFactory.createInvoice();
//             invoice1.generateInvoice();
//         }

//     }
// }