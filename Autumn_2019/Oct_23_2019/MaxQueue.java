package Autumn_2019.Oct_23_2019;

import java.util.ArrayDeque;
import java.util.Deque;

/*
 * A queue supporting an additional constant time operation: getMax.
 * getMax returns the largest element in the queue.
 * Copied from https://stackoverflow.com/questions/4802038
 */
public class MaxQueue {
    Deque<Pair> pushStack = new ArrayDeque<>();
    Deque<Pair> popStack = new ArrayDeque<>();

    /*
     * Add an element into the queue
     */
    void push(long val) {
        long max = val;
        if (!pushStack.isEmpty()) {
            max = Math.max(max, pushStack.peekFirst().max);
        }
        pushStack.addFirst(new Pair(val, max));
    }

    /*
     * Remove an element from the queue
     */
    long pop() {
        if (popStack.isEmpty()) {
            if (pushStack.isEmpty()) {
                throw new RuntimeException("Pop from empty queue");
            } else {
                // move pushStack to popStack, updating the max values
                // reset the max for each element.
                // For the first element, the max will be the value.
                long val = pushStack.removeFirst().val;
                popStack.addFirst(new Pair(val, val));
                // For the rest, the max is the max of (max so far, new value)
                while (!pushStack.isEmpty()) {
                    val = pushStack.removeFirst().val;
                    long max = Math.max(popStack.peekFirst().max, val);
                    Pair pushV = new Pair(val, max);
                    popStack.addFirst(pushV);
                }
            }
        }
        return popStack.removeFirst().val;
    }

    /*
     * return maxValue, or null if none exist
     */
    Long getMax() {
        if (pushStack.isEmpty()) {
            if (popStack.isEmpty()) {
                return null;
            } else {
                return popStack.peekFirst().max;
            }
        } else if (popStack.isEmpty()) {
            return pushStack.peekFirst().max;
        } else {
            return Math.max(pushStack.peekFirst().max, popStack.peekFirst().max);
        }
    }

    static class Pair {
        long val;
        long max;

        Pair(long val, long max) {
            this.val = val;
            this.max = max;
        }
    }
}
