package DataFrames;

import java.util.Comparator;

public class newSegmentFrame {

    private String chr;
    private int start;
    private int end;
    private double readDepthValue;
    private double SVdetectValue;

    public void setValues(String chr, int start, int end, double readDepthValue,
                          double SVdetectValue){

        this.chr = chr;
        this.start = start;
        this.end = end;
        this.readDepthValue = readDepthValue;
        this.SVdetectValue = SVdetectValue;

    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setReadDepthValue(double readDepthValue) {
        this.readDepthValue = readDepthValue;
    }

    public void setSVdetectValue(double SVdetectValue) {
        this.SVdetectValue = SVdetectValue;
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

    public double getReadDepthValue() {
        return readDepthValue;
    }

    public double getSVdetectValue() { return SVdetectValue; }

    public static class sortData implements Comparator<newSegmentFrame> {
        @Override
        public int compare(newSegmentFrame t1, newSegmentFrame t2) {

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
