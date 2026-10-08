

import java.util.ArrayList;
import java.util.List;

interface NotificationStrategy{
    void sendNotification();
}

class EMailNotifciation implements NotificationStrategy{
    public void sendNotification(){
        System.out.println("This is an email notification");
    }
}

class SMSNotificiation implements NotificationStrategy{
    public void sendNotification(){
        System.out.println("This is a sms notification");
    }
}

class PushNotificiation implements NotificationStrategy{
    public void sendNotification(){
        System.out.println("This is a push notification");
    }
}

class RetryNotificiation implements NotificationStrategy{
    public void sendNotification(){
        System.out.println("This is a retry notification");
    }
}


class NotificationService{
    NotificationStrategy notificationStrategy;

    public NotificationService(NotificationStrategy notificationStrategy){
        this.notificationStrategy = notificationStrategy;
    }

    public void sendNotification(){
        notificationStrategy.sendNotification();
    }
}


abstract class NotificationDecorator implements NotificationStrategy{

    NotificationStrategy notificationStrategy;

    public NotificationDecorator(NotificationStrategy notificationStrategy){
        this.notificationStrategy = notificationStrategy;
    }

    public void sendNotification(){
        if(notificationStrategy != null){
            notificationStrategy.sendNotification();
        }
    }


}

class EmailNotificationDecorator extends NotificationDecorator{

    EMailNotifciation emailNotifciation;
    public EmailNotificationDecorator(NotificationStrategy notificationStrategy){
        super(notificationStrategy);
        emailNotifciation = new EMailNotifciation();
    }

    public void sendNotification(){
        emailNotifciation.sendNotification();
        if(notificationStrategy != null){
            notificationStrategy.sendNotification();
        }
    }
}

class SMSNotificationDecorator extends NotificationDecorator{
    SMSNotificiation smsNotificiation;
    public SMSNotificationDecorator(NotificationStrategy notificationStrategy){
        super(notificationStrategy);
        smsNotificiation = new SMSNotificiation();

    }
    public void sendNotification(){
        smsNotificiation.sendNotification();
        if(notificationStrategy != null){
            notificationStrategy.sendNotification();
        }
    }
}

class PushNotificationDecorator extends NotificationDecorator{

    PushNotificiation pushNotificiation;

    public PushNotificationDecorator(NotificationStrategy notificationStrategy){
        super(notificationStrategy);
        pushNotificiation = new PushNotificiation();
    }
    public void sendNotification(){
        pushNotificiation.sendNotification();
        notificationStrategy.sendNotification();
    }
}






abstract class  NotificationObserver  {

    NotificationDecorator notificationDecorator;
    public NotificationObserver(NotificationDecorator notificationDecorator){
        this.notificationDecorator = notificationDecorator;
    }

    public void notifySubscriber(){
        this.notificationDecorator.sendNotification();
    }

    abstract void notifyMe();
}

interface  observerPrinciples{
     void subscribe(NotificationObserver NotificationObserver);
     void unSubscribe(NotificationObserver NotificationObserver);
     void notify_All();

}

class UserObserver extends NotificationObserver {

    public UserObserver(NotificationDecorator notificationDecorator) {
        super(notificationDecorator);
    }

    @Override
    public void notifyMe() {
        System.out.println("UserObserver notified");
    }


}

class RestaurantDashboard extends NotificationObserver {
    public RestaurantDashboard(NotificationDecorator notificationDecorator) {
        super(notificationDecorator);
    }

    @Override
    public void notifyMe() {
        System.out.println("notified to the RestaurantDashboard");
    }
}

class DeliveryPartnerApp extends NotificationObserver {

    public DeliveryPartnerApp(NotificationDecorator notificationDecorator) {
        super(notificationDecorator);
    }

    @Override
    public void notifyMe() {
        System.out.println("notified to the DeliveryPartnerApp");
    }
}


class NotifySubscribers implements observerPrinciples{

    List<NotificationObserver> subscribers;

    public NotifySubscribers(){
        this.subscribers = new ArrayList<NotificationObserver>();
    }


    @Override
    public void subscribe(NotificationObserver NotificationObserver) {
        subscribers.add(NotificationObserver);
    }

    @Override
    public void unSubscribe(NotificationObserver NotificationObserver) {
        subscribers.remove(NotificationObserver);
    }

    @Override
    public void notify_All() {
        for(NotificationObserver NotificationObserver : subscribers){
            NotificationObserver.notifySubscriber();
            NotificationObserver.notifyMe();
        }
    }
}


class RequestHandler{
    String  reason;
    public RequestHandler(String reason){
        this.reason = reason;
    }
    public String getReason(){
        return reason;
    }
    public void setReason(String reason){
        this.reason = reason;
    }
}

abstract class HandlerRequest{
    HandlerRequest nextHandler;

    public HandlerRequest(HandlerRequest nextHandler){
        this.nextHandler = nextHandler;
    }
    abstract void handleRequest(RequestHandler request);

