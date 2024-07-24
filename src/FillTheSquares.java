import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class FillTheSquares {
    
    JFrame frame;
    JLabel scoreboard, details;
    JPanel menu, board;
    JLabel[][] squareLabels;
    int rows, columns;
    JButton[] hddBtns, vddBtns;
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

    public FillTheSquares(String mode, int n) {
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
        board = new JPanel();
        scoreboard = new JLabel();
        details = new JLabel();
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
    } 

    public void play() {
        UI();
        updateScore();
        updateTurnIndicator();
        frameSetup();
    }

    private void frameSetup() {
        menu.add(scoreboard);
        menu.add(details);
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
        frame.setTitle("Gamebox");

        // control panels
        menu = new JPanel();
        menu.setLayout((LayoutManager)null);
        menu.setBounds(0, 0, 350, 700);
        menu.setBackground(Color.black);

        board = new JPanel();
        board.setLayout((LayoutManager)null);
        board.setBounds(350, 0, 650, 700);
        board.setBackground(Color.DARK_GRAY);

        posX = 70;
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
        
        posX = 60;
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
        scoreboard.setText("Player1: " + player1 + " Player2: " + player2);
        scoreboard.setBackground(Color.white);
        scoreboard.setFont(new Font("Calibri", 0, 30));
        scoreboard.setBounds(10, 50, 350, 100);
        scoreboard.setVisible(true);
        menu.add(scoreboard);
        frameSetup();
    }

    private void updateTurnIndicator() {
        if (turn) {
            if (mode=="vsHuman2p") details.setText("Turn: Player1");
            else details.setText("Turn: Player");
        }
        else {
            if (mode=="vsHuman2p") details.setText("Turn: Player2");
            else details.setText("Turn: Bot");    
        }
        details.setBackground(Color.white);
        details.setFont(new Font("Calibri", 0, 30));
        details.setBounds(10, 150, 350, 100);
        details.setVisible(true);
        // menu.add(details);
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
        details.setBounds(10, 150, 350, 100);
        details.setVisible(true);
        // menu.add(details);
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

    // private void scoring_old() {
    //     boolean done = false;
        
    //     if (currType==0) {
    //         if (hbtnColors[currInd]=='g') {
    //             if (currInd>=8 && hbtnColors[currInd-8]=='g') {
    //                 // int tmp = (currInd-8)+((int)((currInd-8)/8));
    //                 int tmp = currInd + currInd%rows - (rows+1);
    //                 if (vbtnColors[tmp]=='g' && vbtnColors[tmp+1]=='g') {
    //                     if (!turn) player1++; // turn gets changed right after move is recorded, so, use the reversed value
    //                     else player2++;
    //                     done = true;
    //                 }   
    //             }
    //             if (done) return;

    //             if (currInd<8 && hbtnColors[currInd+8]=='g') {
    //                 int tmp = currInd+((int)(currInd/8));
    //                 if (vbtnColors[tmp]=='g' && vbtnColors[tmp+1]=='g') {
    //                     if (!turn) player1++; // turn gets changed right after move is recorded, so, use the reversed value
    //                     else player2++;
    //                     done = true;
    //                 }   
    //             }
    //             if (done) return;
    //         }
    //     }

    //     if (currType==1) {
    //         if (vbtnColors[currInd]=='g') {
    //             if (currInd%9>0 && vbtnColors[currInd-1]=='g') {
    //                 int tmp = (currInd-1)-((int)((currInd-1)/8));
    //                 if (hbtnColors[tmp]=='g' && hbtnColors[tmp+8]=='g') {
    //                     if (!turn) player1++; // turn gets changed right after move is recorded, so, use the reversed value
    //                     else player2++;
    //                     done = true;
    //                 }   
    //             }
    //             if (done) return;

    //             if (currInd%9<8 && vbtnColors[currInd+1]=='g') {
    //                 int tmp = currInd-((int)(currInd/8));
    //                 if (hbtnColors[tmp]=='g' && hbtnColors[tmp+8]=='g') {
    //                     if (!turn) player1++; // turn gets changed right after move is recorded, so, use the reversed value
    //                     else player2++;
    //                     done = true;
    //                 }   
    //             }
    //             if (done) return;
    //         }
    //     }
    // }



}
