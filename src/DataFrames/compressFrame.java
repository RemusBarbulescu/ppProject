package DataFrames;

public class compressFrame {

    private String chr;
    private int start;
    private int end;
    private double value;
    private String readDepthValue;
    private String SVdetectValue;
    private String comment;

    public void setValues(String chr, int start, int end, double value, String readDepthValue,
                          String SVdetectValue, String comment){

        this.chr = chr;
        this.start = start;
        this.end = end;
        this.value = value;
        this.readDepthValue = readDepthValue;
        this.SVdetectValue = SVdetectValue;
        this.comment = comment;

    }

    public void setValue(double value){ this.value = value; }

    public void setComment(String comment) { this.comment = comment; }

    public void setEnd (int end) { this.end = end; }

    public void setReadDepthValue (String readDepthValue) { this.readDepthValue = readDepthValue; }

    public void setSVdetectValue (String SVdetectValue) { this.SVdetectValue = SVdetectValue; }

    public String getChr() { return this.chr; }

    public int getStart() { return this.start; }

    public int getEnd() { return this.end; }

    public double getValue() { return this.value; }

    public String getReadDepthValue() { return this.readDepthValue; }

    public String getSVdetectValue() { return this.SVdetectValue; }

    public String getComment() { return this.comment; }

}
