package DoorDash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class ShortestPath {
    public static void main(String[] args) {
        List<Integer> fromNodes = Arrays.asList(1, 2, 3, 4, 5, 1, 5);
        List<Integer> toNodes = Arrays.asList(2, 3, 4, 5, 1, 3, 3);
        List<Integer> weights = Arrays.asList(1, 1, 1, 1, 3, 2, 1);

        ShortestPath shortestPath = new ShortestPath();
        boolean[] ans = shortestPath.getShortestPath(5, fromNodes, toNodes, weights);
        for (int i = 1; i < ans.length; i ++) {
            System.out.print(ans[i] ? "YES, " : "NO, ");
        }
    }

    public boolean[] getShortestPath(int gNodes, List<Integer> fromNodes, List<Integer> toNodes, List<Integer> weights) {
        int N = fromNodes.size() + 1;

        ArrayList[] g = new ArrayList[N];
        for (int i = 1; i < N; i ++) {
            g[i] = new ArrayList();
        }

        for (int i = 0; i < fromNodes.size(); i ++) {
            g[fromNodes.get(i)].add(new int[]{toNodes.get(i), weights.get(i), i + 1});
            g[toNodes.get(i)].add(new int[]{fromNodes.get(i), weights.get(i), i + 1});

        }

        int shortestPath = dijkstra(N, gNodes, g);
        System.out.println("Shortest Path Length: " + shortestPath);

        boolean[] ans = new boolean[N];

        dfs(ans, 1, gNodes, 0, shortestPath, g, new HashSet<>());

        return ans;
    }

    private int dijkstra(int N, int gNodes, ArrayList[] g) {
        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        boolean[] v = new boolean[N];
        int[] dist = new int[N];
        Arrays.fill(dist, Integer.MAX_VALUE);

        pq.offer(new int[]{1, 0});
        dist[1] = 0;

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            if (cur[0] == gNodes) {
                return cur[1];
            }
            if (v[cur[0]]) continue;
            v[cur[0]] = true;

            for (int i = 0; i < g[cur[0]].size(); i ++) {
                int[] next = (int[])g[cur[0]].get(i);
                int node = next[0];
                int cost = next[1];
                if (dist[cur[0]] + cost < dist[node]) {
                    dist[node] = dist[cur[0]] + cost;
                    pq.offer(new int[]{node, dist[node]});
                }
            }
        }

        return -1;
    }

    private void dfs(boolean[] ans, int cur, int target, int cost, int len, ArrayList[] g, Set<Integer> set) {
        if (cost > len) return;

        if (cost == len && target == cur) {
            for (int road : set) {
                ans[road] = true;
            }
            return;
        }

        for (int i = 0; i < g[cur].size(); i ++) {
            int[] next = (int[]) g[cur].get(i);
            if (set.contains(next[2])) continue;
            set.add(next[2]);
            dfs(ans, next[0], target, cost + next[1], len, g, set);
            set.remove(next[2]);
        }
    }
}
