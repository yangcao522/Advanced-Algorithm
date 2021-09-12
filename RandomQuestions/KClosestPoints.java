package RandomQuestions;

import java.util.PriorityQueue;
import java.util.Queue;

public class KClosestPoints {
    //1) priorityqueue solution
    public int[][] kClosest1(int[][] points, int k) {
        Queue<Integer> q = new PriorityQueue<>((a, b) -> (
            points[b][0] * points[b][0] + points[b][1] * points[b][1] -
            points[a][0] * points[a][0] - points[a][1] * points[a][1]
        ));
        
        int N = points.length;
        for (int i = 0; i < N; i ++) {
            q.offer(i);
            if (q.size() > k) q.poll();
        }
        
        int[][] ans = new int[q.size()][2];
        int index = 0;
        while (!q.isEmpty()) {
            int cur = q.poll();
            ans[index ++] = points[cur];
        }
        return ans;
    }
    
    //2) quick select solution
    public int[][] kClosest2(int[][] points, int k) {
        int N = points.length;
        int[][] nums = new int[N][2];
        for (int i = 0; i < N; i ++) {
            nums[i][0] = getDist(points[i]);
            nums[i][1] = i;
        }
        
        int l = 0, r = N - 1;
        getK(nums, k, l, r);
        int[][] ans = new int[k][2];
        for (int i = 0; i < k; i ++) {
            ans[i] = points[nums[i][1]];
        }
        return ans;
    }

    private void getK(int[][] nums, int k, int left, int right) {
        int p = nums[right][0];
        int l = left, r = right - 1;
        
        while (l <= r) {
            while (l <= r && nums[l][0] <= p) {
                l ++;
            }
            
            while (l <= r && nums[r][0] >= p) {
                r --;
            }
            
            if (l <= r) {
                int[] tmp = nums[l];
                nums[l] = nums[r];
                nums[r] = tmp;
            }
        }
        
        int[] tmp = nums[right];
        nums[right] = nums[l];
        nums[l] = tmp;
        
        if (k == l + 1) {
            return;
        } else if (k < l + 1) {
            getK(nums, k, left, l - 1);
        } else {
            getK(nums, k, l + 1, right);
        }
    }

    private int getDist(int[] p) {
        return p[0] * p[0] + p[1] * p[1];
    }
}
