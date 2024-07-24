public class Bot2_advanced {
    
    int dims, target, current;
    int[] hScores, vScores, res;
    int maxDepth;
    
    public Bot2_advanced(int n) {
        dims = n;
        hScores = new int[n*(n+1)];
        vScores = new int[n*(n+1)];
        target = (n*n)/2 + 1;
        res = new int[2];
        maxDepth = 5;
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
        minmax(copiedSquares, srows, scols, copiedHbtnColors, copiedVbtnColors, botScore, oppScore, true, maxDepth);
        return res;
    }

    private int minmax(int[][] squares, int[][] srows, int[][] scols, char[] oldHbtnColors, char[] oldVbtnColors, int botScore, int oppScore, boolean turn, int depth) {
        int tmp = dims*(dims+1);
        if (depth==0) return botScore-oppScore;
        int[] hScores = new int[tmp], vScores = new int[tmp];
        boolean newTurn = turn;
        // hbtns
        for (int i=0; i<tmp; i++) {
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
                    oldHbtnColors[i] = 'g';
                }
                else {
                    oppScore++;
                    oldHbtnColors[i] = 'r';
                }
            }
            if (tmp2!=4 && tmp3!=4) newTurn = !turn;
            // else newTurn = !turn;
            hScores[i] = minmax(squares, srows, scols, oldHbtnColors, oldVbtnColors, botScore, oppScore, newTurn, depth-1);
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
        }

        // vbtns
        for (int i=0; i<tmp; i++) {
            if (oldVbtnColors[i] != 'x') continue;
            int tmp2 = ++squares[srows[1][i]][scols[1][i]];
            if (tmp2==4) {
                if (turn) {
                    botScore++;
                    oldVbtnColors[i] = 'g';
                }
                else {
                    oppScore++;
                    oldVbtnColors[i] = 'r';
                }
            }
            int tmp3 = ++squares[srows[1][i]][scols[1][i]+1];
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
            // else newTurn = !turn;
            vScores[i] = minmax(squares, srows, scols, oldHbtnColors, oldVbtnColors, botScore, oppScore, newTurn, depth-1);
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
        }

        int mx;
        if (turn) mx = -2147483648;
        else mx = 2147483647;
        // hbtns
        for (int i=0; i<tmp; i++) {
            if (oldHbtnColors[i]!='x') continue;
            if (turn && hScores[i]>=mx) {
                mx = hScores[i];
                if (depth==maxDepth) {
                    res[0] = i;
                    res[1] = 0;
                }
            }
            else if (!turn && hScores[i]<=mx) mx = hScores[i];
        }
        // vbtns
        for (int i=0; i<tmp; i++) {
            if (oldVbtnColors[i]!='x') continue;
            if (turn && vScores[i]>=mx) {
                mx = vScores[i];
                if (depth==maxDepth) {
                    res[0] = i;
                    res[1] = 1;
                }
            }
            else if (!turn && vScores[i]<=mx) mx = vScores[i];
        }
        return mx;
    }
}
