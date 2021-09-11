package DoorDash;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class EmployeeFreeTime {

    class Interval {
        public int start;
        public int end;
    
        public Interval() {}
    
        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    }
    
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule, int start, int end, int duration) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (List<Interval> intervals : schedule) {
            for (Interval interval : intervals) {
                map.put(interval.start, map.getOrDefault(interval.start, 0) + 1);
                map.put(interval.end, map.getOrDefault(interval.end, 0) - 1);
            }
        }
        
        int cnt = 0;
        int prev = -1;
        
        List<Interval> ans = new ArrayList<>();
        for (int key : map.keySet()) {
            if (prev != -1) {
                // 这里是DoorDash加的限制条件
                int s = Math.max(start, prev);
                int e = Math.min(end, key);
                if (e - s >= duration) {
                    ans.add(new Interval(prev, key));
                }
                prev = -1;
            }
            cnt += map.get(key);
            if (cnt == 0) prev = key;
        }
        return ans;
    }
}
