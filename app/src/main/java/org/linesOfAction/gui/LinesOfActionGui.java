package org.linesOfAction.gui;

import javax.swing.JFrame;

public class LinesOfActionGui extends JFrame {

    public LinesOfActionGui() {
        super("Lines of Action");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        
        this.add(new BoardPanel());

    }
    
}
