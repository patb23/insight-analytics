package com.insight.puzzle;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



public class SorterTest {

    List<LCA.Category> categoryList = new ArrayList<>();
    Sorter sorter = new Sorter();
    @Before
    public void setUp() throws Exception {

        categoryList.add(new LCA.Category("Job1", 20, 100 ));
        categoryList.add(new LCA.Category("Job2", 60, 100 ));
        categoryList.add(new LCA.Category("Job3", 10, 100 ));
        categoryList.add(new LCA.Category("Job4", 1, 100 ));
        categoryList.add(new LCA.Category("Job5", 1, 100 ));
        categoryList.add(new LCA.Category("Job6", 3, 100 ));
        categoryList.add(new LCA.Category("Job7", 5, 100 ));

    }

    @Test
    public void whenListIsSmall(){

        List<LCA.Category> newList = new ArrayList<>();
        newList.addAll(categoryList);


        List<LCA.Category> sortedList = sorter.top(newList,10);
        assertThat(sortedList).hasSize(7);
        assertThat(sortedList.get(0).value).isEqualTo(60);

        newList.addAll(categoryList);
        newList.addAll(categoryList);
        List<LCA.Category> srtedList = sorter.top(newList,10);
        assertThat(srtedList).hasSize(10);


    }


}