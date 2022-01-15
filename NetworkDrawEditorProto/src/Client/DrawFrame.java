package Client;

import Public.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

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
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        Canvas canvas = new Canvas(model, cont);
        this.add(BorderLayout.CENTER, canvas);

        ButtonPanel buttons = new ButtonPanel(model, this);
        this.add(BorderLayout.SOUTH, buttons);

        this.setVisible(true);
    }
}


@SuppressWarnings("deprecation")
class Canvas extends JPanel implements Observer {
    protected DrawModel model;

    public Canvas(DrawModel m, DrawController c) {
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
        for (Figure f : model.getFigures()) {
            f.draw(g);
        }
        Figure f;
        if ((f = model.getDrawingFigure()) != null) {
            if (model.getMode() != "select") {
                 f.draw(g);
             } else {
                 model.getHandle().draw(g);
             }
        }
    }

    public void update(Observable o, Object arg) {
        repaint();
    }
}


class ButtonPanel extends JPanel implements ActionListener {
    protected DrawModel model;
    protected JFrame frame;
    protected JButton color, selectFunc, clear, saveAs, load;

    private final String[] TOOLS;
    private String ptool;

    public ButtonPanel(DrawModel model, JFrame parent) {
        this.model = model;
        this.frame = parent;
        this.setBackground(Color.GRAY);

        var icon = new ImageIcon("resourses/icon_color.jpg");
        color = new JButton(icon);
        color.setActionCommand("color");
        color.addActionListener(this);
        color.setToolTipText("色を編集");
        this.add(color);

        icon = new ImageIcon("resourses/icon_rectangle.jpg");
        selectFunc = new JButton(icon);
        selectFunc.setActionCommand("selectFunc");
        selectFunc.addActionListener(this);
        selectFunc.setToolTipText("ツールを選択");
        this.add(selectFunc);

        icon = new ImageIcon("resourses/icon_clear.jpg");
        clear = new JButton(icon);
        clear.setActionCommand("clear");
        clear.addActionListener(this);
        clear.setToolTipText("図形を削除");
        this.add(clear);

        icon = new ImageIcon("resourses/icon_save.jpg");
        saveAs = new JButton(icon);
        saveAs.setActionCommand("saveAs");
        saveAs.addActionListener(this);
        saveAs.setToolTipText("保存");
        this.add(saveAs);

        icon = new ImageIcon("resourses/icon_load.jpg");
        load = new JButton(icon);
        load.setActionCommand("load");
        load.addActionListener(this);
        load.setToolTipText("読み込み");
        this.add(load);

        TOOLS = new String[] {
            "四角",
            "塗りつぶし四角",
            "丸",
            "塗りつぶし丸",
            "フリーハンド",
            "図形選択",
            "レーザーポインター",
        };
        ptool = TOOLS[0];
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "color":
                Color c = JColorChooser.showDialog(frame, "色の編集", Color.WHITE);
                model.setColor(c);
                break;

            case "clear":
                if (model.getMode() == "select" && model.getDrawingFigure() != null) {
                    model.sendData(new DataBox(Command.DELETE_FIGURE, model.getDrawingFigure()));
                    model.setDrawingFigure(null);
                } else {
                    int value = JOptionPane.showConfirmDialog(
                        frame,
                        "全てのオブジェクトを削除してもよろしいですか？",
                        "全消去",
                        JOptionPane.OK_CANCEL_OPTION
                    );
                    if (value == JOptionPane.OK_OPTION) {
                        // model.setFigures(new ArrayList<Figure>());
                        model.sendData(new DataBox(Command.SET_FIGURES, new ArrayList<Figure>()));
                        if(model.getMode() == "laser")  // レーザーポインタも消えるので復活させる
                            model.setMode("laser");
                    }
                }
                break;

            case "saveAs":
                var fcsave = new JFileChooser();
                if (fcsave.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    File selected = fcsave.getSelectedFile();
                    model.writeFile(selected.toString());
                }
                break;

            case "load":
                var fcload = new JFileChooser();
                if (fcload.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    File selected = fcload.getSelectedFile();
                    model.readFile(selected.toString());
                }
                break;

            case "selectFunc":
                String value = (String)JOptionPane.showInputDialog(
                    frame,
                    "ツールを選択してください",
                    "ツール選択",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    TOOLS,
                    ptool
                );
                if(value == null)
                    break;
                else
                    ptool = value;
                    
                if (ptool == TOOLS[0]) {
                    model.setMode("draw");
                    model.setFigShape(FigShape.RECTANGLE);
                    selectFunc.setIcon(new ImageIcon("resourses/icon_rectangle.jpg"));
                } else if (ptool == TOOLS[1]) {
                    model.setMode("draw");
                    model.setFigShape(FigShape.FILLRECT);
                    selectFunc.setIcon(new ImageIcon("resourses/icon_fillrect.jpg"));
                } else if (ptool == TOOLS[2]) {
                    model.setMode("draw");
                    model.setFigShape(FigShape.CIRCLE);
                    selectFunc.setIcon(new ImageIcon("resourses/icon_circle.jpg"));
                } else if (ptool == TOOLS[3]) {
                    model.setMode("draw");
                    model.setFigShape(FigShape.FILLCIRCLE);
                    selectFunc.setIcon(new ImageIcon("resourses/icon_fillcircle.jpg"));
                } else if (ptool == TOOLS[4]) {
                    model.setMode("draw");
                    model.setFigShape(FigShape.FREEHAND);
                    selectFunc.setIcon(new ImageIcon("resourses/icon_freehand.jpg"));
                } else if (ptool == TOOLS[5]) {
                    model.setMode("select");
                    selectFunc.setIcon(new ImageIcon("resourses/icon_select.jpg"));
                } else if (ptool == TOOLS[6]) {
                    model.setMode("laser");
                    selectFunc.setIcon(new ImageIcon("resourses/icon_pointer.jpg"));
                }
                break;

            default:
                break;
        }
    }
}
