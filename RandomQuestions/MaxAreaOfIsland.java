package DoorDash;

public class MaxAreaOfIsland {
    public int maxAreaOfIsland(int[][] grid) {
        int M = grid.length, N = grid[0].length;
        boolean[][] v = new boolean[M][N];
        int ans = 0;
        for (int i = 0; i < M; i ++) {
            for (int j = 0; j < N; j ++) {
                if (!v[i][j] && grid[i][j] == 1) {
                    ans = Math.max(ans, dfs(i, j, grid, v));
                }
            }
        }
        return ans;
    }
    
    int[] dir = new int[]{-1, 0, 1, 0, -1};
    private int dfs(int x, int y, int[][] grid, boolean[][] v) {
        int ans = 0;
        v[x][y] = true;
        ans ++;
        for (int i = 0; i < 4; i ++) {
            int nx = x + dir[i];
            int ny = y + dir[i + 1];
            if (nx < 0 || nx >= grid.length || ny < 0 || ny >= grid[0].length) continue;
            if (grid[nx][ny] != 1 || v[nx][ny]) continue;
            ans += dfs(nx, ny, grid, v);
        }
        return ans;
    }
}
