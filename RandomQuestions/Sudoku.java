package RandomQuestions;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {
    //1) 判断board是否合法
    public boolean isValidSudoku(char[][] board) {
        int N = 9;
        
        int[] rows = new int[N];
        int[] cols = new int[N];
        int[] blocks = new int[N];
        
        for (int i = 0; i < N; i ++) {
            for (int j = 0; j < N; j ++) {
                if (board[i][j] == '.') continue;
                int val = board[i][j] - '0';
                
                //check row
                if ((rows[i] >> val & 1) == 1) return false;
                rows[i] |= (1 << val);
                
                //check col
                if ((cols[j] >> val & 1) == 1) return false;
                cols[j] |= (1 << val);
                
                //check blocks
                int x = i / 3;
                int y = j / 3;
                int idx = x * 3 + y;
                if ((blocks[idx] >> val & 1) == 1) return false;
                blocks[idx] |= (1 << val);
            }
        }
        
        return true;
    }

    //2) dfs解数独
    List<int[]> emptyCells = new ArrayList<>();
    int[] rows = new int[9];
    int[] cols = new int[9];
    int[] blks = new int[9];
    
    public void solveSudoku(char[][] board) {
        int M = board.length;
        //查找所有的empty cell, 以及build三个bit array
        for (int i = 0; i < M; i ++) {
            for (int j = 0; j < M; j ++) {
                if (board[i][j] == '.') {
                    emptyCells.add(new int[]{i, j});
                } else {
                    int val = board[i][j] - '0';
                    rows[i] |= 1 << val;
                    cols[j] |= 1 << val;
                    blks[i / 3 * 3 + j / 3] |= 1 << val;
                }
            }
        }
        dfs(board, 0);
    }
    
    //backtracking
    private boolean dfs(char[][] board, int i) {
        if (i == emptyCells.size()) return true;
        int x = emptyCells.get(i)[0];
        int y = emptyCells.get(i)[1];
        for (int k = 1; k <= 9; k ++) {
            if ((rows[x] >> k & 1) == 1) continue;
            if ((cols[y] >> k & 1) == 1) continue;
            if ((blks[x / 3 * 3 + y / 3] >> k & 1) == 1) continue;
            rows[x] |= 1 << k;
            cols[y] |= 1 << k;
            blks[x / 3 * 3 + y / 3] |= 1 << k;
            board[x][y] = (char)('0' + k);
            if (dfs(board, i + 1)) return true;
            board[x][y] = '.';
            rows[x] ^= 1 << k;
            cols[y] ^= 1 << k;
            blks[x / 3 * 3 + y / 3] ^= 1 << k;
        }
        
        return false;
    }
}
