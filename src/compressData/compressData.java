package compressData;

import DataFrames.cnvFrame;
import DataFrames.compressFrame;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Formatter;
import java.util.List;

public class compressData {

    protected void compress(List<cnvFrame> cnvCombined){

        compressFrame buffer = new compressFrame();
        buffer.setValues("", 0, 0, 0,
                "", "", "");
        Formatter out = null;
        try {
            out = new Formatter("../MetaCNV/Output Files/test.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert out != null;
        out.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\n",
                "chr", "start", "end", "CN", "RD.CN", "SV.CN", "Comments");

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


                    out.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\n", buffer.getChr(), buffer.getStart(),
                            buffer.getEnd(), buffer.getValue(), buffer.getReadDepthValue(),
                            buffer.getSVdetectValue(), buffer.getComment());

                    buffer.setValues(itr.getChr(), itr.getStart(), itr.getEnd(), itr.getCombinedValue(),
                            rd.toString(), sv.toString(), itr.getComment());

                }
            }
        }
        out.close();
    }
}
