package RandomQuestions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class MergeAccounts {
    Map<String, Set<String>> map = new HashMap<>();
    Map<String, String> email2name = new HashMap<>();
    
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // build graph
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i ++) {
                String email = account.get(i);
                email2name.put(email, name);
                
                if (i == 1) continue;
                String prev = account.get(i - 1);
                map.computeIfAbsent(prev, key -> new HashSet<>()).add(email);
                map.computeIfAbsent(email, key -> new HashSet<>()).add(prev);
            }
        }    
        
        //merge
        Set<String> v = new HashSet<>();
        List<List<String>> ans = new ArrayList<>();
        for (String email : email2name.keySet()) {
            if (v.contains(email)) continue;
            List<String> tmp = new ArrayList<>();
            dfs(email, tmp, v);
            Collections.sort(tmp);
            tmp.add(0, email2name.get(email));
            ans.add(tmp);
        }
        return ans;
    }
    
    private void dfs(String cur, List<String> ans, Set<String> v) {
        ans.add(cur);
        v.add(cur);
        if (!map.containsKey(cur)) return;
        for (String next : map.get(cur)) {
            if (v.contains(next)) continue;
            dfs(next, ans, v);
        }
    }
}