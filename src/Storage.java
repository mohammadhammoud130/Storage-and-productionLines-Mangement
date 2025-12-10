import java.io.*;
import java.util.HashMap;
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
}
