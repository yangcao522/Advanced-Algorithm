package RandomQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeetingScheduler {
    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        Arrays.sort(slots1, (a, b) -> (a[1] - b[1]));
        Arrays.sort(slots2, (a, b) -> (a[1] - b[1]));
        
        int M = slots1.length, N = slots2.length;
        int i = 0, j = 0;
        
        while (i < M && j < N) {
            int[] a = slots1[i];
            int[] b = slots2[j];
            
            int start = Math.max(a[0], b[0]);
            int end = Math.min(a[1], b[1]);
            if (end - start >= duration) {
                return Arrays.asList(start, start + duration);
            }
            
            if (a[1] == b[1]) {
                i ++;
                j ++;
            } else if (a[1] < b[1]) {
                i ++;
            } else {
                j ++;
            }
        }
        
        return new ArrayList<>();
    }
}
