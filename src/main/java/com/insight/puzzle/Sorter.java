package com.insight.puzzle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.swap;

/**
 * referred "Data Structures and Algorithms Using Java" and chose Sorter based on that.
 * Stopped with left-balanced tree implementation and popping the root node into the result.
 */
public class Sorter {


    private void heap(List<LCA.Category> input, int index) {


        int heapSize = input.size();
        int largest = index;
        int left = largest * 2 + 1;
        int right = largest * 2 + 2;


        Comparator<LCA.Category> byValue = Comparator.comparingInt(LCA.Category::getValue);
        if (left < heapSize && input.get(left).compareTo(input.get(largest)) > 0)
            largest = left;

        if(right < heapSize && input.get(right).compareTo(input.get(largest))>0)
            largest = right;

        if(largest!=index){
            swap(input, index, largest);
        }

    }


    private List<LCA.Category> sort(List<LCA.Category> categories) {
        for (int i = categories.size() / 2 - 1; i >= 0; i--) {
            heap(categories, i);
        }
        return categories;
    }


    /**
     * Builds left-balanced hash, pops the root and then calls the hash method till Top 10 are obtained.
     * @param categories
     * @param n
     * @return
     */
    public List<LCA.Category> top(List<LCA.Category> categories, int n){

        List<LCA.Category> topN = new ArrayList<>();
        int size = categories.size();
        int max = size<n?size:n;
        List<LCA.Category> input = new ArrayList<>(categories);
        List<LCA.Category> heaped = sort(input);
        for(int i=0; i<max; i++){
            topN.add(heaped.get(0));
            heaped.remove(0);
            sort(heaped);
        }
        return topN;
    }


}