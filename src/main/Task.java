package main;

public class Task {

    public String description = "";
    public int x, y;
    public boolean completed;
    public int id;

    public Task(int x, int y, String description, int id) {

        this.x = x;
        this.y = y;
        this.description = description;
        this.id = id;
    }

}
