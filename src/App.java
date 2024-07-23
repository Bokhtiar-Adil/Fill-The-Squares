import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.color.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class App {
    
    
    
    public static void main(String[] args) throws Exception {
        
        JFrame mainFrame;
        mainFrame = new JFrame();
        mainFrame.setSize(1000, 700);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo((Component)null);
        mainFrame.setDefaultCloseOperation(3);
        mainFrame.setLayout((LayoutManager)null);
        mainFrame.setTitle("Gamebox");

        // bg panel
        JPanel bgPnl;
        bgPnl = new JPanel();
        bgPnl.setLayout((LayoutManager)null);
        bgPnl.setBounds(0, 0, 1000, 700);
        bgPnl.setBackground(Color.DARK_GRAY);

        // control panels
        JPanel btnPnl;
        btnPnl = new JPanel();
        btnPnl.setLayout((LayoutManager)null);
        btnPnl.setBounds(0, 0, 350, 700);
        btnPnl.setBackground(Color.black);
        
        // buttons
        JButton vsHuman2p;
        vsHuman2p = new JButton();
        vsHuman2p.setText("Play vs Human");
        vsHuman2p.setBounds(0, 275, 350, 50);
        vsHuman2p.setBackground(Color.blue);
        vsHuman2p.setBorder((Border)null);
        vsHuman2p.setBorderPainted(false);
        vsHuman2p.setOpaque(false);
        vsHuman2p.setFocusable(false);
        vsHuman2p.setForeground(Color.red);
        vsHuman2p.setFont(new Font("Calibri", 0, 20));
        vsHuman2p.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == vsHuman2p) {
                    mainFrame.setVisible(false);
                    FillTheSquares fillTheSquares = new FillTheSquares("vsHuman2p", 3);
                    fillTheSquares.play();
                }

            }
        });
        btnPnl.add(vsHuman2p);

        JButton vsBot1;
        vsBot1 = new JButton();
        vsBot1.setText("Play vs Bot-1");
        vsBot1.setBounds(0, 350, 350, 50);
        vsBot1.setBackground(Color.blue);
        vsBot1.setBorder((Border)null);
        vsBot1.setBorderPainted(false);
        vsBot1.setOpaque(false);
        vsBot1.setFocusable(false);
        vsBot1.setForeground(Color.red);
        vsBot1.setFont(new Font("Calibri", 0, 20));
        vsBot1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == vsBot1) {
                    mainFrame.setVisible(false);
                    FillTheSquares fillTheSquares = new FillTheSquares("vsBot1", 3);
                    fillTheSquares.play();
                }

            }
        });
        btnPnl.add(vsBot1);

        JButton vsBot2;
        vsBot2 = new JButton();
        vsBot2.setText("Play vs Bot-2");
        vsBot2.setBounds(0, 425, 350, 50);
        vsBot2.setBackground(Color.blue);
        vsBot2.setBorder((Border)null);
        vsBot2.setBorderPainted(false);
        vsBot2.setOpaque(false);
        vsBot2.setFocusable(false);
        vsBot2.setForeground(Color.red);
        vsBot2.setFont(new Font("Calibri", 0, 20));
        vsBot2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == vsBot2) {
                    mainFrame.setVisible(false);
                    FillTheSquares fillTheSquares = new FillTheSquares("vsBot2", 3);
                    fillTheSquares.play();
                }

            }
        });
        btnPnl.add(vsBot2);

        bgPnl.add(btnPnl);
        mainFrame.add(bgPnl);
        mainFrame.setVisible(true);
    }
}
