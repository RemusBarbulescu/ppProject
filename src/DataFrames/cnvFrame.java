package DataFrames;

public class cnvFrame {

    private String chr;
    private int start;
    private int end;
    private double combinedValue;
    private double readDepthValue;
    private double SVdetectValue;
    private String comment;

    public void setValues(String chr, int start, int end, double combinedValue, double readDepthValue,
                          double SVdetectValue, String comment){

        this.chr = chr;
        this.start = start;
        this.end = end;
        this.combinedValue = combinedValue;
        this.readDepthValue = readDepthValue;
        this.SVdetectValue = SVdetectValue;
        this.comment = comment;

    }

    public void setCombinedValue(double value){ this.combinedValue = value; }

    public void setComment(String comment) { this.comment = comment; }

    public void setEnd (int end) { this.end = end; }

    public String getChr() { return this.chr; }

    public int getStart() { return this.start; }

    public int getEnd() { return this.end; }

    public double getCombinedValue() { return this.combinedValue; }

    public double getReadDepthValue() { return this.readDepthValue; }

    public double getSVdetectValue() { return this.SVdetectValue; }

    public String getComment() { return this.comment; }
}
