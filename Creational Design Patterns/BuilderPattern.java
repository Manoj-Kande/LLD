import java.util.List;

class BurgerMeal{
    // requried

    private  final String bunType;
    private final String patty;

    //Optional
    private final boolean hasCheese;
    private final List<String> toopings;
    private final String side;
    private final String drink;

    public BurgerMeal(BurgerBuilder builder){
        this.bunType = builder.bunType;
        this.patty = builder.patty;
        this.hasCheese = builder.hasCheese;
        this.toopings = builder.toopings;
        this.side = builder.side;
        this.drink = builder.drink;
    }

    public static class BurgerBuilder{

         private  final String bunType;
         private final String patty;

        //Optional
        private  boolean hasCheese;
        private  List<String> toopings;
        private  String side;
        private  String drink;


        public BurgerBuilder(String bunType, String patty){
            this.bunType = bunType;
            this.patty = patty;
        }
        
        public BurgerBuilder withCheese(boolean hasCheese){
            this.hasCheese = hasCheese;
            return this;
        }

        public BurgerBuilder withToppings(List<String> toppings){
            this.toopings = toppings;
            return this;
        }

        public BurgerBuilder withSide(String side){
            this.side = side;
            return this;
        }

        public BurgerBuilder withDrink(String drink){
            this.drink = drink;
            return this;
        }

        public BurgerMeal build(){
            return new BurgerMeal(this);
        }


    }
}

class Main{
    public static void main(String[] args) {
        BurgerMeal meal = new BurgerMeal
        .BurgerBuilder("wheat", "VEG")
        .withCheese(true)
        .withDrink("Coke")
        .build();
        System.out.println(meal);

    }
}