package DataFrames;

public class genesFrame {

    private String chr;
    private String geneId;
    private String biotype;
    private String name;
    private String strand;
    private String exonId;
    private int start;
    private int end;

    public void setValues(String chr, String geneId, String biotype, String name, String strand,
                          String exonId, int start, int end){

        this.chr = chr;
        this.geneId = geneId;
        this.biotype = biotype;
        this.name = name;
        this.strand = strand;
        this.exonId = exonId;
        this.start = start;
        this.end = end;

    }

    public String getChr() { return chr; }

    public String getGeneId() { return geneId; }

    public String getBiotype() { return biotype; }

    public String getName() { return name; }

    public String getStrand() { return strand; }

    public String getExonId() { return exonId; }

    public int getStart() { return start; }

    public int getEnd() { return end; }
}
