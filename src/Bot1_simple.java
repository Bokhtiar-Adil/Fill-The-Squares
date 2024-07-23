public class Bot1_simple {

    int dims;
    int[] hScores, vScores;
    
    public Bot1_simple(int n) {
        dims = n;
        hScores = new int[n*(n+1)];
        vScores = new int[n*(n+1)];
    }

    public int[] move(int[][] squares, int[][] srows, int[][] scols, char[] hbtnColors, char[] vbtnColors) {
        int[] res = new int[2];
        int tmp = dims*(dims+1), mx = 0;
        for (int i=0; i<tmp; i++) {
            int val = 0;
            int tmp3 = squares[srows[0][i]][scols[0][i]] + 1;
            val = (tmp3==4) ? 1000 : (tmp3>4 ? 0 : tmp3);
            if (hbtnColors[i] == 'x') {
                hScores[i] = val;
                if (i>=dims) {
                    tmp3 = squares[srows[0][i]+1][scols[0][i]]+1;
                    hScores[i] += ((tmp3==4)? 1000 : (tmp3>4 ? 0 : tmp3));
                }
            }
            else hScores[i] = 0;
            if (vbtnColors[i] == 'x') {
                vScores[i] = val;
                if (i%(dims+1)>0) {
                    tmp3 = squares[srows[1][i]][scols[1][i]+1]+1;
                    vScores[i] += ((tmp3==4)? 1000 : (tmp3>4 ? 0 : tmp3));
                }
            }
            else vScores[i] = 0;
        }
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