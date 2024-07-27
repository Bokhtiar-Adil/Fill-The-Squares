public class Bot1_simple {

    int dims;
    int[] hScores, vScores; // these two will contain the calculated scores of the correspoding hbtns and vbtns 
    
    public Bot1_simple(int n) {
        dims = n;
        hScores = new int[n*(n+1)];
        vScores = new int[n*(n+1)];
    }

    // this bot uses a very simple move logic
    // it will calculate the current best move by analysing only single step
    public int[] move(int[][] squares, int[][] srows, int[][] scols, char[] hbtnColors, char[] vbtnColors) {
        // res contains the result i.e. the decided move of the bot
        // res[0] = currInd, res[1] = currType
        int[] res = new int[2];
        int tmp = dims*(dims+1), mx = 0;
        for (int i=0; i<tmp; i++) {
            int val = 0;
            int tmp3 = squares[srows[0][i]][scols[0][i]] + 1;
            // tmp3 == 4 means the current square have all four of its borders filled
            // in that case, performing this move will increase the bot score
            // so, a huge value of 1000 is assigned to prioritize this move
            // tmp3 > 4 means the square was already filled before, so adding 1 turned into something greater than 4
            // so this move is invalid and so 0 is assigned in that case
            // in all other cases (tmp3 == 1,2,3) the value itself is assigned
            val = (tmp3==4) ? 1000 : (tmp3>4 ? 0 : tmp3);
            // if the color is not 'x', means this btn is already filled by someone before
            // so, non-'x' are processed and others are assigned the value 0
            // for horizontal ones
            if (hbtnColors[i] == 'x') {
                hScores[i] = val;
                if (i>=dims) {
                    tmp3 = squares[srows[0][i]+1][scols[0][i]]+1; // the other square is also being checked
                    hScores[i] += ((tmp3==4)? 1000 : (tmp3>4 ? 0 : tmp3));
                }
            }
            else hScores[i] = 0;
            // for vertical ones
            if (vbtnColors[i] == 'x') {
                vScores[i] = val;
                if (i%(dims+1)>0) {
                    tmp3 = squares[srows[1][i]][scols[1][i]+1]+1;
                    vScores[i] += ((tmp3==4)? 1000 : (tmp3>4 ? 0 : tmp3));
                }
            }
            else vScores[i] = 0;
        }
        // now finding the best move - highest scored btn is to be picked and the corresponding properties are to be stored in res
        for (int i=0; i<tmp; i++) {
            if (hScores[i]>=mx) {
                mx = hScores[i];
                res[0] = i;
                res[1] = 0;
            }
            if (vScores[i]>mx) {
                mx = vScores[i];
                res[0] = i;
                res[1] = 1;
            }            
        }
        return res;
    }
}