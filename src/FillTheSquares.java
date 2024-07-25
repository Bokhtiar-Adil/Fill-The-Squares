import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class FillTheSquares {
    
    JFrame frame;
    JLabel scoreboard, details;
    JLabel[] descs;
    JPanel menu, board;
    JLabel[][] squareLabels;
    int rows, columns;
    JButton[] hddBtns, vddBtns;
    JButton rulesBtn, backBtn, exitBtn;
    char[] hbtnColors, vbtnColors;
    int[][] srows, scols;
    int[][] squares;
    boolean turn, finished;
    Color normal, hovered, p1Filled, p2Filled;
    int player1, player2;
    int currInd, currType;
    Bot1_simple bot1;
    Bot2_advanced bot2;
    Bot3_fast bot3;
    String mode;
    int size, menuXOffset = 10, menuYOffset = 50, descItem = 4;

    final String APP_NAME = "FILL THE SQUARES";
    final int FRAME_WIDTH = 1000;
    final int FRAME_HEIGHT = 700;
    final int NUMBER_OF_MODES = 5;
    final int NUMBER_OF_SIZES = 6;
    String[] modes = {"vsHuman2p", "onlineP2p", "vsBot1", "vsBot2", "vsBot3"};
    String[] modeLbls = {"2 PLayer", "Online P2P", "vs Bot-1", "vs Bot-2", "vs Bot-3"};
    int[] sizes = {3,4,5,6,7,8};
    String[] sizeLbls = {"3x3", "4x4", "5x5", "6x6", "7x7", "8x8"};
    JFrame mainFrame;

    public FillTheSquares(JFrame mainFrame, String mode, int n) {
        rows = n;
        columns = n;
        hddBtns = new JButton[rows*(columns+1)];
        vddBtns = new JButton[rows*(columns+1)];
        hbtnColors = new char[rows*(columns+1)];
        vbtnColors = new char[rows*(columns+1)];
        srows = new int[2][rows*(columns+1)];
        scols = new int[2][rows*(columns+1)];
        squares = new int[rows+1][columns+1];
        for (int i=0; i<rows+1; i++) {
            for (int j=0; j<columns+1; j++) squares[i][j] = 0;
        }
        squareLabels = new JLabel[rows][columns];
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) squareLabels[i][j] = new JLabel();
        }
        frame = new JFrame();
        menu = new JPanel();
        board = new JPanel();
        scoreboard = new JLabel();
        details = new JLabel();
        descs = new JLabel[descItem];
        for (int i=0; i<descItem; i++) descs[i] = new JLabel();
        rulesBtn = new JButton();
        backBtn = new JButton();
        exitBtn = new JButton();
        for (int i=0; i<rows*(columns+1); i++) {
            hddBtns[i] = new JButton();
            hbtnColors[i] = 'x';
        }
        for (int i=0; i<rows*(columns+1); i++) {
            vddBtns[i] = new JButton();
            vbtnColors[i] = 'x';
        }
        turn = true;
        finished = false;
        normal = Color.LIGHT_GRAY;
        hovered = Color.yellow;
        p1Filled = Color.green;
        p2Filled = Color.red;
        player1 = 0;
        player2 = 0;
        currInd = 2;
        currType = 2;
        bot1 = new Bot1_simple(n);
        bot2 = new Bot2_advanced(n);
        bot3 = new Bot3_fast(n);
        this.mode = mode;
        size = n;
        this.mainFrame = mainFrame;
    } 

    public void play() {
        UI();
        updateScore();
        updateTurnIndicator();
        frameSetup();
    }

    private void frameSetup() {
        for (int i=0; i<descItem; i++) menu.add(descs[i]);
        menu.add(scoreboard);
        menu.add(details);
        menu.add(rulesBtn);
        menu.add(backBtn);
        menu.add(exitBtn);
        frame.add(menu);
        frame.add(board);
        frame.setVisible(true);
    }

    private void UI() {
        
        int posX, posY;
        
        frame = new JFrame();
        frame.setSize(1000, 700);
        frame.setResizable(false);
        frame.setLocationRelativeTo((Component)null);
        frame.setDefaultCloseOperation(3);
        frame.setLayout((LayoutManager)null);
        frame.setTitle(APP_NAME);

        // control panels
        menu.setLayout((LayoutManager)null);
        menu.setBounds(0, 0, 350, 700);
        menu.setBackground(Color.black);

        String descText = "";
        for (int i=0; i<descItem; i++) {
            int tmp = 0;
            if (i==0) tmp = 40;
            int kkk = 0;
            for (kkk=0; kkk<NUMBER_OF_MODES; kkk++) {
                if (modes[kkk] == mode) break;
            }
            if (i==0) descText = APP_NAME;
            if (i==1) descText = ("Mode: " + modeLbls[kkk]);
            kkk = 0;
            for (kkk=0; kkk<NUMBER_OF_SIZES; kkk++) {
                if (sizes[kkk] == size) break;
            }
            if (i==2) descText = ("Size: " + sizeLbls[kkk]);
            if (i==3) descText = ("Scoreboard:");
            descs[i].setText(descText);
            if (i==0) descs[i].setForeground(Color.CYAN);  
            else descs[i].setForeground(Color.WHITE); 
            descs[i].setFont(new Font("Calibri", 0, 30));
            descs[i].setBounds(menuXOffset+tmp, menuYOffset, 350, 100);
            descs[i].setVisible(true);
            menuYOffset+=50;   
        }

        rulesBtn.setText("Rules");
        rulesBtn.setBounds(125, menuYOffset+200, 100, 40);
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

        backBtn.setText("Back");
        backBtn.setBounds(125, menuYOffset+250, 100, 40);
        backBtn.setBackground(Color.WHITE);
        backBtn.setBorder((Border)null);
        backBtn.setBorderPainted(false);
        backBtn.setOpaque(true);
        backBtn.setFocusable(false);
        backBtn.setForeground(Color.BLUE);
        backBtn.setFont(new Font("Calibri", 0, 30));
        backBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int chkb = JOptionPane.YES_NO_OPTION;
                int chkr = JOptionPane.showConfirmDialog(null,
                        "Are you sure to go back to main menu?", APP_NAME, chkb);
                if (chkr == 0) {
                    mainFrame.setVisible(true);
                    frame.dispose();
                }                   
            }
        });
        backBtn.addMouseListener(new MouseListener() {

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
                if (e.getSource() == backBtn) {
                    // backBtn.setForeground(Color.GREEN);
                    backBtn.setBackground(Color.GREEN);
                }
                    
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getSource() == backBtn) {
                    backBtn.setForeground(Color.BLUE);
                    backBtn.setBackground(Color.WHITE);
                }
                
            }
        });

        exitBtn.setText("Exit");
        exitBtn.setBounds(125, menuYOffset+300, 100, 40);
        exitBtn.setBackground(Color.WHITE);
        exitBtn.setBorder((Border)null);
        exitBtn.setBorderPainted(false);
        exitBtn.setOpaque(true);
        exitBtn.setFocusable(false);
        exitBtn.setForeground(Color.BLUE);
        exitBtn.setFont(new Font("Calibri", 0, 30));
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
        

        board.setLayout((LayoutManager)null);
        board.setBounds(350, 0, 650, 700);
        board.setBackground(Color.DARK_GRAY);

        // 70
        int ddd = (columns%2==0)?columns/2:columns/2+1;
        posX = 350 - (ddd*70) + 10;
        posY = 110;
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                squareLabels[i][j].setText(" ");
                squareLabels[i][j].setBounds(posX+j*65, posY+i*60, 40, 50);
                squareLabels[i][j].setBackground(Color.DARK_GRAY);
                squareLabels[i][j].setForeground(Color.white);
                squareLabels[i][j].setFont(new Font("Calibri", 0, 20));
                squareLabels[i][j].setAlignmentX(10);
                squareLabels[i][j].setAlignmentY(10);
                squareLabels[i][j].setOpaque(true);
                squareLabels[i][j].setVisible(true);
                board.add(squareLabels[i][j]);
            }
        }
        // 60
        posX -= 10;
        posY = 100;
        for (int i=0; i<rows+1; i++) {
            for (int j=0; j<columns; j++) {   
                srows[0][i*rows+j] = (i>0)?i-1:0;
                scols[0][i*rows+j] = j;
                hddBtns[i*rows+j].setBounds(posX+j*65, posY+i*60, 50, 5);
                hddBtns[i*rows+j].setBackground(normal);
                hddBtns[i*rows+j].setBorderPainted(false);
                hddBtns[i*rows+j].setOpaque(true);
                hddBtns[i*rows+j].setFocusable(false);
                hddBtns[i*rows+j].setForeground(Color.black);
                hddBtns[i*rows+j].setFont(new Font("Arial", 0, 20));
                final int index = i, index2 = j;
                hddBtns[index*rows+index2].addMouseListener(new MouseListener() {

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
                        if (e.getSource() == hddBtns[index*rows+index2])
                            if (hbtnColors[index*rows+index2] == 'x') hddBtns[index*rows+index2].setBackground(hovered);
                    }
        
                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (e.getSource() == hddBtns[index*rows+index2])
                        if (hbtnColors[index*rows+index2] == 'x') hddBtns[index*rows+index2].setBackground(normal);
                    }
        
                });
                hddBtns[index*rows+index2].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (finished) return;
                        currInd = index*rows+index2;
                        if (hbtnColors[currInd] == 'x') {
                            currType = 0;
                            if (turn) {
                                hbtnColors[currInd] = 'g';
                                hddBtns[currInd].setBackground(p1Filled);
                            }
                            else {
                                hbtnColors[currInd] = 'r';
                                hddBtns[currInd].setBackground(p2Filled);
                            }
                            scoring(srows[0][currInd], scols[0][currInd], currType, currInd);
                        }   
                    }
                });
                board.add(hddBtns[i*rows+j]);
            }
        }
        posX -= 10;
        posY += 5;
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns+1; j++) {
                srows[1][i*(columns+1)+j] = i;
                scols[1][i*(columns+1)+j] = (j>0)?j-1:j;
                vddBtns[i*(columns+1)+j].setBounds(posX+j*65, posY+i*60, 5, 50);
                vddBtns[i*(columns+1)+j].setBackground(normal);
                vddBtns[i*(columns+1)+j].setBorderPainted(false);
                vddBtns[i*(columns+1)+j].setOpaque(true);
                vddBtns[i*(columns+1)+j].setFocusable(false);
                vddBtns[i*(columns+1)+j].setForeground(Color.black);
                vddBtns[i*(columns+1)+j].setFont(new Font("Arial", 0, 20));
                final int index = i, index2 = j;
                vddBtns[index*(columns+1)+index2].addMouseListener(new MouseListener() {

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
                        if (e.getSource() == vddBtns[index*(columns+1)+index2])
                            if (vbtnColors[index*(columns+1)+index2] == 'x') vddBtns[index*(columns+1)+index2].setBackground(hovered);
                    }
        
                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (e.getSource() == vddBtns[index*(columns+1)+index2])
                        if (vbtnColors[index*(columns+1)+index2] == 'x') vddBtns[index*(columns+1)+index2].setBackground(normal);
                    }
                });
                vddBtns[index*(columns+1)+index2].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (finished) return;
                        currInd = index*(columns+1)+index2;
                        if (vbtnColors[currInd] == 'x') {
                            currType = 1;
                            if (turn) {
                                vbtnColors[currInd] = 'g';
                                vddBtns[currInd].setBackground(p1Filled);
                            }
                            else {
                                vbtnColors[currInd] = 'r';
                                vddBtns[currInd].setBackground(p2Filled);
                            }
                            scoring(srows[1][currInd], scols[1][currInd], currType, currInd);
                        }
                    }        
                });
                board.add(vddBtns[i*(columns+1)+j]);
            }
        }
    }

    private void scoring(int row, int column, int currType, int currInd) {
        squares[row][column]++;
        if (currType==0 && currInd>=rows) squares[row+1][column]++;
        else if (currType==1 && currInd%(columns+1)>0) squares[row][column+1]++;
        int val = 0;
        if (squares[row][column]==4) {
            val++;
            if (turn) squareLabels[row][column].setText("  1 ");
            else squareLabels[row][column].setText("  2 ");    
            squares[row][column] = 100;
        }
        if (currType==0 && squares[row+1][column]==4) {
            val++;
            if (turn) squareLabels[row+1][column].setText("  1 ");
            else squareLabels[row+1][column].setText("  2 ");
            squares[row+1][column] = 100;
        }
        if (currType==1 && squares[row][column+1]==4) {
            val++;
            if (turn) squareLabels[row][column+1].setText("  1 ");
            else squareLabels[row][column+1].setText("  2 ");
            squares[row][column+1] = 100;
        }
        if (turn) player1 += val;
        else player2 += val;
        updateScore();
        if (val==0) turn = !turn;
        if (player1>(rows*columns)/2 || player2>(rows*columns)/2 || player1+player2==rows*columns) finishRoutine();
        else {
            updateTurnIndicator();
            if (mode!="vsHuman2p" && !turn) {
                botManager();
                frameSetup();
            } 
        }
    }

    private void updateScore() {
        String scoreText = "";
        if (mode == modes[0] || mode == modeLbls[1]) scoreText = "Player 1: " + player1 + " -- Player 2: ";
        else scoreText = "Player: " + player1 + " -- Bot: ";
        scoreText += player2;
        scoreboard.setText(scoreText);
        scoreboard.setForeground(Color.ORANGE);
        scoreboard.setFont(new Font("Calibri", 0, 30));
        scoreboard.setBounds(menuXOffset, menuYOffset, 350, 100);
        scoreboard.setVisible(true);
        menu.add(scoreboard);
        frameSetup();
    }

    private void updateTurnIndicator() {
        if (turn) {
            if (mode=="vsHuman2p") details.setText("Turn: Player 1");
            else details.setText("Turn: Player");
            details.setForeground(Color.GREEN);
        }
        else {
            if (mode=="vsHuman2p") details.setText("Turn: Player 2");
            else details.setText("Turn: Bot");    
            details.setForeground(Color.RED);
        }        
        details.setFont(new Font("Calibri", 0, 30));
        details.setBounds(menuXOffset, menuYOffset+50, 350, 100);
        details.setVisible(true);
        frameSetup();
    }

    private void finishRoutine() {
        finished = true;
        if (player1>player2) {
            if (mode=="vsHuman2p") details.setText("Player 1 wins!!");
            else details.setText("Player wins!!");
        }
        else if (player2>player1) {
            if (mode=="vsHuman2p") details.setText("Player 2 wins!!");
            else details.setText("Bot wins!!");
        }
        else details.setText("Its a draw!!");
        details.setBackground(Color.white);
        details.setFont(new Font("Calibri", 0, 30));
        details.setBounds(10, 250, 350, 100);
        details.setVisible(true);
        frameSetup();
    }

    private void botManager() {
        int[] botMove = new int[2];
        if (mode=="vsBot1") botMove = bot1.move(squares, srows, scols, hbtnColors, vbtnColors);
        else if (mode=="vsBot2") botMove = bot2.move(squares, srows, scols, hbtnColors, vbtnColors, player2, player1);
        else if (mode=="vsBot3") botMove = bot3.move(squares, srows, scols, hbtnColors, vbtnColors, player2, player1);
        if (botMove[1]==0) {
            hbtnColors[botMove[0]] = 'r';
            hddBtns[botMove[0]].setBackground(p2Filled);
        }
        else {
            vbtnColors[botMove[0]] = 'r';
            vddBtns[botMove[0]].setBackground(p2Filled);
        }
        
        scoring(srows[botMove[1]][botMove[0]], scols[botMove[1]][botMove[0]], botMove[1], botMove[0]);        
    }
}
