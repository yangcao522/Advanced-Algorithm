package RandomQuestions;

import java.util.Stack;

public class PolishNotation {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            try {
                int x = Integer.parseInt(token);
                stack.push(x);
            } catch (Exception e){
                int a = stack.pop();
                int b = stack.pop();
                if (token.equals("+")) stack.push(a + b);
                if (token.equals("-")) stack.push(b - a);
                if (token.equals("*")) stack.push(a * b);
                if (token.equals("/")) stack.push(b / a);    
            }
        }
        return stack.pop();
    }
}
