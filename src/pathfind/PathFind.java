package pathfind;

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;

/**
 *
 * @author Ben
 */
public class PathFind extends JComponent {

    final private static int PIXEL_SIZE = 3;
    final private static int DRAW_STEP = 2;
    private static Map map;
    public static long startTime;

    public static void main(String[] args) {
        startTime = java.lang.System.currentTimeMillis();
        map = new Map("map1.txt");
        PathFind pf = new PathFind(map);
        ArrayList<Node> path = pf.doIterator(); //Main algorithm
        System.out.println("Solved in: " + (java.lang.System.currentTimeMillis() - startTime) / 1000f + " seconds!");
        JFrame frame = new JFrame();
        frame.add(pf);
        frame.setVisible(true);
        frame.toFront();
        frame.repaint(); //Set up visuals
        frame.setSize(map.getCols() * PIXEL_SIZE, map.getRows() * PIXEL_SIZE + 22); //Account for some top bar?
        for (Node n : path) { //Color squares
            try {
                Thread.sleep(DRAW_STEP);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            map.lightSpot(n);
            frame.repaint();
        }
    }

    public PathFind(Map map) {
        PathFind.map = map;
    }

    private ArrayList<Node> doIterator() {
        Iterator it = new Iterator(map, map.getEnd());
        it.setPeak(map.getStart());
        return it.getPath();
    }

    public void paintComponent(Graphics g) { //Color each squares depending on map value
        for (int row = 0; row < map.getRows(); row++) {
            for (int col = 0; col < map.getCols(); col++) {
                switch (map.getPos(row, col)) {
                    case 0:
                        g.setColor(Color.WHITE);
                        break;
                    case 1:
                        g.setColor(Color.GRAY);
                        break;
                    case 2:
                        g.setColor(Color.BLUE);
                        break;
                }
                g.fillRect(col * PIXEL_SIZE, row * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }
}
