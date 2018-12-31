package compressData;

import DataFrames.cnvFrame;
import DataFrames.compressFrame;
import MatchToReferenceGenome.matchToRefGenome;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Formatter;
import java.util.List;

public class compressData extends matchToRefGenome {

    protected void compress(List<cnvFrame> cnvCombined){

        compressFrame buffer = new compressFrame();
        buffer.setValues("", 0, 0, 0,
                "", "", "");
        Formatter out = null;
        try {

            if (!cnvCombined.get(0).getChr().equals("X") && !cnvCombined.get(0).getChr().equals("Y")) {
                out = new Formatter("../MetaCNV/Output Files/test.txt");
            }else {
                out = new Formatter("../MetaCNV/Output Files/testXY.txt");
            }
            out.format("%s\t%s\t%s\t%s\t%s\t%s\t%s",
                    "chr", "start", "end", "CN", "RD.CN", "SV.CN", "Comments");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (cnvFrame itr : cnvCombined) {

            BigDecimal rd = new BigDecimal(itr.getReadDepthValue());
            BigDecimal sv = new BigDecimal(itr.getSVdetectValue());
            rd = rd.setScale(1, RoundingMode.HALF_UP);
            sv = sv.setScale(1, RoundingMode.HALF_UP);

            if (buffer.getChr().isEmpty()) {

                buffer.setValues(itr.getChr(), itr.getStart(), itr.getEnd(), itr.getCombinedValue(),
                        rd.toString(), sv.toString(), itr.getComment());
            }else {

                if (buffer.getChr().equals(itr.getChr()) && buffer.getValue() == itr.getCombinedValue()) {

                    buffer.setEnd(itr.getEnd());

                    if (!buffer.getReadDepthValue().contains(rd.toString())){

                        buffer.setReadDepthValue(buffer.getReadDepthValue() + ", " +
                                rd);

                    }

                    if(!buffer.getSVdetectValue().contains(sv.toString())){

                        buffer.setSVdetectValue(buffer.getSVdetectValue() + ", " + sv);

                    }

                    if(!buffer.getComment().contains(itr.getComment())){

                        buffer.setComment(buffer.getComment() + ", " + itr.getComment());

                    }

                }else {


                    out.format("\n%s\t%s\t%s\t%s\t%s\t%s\t%s", buffer.getChr(), buffer.getStart(),
                            buffer.getEnd(), buffer.getValue(), buffer.getReadDepthValue(),
                            buffer.getSVdetectValue(), buffer.getComment());

                    buffer.setValues(itr.getChr(), itr.getStart(), itr.getEnd(), itr.getCombinedValue(),
                            rd.toString(), sv.toString(), itr.getComment());

                }
            }
        }
        out.close();
//        System.out.println("Matching to the reference genome...");
//        matchCNVtoRefGenome("../MetaCNV/Output Files/test.txt");
    }
}
