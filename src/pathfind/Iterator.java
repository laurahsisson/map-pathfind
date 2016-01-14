package pathfind;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Ben
 */
public class Iterator {

    LinkedList<Node> lastGen;
    LinkedList<Node> curGen;
    LinkedList<Node> nextGen;
    Node peak;
    Node end;
    Map map;
    
    public Iterator(Map map, Node end) { //Set up the iterator, taking a map and an end node
        this.map = map;
        this.end = end;
        lastGen = new LinkedList<>();
        curGen = new LinkedList<>();
        nextGen = new LinkedList<>();
    }
    /*Called each generation, goes through ever node in current generation and add that nodes neighbours to the list
    then updates the generationa lists*/
    public void doGen() { 
        for (Node n : curGen) {
            n.getNeighbors();
        }
        lastGen.addAll(curGen);
        curGen = (LinkedList<Node>) nextGen.clone();
        nextGen.clear();
    }
 
    public void setPeak(Node peak) {//Starts up the algorithm, called by PathFind
        this.peak = new Node(peak.getRow(), peak.getCol(), this, map);
        doLoop();
    }

    private void doLoop() {
        curGen.add(peak);
        while (!lastGen.contains(end)) {
            doGen();
        }
    }

    public Node isValid(int row, int col) { //Check if this node is an empty spaces on the map that we have not visited
        if (map.isValid(row, col)) {
            Node n = new Node(row, col, this, map);
            if (!(lastGen.contains(n) || curGen.contains(n) || nextGen.contains(n))) {
                return n;
            }
        }
        return null;
    }

    public void add(Node n) {
        nextGen.add(n);
    }

    public ArrayList<Node> getPath() { //Returns an list starting at the end node and going to the peak node
        ArrayList<Node> path = new ArrayList<>();
        int endIndex =lastGen.indexOf(end); //Incomplete end node
        lastGen.get(endIndex).addToList(path);
        return path;
    }
}
