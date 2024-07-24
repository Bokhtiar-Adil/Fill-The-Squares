public class Bot3_fast {
    
    int dims, target, current;
    int[] hScores, vScores, res;
    int maxDepth;
    
    public Bot3_fast(int n) {
        dims = n;
        hScores = new int[n*(n+1)];
        vScores = new int[n*(n+1)];
        target = (n*n)/2 + 1;
        res = new int[2];
        maxDepth = 9;
    }

    public int[] move(int[][] squares, int[][] srows, int[][] scols, char[] hbtnColors, char[] vbtnColors, int botScore, int oppScore) {
        
        int tmp = dims*(dims+1);
        for (int i=0; i<tmp; i++) {
            hScores[i] = 0;
            vScores[i] = 0;
        }
        int[][] copiedSquares = new int[dims+1][dims+1];
        for (int i=0; i<dims+1; i++) {
            for (int j=0; j<dims+1; j++) copiedSquares[i][j] = squares[i][j];
        }
        
        char[] copiedHbtnColors = new char[tmp], copiedVbtnColors = new char[tmp];
        for (int i=0; i<tmp; i++) {
            copiedHbtnColors[i] = hbtnColors[i];
            copiedVbtnColors[i] = vbtnColors[i];
        }
        alpha_beta_pruning(copiedSquares, srows, scols, copiedHbtnColors, copiedVbtnColors, botScore, oppScore, -2147483648, 2147483647, true, maxDepth);
        return res;
    }

    private int alpha_beta_pruning(int[][] squares, int[][] srows, int[][] scols, char[] oldHbtnColors, char[] oldVbtnColors, int botScore, int oppScore, int alpha, int beta, boolean turn, int depth) {
        int tmp = dims*(dims+1);
        if (depth==0) return botScore;
        int[] hScores = new int[tmp], vScores = new int[tmp];
        boolean newTurn = turn;
        int best = (turn) ? -2147483648 : 2147483647;
        for (int i=0; i<tmp; i++) {
            // hbtns
            if (oldHbtnColors[i] != 'x') continue;
            int tmp2 = ++squares[srows[0][i]][scols[0][i]];
            if (tmp2==4) {
                if (turn) {
                    botScore++;
                    oldHbtnColors[i] = 'g';
                }
                else {
                    oppScore++;
                    oldHbtnColors[i] = 'r';
                }
            }
            int tmp3 = ++squares[srows[0][i]+1][scols[0][i]];
            if (tmp3==4) {
                if (turn) {
                    botScore++;
                    oldHbtnColors[i] = 'r';
                }
                else {
                    oppScore++;
                    oldHbtnColors[i] = 'g';
                }
            }
            if (tmp2!=4 && tmp3!=4) newTurn = !turn;
            // else newTurn = !turn;
            hScores[i] = alpha_beta_pruning(squares, srows, scols, oldHbtnColors, oldVbtnColors, botScore, oppScore, alpha, beta, newTurn, (newTurn!=turn)?depth-1:depth);
            squares[srows[0][i]][scols[0][i]]--;
            squares[srows[0][i]+1][scols[0][i]]--;
            oldHbtnColors[i] = 'x';
            if (tmp2==4) {                
                if (turn) botScore--;
                else oppScore--;
            }
            if (tmp3==4) {
                if (turn) botScore--;
                else oppScore--;
            }
            newTurn = turn;

            if (turn) {
                if (hScores[i]>best) {
                    best = hScores[i];
                    if (depth==maxDepth) {
                        res[0] = i;
                        res[1] = 0; 
                    }
                }
                if (best>alpha) alpha = best;
            }
            else {
                if (hScores[i]<best) best = hScores[i];
                if (best<beta) beta = best;
            }
            if (beta <= alpha) break;

            // vbtns
            if (oldVbtnColors[i] != 'x') continue;
            tmp2 = ++squares[srows[1][i]][scols[1][i]];
            if (tmp2==4) {
                if (turn) {
                    botScore++;
                    oldVbtnColors[i] = 'r';
                }
                else {
                    oppScore++;
                    oldVbtnColors[i] = 'g';
                }
            }
            tmp3 = ++squares[srows[1][i]][scols[1][i]+1];
            if (tmp3==4) {
                if (turn) {
                    botScore++;
                    oldVbtnColors[i] = 'g';
                }
                else {
                    oppScore++;
                    oldVbtnColors[i] = 'r';
                }
            }
            if (tmp2!=4 && tmp3!=4) newTurn = !turn;
            vScores[i] = alpha_beta_pruning(squares, srows, scols, oldHbtnColors, oldVbtnColors, botScore, oppScore, alpha, beta, newTurn, (newTurn!=turn)?depth-1:depth);
            squares[srows[1][i]][scols[1][i]]--;
            squares[srows[1][i]][scols[1][i]+1]--;
            oldVbtnColors[i] = 'x';
            if (tmp2==4) {                
                if (turn) botScore--;
                else oppScore--;
            }
            if (tmp3==4) {
                if (turn) botScore--;
                else oppScore--;
            }
            newTurn = turn;

            if (turn) {
                if (vScores[i]>best) {
                    best = vScores[i];
                    if (depth==maxDepth) {
                        res[0] = i;
                        res[1] = 1; 
                    }
                }
                if (best>alpha) alpha = best;
            }
            else {
                if (vScores[i]<best) best = vScores[i];
                if (best<beta) beta = best;
            }
            if (beta <= alpha) break;

        }
        return best;
    }
}
