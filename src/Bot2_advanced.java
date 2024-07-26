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
        //minmax(copiedSquares, srows, scols, copiedHbtnColors, copiedVbtnColors, botScore, oppScore, true, maxDepth);
        minmax_v2(copiedSquares, srows, scols, copiedHbtnColors, copiedVbtnColors, 0, -2147483648, 2147473647, true, maxDepth);
        int best = -2147483648;
        for (int i = 0; i < tmp; i++) {
            if (hbtnColors[i] != 'x')
                continue;
            if (hScores[i] > best) {
                best = hScores[i];
                res[0] = i;
                res[1] = 0;
            }
        }
        for (int i = 0; i < tmp; i++) {
            if (vbtnColors[i] != 'x')
                continue;
            if (vScores[i] > best) {
                best = vScores[i];
                res[0] = i;
                res[1] = 1;
            }
        }
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

    private int minmax_v2(int[][] squares, int[][] srows, int[][] scols, char[] hbtnColors,
            char[] vbtnColors, int currScore, int alpha, int beta, boolean turn, int depth) {

        if (depth == 0)
            return currScore;
        int tmp = dims * (dims + 1);
        // hbtns
        int best = (turn) ? -2147483648 : 2147483647;
        for (int i = 0; i < tmp; i++) {
            if (hbtnColors[i] != 'x')
                continue;
            boolean newTurn = turn;
            int tmp2 = squares[srows[0][i]][scols[0][i]] + 1;
            squares[srows[0][i]][scols[0][i]]++;
            int tmp3 = 0;
            if (i >= dims) {
                tmp3 = squares[srows[0][i] + 1][scols[0][i]] + 1;
                squares[srows[0][i] + 1][scols[0][i]]++;
            }
            int val = heuristicValueCalculation(tmp2, tmp3, depth);
            newTurn = findNewTurn(tmp2, tmp3, newTurn);
            if (turn == true)
                hbtnColors[i] = 'r';
            else
                hbtnColors[i] = 'g';
            hScores[i] = minmax_v2(squares, srows, scols, hbtnColors, vbtnColors, currScore + val, alpha,
                    beta, newTurn, (newTurn == turn) ? depth : depth - 1);
            hbtnColors[i] = 'x';
            squares[srows[0][i]][scols[0][i]]--;
            newTurn = turn;
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
            if (beta<alpha) break;
        }

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
            vScores[i] = minmax_v2(squares, srows, scols, hbtnColors, vbtnColors, currScore + val, alpha,
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
        if (criterion_1 == 4 && criterion_2 == 4)
            val += 50 * depth;
        else if (criterion_1 == 4 || criterion_2 == 4)
            val += 25 * depth;
        else if (criterion_1 == 3 || criterion_2 == 3) 
            val -= 50 * depth;
        else 
            val += 5 * depth;
        return val;
    }

    private boolean findNewTurn(int criterion_1, int criterion_2, boolean turn) {
        if (criterion_1==4 || criterion_2==4) return turn;
        else return !turn;
    }

}
