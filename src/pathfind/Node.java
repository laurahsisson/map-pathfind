package pathfind;

import java.util.ArrayList;

/**
 *
 * @author Ben
 */
public class Node {

    private final int row;
    private final int col;
    private Node parent;
    private Map map;
    private Iterator it;
    private ArrayList<Node> children;

    public Node(int row, int col, Iterator it, Map map) { //Creates a node

        this.row = row;
        this.col = col;
        this.it = it;
        this.map = map;
        children = new ArrayList<>();

    }
    public Node(int row, int col) { //Creates a parentless node used to represent a position
        this.row = row;
        this.col = col;
        children = new ArrayList<>();
    }

    public void add(Node n) {
        children.add(n);
    }

    public boolean isPeak() { //Is this the top
        return parent == null;
    }

    public boolean isEnd() { //Is this a finishing root
        return children.isEmpty();//north == null && south == null && east == null && west == null;
    }
    @Override
    public boolean equals(Object n) {
        return row == ((Node)n).row && col == ((Node)n).col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getNode(Node n) {//Gets a full node off a coordinate node
        if (equals(n)) {
            return this;
        } else {
            for (Node test : children) {
                
                Node hold = test.getNode(n);
                if (hold != null) {
                    return hold;
                }
            }
        }
        return null;
    }
    public void getNeighbors(){ //Adds all the neighbors to it
        Node north = it.isValid(row - 1, col);
        if (north != null) { 
            north.setParent(this);
            add(north);
            it.add(north);
        }

        Node south = it.isValid(row + 1, col);
        if (south != null) {
            south.setParent(this);
            add(south);
            it.add(south);
        }

        Node east = it.isValid(row, col - 1);
        if (east != null) {
            east.setParent(this);
            add(east);
            it.add(east);
        }

        Node west = it.isValid(row, col + 1);
        if (west != null) {
            west.setParent(this);
            add(west);
            it.add(west);
        }
    }
    
    public boolean contains(Node n) {
        boolean result = false;
        if (equals(n)) {
            result = true;
        } else if (isEnd()) {
            result = equals(n);
        } else {
            for (Node test : children) {
                if (test.contains(n)) {
                    result = true;
                }
            }
        }
        return result;
    }

    public void addToList(ArrayList<Node> nodes) {
        nodes.add(this);
        if (parent != null) {
            parent.addToList(nodes);
        }
    }

    public String toString() {
        return row + " , " + col;
    }
    
    public void PrintChain(){
        System.out.println(this);
        for (Node n : children){
            n.PrintChain();
        }
    }
}
