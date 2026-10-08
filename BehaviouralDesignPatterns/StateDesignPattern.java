
interface AtmState{
    void insertCard();
    void pressDispenseMoney();
    void cancel();
    void ejectCard();
}

class NoCardState implements  AtmState{

    public AtmMachine atm;
    public NoCardState(AtmMachine atmState){
        this.atm = atmState;
    }

    public void insertCard() {
        System.out.println("Card inserted");
        //NoCardState ------> HasCardState
        atm.setState(atm.getHasCardState());

    }

    public void pressDispenseMoney() {
        System.out.println("Insert card first");
    }

    public void cancel() {
        System.out.println("No transaction");
    }

    public void ejectCard() {
        System.out.println("No card");
    }
}

class HasCardState implements AtmState{

    public AtmMachine atm;
    public HasCardState(AtmMachine atmState){
        this.atm = atmState;
    }

    public void insertCard() {
        System.out.println("Card already inserted");
    }

    public void pressDispenseMoney() {
        System.out.println("Dispensing started");

    }

    public void cancel() {
        System.out.println("Cancelled");
        ejectCard();
    }

    public void ejectCard() {
        System.out.println("Card ejected");
        //HAS_CARD_STATE ===> NO_CARD_STATE
        atm.setState(atm.getNoCardState());

    }
}
class MoneyDispenserState implements AtmState{

    public AtmMachine atmState;
    public MoneyDispenserState(AtmMachine atmState){
        this.atmState = atmState;
    }

    public void insertCard() {
        System.out.println("Please wait");
    }

    public void pressDispenseMoney() {
        System.out.println("Already dispensing");
    }

    public void cancel() {
        System.out.println("Cannot cancel dispensing");
    }

    public void ejectCard() {
        System.out.println("Please wait");
    }
}

class AtmMachine{
    AtmState currentState ;
    AtmState noCardState;
    AtmState hasCardState;
    AtmState moneyDispenserState;

    public AtmMachine(){
        noCardState = new NoCardState(this);
        currentState = noCardState;
        hasCardState = new HasCardState(this);
        moneyDispenserState = new MoneyDispenserState(this);
    }

    void setState(AtmState newState){
        this.currentState = newState;
    }

    AtmState getNoCardState(){
        return noCardState;
    }

    AtmState getHasCardState(){
        return hasCardState;
    }

    void withdraw(){

    }

    AtmState getMoneyDispensingState(){
        return moneyDispenserState;
    }
    void pleaseInsertCard() {
        currentState.insertCard();
    }
    void dispenseCash() {
        currentState.pressDispenseMoney();
    }
    void haltDisense() {
        currentState.cancel();
    }
    void removeCard() {
        currentState.ejectCard();
    }
    void completeDispense() {
        if (currentState != moneyDispenserState) {
            System.out.println("Nothing to complete");
            return;
        }
        System.out.println("Cash dispensed: 100");
        currentState = hasCardState;
        removeCard();
    }

}

public class StateDesignPattern {
    static void main() {
        AtmMachine atm = new AtmMachine();
        atm.dispenseCash();
        atm.pleaseInsertCard();
        atm.pleaseInsertCard();
        atm.dispenseCash();
        atm.dispenseCash();
        atm.haltDisense();
        atm.completeDispense();
        atm.pleaseInsertCard();
        atm.haltDisense();
        atm.completeDispense();
    }
}
