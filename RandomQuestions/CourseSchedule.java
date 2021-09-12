package RandomQuestions;

import java.util.ArrayList;
import java.util.List;

public class CourseSchedule {
    public int[] findOrder(int N, int[][] pre) {
        ArrayList[] g = new ArrayList[N];
        
        for (int i = 0; i < N; i ++) g[i] = new ArrayList<>();
        for (int[] p : pre) g[p[1]].add(p[0]);
        
        int[] state = new int[N];
        List<Integer> ans = new ArrayList<>();
        
        for (int i = 0; i < N; i ++) {
            if (state[i] == 0 && hasCycle(state, i, g, ans)) return new int[]{};
        }
        
        int[] res = new int[ans.size()];
        for (int i = 0; i < res.length; i ++) res[i] = ans.get(res.length - i - 1);
        return res;
    }
    
    private boolean hasCycle(int[] state, int cur, ArrayList[] g, List<Integer> ans) {
        state[cur] = 1;
        for (int i = 0; i < g[cur].size(); i ++) {
            int next = (int)g[cur].get(i);
            if (state[next] == 1) return true;
            //这边一定要是state[next] == 0!!!!!
            if (state[next] == 0 && hasCycle(state, next, g, ans)) return true;
        }
        state[cur] = 2;
        ans.add(cur);
        return false;
    }
}
