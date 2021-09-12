package RandomQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class MergeIntervals {
    public int[][] merge1(int[][] intervals) {
        if (intervals.length == 0) return new int[0][0];
        Arrays.sort(intervals, (a, b) -> (a[0] - b[0]));
        List<int[]> ans = new ArrayList<>();
        
        int start = intervals[0][0], end = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i ++) {
            int curStart = intervals[i][0];
            int curEnd = intervals[i][1];
            if (end < curStart) {
                ans.add(new int[]{start, end});
                start = curStart;
            } 
            end = Math.max(end, curEnd);
        }
        
        ans.add(new int[]{start, end});
        
        int[][] res = new int[ans.size()][2];
        for (int i = 0; i < ans.size(); i ++) {
            res[i] = ans.get(i);
        }
        return res;
    }
    
    public int[][] merge2(int[][] intervals) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int[] interval : intervals) {
            map.put(interval[0], map.getOrDefault(interval[0], 0) + 1);
            map.put(interval[1], map.getOrDefault(interval[1], 0) - 1);
        }
        
        Integer pre = 0, count = 0;
        List<int[]> ans = new ArrayList<>();
        for (int key : map.keySet()) {            
            if (count == 0) pre = key;
            count += map.get(key);
            if (count == 0) {
                ans.add(new int[]{pre, key});
            }
        }
        
        int[][] res = new int[ans.size()][2];
        for (int i = 0; i < ans.size(); i ++) {
            res[i] = ans.get(i);
        }
        return res;
    }
}
