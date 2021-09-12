package RandomQuestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Compare two trees.
A tree can have multiple childs and a several attributes.

Return the key of not completely equal nodes.

Example,

Tree 1
         A  [ true, 1 ]
    B          C            D           E
[ true, 2]  [false, 3] [true, 4] [false, 5]
                                         \
                                     F [ true, 6 ]


Tree 2
         A  [ true, 1 ]
    B         C            D           E
[ true, 2]  [false, 6] [false, 4] [false, 5]

Return:
C D F

Explain:
C ( 3 != 6‍‌‌‌‌‍‍‍‌‍‌‌‍‌‌‌‍‌‌ ), D ( true != false ), F ( F is missing in Tree 2 )
 */
public class CompareTwoTrees {
    static class TreeNode {
        List<TreeNode> children;
        char id;
        boolean flag;
        int val;
        public TreeNode(char id, boolean flag, int val) {
            this.id = id;
            this.flag = flag;
            this.val = val;
            this.children = new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        TreeNode root1 = new TreeNode('A', true, 1);
        root1.children.add(new TreeNode('B', true, 2));
        root1.children.add(new TreeNode('C', false, 3));
        root1.children.add(new TreeNode('D', true, 4));
        root1.children.add(new TreeNode('E', false, 5));
        root1.children.get(3).children.add(new TreeNode('F', true, 6));

        TreeNode root2 = new TreeNode('A', true, 1);
        root2.children.add(new TreeNode('B', true, 2));
        root2.children.add(new TreeNode('C', false, 6));
        root2.children.add(new TreeNode('D', false, 4));
        root2.children.add(new TreeNode('E', false, 5));

        dfs(root1, root2);
        for (char c : diffNodes) {
            System.out.print(c + ",");
        }
        System.out.println();
    }

    static List<Character> diffNodes = new ArrayList<>();
    public static List<Character> getDiffNodes(TreeNode root1, TreeNode root2) {
        dfs(root1, root2);
        return diffNodes;
    }

    private static void dfs(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return;
        if (root1 == null && root2 != null) {
            diffNodes.add(root2.id);
            return;
        }

        if (root1 != null && root2 == null) {
            diffNodes.add(root1.id);
            return;
        }

        if (root1.flag != root2.flag || root1.val != root2.val) {
            diffNodes.add(root1.id);
        }

        Map<Character, TreeNode> map1 = new HashMap<>();
        Map<Character, TreeNode> map2 = new HashMap<>();
        List<TreeNode> children1 = root1.children;
        List<TreeNode> children2 = root2.children;
        for (TreeNode child1 : children1) {
            map1.put(child1.id, child1);
        }

        for (TreeNode child2 : children2) {
            map2.put(child2.id, child2);
        }

        for (char key : map1.keySet()) {
            if (!map2.containsKey(key)) {
                addNodes(map1.get(key));
            } else {
                dfs(map1.get(key), map2.get(key));
                map2.remove(key);
            }
        }

        for (char key : map2.keySet()) {
            addNodes(map2.get(key));
        }
    }

    private static void addNodes(TreeNode node) {
        if (node == null) return;
        diffNodes.add(node.id);
        List<TreeNode> children = node.children;
        for (TreeNode child : children) {
            addNodes(child);
        }
    }
}

/**
 * TO-DO:
 * 1. 721 https://www.1point3acres.com/bbs/thread-769647-1-1.html
 * 2. 759 
 * 3. 1359 print all valid
 * 4. Compare Tree -> put diff nodes into 3 different categories: https://www.1point3acres.com/bbs/thread-775737-1-1.html
 * 5. https://www.1point3acres.com/bbs/thread-787110-1-1.html
 * 6. Copy to notebook
 */
