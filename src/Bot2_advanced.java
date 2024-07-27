public class Bot2_advanced {
    
    int dims;
    int[] hScores, vScores, res;
    int maxDepth;
    
    public Bot2_advanced(int n) {
        dims = n;
        // these two will contain the calculated scores of the correspoding hbtns and vbtns 
        hScores = new int[n*(n+1)]; 
        vScores = new int[n*(n+1)];
        // res contains the result i.e. the decided move of the bot
        // res[0] = currInd, res[1] = currType
        res = new int[2];
        maxDepth = 5; // depth of the minmax algorithm
    }

    // this bot implements minmax algorithm with alpha-beta pruning
    public int[] move(int[][] squares, int[][] srows, int[][] scols, char[] hbtnColors, char[] vbtnColors, int botScore, int oppScore) {
        
        int tmp = dims*(dims+1);
        // initializing with zeroes to get the fresh value
        for (int i=0; i<tmp; i++) {
            hScores[i] = 0;
            vScores[i] = 0;
        }
        // copying the value to not affect the actual squares array
        int[][] copiedSquares = new int[dims+1][dims+1];
        for (int i=0; i<dims+1; i++) {
            for (int j=0; j<dims+1; j++) copiedSquares[i][j] = squares[i][j];
        }
        // copying the value to not affect the actual btnColors arrays
        char[] copiedHbtnColors = new char[tmp], copiedVbtnColors = new char[tmp];
        for (int i=0; i<tmp; i++) {
            copiedHbtnColors[i] = hbtnColors[i];
            copiedVbtnColors[i] = vbtnColors[i];
        }
        // calling minmax algorithm
        minmax(copiedSquares, srows, scols, copiedHbtnColors, copiedVbtnColors, 0, -2147483648, 2147473647, true, maxDepth);
        // after minmax algorithm finishes calculatiing the heuristic scores for each btn, the highest value is picked
        int best = -2147483648;
        for (int i = 0; i < tmp; i++) {
            if (hbtnColors[i] != 'x') // means already filled btns
                continue;
            if (hScores[i] > best) {
                best = hScores[i];
                res[0] = i;
                res[1] = 0;
            }
        }
        for (int i = 0; i < tmp; i++) {
            if (vbtnColors[i] != 'x') // means already filled btns
                continue;
            if (vScores[i] > best) {
                best = vScores[i];
                res[0] = i;
                res[1] = 1;
            }
        }
        return res;
    }

    // minmax algorithm with alpha-beta pruning
    private int minmax(int[][] squares, int[][] srows, int[][] scols, char[] hbtnColors, char[] vbtnColors, int currScore, int alpha, int beta, boolean turn, int depth) {

        // base case
        if (depth == 0)
            return currScore;
        // dimension of the hbtns and vbtns arrays
            int tmp = dims * (dims + 1);
        /// hbtns
        int best = (turn) ? -2147483648 : 2147483647; // if bot's turn, most is best; if player's turn least is best
        for (int i = 0; i < tmp; i++) {
            // skipping the already filled btns
            if (hbtnColors[i] != 'x')
                continue;
            boolean newTurn = turn;
            // adding one to the corresponding square
            int tmp2 = squares[srows[0][i]][scols[0][i]] + 1;
            squares[srows[0][i]][scols[0][i]]++;
            int tmp3 = 0;
            // taking care of the second square
            if (i >= dims) {
                tmp3 = squares[srows[0][i] + 1][scols[0][i]] + 1;
                squares[srows[0][i] + 1][scols[0][i]]++;
            }
            // calculating heuristic value for this btn
            int val = heuristicValueCalculation(tmp2, tmp3, depth);
            // finding the turn for the next move
            newTurn = findNewTurn(tmp2, tmp3, newTurn);
            if (turn == true)
                hbtnColors[i] = 'r'; // bot's move
            else
                hbtnColors[i] = 'g'; // player's move
            hScores[i] = minmax(squares, srows, scols, hbtnColors, vbtnColors, currScore + val, alpha,
                    beta, newTurn, (newTurn == turn) ? depth : depth - 1);
            // unchanging the changed values so that the next iteration will not be affected by the current iteration
            hbtnColors[i] = 'x';
            squares[srows[0][i]][scols[0][i]]--;
            if (i >= dims)
                squares[srows[0][i] + 1][scols[0][i]]--;
            if (turn == true) {
                if (hScores[i] > best) {
                    best = hScores[i];
                    if (depth == maxDepth) {
                        res[0] = i;
                        res[1] = 0;
                    }
                    if (best > alpha)
                        alpha = best;
                }
            } else {
                if (hScores[i] < best)
                    best = hScores[i];
                if (best < beta)
                    beta = best;
            }
            if (beta<alpha) break; // alpha-beta pruning
        }

        // now doing the same for the vertical btns
        for (int i = 0; i < tmp; i++) {
            if (vbtnColors[i] != 'x')
                continue;
            boolean newTurn = turn;
            int tmp2 = squares[srows[1][i]][scols[1][i]] + 1;
            squares[srows[1][i]][scols[1][i]]++;
            int tmp3 = 0;
            if (i % (dims + 1) > 0) {
                tmp3 = squares[srows[1][i]][scols[1][i] + 1] + 1;
                squares[srows[1][i]][scols[1][i] + 1]++;
            }
            int val = heuristicValueCalculation(tmp2, tmp3, depth);
            newTurn = findNewTurn(tmp2, tmp3, newTurn);
            if (turn == true)
                vbtnColors[i] = 'r';
            else
                vbtnColors[i] = 'g';
            vScores[i] = minmax(squares, srows, scols, hbtnColors, vbtnColors, currScore + val, alpha,
                    beta, newTurn, (newTurn == turn) ? depth : depth - 1);
            vbtnColors[i] = 'x';
            squares[srows[1][i]][scols[1][i]]--;
            newTurn = turn;
            if (i % (dims + 1) > 0)
                squares[srows[1][i]][scols[1][i] + 1]--;
            if (turn == true) {
                if (vScores[i] > best) {
                    best = vScores[i];
                    if (best > alpha)
                        alpha = best;
                }
            } else {
                if (vScores[i] < best)
                    best = vScores[i];
                if (best < beta)
                    beta = best;
            }
            if (beta<alpha) break;
        }

        return best;

    }

    private int heuristicValueCalculation(int criterion_1, int criterion_2, int depth) {
        int val = 0;
        // each value is multiplied by depth means - 
        // suppose we don't multiply by depth - 2 squares get 50, 1 get 25, and so on
        // now, we can get the score 80 in multiple ways - (50+25+5) or (5+25+50) or others
        // this means, the bot can pick 2 squares at first move or at second move or at last move
        // but, if it picks the 5 at the first move, player gets turn for the next move
        // then the player can take the squares
        // so the bot needs to take 2 squares first i.e. score 50, not the score 5
        // to solve this issue, depth is multiplied
        // now (5+25+50) becomes (5*3+25*2+50*1) = 115 and (50+25+5) becomes (50*3+25*2+5*1) = 205
        // so, 2 squares will be picked first
        if (criterion_1 == 4 && criterion_2 == 4) 
            val += 50 * depth; // this move will get 2 squares, so a huge value is assigned
        else if (criterion_1 == 4 || criterion_2 == 4)
            val += 25 * depth; // this move will get 1 square, so half of the previous value is assigned
        else if (criterion_1 == 3 || criterion_2 == 3) 
            val -= 50 * depth; // this move will give the opponent a chance to fill the square, so it is having a negative value
        else 
            val += 5 * depth; // this move has no immediate good effect
        return val;
    }

    // if bot gets square/s, it will get another consecutive turn
    private boolean findNewTurn(int criterion_1, int criterion_2, boolean turn) {
        if (criterion_1==4 || criterion_2==4) return turn;
        else return !turn;
    }

}

