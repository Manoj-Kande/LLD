
/*
interface NotificationService{
    void send(String message);
}


class BasicNotificationService implements NotificationService{
    public void send(String message){
        System.out.println("Notification is being sent " + message);
    }
}

//abstract class NotificationDecorator implements NotificationService{
//
//    protected NotificationService notificationService;
//
//    public NotificationDecorator(NotificationService notificationService){
//        this.notificationService = notificationService;
//    }
//
//    @Override
//    public void send(String message) {
//        notificationService.send(message);
//    }
//}

abstract class NotificationDecorator implements NotificationService {

    protected NotificationService notificationService;

    public NotificationDecorator(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public abstract void send(String message);
}

class LoggingDecorator extends  NotificationDecorator{

    public LoggingDecorator(NotificationService notificationService){
        super(notificationService);
    }

    @Override
    public void send(String message){
        System.out.println("Logging is being done for the message = " + message);
        notificationService.send(message);
    }
}

class EncryptionDecorator extends NotificationDecorator{

    public EncryptionDecorator(NotificationService notificationService){
        super(notificationService);
    }
    @Override
    public void send(String message){
        System.out.println("Encryption is being done for the message = " + message);
        notificationService.send(message);

    }
}

public class DecoratorDesignPattern {
    static void main() {
        NotificationService service =
                new EncryptionDecorator(
                        new LoggingDecorator(
                                new BasicNotificationService()
                        )
                );
        service.send("Hello");
    }
}
*/


///*
interface NotificationService {
    void send(String message);
}


// ===============================
// CORE / ORIGINAL OBJECT
// ===============================

//class BasicNotificationService implements NotificationService {
//
//    @Override
//    public void send(String message) {
//        System.out.println("Notification is being sent: " + message);
//    }
//}

class BasicNotificationService implements NotificationService {

    private int attempts = 0;

    @Override
    public void send(String message) {

        attempts++;

        System.out.println("Attempt: " + attempts);

        if (attempts < 3) {
            throw new RuntimeException("Notification service failed!");
        }

        System.out.println("Notification sent successfully: " + message);
    }
}

// ===============================
// CONCRETE DECORATOR #3
// ===============================
class RetryDecorator implements NotificationService {

    private final NotificationService notificationService;

    public RetryDecorator(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void send(String message) {

        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            try {
                notificationService.send(message);
                return; // success → stop completely
            }

            catch (Exception e) {
                System.out.println("Attempt " + attempt + " failed");

                if (attempt == maxAttempts) {
                    System.out.println("Notification failed after " + maxAttempts + " attempts");
                }
            }
        }
    }
}


// ===============================
// CONCRETE DECORATOR #1
// ===============================

class LoggingDecorator implements NotificationService {

    private final NotificationService notificationService;

    public LoggingDecorator(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void send(String message) {

        // 1. Add our own behavior
        System.out.println("Logging: " + message);

        // 2. Delegate to the object we are wrapping
        notificationService.send(message);
    }
}


// ===============================
// CONCRETE DECORATOR #2
// ===============================

class EncryptionDecorator implements NotificationService {

    private final NotificationService notificationService;

    public EncryptionDecorator(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void send(String message) {

        // 1. Add our own behavior
        System.out.println("Encryption: " + message);

        // 2. Delegate to the object we are wrapping
        notificationService.send(message);
    }
}


// ===============================
// MAIN
// ===============================

public class DecoratorDesignPattern {

    public static void main(String[] args) {

        NotificationService service =
                new RetryDecorator(
                        new LoggingDecorator(
                                new BasicNotificationService()
                        )
                );

        service.send("Hello");
    }
}
//*/