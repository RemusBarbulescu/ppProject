package DataFrames;

import java.util.Comparator;

public class cnvData {

    private String chr; // chromosome
    private int start; // start position of the segment
    private int end; // end position of the segment
    private double value;


    public void setValues(String chr, int start, int end, double value){

        this.chr = chr;
        this.start = start;
        this.end = end;
        this.value = value;

    }

    public String getChr() {
        return chr;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public double getValue() {
        return value;
    }

    public static class sortData implements Comparator<cnvData> {
        @Override
        public int compare(cnvData t1, cnvData t2) {

            int chrCompare = t1.getChr().compareTo(t2.getChr());
            int startCompare = Integer.compare(t1.getStart(), t2.getStart());
            if (chrCompare != 0)
                return chrCompare;
            else if (startCompare != 0)
                return startCompare;

            return 0;

        }
    }
}
