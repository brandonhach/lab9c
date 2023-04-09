/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algo.search;

import Apps.WordFrequency;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author ITSC 2214 and ITCS 6114 at cs.cci.uncc.edu
 */
public class MyHashMap<E extends WordFrequency> {
    TreeMap<String, Integer> sortedMap;

    public MyHashMap(List<E> list) {
        TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();
        for (E wfreq : list)
            treeMap.put(wfreq.getWord(), new Integer(wfreq.getFrequency()));

        // Using a custom comparator to sort the TreeMap by value
        TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(new ValueComparator(treeMap));
        sortedMap.putAll(treeMap);
    }

    /**
     * Hints: The search() function aims at examining whether a target object key
     * exists in the HashMap or not. Please directly use the methods from the Java
     * Built-in HashMap class. The given parameter argument would be a WordFrequency
     * object which is a pair of a unique word and its frequency.
     **/
    public boolean search(E target) {
        if (sortedMap == null || target == null)
            return false;

        // TODO impletement the search method by using Java Built-in HashMap method,
        // get()
        if (sortedMap.get(target.getWord()) == null)
            return false;

        else
            return true;

    }

    // Custom comparator to sort a TreeMap by value
    static class ValueComparator implements Comparator<String> {
        Map<String, Integer> map;

        public ValueComparator(Map<String, Integer> map) {
            this.map = map;
        }

        public int compare(String a, String b) {
            if (map.get(a) >= map.get(b)) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
