package com.insight.puzzle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The class responsible for grouping the file content based on State and Job Title, call the Sorter to sort, get the top 10 result.
 * The result is written to hard-coded files
 */
public class H1BAggregator {


    //to be wired
    Sorter sorter = new Sorter();

    static final String STATE_FILE = "output/top_10_states.txt";
    static final String OCCUPATION_FILE = "output/top_10_occupations.txt";


    static final String STATE_HEADER = "TOP_STATES;NUMBER_CERTIFIED_APPLICATIONS;PERCENTAGE";
    static final String OCCUPATION_HEADER = "TOP_OCCUPATIONS;NUMBER_CERTIFIED_APPLICATIONS;PERCENTAGE";

    /**
     * Not sleak - code duolicated.
     * Will attempt to make this generic.
     * @param lcaList
     */
    public void aggregate(List<LCA> lcaList) {


        final int count = lcaList.size();

        //can be generalized with the help of methidhandles
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
//        final MethodHandle methodHandle = lookup.findGetter(LCA.class, groupBy, String.class );


        List<LCA.Category> categoryByState = lcaList.stream()
                .collect(Collectors.groupingBy(LCA::getWorkLocationState))
                .entrySet().stream()
                .map((entry) -> new LCA.Category(entry.getKey(), entry.getValue().size(), count))
                .collect(Collectors.toList());

        List<LCA.Category> categoryByJob = lcaList.stream()
                .collect(Collectors.groupingBy(LCA::getJobTitle))
                .entrySet().stream()
                .map((entry) -> new LCA.Category(entry.getKey(), entry.getValue().size(), count))
                .collect(Collectors.toList());

        List<LCA.Category> top10ByState = sorter.top(categoryByState, 10);
        List<LCA.Category> top10ByJob = sorter.top(categoryByJob, 10);

        writeToFile(top10ByJob, OCCUPATION_FILE, OCCUPATION_HEADER);
        writeToFile(top10ByState, STATE_FILE, STATE_HEADER);


    }

    private void writeToFile(List<LCA.Category> items, String fileName, String fileHeader) {


        File f = new File(fileName);
        f.delete();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            writer.write(fileHeader);
            writer.newLine();
            items.forEach((entry) ->
            {
                try {
                    writer.write(entry.toString());
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}