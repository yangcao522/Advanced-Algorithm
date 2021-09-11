package DoorDash;

public class NumberOfIsland {
    class UF {
        int[] father;
        int[] size;
        int cnt;
        
        UF(char[][] grid) {
            int M = grid.length, N = grid[0].length;
            father = new int[M * N];
            size = new int[M * N];
            for (int i = 0; i < M; i ++) {
                for (int j = 0; j < N; j ++) {
                    if (grid[i][j] == '0') continue;
                    cnt ++;
                    int index = i * N + j;
                    father[index] = index;
                    size[index] = 1;
                }
            }
        }
        
        int find(int num) {
            if (num != father[num]) {
                father[num] = find(father[num]);
            }
            return father[num];
        }
        
        void union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return;
            if (size[ra] > size[rb]) {
                father[rb] = ra;
                size[ra] += size[rb];
            } else {
                father[ra] = rb;
                size[rb] += size[ra];
            }
            cnt --;
        }
    }
    
    public int numIslands(char[][] grid) {
        UF uf = new UF(grid);
        int M = grid.length, N = grid[0].length;
        for (int i = 0; i < M; i ++) {
            for (int j = 0; j < N; j ++) {
                if (grid[i][j] == '0') continue;
                if (j + 1 < N && grid[i][j + 1] == '1') uf.union(i * N + j, i * N + (j + 1));
                if (i + 1 < M && grid[i + 1][j] == '1') uf.union(i * N + j, (i + 1) * N + j);
            }
        }
        return uf.cnt;
    }
}
