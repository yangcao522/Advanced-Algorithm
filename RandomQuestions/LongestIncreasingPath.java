package RandomQuestions;

import java.util.Arrays;

public class LongestIncreasingPath {
    // Time Complexity: O(MN)
    public int longestIncreasingPath(int[][] matrix) {
        int M = matrix.length, N = matrix[0].length;
        int ans = 0;
        int[][] memo = new int[M][N];
        for (int[] mm : memo) Arrays.fill(mm, -1);
        for (int i = 0; i < M; i ++) {
            for (int j = 0; j < N; j ++) {
                ans = Math.max(dfs(i, j, matrix, memo), ans);
            }
        }
        return ans;
    }
    
    int[] dir = new int[]{-1, 0, 1, 0, -1};
    private int dfs(int x, int y, int[][] m, int[][] memo) {
        if (memo[x][y] != -1) return memo[x][y];
        int ans = 1;
        int max = 0;
        for (int i = 0; i < 4; i ++) {
            int nx = x + dir[i];
            int ny = y + dir[i + 1];
            if (nx < 0 || nx >= m.length || ny < 0 || ny >= m[0].length) continue;
            if (m[nx][ny] <= m[x][y]) continue;
            max = Math.max(dfs(nx, ny, m, memo), max);
        }
        memo[x][y] = ans + max;
        return memo[x][y];
    }
}
