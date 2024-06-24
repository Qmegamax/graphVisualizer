package qmegamax.graphVisualizer;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CanvasFrame extends JFrame {
    private static imageCanvas CANVAS;
    private final static int WIDTH = 1200;
    private final static int HEIGHT = 1200;
    private static int SCALE = 100;
    private static Color nodeColor = new Color(102, 0, 102);
    private static Color textColor = new Color(204, 0, 102);
    private static Color linkColor = new Color(0, 204, 204);
    private static Color backgroundColor = new Color(0, 0, 0);
    private static int DISTANCE = 120;

    private JTextField textField;
    private static JTextField textField_1;
    private static JTextField textField_2;
    private static JTextField textField_3;

    public static Color getColorFromText(JTextField text){
        if(!text.getText().matches("[\\d]{1,3},[\\d]{1,3},[\\d]{1,3}")){
            JOptionPane.showMessageDialog(null, "Wrong color format!", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        int[] color = new int[]{
                Integer.parseInt(text.getText().split(",")[0]),
                Integer.parseInt(text.getText().split(",")[1]),
                Integer.parseInt(text.getText().split(",")[2])
        };

        for(int col : color){
            if(col>255){
                JOptionPane.showMessageDialog(null, "Wrong color format!", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        return new Color(color[0],color[1],color[2]);
    }

    public CanvasFrame(Graph graph) {
        JFrame frame = new JFrame("Graph Visualizer");

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("Parameters");
        menuBar.add(mnNewMenu);

        JLabel lblNewLabel_4 = new JLabel("Distance between nodes");
        mnNewMenu.add(lblNewLabel_4);

        JSpinner spinner = new JSpinner();
        spinner.setValue(DISTANCE);
        spinner.addChangeListener(e -> {
            if(!spinner.getValue().toString().matches("[\\d]{1,4}")){
                JOptionPane.showMessageDialog(null, "Wrong spinner format!", "Error", JOptionPane.ERROR_MESSAGE);
                spinner.setValue(DISTANCE);
            }

            int dist=(int)spinner.getValue();

            if(dist<=50){
                JOptionPane.showMessageDialog(null, "Minimal distance is 51!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DISTANCE=dist;
            CANVAS.generatePlacements();
            CANVAS.repaint();
        });
        mnNewMenu.add(spinner);

        JLabel lblNewLabel_6 = new JLabel("Link amount");
        mnNewMenu.add(lblNewLabel_6);

        JSpinner spinner_2 = new JSpinner();
        spinner_2.setValue(7);
        spinner_2.addChangeListener(e -> {
            if(!spinner_2.getValue().toString().matches("[\\d]{1,6}")){
                JOptionPane.showMessageDialog(null, "Wrong spinner format!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            Graph newGraph = new Graph(CANVAS.graph.nodeCount);
            Main.addNodes(newGraph,(int) spinner_2.getValue());
            CANVAS.graph=newGraph;

            CANVAS.generatePlacements();
            CANVAS.repaint();
        });
        mnNewMenu.add(spinner_2);

        JLabel lblNewLabel_7 = new JLabel("Node amount");
        mnNewMenu.add(lblNewLabel_7);

        JSpinner spinner_3 = new JSpinner();
        spinner_3.setValue(10);
        spinner_3.addChangeListener(e -> {
            if(!spinner_3.getValue().toString().matches("[\\d]{1,7}")){
                JOptionPane.showMessageDialog(null, "Wrong spinner format!", "Error", JOptionPane.ERROR_MESSAGE);
                spinner_3.setValue(DISTANCE);
            }

            Graph newGraph = new Graph((int) spinner_3.getValue());
            System.out.println(CANVAS.graph.nodeCount);
            Main.addNodes(newGraph,CANVAS.graph.nodeCount);
            System.out.println(CANVAS.graph.nodeCount);
            CANVAS.graph=newGraph;

            CANVAS.generatePlacements();
            CANVAS.repaint();
        });
        mnNewMenu.add(spinner_3);

        JMenu mnNewMenu_1 = new JMenu("Color");
        menuBar.add(mnNewMenu_1);

        JLabel lblNewLabel = new JLabel("Background color");
        mnNewMenu_1.add(lblNewLabel);

        textField = new JTextField();
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                Color newColor = getColorFromText(textField);

             if(newColor!=null){
                 backgroundColor=newColor;
                 CANVAS.setBackground(newColor);
             }else{
                 textField.setText(String.valueOf(backgroundColor.getRed())+","+String.valueOf(backgroundColor.getGreen())+","+String.valueOf(backgroundColor.getBlue()));
             }

            }
        });
        textField.setText("0,0,0");
        mnNewMenu_1.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("Node color");
        mnNewMenu_1.add(lblNewLabel_1);

        textField_1 = new JTextField();
        textField_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                Color newColor = getColorFromText(textField_1);

                if(newColor!=null){
                    nodeColor=newColor;
                    CANVAS.repaint();
                }else{
                    textField_1.setText(String.valueOf(nodeColor.getRed())+","+String.valueOf(nodeColor.getGreen())+","+String.valueOf(nodeColor.getBlue()));
                }

            }
        });
        textField_1.setText("102,0,102");
        mnNewMenu_1.add(textField_1);
        textField_1.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Link color");
        mnNewMenu_1.add(lblNewLabel_2);

        textField_2 = new JTextField();
        textField_2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                Color newColor = getColorFromText(textField_2);

                if(newColor!=null){
                    linkColor=newColor;
                    CANVAS.repaint();
                }else{
                    textField_2.setText(String.valueOf(linkColor.getRed())+","+String.valueOf(linkColor.getGreen())+","+String.valueOf(linkColor.getBlue()));
                }

            }
        });
        textField_2.setText("0,204,204");
        mnNewMenu_1.add(textField_2);
        textField_2.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Text color");
        mnNewMenu_1.add(lblNewLabel_3);

        textField_3 = new JTextField();
        textField_3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                Color newColor = getColorFromText(textField_3);

                if(newColor!=null){
                    textColor=newColor;
                    CANVAS.repaint();
                }else{
                    textField_3.setText(String.valueOf(textColor.getRed())+","+String.valueOf(textColor.getGreen())+","+String.valueOf(textColor.getBlue()));
                }

            }
        });
        textField_3.setText("204,0,102");
        mnNewMenu_1.add(textField_3);
        textField_3.setColumns(10);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new keyDispatcher());
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        CANVAS = new imageCanvas(graph, WIDTH, HEIGHT);
        frame.add(CANVAS);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    static class imageCanvas extends Canvas {
        Graph graph;
        int x;
        int y;
        Dimension[] nodePositions;

        public imageCanvas(Graph graph, int x, int y) {
            this.graph = graph;
            this.x = x;
            this.y = y;
            setBackground(backgroundColor);
            setSize(x, y);

            generatePlacements();
        }

        public void generatePlacements() {
            Dimension[] positions = new Dimension[graph.nodeCount];
            boolean noConnection;

            do {
                noConnection = false;

                for (int i = 0; i < graph.nodeCount; i++) {
                    if (positions[i] != null) continue;

                    if (i == 0) {
                        positions[i] = new Dimension(0, 0);
                        continue;
                    }

                    Dimension ancorPosition = null;
                    Dimension newCords;

                    if (graph.links[i].isEmpty()) {
                        do {
                            ancorPosition = positions[(int) (Math.random() * positions.length)];

                        } while (ancorPosition == null);

                        newCords = getNewCords(ancorPosition.width, ancorPosition.height, positions,true);

                        if (newCords == null) {
                            noConnection = true;
                            continue;
                        }
                    } else {
                        boolean connectedToSth = false;
                        
                        for (int num : graph.links[i]) {
                            if (positions[num] != null) {
                                ancorPosition = positions[num];
                                connectedToSth=true;
                                break;
                            }
                        }
                        
                        if(!connectedToSth){
                            do {
                                ancorPosition = positions[(int) (Math.random() * positions.length)];

                            } while (ancorPosition == null);

                            newCords = getNewCords(ancorPosition.width, ancorPosition.height, positions,true);

                            if (newCords == null) {
                                noConnection = true;
                                continue;
                            }
                        }else{
                            newCords = getNewCords(ancorPosition.width, ancorPosition.height, positions,false);
                        }
                    }

                    positions[i] = newCords;
                }

            } while (noConnection);

            nodePositions = positions;
        }

        public Dimension getNewCords(int a, int b, Dimension[] positions,boolean seperate) {
            boolean collision;
            Dimension newCordinates;
            int maxTries = 4;

            do {
                collision = false;

                double degree = Math.random() * 360;
                newCordinates = new Dimension((int) (a + DISTANCE * Math.cos(degree)), (int) (b + DISTANCE * Math.sin(degree)));

                for (Dimension d : positions) {
                    if (d == null) continue;
                    double distance = Math.sqrt(Math.pow(newCordinates.width - d.width, 2) + Math.pow(newCordinates.height - d.height, 2));

                    if (distance <= 50) {
                        collision = true;
                        maxTries--;
                        break;
                    }
                }

                if(maxTries==0 && seperate){
                    return null;}
            } while (collision && maxTries>0);

            return newCordinates;
        }

        public void paint(Graphics g) {

            for (int i = 0; i < graph.nodeCount; i++) {

                int positionX = nodePositions[i].width * SCALE / 100 + x / 2;
                int positionY = nodePositions[i].height * SCALE / 100 + y / 2;

                g.setColor(linkColor);
                for (int b : graph.links[i]) {
                    if (nodePositions[i] == null || nodePositions[b] == null) continue;
                    g.drawLine(positionX, positionY, nodePositions[b].width * SCALE / 100 + x / 2, nodePositions[b].height * SCALE / 100 + y / 2);
                }
            }

            g.setColor(nodeColor);
            for (int i = 0; i < graph.nodeCount; i++) {
                int positionX = nodePositions[i].width * SCALE / 100 + x / 2;
                int positionY = nodePositions[i].height * SCALE / 100 + y / 2;

                g.fillOval(positionX - 10 * SCALE / 100, positionY - 10 * SCALE / 100, 20 * SCALE / 100, 20 * SCALE / 100);
            }

            g.setColor(textColor);
            for (int i = 0; i < graph.nodeCount; i++) {
                int positionX = nodePositions[i].width * SCALE / 100 + x / 2;
                int positionY = nodePositions[i].height * SCALE / 100 + y / 2;

                g.setFont(new Font("SansSerif", Font.BOLD, SCALE / 10));
                g.drawString(String.valueOf(i), positionX - SCALE / 100 * 5, positionY + SCALE / 100 * 5);
            }
        }
    }

    private static class keyDispatcher implements KeyEventDispatcher {
        Color[] colors = new Color[]{
                new Color(255, 0, 0),
                new Color(255, 127, 0),
                new Color(255, 255, 0),
                new Color(127, 255, 0),
                new Color(0, 255, 0),
                new Color(0, 255, 127),
                new Color(0, 255, 255),
                new Color(0, 127, 255),
                new Color(0, 0, 255),
                new Color(127, 0, 255),
                new Color(255, 0, 255),
                new Color(255, 0, 127),
        };
        int tick1 = 0;

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_PRESSED) {

                //System.out.println(e.getKeyCode());

                if (e.getKeyCode() == 40) {
                    SCALE -= 3;
                    CANVAS.repaint();
                }

                if (e.getKeyCode() == 38) {
                    SCALE += 3;
                    CANVAS.repaint();
                }

                if (e.getKeyCode() == 82) {
                    CANVAS.generatePlacements();
                    CANVAS.repaint();
                }

                if (e.getKeyCode() == 76) {
                    nodeColor = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
                    linkColor = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
                    textField_1.setText(String.valueOf(nodeColor.getRed())+","+String.valueOf(nodeColor.getGreen())+","+String.valueOf(nodeColor.getBlue()));
                    textField_2.setText(String.valueOf(linkColor.getRed())+","+String.valueOf(linkColor.getGreen())+","+String.valueOf(linkColor.getBlue()));

                    CANVAS.repaint();
                }

                if (e.getKeyCode() == 77) {

                    tick1++;
                    if (tick1 > 44) tick1 = 0;
                    linkColor = colors[tick1 / 4];

                    CANVAS.repaint();
                }
            }
            return false;
        }
    }
}