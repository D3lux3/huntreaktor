import java.util.ArrayList;

public class Node  implements Comparable<Node>  {

    private Node parent;
    private int x;
    private int y;
    private int distance;
    private Type type;
    private ArrayList<Node> neighbours;

    public Node(int x, int y, Type type){
        this.x = x;
        this.y = y;
        this.type = type;
        parent = null;
        this.distance = Integer.MAX_VALUE;
        this.neighbours = new ArrayList<>();
    }

    public void addNeighbour(Node node) {
        this.neighbours.add(node);
    }

    public void setParent(Node node) {
        this.parent = node;
    }

    public void setDistance(int distance){
        this.distance = distance;
    }

    public ArrayList<Node> getNeighbours() {
        return this.neighbours;
    }

    public void setType(Type type) {
        this.type = type;
    }
    public Type getType() {
        return this.type;
    }
    public Node getParent() {
        return this.parent;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance() {
        return this.distance;
    }

    @Override
    public int compareTo(Node n) {
        if (this.distance > n.getDistance()) {
            return 1;
        } else if (distance < n.getDistance()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        if (this.type == Type.GOAL) {
            return "F";
        } else if (this.type == Type.START) {
            return "S";
        } else if (this.type == Type.WALKABLE) {
            return "w";
        } else if (this.type == Type.WALL) {
            return "#";
        }
        return " ";
    }

}
