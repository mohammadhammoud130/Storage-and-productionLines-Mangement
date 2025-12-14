public class Item {
    private  static int  id =0;
    private String name;
    private String category;
    private double price;
    private int quantity;
    private int minThreshold;

    public Item() {id=id++;}

    public Item(String name, String category, double price, int quantity, int minThreshold) {
        id = id++;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.minThreshold = minThreshold;
    }

    public boolean isUnderMinThreshold(){
        return quantity > minThreshold;
    }

    public void updateStock(int value, boolean add) {
        if (add) {
            this.quantity += value;
        } else {
            if (this.quantity < value) {
                throw new IllegalArgumentException("Not enough stock to reduce for item: " + name);
            }
            this.quantity -= value;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(int minThreshold) {
        this.minThreshold = minThreshold;
    }
}
