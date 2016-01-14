package pathfind;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.IOException;

/**
 *
 * @author Ben The maze is thanks to Henry Kroll III, thenerdshow.com
 * http://thenerdshow.com/amaze.html?rows=10&cols=10&color=FF0000&bgcolor=000000&sz=10px&blank=+&wall=%23#.Vh_t0BNVikp
 * Proper format is maze with no empty lines either before or after.
 */
public class Map {

    private int[][] map; //0 is empty 1 is taken

    public Map(String path) { //Creates a map from a file location
        
        ArrayList<Integer> spots = new ArrayList<>();

        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(new File(path)));
            int next = reader.read();
            int rows = 0;
            int cols = 0;
            while (next != -1) { //Setup an arraylist, we do not know the maps size right now
                if (next == 10) { //Newline
                    rows++;
                    cols = 0;

                }
                if (next == 35) { //X
                    spots.add(1);
                    cols++;
                }
                if (next == 32) { //Space
                    spots.add(0);
                    cols++;
                }
                next = reader.read();
            }
            map = new int[rows + 1][cols]; //Map is not ended by a newLine; Thus we do row+1
            int i = 0;
            for (int row = 0; row < map.length; row++) {
                for (int col = 0; col < map[0].length; col++) { //Setup the actual array, now that we know the size
                    map[row][col] = spots.get(i);
                    i++;
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error loading file");
        }

    }

    public int[][] getMap() {
        return map.clone();
    }

    public int getRows() {
        return map.length;
    }

    public int getCols() {
        return map[0].length;
    }

    public int getPos(int row, int col) {
        return map[row][col];
    }

    public String toString() {
        String s = "";
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                //s += map[row][col] == 1 ? "*" : " ";
                switch (map[row][col]){
                    case 0 :
                        s+=" ";
                        break;
                    case 1: 
                        s+="*";
                        break;
                    case 2:
                        s+="X";
                        break;
                }

            }
            s += "\n";
        }
        return s;
    }

    public Node getStart() { //Returns the first open space on the bottom row
        for (int i = 0; i < map[0].length; i++) {
            if (isValid(0, i)) {
                return new Node(0, i);
            }
        }
        return null;
    }

    public Node getEnd() { //Returns the first open square on the top row
        for (int i = 0; i < map[map.length - 1].length; i++) {
            if (isValid(map.length - 1, i)) {
                return new Node(map.length - 1, i);
            }
        }
        return null;
    }

    public boolean isValid(int row, int col) { //Make sure the space is on the map and not a wall
        if (row < 0 || row > map.length-1 || col < 0 || row > map[map.length - 1].length-1) {
            return false;
        }
        return map[row][col] == 0;
    }
    
    public void lightSpot(Node n){ //Color this square so we can draw it in the frame
        map[n.getRow()][n.getCol()]=2;
    }
}
