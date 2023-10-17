package commons;

public class RestorantEnums {

    public enum Meal {
        greek_salad ("Greek salad", 10.0f, "Typical Greek salad"),
        omelette    ("Omelette", 9.0f, "Ham & cheese omelette with rocca salad"),
        chiken_pasta("Chiken pasta",9.0f, "Chicken pasta with mushroom and cream"),
        steak_with_fries("Steak & Fries", 10.5f, "Beef steak with french fries and calslaw salad");

        private String label;
        private float price;
        private String ingredients;

        Meal(String label, float price, String ingredients) {
            this.label = label;
            this.price = price;
            this.ingredients = ingredients;
        }
    }

    public enum Beverage {
        water, orange_juice, apple_juice, Coke, wine
    }
}
