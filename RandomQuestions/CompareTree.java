package RandomQuestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class CompareTree {
    static class TreeNode {
        String key;
        int value;
        List<TreeNode> children;

        public TreeNode(String key, int value) {
            this.key = key;
            this.value = value;
            this.children = new LinkedList<>();
        }
    }

    private void print(List<TreeNode> ans) {
        for (TreeNode node : ans) {
            System.out.print("(" + node.key + "," + node.value + "), ");
        }
        System.out.println();
    }

    //1) 数个数
    public int findDiffNodes(TreeNode root1, TreeNode root2) {
        // Both trees are empty, they are the same tree
        if (root1 == null && root2 == null) {
            return 0;
        }
        // Only one of the trees is not null
        // Or if the key of both trees are different, then return
        // the node count of both trees (which could be zero or one of them)
        if ((root1 == null || root2 == null) || (!root1.key.equals(root2.key))) {
            return countNodes(root1) + countNodes(root2);
        }

        // If their values are different
        // Then we include the current nodes of the two trees as the only diff and then
        // compute the diff of the children
        int diffCount = 0;
        if (root1.value != root2.value) {
            diffCount = 2;
        }

        List<TreeNode> children1 = root1.children;
        List<TreeNode> children2 = root2.children;

        // Compare both children by their keys, to see if they have any intersections
        Map<String, TreeNode> childrenKeyMap1 = new HashMap<>();
        for (TreeNode child1 : children1) {
            childrenKeyMap1.put(child1.key, child1);
        }
        Map<String, TreeNode> childrenKeyMap2 = new HashMap<>();
        for (TreeNode child2 : children2) {
            childrenKeyMap2.put(child2.key, child2);
        }

        for (String key : childrenKeyMap1.keySet()) {
            TreeNode child1 = childrenKeyMap1.get(key);
            if (!childrenKeyMap2.containsKey(key)) {
                diffCount += countNodes(child1);
            } else {
                TreeNode child2 = childrenKeyMap2.get(key);
                diffCount += findDiffNodes(child1, child2);
                childrenKeyMap2.remove(key);
            }
        }

        // If the second map still has keys remaining, that means the are extra nodes
        // that are not found in the first subtree, so should add their node count to the result
        for (TreeNode child2 : childrenKeyMap2.values()) {
            diffCount += countNodes(child2);
        }

        return diffCount;
    }

    private int countNodes(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int count = 1;
        for (TreeNode child : node.children) {
            count += countNodes(child);
        }
        return count;
    }


    //2) 记录节点
    static List<TreeNode> inserted = new ArrayList<>();
    static List<TreeNode> deleted = new ArrayList<>();
    static List<TreeNode> modified = new ArrayList<>();
    public void recordDiffNodes(TreeNode root1, TreeNode root2) {
        dfs(root1, root2);
    }

    public void dfs(TreeNode root1, TreeNode root2) {
        // Both trees are empty, they are the same tree
        if (root1 == null && root2 == null) {
            return;
        }
        // Only one of the trees is not null
        // Or if the key of both trees are different, then return
        // the node count of both trees (which could be zero or one of them)
        if ((root1 == null || root2 == null) || (!root1.key.equals(root2.key))) {
            addInsertedNodes(root1, inserted);
            addDeletedNodes(root2, deleted);
        }

        // If their values are different
        // Then we include the current nodes of the two trees as the only diff and then
        // compute the diff of the children
        if (root1.value != root2.value) {
            modified.add(root1);
            modified.add(root2);
        }

        List<TreeNode> children1 = root1.children;
        List<TreeNode> children2 = root2.children;

        // Compare both children by their keys, to see if they have any intersections
        Map<String, TreeNode> childrenKeyMap1 = new HashMap<>();
        for (TreeNode child1 : children1) {
            System.out.println(child1.key);
            childrenKeyMap1.put(child1.key, child1);
        }
        Map<String, TreeNode> childrenKeyMap2 = new HashMap<>();
        for (TreeNode child2 : children2) {
            childrenKeyMap2.put(child2.key, child2);
        }

        for (String key : childrenKeyMap1.keySet()) {
            TreeNode child1 = childrenKeyMap1.get(key);
            if (!childrenKeyMap2.containsKey(key)) {
                addInsertedNodes(child1, inserted);
            } else {
                TreeNode child2 = childrenKeyMap2.get(key);
                dfs(child1, child2);
                childrenKeyMap2.remove(key);
            }
        }

        // If the second map still has keys remaining, that means the are extra nodes
        // that are not found in the first subtree, so should add their node count to the result
        for (TreeNode child2 : childrenKeyMap2.values()) {
            addDeletedNodes(child2, deleted);
        }
    }

    private void addInsertedNodes(TreeNode node, List<TreeNode> nodes) {
        if (node == null) {
            return;
        }

        nodes.add(node);
        for (TreeNode child : node.children) {
            addInsertedNodes(child, nodes);
        }
    }

    private void addDeletedNodes(TreeNode node, List<TreeNode> nodes) {
        if (node == null) {
            return;
        }

        nodes.add(node);
        for (TreeNode child : node.children) {
            addDeletedNodes(child, nodes);
        }
    }

    public static void main(String[] args) {
        CompareTree sln = new CompareTree();

        TreeNode root1 = new TreeNode("a", 4);
        root1.children.add(new TreeNode("b", 1));
        root1.children.add(new TreeNode("c", 5));
        root1.children.get(0).children.add(new TreeNode("d", 3));
        root1.children.get(0).children.add(new TreeNode("e", 5));
        root1.children.get(0).children.add(new TreeNode("f", 8));
        root1.children.get(1).children.add(new TreeNode("g", 12));

        TreeNode root2 = new TreeNode("a", 4);
        root2.children.add(new TreeNode("b", 1));
        root2.children.add(new TreeNode("m", 5));
        root2.children.get(0).children.add(new TreeNode("e", 5));
        root2.children.get(0).children.add(new TreeNode("d", 3));
        root2.children.get(0).children.add(new TreeNode("w", 8));
        root2.children.get(1).children.add(new TreeNode("g", 12));

        // int diffCount = sln.findDiffNodes(root1, root2);
        // System.out.println(diffCount);
        sln.recordDiffNodes(root1, root2);
        sln.print(inserted);
        sln.print(deleted);
        sln.print(modified);
    }
}


