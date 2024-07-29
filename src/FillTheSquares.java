import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.security.SecureRandom;


public class FillTheSquares {
    
    JFrame frame;
    JLabel scoreboard, details;
    JLabel[] descs, bdescs;
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
    OnlineP2P online;
    String mode;
    int size, menuXOffset = 10, menuYOffset = 50, descItem = 4, bdescItem = 2;

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
    String onlineDetails = "";

    public FillTheSquares(JFrame mainFrame, String mode, int n) {
        rows = n;
        columns = n;
        hddBtns = new JButton[rows*(columns+1)]; // horizontal lines of the squares
        vddBtns = new JButton[rows*(columns+1)]; // vertical lines of the squares
        hbtnColors = new char[rows*(columns+1)]; // colors of the horizontal btns
        vbtnColors = new char[rows*(columns+1)]; // colors of the vertical btns
        // the following two arrays hold which specific square [x][y] btn belongs to
        // x = 0 means horizontal btn, x = 1 means vertical btn
        // other than the topmost, downmost, leftmost and rightmost lines, each line belong to two different squares
        // for example, horizontal line of row=1, col=1 belongs to two squares - one above and one below
        // similarly, vertical line of row=1, col=1 belongs to two squares - one left and one right
        // these arrays hold the above ones for the horizontals and the left ones for the verticals
        srows = new int[2][rows*(columns+1)];
        scols = new int[2][rows*(columns+1)];
        // to store how many borders of a square[x][y] are already selected by players
        // taking (row+1) and (column+1) to mitigate a issue with topmost, downmost, leftmost and rightmost lines
        squares = new int[rows+1][columns+1]; 
        for (int i=0; i<rows+1; i++) {
            for (int j=0; j<columns+1; j++) squares[i][j] = 0;
        }
        // after a square is completely filled, the label will pop up to show who acquired it
        squareLabels = new JLabel[rows][columns];
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) squareLabels[i][j] = new JLabel();
        }
        frame = new JFrame();
        menu = new JPanel();
        board = new JPanel();
        scoreboard = new JLabel();
        details = new JLabel();
        descs = new JLabel[descItem]; // text items on the menu
        for (int i=0; i<descItem; i++) descs[i] = new JLabel();
        bdescs = new JLabel[bdescItem]; // text items above the board
        for (int i=0; i<bdescItem; i++) bdescs[i] = new JLabel();
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
        turn = true; // to indicate whose turn it is
        finished = false; // to indicate whether the game has reached a finishing condition
        normal = Color.LIGHT_GRAY; // when a line is not selected, this color will be displayed
        hovered = Color.yellow; // while a line is hovered on, this color will be displayed
        p1Filled = Color.green; // if player 1 fills a line, this color will be showed
        p2Filled = Color.red; // if player 2 fills a line, this color will be showed
        player1 = 0; // initila score of player 1
        player2 = 0; // initila score of player 2 or bot
        currInd = 2; // indicate the index of the btn on hddbtn and vddbtn arrays
        currType = 2; // currType = 0 means horizontal and currType = 1 means vertical - initially 2 to avoid mistake
        bot1 = new Bot1_simple(n);
        bot2 = new Bot2_advanced(n);
        bot3 = new Bot3_fast(n);
        online = new OnlineP2P();
        this.mode = mode; // stores the game mode
        size = n; // stores the grid size
        this.mainFrame = mainFrame; // stores the frame of the home page
    }

    public void setupOnlineProperties() {
        
        int chkb = JOptionPane.YES_NO_CANCEL_OPTION;
        int chkr = JOptionPane.showConfirmDialog(null, "'Yes': Host a new match; 'No': Join an ongoing match", APP_NAME, chkb);
        boolean isHost;
        String username = generateRandomString();
        if (chkr==1) {
            isHost = true; 
            onlineDetails = "You are host | "; 
        }
        else {
            isHost = false; 
            onlineDetails = "You are guest | ";
        }
        onlineDetails += ("Username: " + username);
        online.setUpMatch(username, isHost, Long.valueOf(size));
    }


    // when the game window is opened, this fucntion is called
    public void play() {
        UI();
        updateScore();
        updateTurnIndicator();
        frameSetup();
    }

    private String generateRandomString() {
        final int length = 5;
        final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
        final SecureRandom RANDOM = new SecureRandom();
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }
    
    // after making all the changes in different parts of the window, this function adds the contents to the frame
    // is called whenever there is a change in the frame
    private void frameSetup() {
        for (int i=0; i<descItem; i++) menu.add(descs[i]);
        menu.add(scoreboard);
        menu.add(details);
        menu.add(rulesBtn);
        menu.add(backBtn);
        menu.add(exitBtn);
        frame.add(menu);
        for (int i=0; i<bdescItem; i++) board.add(bdescs[i]);
        frame.add(board);
        frame.setVisible(true);
    }

    // designs all the btns, labels, panels, etc
    private void UI() {
        
        int posX, posY; // to hold the xOffset and yOffset of the grid lines
        
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

        // this loop writes the contents on the menu
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

        // this loop writes the text above the grid
        for (int i=0; i<bdescItem; i++) {
            int t = rows*columns, t2 = player1+player2, t3 = t-t2;;
            if (i==0) bdescs[i].setText(onlineDetails);
            if (i==1) bdescs[i].setText("Total squares: " + t + " | Filled: " + t2 + " | Left: " + t3);
            bdescs[i].setFont(new Font("Calibri", 0, 20));
            bdescs[i].setBounds(173, 20, 305, 100);
            bdescs[i].setForeground(Color.WHITE);
            bdescs[i].setVisible(true);
        }

        // to dynamically calculate the starting postion of the grid
        // grids can be of diffrent sizes such as 3x3, 4x4, 8x8, etc
        // this ddd willl be used to center the grid irrespective of the grid size
        int ddd = (columns%2==0)?columns/2:columns/2+1;
        posX = 350 - (ddd*70) + 10; // 70 - ignore this comment
        posY = 375 - ddd*60; // 110 - ignore this comment
        // square labels are displayed - they will show shich player took which squares
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                squareLabels[i][j].setText(" "); // initially no text
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
        // horizontal lines are printed here
        // an extra row is printed - the downmost row
        posX -= 10; // 60 - ignore this comment
        posY -= 10; // 100 - ignore this comment
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
                final int index = i, index2 = j; // non-final variables dont work in the listeners
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
                        currInd = index*rows+index2; // calculating the current index of the btn in the hddbtns
                        if (hbtnColors[currInd] == 'x') {
                            currType = 0;
                            if (turn) {
                                hbtnColors[currInd] = 'g'; // player 1 selected it
                                hddBtns[currInd].setBackground(p1Filled);
                            }
                            else {
                                hbtnColors[currInd] = 'r'; // player 2 or bot selected it
                                hddBtns[currInd].setBackground(p2Filled);
                            }
                            scoring(srows[0][currInd], scols[0][currInd], currType, currInd); // calculates the new score
                        }   
                    }
                });
                board.add(hddBtns[i*rows+j]);
            }
        }
        // vertical lines are printed here
        // an extra column is printed - the rightmost one
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

    // this fucntion calculates the score
    // args include row, column, type, and index of the selected btn
    // this calculates how many new squares are acquired by the current move
    private void scoring(int row, int column, int currType, int currInd) {
        squares[row][column]++; // incrementing the number of borders that has been filled of this square
        // the topmost horizontal line belongs to only one square, others belong to 2 different squares - above and below
        // topmost row contains the btns index 0 to (rows-1)
        // similarly, for the vertical ones, the leftmost column belongs to only one square
        // others belong to two squares - left and right 
        // the leftmost column hold the vertical lines with index = index%(columns+1)==0
        // how about the rightmost vertical ones and the downmost horizontal ones? don't they belong to only one squares?
        // yes, to mitigate that issue, an extra row and an extra column was taken in the squares array 
        if (currType==0 && currInd>=rows) squares[row+1][column]++;
        else if (currType==1 && currInd%(columns+1)>0) squares[row][column+1]++;
        int val = 0; // this will contain the total score acquired from the current move
        // squares[x][y] = k means k borders of this suqare have been filled so far
        // if k=4, this means all borders are filled and this player will acquire this square
        if (squares[row][column]==4) {
            val++; // 
            if (turn) squareLabels[row][column].setText("  1 ");
            else {
                if (mode=="vsHuman2p" || mode=="onlineP2p") squareLabels[row][column].setText("  2 ");
                else squareLabels[row][column].setText("  B ");
            }
            squares[row][column] = 100;
        }
        // for horizontal ones
        if (currType==0 && squares[row+1][column]==4) {
            val++;
            if (turn) squareLabels[row+1][column].setText("  1 ");
            else {
                if (mode=="vsHuman2p" || mode=="onlineP2p") squareLabels[row+1][column].setText("  2 ");
                else squareLabels[row+1][column].setText("  B ");
            }
            squares[row+1][column] = 100;
        }
        // for vertical ones
        if (currType==1 && squares[row][column+1]==4) {
            val++;
            if (turn) squareLabels[row][column+1].setText("  1 ");
            else {
                if (mode=="vsHuman2p" || mode=="onlineP2p") squareLabels[row][column+1].setText("  2 ");
                else squareLabels[row][column+1].setText("  B ");
            }
        }
        if (turn) player1 += val;
        else player2 += val;
        updateScore();
        if (val==0) turn = !turn; // if a player acquires new square/s this round, he will get another move as reward
        // if more than half of the total squares are acquired by a player, he wins
        // if both attains the same, its a draw
        if (player1>(rows*columns)/2 || player2>(rows*columns)/2 || player1+player2==rows*columns) finishRoutine();
        else {
            updateTurnIndicator();
            if (mode!="vsHuman2p" && mode!="onlineP2p" && !turn) {
                botManager();
                frameSetup();
            } 
        }
    }

    // updates the score and the texts on the window
    private void updateScore() {
        String scoreText = "";
        if (mode == modes[0] || mode == modeLbls[1]) scoreText = "Player 1: " + player1 + " | Player 2: ";
        else scoreText = "Player: " + player1 + " | Bot: ";
        scoreText += player2;
        scoreboard.setText(scoreText);
        scoreboard.setForeground(Color.ORANGE);
        scoreboard.setFont(new Font("Calibri", 0, 30));
        scoreboard.setBounds(menuXOffset, menuYOffset, 350, 100);
        scoreboard.setVisible(true);
        int t = rows*columns, t2 = player1+player2, t3 = t-t2;;
        bdescs[1].setText("Total squares: " + t + " | Filled: " + t2 + " | Left: " + t3);
        frameSetup();
    }

    // updates the turn indicators on the window
    private void updateTurnIndicator() {
        if (turn) {
            if (mode=="vsHuman2p" || mode=="onlineP2p") details.setText("Turn: Player 1");
            else details.setText("Turn: Player");
            details.setForeground(Color.GREEN);
        }
        else {
            if (mode=="vsHuman2p" || mode=="onlineP2p") details.setText("Turn: Player 2");
            else details.setText("Turn: Bot");    
            details.setForeground(Color.RED);
        }        
        details.setFont(new Font("Calibri", 0, 30));
        details.setBounds(menuXOffset, menuYOffset+50, 350, 100);
        details.setVisible(true);
        frameSetup();
    }

    // invoked when the match is finished
    private void finishRoutine() {
        finished = true;
        if (player1>player2) {
            if (mode=="vsHuman2p" || mode=="onlineP2p") details.setText("Player 1 wins!!");
            else details.setText("Player wins!!");
        }
        else if (player2>player1) {
            if (mode=="vsHuman2p" || mode=="onlineP2p") details.setText("Player 2 wins!!");
            else details.setText("Bot wins!!");
        }
        else details.setText("Its a draw!!");
        details.setBackground(Color.white);
        details.setFont(new Font("Calibri", 0, 30));
        details.setBounds(menuXOffset, menuYOffset+50, 350, 100);
        details.setVisible(true);
        frameSetup();
    }

    // invoked when it's the bot's turn to move
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
