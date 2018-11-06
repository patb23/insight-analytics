package com.insight.puzzle;

/**
 * LCA is Pojo that corresponds to the file content, Category is the Pojo that corresponds to the Output file.
 *
 */
public class LCA {

    private String Status;
    private String jobTitle;
    private String workLocationState;

    public LCA(String status, String jobTitle, String workLocationState) {
        Status = status;
        this.jobTitle = jobTitle;
        this.workLocationState = workLocationState;
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getWorkLocationState() {
        return workLocationState;
    }

    public void setWorkLocationState(String workLocationState) {
        this.workLocationState = workLocationState;
    }

    @Override
    public String toString() {

        return "LCA{" +
                "Status='" + Status + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", workLocationState='" + workLocationState + '\'' +
                '}';
    }


    static class Category  implements Comparable<LCA.Category>{

        String classifier;


        Integer value;

        Integer percentage;

        public Category(String classifier, Integer value, Integer total) {
            this.classifier = classifier;
            this.value = value;

            this.percentage = (int) Math.ceil((value.doubleValue() / total.doubleValue()) * 100);

        }

        public String getClassifier() {
            return classifier;
        }

        public void setClassifier(String classifier) {
            this.classifier = classifier;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        @Override
        public String toString() {

            return String.format("%s ; %d; %d",classifier, value,percentage);
        }


        @Override
        public int compareTo(Category o) {
            return value.compareTo(o.value);
        }
    }

}