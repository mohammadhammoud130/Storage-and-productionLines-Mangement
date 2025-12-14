import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Task {
    private static int id = 0;
    private Product requiredProduct;
    private int requiredQuantity;
    private String client;
    private Date startDate;
    private Date deliveryDate;
    private String status;
    private double progress;
    private ProductLine assignedLine;


    public Task() {id = id++;}

    public Task(Product requiredProduct, int requiredQuantity, String client, Date startDate, Date deliveryDate, String status, double progress, ProductLine assignedLine) {
        id = id++;
        this.requiredProduct = requiredProduct;
        this.requiredQuantity = requiredQuantity;
        this.client = client;
        this.startDate = startDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.progress = progress;
        this.assignedLine = assignedLine;
    }

    public boolean checkMaterials() {
        // to mak sure that there is  items in the inventory and its not empty before the check
        if (Storage.getInventory() == null || Storage.getInventory().isEmpty()) {
            throw new IllegalStateException("Inventory in Storage is empty! Cannot check materials.");
        }

        HashMap<Item, Integer> requiredItems = requiredProduct.getRequiredItems();

        for (Map.Entry<Item, Integer> entry : requiredItems.entrySet()) {
            Item item = entry.getKey();
            int qtyPerUnit = entry.getValue();//كمية المطلوبة من مادة واحدة
            int totalNeed = qtyPerUnit * requiredQuantity;

            if (item.getQuantity() < totalNeed) {
                System.out.println("Not enough quantity of the item: " + item.getName());
                System.out.println("Required: " + totalNeed + " | Available: " + item.getQuantity());
                return false;
            }

            if ((item.getQuantity() - totalNeed) < item.getMinThreshold()) {
                System.out.println("Warning: Stock of " + item.getName() + " will drop below minimum threshold!");
                return false;
            }

        }

        for(Map.Entry<Item,Integer> entry : requiredItems.entrySet()){
            int totalNeed = entry.getValue() * requiredQuantity ;
         Storage.updateItemQTY(entry.getKey().getId() , totalNeed , false);
        }

        System.out.println("All materials are available for the task!");
        return true;

    }

    public int getId() {
        return id;
    }

    public Product getRequiresdProduct() {
        return requiredProduct;
    }

    public void setRequiredProduct(Product requiresdProduct) {
        this.requiredProduct = requiresdProduct;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(int requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public ProductLine getAssignedLine() {
        return assignedLine;
    }

    public void setAssignedLine(ProductLine assignedLine) {
        this.assignedLine = assignedLine;
    }
}
