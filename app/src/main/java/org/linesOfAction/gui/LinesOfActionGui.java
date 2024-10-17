package org.linesOfAction.gui;

import javax.swing.JFrame;

import org.linesOfAction.util.Constants;

public class LinesOfActionGui extends JFrame {

    public LinesOfActionGui() {
        super("Lines of Action");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(Constants.GUI_SIZE, Constants.GUI_SIZE);
        
        this.add(new BoardPanel());

    }
    
}
