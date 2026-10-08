
import java.util.ArrayList;
import java.util.List;

interface Observer{
    void notifyMe();
}

interface Subject{
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
}

class YoutubeChannel implements Subject{

    List<Observer> observers;

    public YoutubeChannel(){
        observers = new ArrayList<Observer>();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifySubscribe(String message){
        for(Observer observer : observers){
            observer.notifyMe();
        }
    }

    public void uploadVideo(String videoId){
        notifySubscribe(videoId);
    }
}

class Client implements Observer{

    @Override
    public void notifyMe() {
        System.out.println("client received the notification...");
    }
}


public class ObserverDesignPattern {
    static void main() {
        YoutubeChannel youtubeChannel = new YoutubeChannel();
        youtubeChannel.addObserver(new Client());
        youtubeChannel.uploadVideo("https://www.youtube.com");
    }
}
