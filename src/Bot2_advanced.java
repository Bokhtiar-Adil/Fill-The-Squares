public class Bot2_advanced {
    
    int dims, target, current;
    int[] hScores, vScores, res;
    int mx;
    
    public Bot2_advanced(int n) {
        dims = n;
        hScores = new int[n*(n+1)];
        vScores = new int[n*(n+1)];
        target = (n*n)/2 + 1;
        res = new int[2];
    }

    public int[] move(int[][] squares, int[][] srows, int[][] scols, char[] hbtnColors, char[] vbtnColors, int botScore, int oppScore) {
        
        int tmp = dims*(dims+1);
        for (int i=0; i<tmp; i++) {
            hScores[i] = 0;
            vScores[i] = 0;
        }
        mx = -2147483648;
        minmax(squares, hScores, vScores, srows, scols, hbtnColors, vbtnColors, botScore, oppScore, true, 3);
        return res;
    }

    private void minmax(int[][] squares, int[] oldHScores, int[] oldVScores, int[][] srows, int[][] scols, char[] oldHbtnColors, char[] oldVbtnColors, int botScore, int oppScore, boolean turn, int depth) {

        int tmp = dims*(dims+1);
        if (depth==0 || botScore>=target) {
            for (int i=0; i<tmp; i++) {
                if (oldHScores[i]>=mx) {
                    mx = oldHScores[i];
                    res[0] = i;
                    res[1] = 0;
                }
                if (oldVScores[i]>mx) {
                    mx = oldVScores[i];
                    res[0] = i;
                    res[1] = 1;
                }            
            }
            return;
        }; 
        if (oppScore>=target) return;

        int[][] copiedSquares = new int[dims+1][dims+1];
        for (int i=0; i<dims+1; i++) {
            for (int j=0; j<dims+1; j++) copiedSquares[i][j] = squares[i][j];    
        }
        int[] copiedHScores = new int[tmp], copiedVScores = new int[tmp];
        char[] copiedHbtnColors = new char[tmp], copiedVbtnColors = new char[tmp];
        for (int i=0; i<tmp; i++) {
            copiedHScores[i] = oldHScores[i];
            copiedVScores[i] = oldVScores[i];
            copiedHbtnColors[i] = oldHbtnColors[i];
            copiedVbtnColors[i] = oldVbtnColors[i];
        }
        for (int i=0; i<tmp; i++) {
            int val = 0;
            int tmp3 = copiedSquares[srows[0][i]][scols[0][i]] + 1, newBotScore = botScore, newOppScore = oppScore;
            boolean newTurn = turn;
            
            if (oldHbtnColors[i] == 'x') {
                int tmpScore = 0;
                if (tmp3==4) tmpScore++;
                val = (tmp3==4) ? 1000 : (tmp3>4 ? 0 : tmp3);
                if (i>=dims) {
                    tmp3 += copiedSquares[srows[0][i]+1][scols[0][i]]+1;
                    if (tmp3==4) tmpScore++;
                    val += ((tmp3==4)? 1000 : (tmp3>4 ? 0 : tmp3));
                }
                if (turn) {
                    copiedHScores[i] += val;
                    copiedHbtnColors[i] = 'r';
                }
                else {
                    copiedHScores[i] -= val;
                    copiedHbtnColors[i] = 'g';
                }
                copiedSquares[srows[0][i]][scols[0][i]]++;
                if (tmp3==4) {
                    if (turn) newBotScore += tmpScore;
                    else newOppScore += tmpScore;
                }
                else newTurn = !turn;
                minmax(copiedSquares, copiedHScores, copiedVScores, srows, scols, copiedHbtnColors, copiedVbtnColors, newBotScore, newOppScore, newTurn, depth-1);
                copiedSquares[srows[0][i]][scols[0][i]]--;
                copiedHScores[i] = oldHScores[i];
                copiedHbtnColors[i] = oldHbtnColors[i];
                newTurn = turn;
                newBotScore = botScore;
                newOppScore = oppScore;
            }
            tmp3 = copiedSquares[srows[0][i]][scols[0][i]] + 1;
            if (oldVbtnColors[i] == 'x') {
                int tmpScore = 0;
                if (tmp3==4) tmpScore++;
                val = (tmp3==4) ? 1000 : (tmp3>4 ? 0 : tmp3);
                if (i%(dims+1)>0) {
                    tmp3 = copiedSquares[srows[1][i]][scols[1][i]+1]+1;
                    if (tmp3==4) tmpScore++;
                    val += ((tmp3==4)? 1000 : (tmp3>4 ? 0 : tmp3));
                }
                if (turn) {
                    copiedVScores[i] += val; 
                    copiedVbtnColors[i] = 'r';
                }
                else {
                    copiedVScores[i] -= val; 
                    copiedVbtnColors[i] = 'g';
                }
                copiedSquares[srows[1][i]][scols[1][i]+1]++;
                if (tmp3==4) {
                    if (turn) newBotScore += tmpScore;
                    else newOppScore += tmpScore;
                }
                else newTurn = !turn;
                minmax(copiedSquares, copiedHScores, copiedVScores, srows, scols, copiedHbtnColors, copiedVbtnColors, newBotScore, newOppScore, newTurn, depth-1);
                copiedSquares[srows[1][i]][scols[1][i]+1]--;
                copiedVScores[i] = oldVScores[i];
                copiedVbtnColors[i] = oldVbtnColors[i];
                newTurn = turn;
                newBotScore = botScore;
                newOppScore = oppScore;
            }
            
        }

        // return res;
    }
}
