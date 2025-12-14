import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    private static final String filePath = "inventory.csv";
    private static Map<Integer, Item> inventory = new HashMap<>();


static{inventory = inventoryLoader();}

    private static HashMap<Integer,Item> inventoryLoader (){
        HashMap<Integer,Item> inventory = new HashMap<>();
        File file = new File(filePath);
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine(); // to skip the header of the "inventory.csv"
            while((line = reader.readLine()) != null){
                try{
                String[] fields = line.split(",");
                    if(fields.length < 6){
                        System.err.println("Invalid line format in line : "  + line);
                    }
                    int id =  Integer.parseInt(fields[0]);
                    String name = fields[1];
                    String category = fields[2];
                    double price = Double.parseDouble(fields[3]);
                    int quantity = Integer.parseInt(fields[4]);
                    int minThreshold = Integer.parseInt(fields[5]);
                    Item item = new Item(name,category,price,quantity,minThreshold);
                    inventory.put(id,item);
                } catch (NullPointerException e) {
                    System.err.println("Null value encountered in line: " + line);
                }
            }


        } catch (FileNotFoundException e) {
        System.err.println("File not found: " + filePath);
    } catch (IOException e) {
        System.err.println("Error reading file: " + e.getMessage());
    } catch (SecurityException e) {
        System.err.println("No permission to read file: " + filePath);
    }
        return inventory;
    }

    public void addItem (Item item) {
        inventory.put(item.getId(), item);

        try(BufferedWriter itemAdder = new BufferedWriter(new FileWriter(filePath,true))){
            itemAdder.write(item.getId()+","
            +item.getName()+","
            +item.getCategory()+","
            +item.getPrice()+","
            +item.getQuantity()+","
            +item.getMinThreshold()+"\n");


        }catch(FileNotFoundException e){
            System.err.println("File not found: " + filePath);
        }catch(IOException e){
            System.err.println("Error opening file: " + filePath);
        }catch(SecurityException e){
            System.err.println("No permission to open file: " + filePath);
        }
    }
                                            //متفير الboolean هون بحدد اذا زيادة او نقص
    public static void updateItemQTY(int id, int updateValue, boolean add) {
        if (inventory == null || inventory.isEmpty()) {
            throw new IllegalStateException("Inventory is empty! Cannot update item quantity.");
        }
        Item item = inventory.get(id);
        if (item == null) {
            throw new IllegalArgumentException("Item with ID " + id + " not found in inventory.");
        }

        item.updateStock(updateValue, add);

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("id,")) {
                    lines.add(line);
                    continue;
                }

                String[] fields = line.split(",");
                if (fields.length >= 6) {
                    int currentId = Integer.parseInt(fields[0]);
                    if (currentId == id) {
                        String updatedLine = item.getId() + ","
                                + item.getName() + ","
                                + item.getCategory() + ","
                                + item.getPrice() + ","
                                + item.getQuantity() + ","
                                + item.getMinThreshold();
                        lines.add(updatedLine);
                    } else {
                        lines.add(line);
                    }
                } else {
                    lines.add(line);
                }
            }
        }catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("No permission to read file: " + filePath);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        } catch (SecurityException e) {
            System.err.println("No permission to write file: " + filePath);
        }

        System.out.println("Quantity updated for item: " + item.getName() + " → New quantity: " + item.getQuantity());
    }

    public Map<Integer, Item> itemsUnderMinThreshold() {
        Map<Integer, Item> itemsUnderMinThreshold = new HashMap<>();

        if (inventory == null) {
            ErrorLogger.logWarning("Inventory is not initialized!");
            throw new IllegalStateException("Inventory is not initialized!");
        }
        if (inventory.isEmpty()) {
            ErrorLogger.logWarning("Inventory is empty. No items to check.");
            return itemsUnderMinThreshold;
        }

        for (Map.Entry<Integer, Item> entry : inventory.entrySet()) {
            Item item = entry.getValue();
            if (item == null) {
              ErrorLogger.logWarning("Null item found in inventory for ID " + entry.getKey());
                continue;
            }
            if (item.isUnderMinThreshold()) {
                itemsUnderMinThreshold.put(entry.getKey(), item);
            }
        }
        return itemsUnderMinThreshold;
    }

    public Map<Integer, Item> itemsOutOfStock() {
            Map<Integer, Item> itemsOutOfStock = new HashMap<>();

        if (inventory == null) {
          ErrorLogger.logWarning("Inventory is not initialized!");
            throw new IllegalStateException("Inventory is not initialized!");
        }
        if (inventory.isEmpty()) {
            ErrorLogger.logWarning("Inventory is empty. No items to check.");
            return itemsOutOfStock;
        }

        for (Map.Entry<Integer, Item> entry : inventory.entrySet()) {
            Item item = entry.getValue();
            if (item == null) {
               ErrorLogger.logWarning("Null item found in inventory for ID " + entry.getKey());
                continue;
            }
            if (item.getQuantity() <= 0) {
                itemsOutOfStock.put(entry.getKey(), item);
            }
        }
        return itemsOutOfStock;
    }

    public Map<Integer, Item> availableItems() {
        HashMap<Integer, Item> availableItems = new HashMap<>();

        if (inventory == null) {
            ErrorLogger.logWarning("Inventory is not initialized!");
            throw new IllegalStateException("Inventory is not initialized!");
        }
        if (inventory.isEmpty()) {
            ErrorLogger.logWarning("Inventory is empty. No items to check.");
            return availableItems;
        }

        for (Map.Entry<Integer, Item> entry : inventory.entrySet()) {
            Item item = entry.getValue();
            if (item == null) {
                ErrorLogger.logWarning("Null item found in inventory for ID " + entry.getKey());
                continue;
            }
            if (!item.isUnderMinThreshold() && item.getQuantity() > 0) {
                availableItems.put(entry.getKey(), item);
            }
        }
        return availableItems;
    }


    public static Map<Integer, Item> getInventory() {
        return inventory;
    }

}
