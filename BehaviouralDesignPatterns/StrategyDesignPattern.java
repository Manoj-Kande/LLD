

interface IWakableRobot{
    void walk();
}

class NormalWalk implements IWakableRobot{
    public void walk(){
        System.out.println("Walking normally...");
    }
}

class NoWalk implements IWakableRobot{
    public void walk(){
        System.out.println("Cant work ...");
    }
}

// --- Strategy Interface for Talk ---
interface ITalkableRobot {
    void talk();
}

// --- Concrete Strategies for Talk ---
class NormalTalk implements ITalkableRobot {
    public void talk() {
        System.out.println("Talking normally...");
    }
}

class NoTalk implements ITalkableRobot {
    public void talk() {
        System.out.println("Cannot talk.");
    }
}

// --- Strategy Interface for Fly ---
interface IFlyableRobot {
    void fly();
}

class NormalFly implements IFlyableRobot {
    public void fly() {
        System.out.println("Flying normally...");
    }
}

class NoFly implements IFlyableRobot {
    public void fly() {
        System.out.println("Cannot fly.");
    }
}


abstract class Robot{
    protected IWakableRobot wakableRobot;
    protected ITalkableRobot talkableRobot;
    protected IFlyableRobot flyableRobot;

    public Robot(IWakableRobot wakableRobot,ITalkableRobot talkableRobot,IFlyableRobot flyableRobot){
        this.wakableRobot = wakableRobot;
        this.talkableRobot = talkableRobot;
        this.flyableRobot = flyableRobot;
    }

    public void fly(){
        flyableRobot.fly();
    }

    public void talk(){
        talkableRobot.talk();
    }

    public void walk(){
        wakableRobot.walk();
    }

    public abstract void projection(); // Abstract method for subclasses

}

class CompanionRobot extends Robot {
    public CompanionRobot(IWakableRobot w, ITalkableRobot t, IFlyableRobot f) {
        super(w, t, f);
    }

    public void projection() {
        System.out.println("Displaying friendly companion features...");
    }
}

class WorkerRobot extends Robot {
    public WorkerRobot(IWakableRobot w, ITalkableRobot t, IFlyableRobot f) {
        super(w, t, f);
    }

    public void projection() {
        System.out.println("Displaying worker efficiency stats...");
    }
}

public class StrategyDesignPattern {

    static void main() {
        Robot robot1 = new CompanionRobot(new NormalWalk(), new NormalTalk(), new NoFly());
        robot1.walk();
        robot1.talk();
        robot1.fly();
        robot1.projection();

        System.out.println("--------------------");

        Robot robot2 = new WorkerRobot(new NoWalk(), new NoTalk(), new NormalFly());
        robot2.walk();
        robot2.talk();
        robot2.fly();
        robot2.projection();
    }
}
