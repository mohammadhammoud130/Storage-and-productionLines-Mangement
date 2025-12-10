import java.util.HashMap;

public class Product {
    private static int id=0;
    private String name;
    private HashMap <Item, Integer> requiredItems;

    public Product() {id = id++;
    }

    public Product(String name, HashMap<Item, Integer> requiredItems) {
        id = id++;
        this.name = name;
        this.requiredItems = requiredItems;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Item, Integer> getRequiredItems() {
        return requiredItems;
    }

    public void setRequiredItems(HashMap<Item, Integer> requiredItems) {
        this.requiredItems = requiredItems;
    }
}
