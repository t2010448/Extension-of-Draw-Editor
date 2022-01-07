package Client;

import javax.swing.*;

import Public.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

////////////////////////////////////////////
// View
public class DrawFrame extends JFrame {
    protected DrawModel model;
    protected DrawController cont;

    public DrawFrame(DrawModel m, DrawController c) {
        model = m;
        cont = c;
        this.setBackground(Color.BLACK);
        this.setTitle("Draw Editor");
        this.setSize(1000,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        Canvas canvas = new Canvas(model,cont);
        this.add(canvas);

        MenuBar menuBar = new MenuBar(model,cont,this);
        this.setJMenuBar(menuBar);

        this.setVisible(true);
    }
}

@SuppressWarnings("deprecation")
class Canvas extends JPanel implements Observer {
    protected DrawModel model;

    public Canvas(DrawModel m,DrawController c) {
        model = m;
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(500, 500));
        this.addMouseListener(c);
        this.addMouseMotionListener(c);
        this.addKeyListener(c);
        this.setFocusable(true);
        model.addObserver(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(Figure f:model.getFigures()) {
            f.draw(g);
        }
        Figure f;
        if((f=model.getDrawingFigure()) != null) {
            f.draw(g);
        }
    }
    public void update(Observable o,Object arg) {
        repaint();
    }
}

class MenuBar extends JMenuBar implements ActionListener {
    protected DrawModel model;
    protected DrawController cont;
    protected DrawFrame frame;
    protected JMenu file,edit,color;
    protected JMenuItem fileNew,fileOpen,fileSave,fileSaveAs,colorChooser;
    
    MenuBar(DrawModel m,DrawController c,DrawFrame f) {
        model = m;
        cont = c;
        frame = f;
        file = new JMenu("ファイル");
        edit = new JMenu("編集");
        color = new JMenu("描画");
        fileNew  = new JMenuItem("新規作成");
        fileOpen = new JMenuItem("開く");
        fileSave = new JMenuItem("上書き保存");
        fileSaveAs = new JMenuItem("名前を付けて保存");
        colorChooser = new JMenuItem("色の編集");
        this.add(file);
        this.add(edit);
        this.add(color);
        file.add(fileNew);
        file.add(fileOpen);
        file.add(fileSave);
        file.add(fileSaveAs);
        color.add(colorChooser);
        fileNew.addActionListener(cont);
        fileOpen.addActionListener(cont);
        fileSave.addActionListener(cont);
        fileSaveAs.addActionListener(cont);
        colorChooser.addActionListener(this);
        colorChooser.setActionCommand("colorChooser");
    }
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "colorChooser" :
                Color c = JColorChooser.showDialog(frame, "色の編集", Color.WHITE);
                model.setColor(c);
        }
    }
}