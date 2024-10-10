package qmegamax.graphVisualizer;

import javax.swing.*;

public class ProgressBar {
    public final JFrame frame;

    public ProgressBar(JFrame mainframe,ProgressBar prevFrame,String text) {
        if(prevFrame!=null)prevFrame.frame.dispose();
        frame = new JFrame(text);
        frame.setLocation(mainframe.getLocationOnScreen().x+mainframe.getSize().width/2-250,mainframe.getLocationOnScreen().y+mainframe.getSize().width/2-10);
        frame.setSize(500, 20);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
