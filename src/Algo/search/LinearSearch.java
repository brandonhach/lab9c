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
 *
 * @author ITSC 2214 and ITCS 6114 at cs.cci.uncc.edu
 */
public class LinearSearch {

    /**
     *
     * @param list
     * @param comparator
     * @param targetValue
     * @return
     *         Hints: The search() function is a sequential search, finding a target
     *         value within a given list by using the given comparison method. The
     *         algorithm works by iterating through each element in the list and
     *         comparing it with the target value. If a match is found, the
     *         algorithm returns the index of the matching element. If the end of
     *         the list is reached without finding a match, the algorithm returns -1
     *         to indicate that the target value was not found.
     */
    public static <E> int search(List<E> list, Comparator<E> comparator, E targetValue) {
        // TODO implement the linear search algorithm
        if (list == null || targetValue == null)
            return -1;

        for (int i = 0; i < list.size(); i++) {
            if (comparator.compare(list.get(i), targetValue) == 0)
                return i;
        }
        return -1;
    }
}
