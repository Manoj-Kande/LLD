
class User{
    // user fields and data
    String name;

    public User(String name){
        this.name = name;
    }

    public void validteUser(User user){
        UserValidation.validate(user);
    }
}

class UserValidation{
    public static void validate(User user){
        System.out.println("validating user = "+ user.name);
    }
}


interface IDocumentService{
    String getDocument(String documentId,User user);
}

class DocumentServiceProxy implements IDocumentService{

    private final RemoteDocumentService remoteDocumentService;

    public DocumentServiceProxy(){
        remoteDocumentService = new RemoteDocumentService();
    }

    @Override
    public String getDocument(String documentId, User user) {
        // 1. Validate the user
        user.validteUser(user);
        // 2. checks cache
        System.out.println("Cache logic is being done");

        // 3 logs access
        System.out.println("logging the action");

        // our main call is being done after all our work is being done..
        return remoteDocumentService.getDocument(documentId, user);

    }
}

class RemoteDocumentService implements IDocumentService{
    @Override
    public String getDocument(String documentId, User user) {
        System.out.println("Fetching Document from the remote server");
        return "document-1";
    }
}


public class ProxyDesignPattern {
    static void main() {
        IDocumentService proxyDesignPattern = new DocumentServiceProxy();
        proxyDesignPattern.getDocument("1", new User("manoj"));
    }
}
