/**
 * FactoryDesignPattern
 */
public interface Logistics {

    public void send();
    
}


class Road implements Logistics{
    @Override 
    public void send(){
        System.out.println("Sending by road Logic");
    }
}

class Air implements Logistics{
    @Override 
    public void send(){
        System.out.println("Sending by Air Logic");
    }
}

class Train implements Logistics{
    @Override 
    public void send(){
        System.out.println("Sending by Train Logic");
    }
}


class LogisticsFactory{
    public static Logistics getLogistics(String mode){
        if(mode == "road"){
            return new Road();
        }else if(mode == "Air"){
            return new Air();
        }
        else return new Train();
    }
}

class LogisticsService{
    public static void send(String mode){
        Logistics logistics = LogisticsFactory.getLogistics("Air");
        logistics.send();
    }

    public static void main(String[] args) {
        send("Air");
    }
}