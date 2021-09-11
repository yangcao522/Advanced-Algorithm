package DoorDash;

import java.util.PriorityQueue;
import java.util.Queue;

public class MedianFromDataStream {
    Queue<Integer> max = new PriorityQueue<>((a, b) -> (b - a));
    Queue<Integer> min = new PriorityQueue<>((a, b) -> (a - b));
    /** initialize your data structure here. */
    public MedianFromDataStream() {
        
    }
    
    public void addNum(int num) {
        if (!min.isEmpty() && min.peek() > num) {
            max.offer(num);
        } else {
            min.offer(num);
        }
        while (Math.abs(max.size() - min.size()) > 1) {
            if (max.size() > min.size()) {
                min.offer(max.poll());
            } else {
                max.offer(min.poll());
            }
        }
    }
    
    public double findMedian() {
        if (max.size() == min.size()) {
            return (max.peek() + min.peek()) / 2.0;
        } else if (max.size() > min.size()) {
            return max.peek();
        } else return min.peek();
    }
}
