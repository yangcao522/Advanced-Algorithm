package RandomQuestions;

public class DiagonalOrder {
    public int[] findDiagonalOrder(int[][] mat) {
        int M = mat.length, N = mat[0].length;
        int[] ans = new int[M * N];
        int idx = 0;
        
        int i = 0, j = 0;
        
        while (idx < M * N) {
            while (i >= 0 && j < N) {
                ans[idx ++] = mat[i][j];
                i --;
                j ++;
            }
            i ++; j --;
            if (j + 1 < N) j ++;
            else if (i + 1 < M) i ++;
            else break;
            
            while (i < M && j >= 0) {
                ans[idx ++] = mat[i][j];
                i ++;
                j --;
            }
            i --; j ++;
            if (i + 1 < M) i ++;
            else if (j + 1 < N) j ++;
            else break;
        }
        
        return ans;
    }
}
