/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algo.search;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Hints: The search() function aims at finding a target value within a sorted
 * list. The algorithm works by repeatedly dividing the search interval in half,
 * comparing the middle element of the interval with the target value, and
 * discarding one half of the interval based on the comparison. If the middle
 * element matches the target value, the algorithm terminates and returns the
 * index of the middle element. If the target value is less than the middle
 * element, the search is performed in the lower half of the interval.
 * Otherwise, the search is performed in the upper half of the interval. The
 * process is repeated until the target value is found or the search interval is
 * empty. The algorithm returns -1 to indicate that the target value was not
 * found.
 * 
 * @author ITSC 2214 and ITCS 6114 at cs.cci.uncc.edu
 */
public class BinarySearch {

    public static <E> int search(List<E> list, Comparator<E> comparator, E targetValue) {
        // TODO implement the binary search algorithm
        int low = 0;
        int high = list.size() - 1;
        int guess;

        // ....
        while (low <= high) {

            guess = (low + high) / 2;

            if (comparator.compare(targetValue, targetValue) == 0)
                return guess;
            else if (comparator.compare(targetValue, list.get(guess)) < 0)
                high = guess - 1;
            else
                low = guess + 1;
        }

        return -1;
    }
}