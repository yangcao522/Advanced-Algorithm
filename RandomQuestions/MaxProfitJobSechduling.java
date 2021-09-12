package RandomQuestions;

import java.util.Arrays;
import java.util.TreeMap;

public class MaxProfitJobSechduling {
    // 1) 原题，注意要提前过滤掉不符合条件的区间
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int N = profit.length;
        int[][] arr = new int[N][3];
        
        for (int i = 0; i < N; i ++) {
            arr[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        
        Arrays.sort(arr, (a, b) -> (a[1] - b[1]));
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(0, 0);
        for (int i = 0; i < N; i ++) {
            int prev_profit = map.floorEntry(arr[i][0]).getValue();
            int cur_profit = map.lastEntry().getValue();
            if (prev_profit + arr[i][2] > cur_profit) {
                map.put(arr[i][1], prev_profit + arr[i][2]);
            }
        }
        
        return map.lastEntry().getValue();
    }

    // 2) follow up, 最多可以有K个job并行执行
    // 第一轮：DP算出最大profit，remove掉合法组合
    // 第二轮：在剩下的job中DP算出最大profit，remove掉这一轮的合法组合
    // 第三轮：重复...
}
