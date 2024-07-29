import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class App {
    
    static final String APP_NAME = "FILL THE SQUARES";
    
    static final int FRAME_WIDTH = 1000;
    static final int FRAME_HEIGHT = 700;
    static final int NUMBER_OF_MODES = 5;
    static final int NUMBER_OF_SIZES = 6;
    
    static int selectedMode = 0, selectedSize = 1;
    
    private static void setMode(int n) {
        selectedMode = n;
    }

    private static void setSize(int n) {
        selectedSize = n;
    }

    public static void main(String[] args) throws Exception {

        
        String[] modes = {"vsHuman2p", "onlineP2p", "vsBot1", "vsBot2", "vsBot3"};
        String[] modeLbls = {"2 PLayer", "Online P2P", "vs Bot-1", "vs Bot-2", "vs Bot-3"};
        int[] sizes = {3,4,5,6,7,8};
        String[] sizeLbls = {"3x3", "4x4", "5x5", "6x6", "7x7", "8x8"};
        
        JFrame mainFrame;
        mainFrame = new JFrame();
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo((Component)null);
        mainFrame.setDefaultCloseOperation(3);
        mainFrame.setLayout((LayoutManager)null);
        mainFrame.setTitle(APP_NAME);

        // bg panel - this panel works as the background for the frame
        JPanel bgPnl;
        bgPnl = new JPanel();
        bgPnl.setLayout((LayoutManager)null);
        bgPnl.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        bgPnl.setBackground(Color.BLACK);

        JLabel titleLbl; // writes title on the screen
        titleLbl = new JLabel();
        titleLbl.setText(APP_NAME);
        titleLbl.setFont(new Font("Calibri", 0, 40));
        titleLbl.setForeground(Color.CYAN);
        titleLbl.setBounds(350, 50, 300, 40);
        titleLbl.setVisible(true);
        bgPnl.add(titleLbl);


        // control panels -- holds all the buttons to play the game
        JPanel btnPnl;
        btnPnl = new JPanel();
        btnPnl.setLayout((LayoutManager)null);
        btnPnl.setBounds(0, 200, FRAME_WIDTH, FRAME_HEIGHT-200);
        btnPnl.setBackground(Color.DARK_GRAY);
        
        JButton playBtn;
        playBtn = new JButton();
        playBtn.setText("PLAY");
        playBtn.setBounds(450, 100, 100, 50);
        playBtn.setVerticalTextPosition(3);
        playBtn.setBackground(Color.WHITE);
        playBtn.setBorder((Border)null);
        playBtn.setBorderPainted(false);
        playBtn.setOpaque(true);
        playBtn.setFocusable(false);
        playBtn.setForeground(Color.BLUE);
        playBtn.setFont(new Font("Calibri", 0, 30));
        playBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == playBtn) {
                    // based on the selected properties, the correspoding game will open
                    FillTheSquares fillTheSquares = new FillTheSquares(mainFrame, modes[selectedMode], sizes[selectedSize]);
                    if (selectedMode==1) fillTheSquares.setupOnlineProperties();
                    mainFrame.setVisible(false);                    
                    fillTheSquares.play();
                }
            }
        });
        playBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getSource() == playBtn) {
                    // playBtn.setForeground(Color.GREEN);
                    playBtn.setBackground(Color.GREEN);
                }
                    
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getSource() == playBtn) {
                    playBtn.setForeground(Color.BLUE);
                    playBtn.setBackground(Color.WHITE);
                }
                
            }
        });
        btnPnl.add(playBtn);

        
        int xOffset = 138, yOffset = 200; // to synchronize the buttons alignment

        JLabel modeLbl;
        modeLbl = new JLabel();
        modeLbl.setText("Mode");
        modeLbl.setFont(new Font("Calibri", 0, 20));
        modeLbl.setForeground(Color.CYAN);
        modeLbl.setBounds(xOffset, yOffset, 100, 30);
        modeLbl.setVisible(true);
        btnPnl.add(modeLbl);
        xOffset += 100;

        boolean onlineYet = true;
        JButton[] modeBtns = new JButton[NUMBER_OF_MODES];
        for (int i=0; i<NUMBER_OF_MODES; i++, xOffset+=125) {
            modeBtns[i] = new JButton();
            if (!onlineYet) {
                if (i==0) xOffset+=125;
                if (i==1) {
                    xOffset-=125;
                    continue;
                }                
            }
            modeBtns[i].setText(modeLbls[i]);
            modeBtns[i].setBounds(xOffset, yOffset, 100, 30);
            modeBtns[i].setBackground(Color.WHITE);
            modeBtns[i].setBorder((Border)null);
            modeBtns[i].setBorderPainted(false);
            modeBtns[i].setOpaque(false);
            modeBtns[i].setFocusable(false);
            modeBtns[i].setForeground(Color.red);
            modeBtns[i].setFont(new Font("Calibri", 0, 20));
            final int index = i;
            modeBtns[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == modeBtns[index]) {
                        setMode(index);
                        for (int j=0; j<NUMBER_OF_MODES; j++) {
                            if (j==selectedMode) modeBtns[j].setForeground(Color.GREEN);
                            else modeBtns[j].setForeground(Color.RED);
                        }
                    }
                }
            });
            modeBtns[index].addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                }
    
                @Override
                public void mousePressed(MouseEvent e) {
                }
    
                @Override
                public void mouseReleased(MouseEvent e) {
                }
    
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (e.getSource() == modeBtns[index]) {
                        // modeBtns[i].setForeground(Color.GREEN);
                        modeBtns[index].setOpaque(true);
                    }
                        
                }
    
                @Override
                public void mouseExited(MouseEvent e) {
                    if (e.getSource() == modeBtns[index]) {
                        modeBtns[index].setOpaque(false);
                    }
                    
                }
            });
            btnPnl.add(modeBtns[i]);
        }
        
        xOffset = 138;
        yOffset += 50;

        JLabel sizeLbl;
        sizeLbl = new JLabel();
        sizeLbl.setText("Size");
        sizeLbl.setFont(new Font("Calibri", 0, 20));
        sizeLbl.setForeground(Color.CYAN);
        sizeLbl.setBounds(xOffset, yOffset, 100, 30);
        sizeLbl.setVisible(true);
        btnPnl.add(sizeLbl);
        xOffset += 100;

        JButton[] sizeBtns = new JButton[NUMBER_OF_SIZES];
        for (int i=0; i<NUMBER_OF_SIZES; i++, xOffset+=100) {
            sizeBtns[i] = new JButton();
            sizeBtns[i].setText(sizeLbls[i]);
            sizeBtns[i].setBounds(xOffset, yOffset, 75, 30);
            sizeBtns[i].setBackground(Color.WHITE);
            sizeBtns[i].setBorder((Border)null);
            sizeBtns[i].setBorderPainted(false);
            sizeBtns[i].setOpaque(false);
            sizeBtns[i].setFocusable(false);
            sizeBtns[i].setForeground(Color.red);
            sizeBtns[i].setFont(new Font("Calibri", 0, 20));
            final int index = i;
            sizeBtns[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == sizeBtns[index]) {
                        setSize(index);
                        for (int j=0; j<NUMBER_OF_SIZES; j++) {
                            if (j==selectedSize) sizeBtns[j].setForeground(Color.GREEN);
                            else sizeBtns[j].setForeground(Color.RED);
                        }
                    }
                }
            });
            sizeBtns[index].addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                }
    
                @Override
                public void mousePressed(MouseEvent e) {
                }
    
                @Override
                public void mouseReleased(MouseEvent e) {
                }
    
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (e.getSource() == sizeBtns[index]) {
                        // sizeBtns[i].setForeground(Color.GREEN);
                        sizeBtns[index].setOpaque(true);
                    }
                        
                }
    
                @Override
                public void mouseExited(MouseEvent e) {
                    if (e.getSource() == sizeBtns[index]) {
                        sizeBtns[index].setOpaque(false);
                    }
                    
                }
            });
            btnPnl.add(sizeBtns[i]);
        }

        xOffset = 300;
        yOffset += 100;

        JButton rulesBtn;
        rulesBtn = new JButton();
        rulesBtn.setText("RULES");
        rulesBtn.setBounds(xOffset, yOffset, 100, 50);
        rulesBtn.setBackground(Color.WHITE);
        rulesBtn.setBorder((Border)null);
        rulesBtn.setBorderPainted(false);
        rulesBtn.setOpaque(true);
        rulesBtn.setFocusable(false);
        rulesBtn.setForeground(Color.BLUE);
        rulesBtn.setFont(new Font("Calibri", 0, 30));
        rulesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == rulesBtn) {
                    
                }
            }
        });
        rulesBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getSource() == rulesBtn) {
                    // rulesBtn.setForeground(Color.GREEN);
                    rulesBtn.setBackground(Color.GREEN);
                }
                    
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getSource() == rulesBtn) {
                    rulesBtn.setForeground(Color.BLUE);
                    rulesBtn.setBackground(Color.WHITE);
                }
                
            }
        });
        btnPnl.add(rulesBtn);
        xOffset += 300;

        JButton exitBtn;
        exitBtn = new JButton();
        exitBtn.setText("EXIT");
        exitBtn.setBounds(xOffset, yOffset, 100, 50);
        exitBtn.setBackground(Color.WHITE);
        exitBtn.setBorder((Border)null);
        exitBtn.setBorderPainted(false);
        exitBtn.setOpaque(true);
        exitBtn.setFocusable(false);
        exitBtn.setForeground(Color.BLUE);
        exitBtn.setFont(new Font("Calibri", 0, 30));
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == exitBtn) {
                    return;
                }
            }
        });
        exitBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int chkb = JOptionPane.YES_NO_OPTION;
                int chkr = JOptionPane.showConfirmDialog(null,
                        "Are you sure to exit?", APP_NAME, chkb);
                if (chkr == 0)
                    System.exit(0);

            }

        });
        exitBtn.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getSource() == exitBtn) {
                    // exitBtn.setForeground(Color.GREEN);
                    exitBtn.setBackground(Color.GREEN);
                }
                    
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getSource() == exitBtn) {
                    exitBtn.setForeground(Color.BLUE);
                    exitBtn.setBackground(Color.WHITE);
                }
                
            }
        });
        btnPnl.add(exitBtn);

        bgPnl.add(btnPnl);
        mainFrame.add(bgPnl);
        mainFrame.setVisible(true);
    }


}
