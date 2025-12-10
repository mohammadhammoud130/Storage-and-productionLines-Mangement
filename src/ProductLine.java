import java.util.ArrayList;

public class ProductLine {
    private static int id = 0;
    private String name;
    private String status;
    private ArrayList<Task> tasks;

    public ProductLine() {id = id++;    }

    public ProductLine(String name, String status, ArrayList<Task> tasks) {
        id = id++;
        this.name = name;
        this.status = status;
        this.tasks = tasks;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
}
