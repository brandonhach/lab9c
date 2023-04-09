/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algo.benchmark;

import Algo.search.LinearSearch;
import Algo.search.BinarySearch;
import Algo.search.MyHashMap;
import Algo.benchmark.util.CollectionGenerator;
import Algo.benchmark.util.Common;
import viz.BridgesLineChart;
import Apps.Shakespeare_Words;
import Apps.WordFrequency;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ITSC 2214 and ITCS 6114 at cs.cci.uncc.edu
 */
public class BenchmarkSearch {
    public final static int REPEATS = 10;
    public final static int DEFAULT_MIN_LIST_SIZE = 100;
    public final static int DEFAULT_MAX_LIST_SIZE = 500;
    public final static int DEFAULT_LIST_SIZE_UPDATE = 50;

    public final static int NUMBER_OF_SEARCHES = 3;
    public final static int LINEAR_SEARCH = 1;
    public final static int BINARY_SEARCH = 2;
    public final static int HASH_MAP_SEARCH = 3;
    public final static String[] Y_legends = { "Linear search", "Binary search", "HashMap search" };

    public final static int BEST_CASE = 1;
    public final static int WORST_CASE = 2;
    public final static int AVERAGE_CASE = 3;
    public final static int INPUT_INSTANCE_SIZE = 0;

    public static <E extends WordFrequency> long[] benchmark(List<E> list,
            Comparator<E> comparator, List<E> sortedList, MyHashMap<E> hashmap,
            E target, boolean isPrint) {
        long startTime;
        E ptarget = target;

        int size = list.size();
        long duration[] = new long[NUMBER_OF_SEARCHES + 1];
        duration[INPUT_INSTANCE_SIZE] = size;

        if (target == null)
            target = list.get(0);
        startTime = System.nanoTime();
        LinearSearch.search(list, comparator, target);
        duration[LINEAR_SEARCH] = System.nanoTime() - startTime + 1;
        if (isPrint)
            System.out.print(list.size() + "\t" + duration[LINEAR_SEARCH] + "\t");

        if (ptarget == null)
            target = sortedList.get((sortedList.size() - 1) / 2);
        startTime = System.nanoTime();
        BinarySearch.search(sortedList, comparator, target);
        duration[BINARY_SEARCH] = System.nanoTime() - startTime + 1;
        if (isPrint)
            System.out.println(duration[BINARY_SEARCH]);

        startTime = System.nanoTime();
        hashmap.search(target);
        duration[HASH_MAP_SEARCH] = System.nanoTime() - startTime + 1;
        if (isPrint)
            System.out.println(duration[HASH_MAP_SEARCH]);

        return duration;
    }

    public static long[][] testShakespeane(List<WordFrequency> wordList,
            int size_start, int size_end, int size_updates,
            String title, int caseid,
            int Bridges_task_id, String Bridges_user_id, String Bridges_user_api_key) {
        int times = (size_end - size_start + 1) % size_updates == 0
                ? (size_end - size_start + 1) / size_updates
                : (size_end - size_start + 1) / size_updates + 1;
        int count = 0;
        long duration[][] = new long[times][NUMBER_OF_SEARCHES + 1];
        long tmp_duration[];
        long total_duration[] = new long[NUMBER_OF_SEARCHES + 1];

        List<WordFrequency> list = null, sortedList = null;
        MyHashMap<WordFrequency> hashmap = null;

        double[] X = new double[times];
        ArrayList<ArrayList<Double>> Ys = new ArrayList<>(times);
        for (int exp_idx = 0; exp_idx < times; exp_idx++)
            Ys.add(new ArrayList<Double>(NUMBER_OF_SEARCHES));

        for (int i = size_start; i <= size_end; i += size_updates) {
            X[count] = i;
            for (int k = 0; k < NUMBER_OF_SEARCHES + 1; k++)
                total_duration[k] = 0;

            for (int j = 0; j < REPEATS; j++) {
                WordFrequency searchkey = null;
                list = CollectionGenerator.generateRandomDataList(wordList, i, 0);

                sortedList = new LinkedList<WordFrequency>();
                for (int index = 0; index < list.size(); index++)
                    sortedList.add(list.get(index));

                Collections.sort(sortedList);
                hashmap = new MyHashMap<>(list);

                switch (caseid) {
                    case BEST_CASE:
                        searchkey = null;
                        break;
                    case WORST_CASE:
                        searchkey = new WordFrequency(
                                sortedList.get(sortedList.size() - 1).getWord() + " UNKNOWN", new Integer(1));
                        break;
                    case AVERAGE_CASE:
                        searchkey = list.get(Common.randomIndex(0, list.size() - 1));
                }

                // tmp_duration = BenchmarkSearch.testframe(array, sortedarray, searchkey,
                // false);
                tmp_duration = BenchmarkSearch.benchmark(list, WordFrequency.Comparators.word_comparison,
                        sortedList, hashmap, searchkey, false);

                for (int k = 0; k < NUMBER_OF_SEARCHES + 1; k++)
                    total_duration[k] += tmp_duration[k];
            }

            for (int k = 0; k < NUMBER_OF_SEARCHES + 1; k++) {
                duration[count][k] = total_duration[k] / REPEATS;
                if (k > 0)
                    Ys.get(count).add(new Double(duration[count][k]));
            }

            count++;
        }

        for (int i = 0; i < count; i++)
            System.out.println(duration[i][INPUT_INSTANCE_SIZE]
                    + "\t" + duration[i][LINEAR_SEARCH]
                    + "\t" + duration[i][BINARY_SEARCH]
                    + "\t" + duration[i][HASH_MAP_SEARCH]);

        BridgesLineChart.linechart(X, Ys, Y_legends,
                Bridges_task_id, Bridges_user_id,
                Bridges_user_api_key, title);
        return duration;
    }

    public static void main(String[] argv) {
        int Bridges_task_id = 26;
        String Bridges_user_id = "brandonhach"; // TODO Please change it to be your own user ID on Bridges
        String Bridges_user_api_key = "581218346755";// TODO Please change it to be your own API Key on Bridges
        List<WordFrequency> wordList = Shakespeare_Words.retrieveShakespeareWords(
                Bridges_task_id, Bridges_user_id, Bridges_user_api_key);
        int start = DEFAULT_MIN_LIST_SIZE;
        int end = (wordList.size() > DEFAULT_MAX_LIST_SIZE ? DEFAULT_MAX_LIST_SIZE : wordList.size());
        int update = DEFAULT_LIST_SIZE_UPDATE;

        System.out.println("================Comparison (Best Case)================");
        System.out.println("Input instance size\tLinear Search\tBinary Search\tHashMap Search");
        testShakespeane(wordList, start, end, update, "Comparison (Best Case)", BEST_CASE,
                Bridges_task_id + 1, Bridges_user_id, Bridges_user_api_key);

        System.out.println("================Comparison (Worst Case)================");
        System.out.println("Input instance size\tLinear Search\tBinary Search\tHashMap Search");
        testShakespeane(wordList, start, end, update, "Comparison (Worst Case)", WORST_CASE,
                Bridges_task_id + 2, Bridges_user_id, Bridges_user_api_key);

        System.out.println("================Comparison (Average Case)================");
        System.out.println("Input instance size\tLinear Search\tBinary Search\tHashMap Search");
        testShakespeane(wordList, start, end, update, "Comparison (Average Case)", AVERAGE_CASE,
                Bridges_task_id + 3, Bridges_user_id, Bridges_user_api_key);
    }
}
