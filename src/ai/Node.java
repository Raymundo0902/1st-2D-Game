package ai;

public class Node {

    Node parent;
    public int col;
    public int row;
    int gCost; // the distance between the starting to the current position
    int hCost; // the distance between the current to goal position
    int fCost; // the sum of (g+h) costs.
    boolean solid;
    boolean open;
    boolean checked;

    public Node(int col, int row) {
        this.col = col;
        this.row = row;
    }



}