    HandlerRequest getNextHandler(){
        return this.nextHandler;
    }
}

class Bot extends HandlerRequest{

    public Bot(HandlerRequest nextHandler) {
        super(nextHandler);
    }

    @Override
    public void handleRequest(RequestHandler request) {
        System.out.println("Bot handler request");
        if(request.reason.equals("PaymentIssue") || request.reason.equals("Manager")){
            HandlerRequest nextHandler = getNextHandler();
            if(nextHandler != null){
                 nextHandler.handleRequest(request);
            }

        }
    }
}

class CustomerExecutive extends HandlerRequest{

    public CustomerExecutive(HandlerRequest nextHandler) {
        super(nextHandler);
    }

    @Override
    public void handleRequest(RequestHandler request) {
        System.out.println("CustomerExecutive handler request");
        if(request.reason.equals("Manager")){
            HandlerRequest nextHandler = getNextHandler();
            if(nextHandler != null){
                nextHandler.handleRequest(request);
            }
        }
    }
}

class Manager extends HandlerRequest {

    public Manager(HandlerRequest nextHandler) {
        super(nextHandler);
    }

    @Override
    public void handleRequest(RequestHandler request) {
        System.out.println("Manager handler request");
    }
}

class Order {

    private OrderState state;

    public Order() {
        this.state = new CreatedState();
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public OrderState getState() {
        return state;
    }

    public void place() {
        state.place(this);
    }

    public void confirm() {
        state.confirm(this);
    }

    public void startPreparing() {
        state.startPreparing(this);
    }

    public void ship() {
        state.ship(this);
    }

    public void deliver() {
        state.deliver(this);
    }

    public void cancel() {
        state.cancel(this);
    }
}

interface OrderState {

    void place(Order order);

    void confirm(Order order);

    void startPreparing(Order order);

    void ship(Order order);

    void deliver(Order order);

    void cancel(Order order);
}

abstract class BaseOrderState implements OrderState {

    @Override
    public void place(Order order) {
        throw new IllegalStateException("Cannot place order in current state");
    }

    @Override
    public void confirm(Order order) {
        throw new IllegalStateException("Cannot confirm order in current state");
    }

    @Override
    public void startPreparing(Order order) {
        throw new IllegalStateException("Cannot start preparing in current state");
    }

    @Override
    public void ship(Order order) {
        throw new IllegalStateException("Cannot ship order in current state");
    }

    @Override
    public void deliver(Order order) {
        throw new IllegalStateException("Cannot deliver order in current state");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Cannot cancel order in current state");
    }
}

class CreatedState extends BaseOrderState {

    @Override
    public void place(Order order) {

        System.out.println("Order placed");

        order.setState(new PlacedState());
    }

    @Override
    public void cancel(Order order) {

        System.out.println("Order cancelled");

        order.setState(new CancelledState());
    }
}
class PlacedState extends BaseOrderState {

    @Override
    public void confirm(Order order) {

        System.out.println("Order confirmed");

        order.setState(new ConfirmedState());
    }


    @Override
    public void cancel(Order order) {

        System.out.println("Order cancelled");

        order.setState(new CancelledState());
    }
}
class ConfirmedState extends BaseOrderState {

    @Override
    public void startPreparing(Order order) {

        System.out.println("Order preparation started");

        order.setState(new PreparingState());
    }

    @Override
    public void cancel(Order order) {

        System.out.println("Order cancelled");

        order.setState(new CancelledState());
    }

}
class PreparingState extends BaseOrderState {

    @Override
    public void ship(Order order) {

        System.out.println("Order shipped");

        order.setState(new OutForDeliveryState());
    }
}

class OutForDeliveryState extends BaseOrderState {

    @Override
    public void deliver(Order order) {

        System.out.println("Order delivered");

        order.setState(new DeliveredState());
    }
}

class DeliveredState extends BaseOrderState {

}

class CancelledState extends BaseOrderState {

}

public class Assignment {
    static void main() {

//        NotifySubscribers subscribers = new NotifySubscribers();
//        subscribers.subscribe(new DeliveryPartnerApp(new SMSNotificiation()));
//        subscribers.notify_All();

//        NotifySubscribers subscribers2 = new NotifySubscribers();
//        NotificationDecorator dec = new SMSNotificationDecorator(new EmailNotificationDecorator(null));
//        NotificationObserver observer = new UserObserver(dec);
//        subscribers2.subscribe(observer);
//        subscribers2.notify_All();

//        Manager manager = new Manager(null);
//        CustomerExecutive customerExecutive = new CustomerExecutive(manager);
//        HandlerRequest bot = new Bot(customerExecutive);
//        bot.handleRequest(new RequestHandler("PaymentIssue"));

        Order order = new Order();

        order.place();
        order.cancel();
        order.confirm();
        order.startPreparing();
        order.ship();

        order.deliver();



    }
}
