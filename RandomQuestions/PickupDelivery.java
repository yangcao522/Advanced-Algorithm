package DoorDash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PickupDelivery {

    // 先看是不是合格的
    public boolean isValid(String[] strs) {
        int N = strs.length;
        if (N == 0) return true;
        if (N % 2 != 0) return false;
        Set<Integer> set = new HashSet<>();
        for (String str : strs) {
            if (str.charAt(0) == 'D' && !set.contains(str.charAt(1) - '0')) return false;
            if (str.charAt(0) == 'P') {
                set.add(str.charAt(1) - '0');
            }
        }
        return true;
    }

    // 然后给n生产所有合格的
    public List<String> printAll(int n) {
        List<String> ans = new ArrayList<>();
        if (n == 0) return ans;
        if (n == 1) {
            ans.add("P1,D1");
            return ans;
        }

        List<String> prev = printAll(n - 1);
        for (String str : prev) {
            List<String> ss = Arrays.asList(str.split(","));
            String a = "P" + n, b = "D" + n;
            //option 1
            for (int i = 0; i <= ss.size(); i ++) {
                List<String> tmp = new ArrayList<>();
                tmp.addAll(ss);
                tmp.add(i, a);
                tmp.add(i + 1, b);
                ans.add(String.join(",", tmp));
            }

            //option 2
            
            for (int i = 0; i < ss.size(); i ++) {
                List<String> tmp = new ArrayList<>();
                tmp.addAll(ss);
                tmp.add(i, a);
                for (int j = i + 2; j <= tmp.size(); j ++) {
                    List<String> tmp2 = new ArrayList<>();
                    tmp2.addAll(tmp);
                    tmp2.add(j, b);
                    ans.add(String.join(",", tmp2));
                }
            }
        }
        return ans;
    }

    // 最后是推导通项公式
    //1) Pi and Di are consecutive: 2 * (i - 1) + 1
    //2) Pi and Di are not consecutive: C(2 * (i - 1) + 1, 2) = (2 * (i - 1) + 1) * (i - 1)
    //=> (2 * i - 1) * i
    //f(i) = (2 * i - 1) * i * f(i - 1)
    private static long mod = (long)1e9 + 7;
    
    public int countOrders(int n) {
        if (n == 1) return 1;
        long ans = 1;
        for (int i = 2; i <= n; i ++) {
            ans = ans * (2 * i - 1) * i % mod;
        }
        return (int) ans;
    }

    public static void main(String[] args) {
        PickupDelivery pd = new PickupDelivery();
        List<String> ans = pd.printAll(2);
        for (String str : ans) System.out.println(str);
    }
}