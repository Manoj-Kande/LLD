
class Request{

}

abstract class Handler{
    private Handler nextHandler;

    public Handler(Handler nextHandler){
        this.nextHandler = nextHandler;
    }

    abstract boolean canHandler(Request request);
    Handler getNextHandler(){
        return this.nextHandler;
    }

    abstract  void handleRequest(Request request);

}
class Level1Handler extends Handler{

    public Level1Handler(Handler nextHandler){
        super(nextHandler);
    }

    @Override
    boolean canHandler(Request request) {
        return false;
    }

    @Override
    void handleRequest(Request request) {
        System.out.println("handling the Level1Handler");
        if(canHandler(request)){

        }else{
            getNextHandler().handleRequest(request);
        }
    }
}

class Level2Handler extends Handler{

    public Level2Handler(Handler nextHandler){
        super(nextHandler);
    }

    @Override
    boolean canHandler(Request request) {
        return false;
    }

    @Override
    void handleRequest(Request request) {
        System.out.println("handling the Level2Handler");
        if(canHandler(request)){

        }else{
            getNextHandler().handleRequest(request);
        }
    }
}

class Level3Handler extends Handler{

    public Level3Handler(Handler nextHandler){
        super(nextHandler);
    }

    @Override
    boolean canHandler(Request request) {
        return false;
    }

    @Override
    void handleRequest(Request request) {
        System.out.println("handling the Level3Handler");
        if(canHandler(request)){

        }else{
            Handler nextHandler = getNextHandler();
            if(nextHandler != null){
                getNextHandler().handleRequest(request);
            }

        }
    }
}
public class ChainOfResponsibilityDesignPattern {
    static void main() {
        Level3Handler level3Handler = new Level3Handler(null);
        Level2Handler level2Handler = new Level2Handler(level3Handler);
        Level1Handler level1Handler = new Level1Handler(level2Handler);
        level1Handler.handleRequest(new Request());
    }
}
