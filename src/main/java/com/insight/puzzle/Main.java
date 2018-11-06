package com.insight.puzzle;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Main executable class that is responsible for loading the files.
 * The field names that are required for this puzzle are only used and read from the file. The field names are matched - no fancy stuff
 * Could have been cleaner if apache CSVParser is used.
 */
public class Main {


    static final String SEMICOLON = "(\"[A-Za-z0-9.,'\"-/()#\\s]*)([;]*)([A-Za-z0-9.,-/'\"()#\\s]*\")";
    static final String JOB_FIELD = "LCA_CASE_JOB_TITLE|JOB_TITLE";
    static final String STATE_FIELD = "LCA_CASE_WORKLOC1_STATE|WORKSITE_STATE";
    static final String STATUS_FIELD = "STATUS|CASE_STATUS";

    static final String STATE ="STATE";
    static final String STATUS="STATUS";
    static final String JOB ="JOB";


    H1BAggregator aggregator;

    /**
     * This method returns the indexes for the fields of interest in the passed file
     * @param line
     * @return
     */
    public Map<String, Integer> getFieldPositions(String line){

        String[] fields = line.split(";");


        Map<String, Integer> fieldMap = new HashMap<>();
        for(int i=0; i< fields.length; i++){

            if(fields[i].matches(JOB_FIELD)){
                fieldMap.put(JOB, i);
            }
            if(fields[i].matches(STATE_FIELD)){
                fieldMap.put(STATE, i);
            }

            if(fields[i].matches(STATUS_FIELD)){
                fieldMap.put(STATUS,i);
            }

        }
        return fieldMap;
    }




    public void loadFile(String filePath){


        Path file = Paths.get(filePath);

        List<LCA> lcaList = new ArrayList<>();
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            String titleRow = reader.readLine();
            Map<String, Integer> fieldMap = getFieldPositions(titleRow);

            int statusIndex = fieldMap.get(STATUS);
            int titleIndex = fieldMap.get(JOB);
            int stateIndex = fieldMap.get(STATE);

            int minLength = Math.max(stateIndex, Math.max(statusIndex, titleIndex));
            while ((line = reader.readLine()) != null) {

                line = StringUtils.replace(line, "&", "amp");
                line = StringUtils.remove(line, "\\");
                line = RegExUtils.replaceAll(line, Pattern.compile(SEMICOLON), "$1$3");
//                System.out.println(line);
                String[] fields = line.split(";");

                if(fields.length<minLength) continue;

                String status = fields[statusIndex];
                String state = fields[stateIndex];
                if(state.length()>2){

                    //Hack - not able to fix the special characters appearing in between quoated fields
                    continue;
                }
                if(!status.equalsIgnoreCase("CERTIFIED")) continue;
                LCA lca = new LCA(status, fields[titleIndex], state);
                lcaList.add(lca);


            }
        } catch (IOException x) {
            System.err.println(x);
        }

        aggregator = new H1BAggregator();
        aggregator.aggregate(lcaList);

       // return lcaList;


    }

    public static void main(String[] args) {
	// write your code here

        String filename = args[0];
//        String fieldNames = args[1];

        Main main = new Main();

        main.loadFile(filename);




    }
}
