public class Bot3_fast {

    int dims, target, current;
    int[] hScores, vScores, res;
    int maxDepth;
    
    public Bot3_fast(int n) {
        dims = n;
        hScores = new int[n * (n + 1)];
        vScores = new int[n * (n + 1)];
        target = (n * n) / 2 + 1;
        res = new int[2];
        maxDepth = 3;
    }

    public int[] move(int[][] squares, int[][] srows, int[][] scols, char[] hbtnColors, char[] vbtnColors, int botScore,
            int oppScore) {

        int tmp = dims * (dims + 1);
        for (int i = 0; i < tmp; i++) {
            hScores[i] = 0;
            vScores[i] = 0;
        }
        int[][] copiedSquares = new int[dims + 1][dims + 1];
        for (int i = 0; i < dims + 1; i++) {
            for (int j = 0; j < dims + 1; j++)
                copiedSquares[i][j] = squares[i][j];
        }

        char[] copiedHbtnColors = new char[tmp], copiedVbtnColors = new char[tmp];
        for (int i = 0; i < tmp; i++) {
            copiedHbtnColors[i] = hbtnColors[i];
            copiedVbtnColors[i] = vbtnColors[i];
        }
        int best = -2147483648;
        find_Immediate_Best_move(copiedSquares, srows, scols, copiedHbtnColors, copiedVbtnColors);
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
        if (best>=1) return res; 
        minmax_v2(copiedSquares, srows, scols, copiedHbtnColors, copiedVbtnColors, 0, -2147483647,
                2147483647, true, maxDepth);
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

    private void find_Immediate_Best_move(int[][] squares, int[][] srows, int[][] scols, char[] hbtnColors, char[] vbtnColors) {

        int tmp = dims * (dims + 1);
        // hbtns
        for (int i = 0; i < tmp; i++) {
            if (hbtnColors[i] != 'x')
                continue;
            int tmp2 = squares[srows[0][i]][scols[0][i]] + 1;
            int tmp3 = 0;
            if (i>=dims) tmp3 = squares[srows[0][i] + 1][scols[0][i]] + 1;
            if (tmp2==4 && tmp3==4) hScores[i] = 2;
            else if (tmp2==4 || tmp3==4) hScores[i] = 1;
            else hScores[i] = 0;
        }
        // vbtns
        for (int i = 0; i < tmp; i++) {
            if (vbtnColors[i] != 'x')
                continue;
            int tmp2 = squares[srows[1][i]][scols[1][i]] + 1;
            int tmp3 = 0;
            if (i%(dims+1)>0) tmp3 = squares[srows[1][i]][scols[1][i]+1] + 1;
            if (tmp2==4 && tmp3==4) vScores[i] = 2;
            else if (tmp2==4 || tmp3==4) vScores[i] = 1;
            else vScores[i] = 0;
        }
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