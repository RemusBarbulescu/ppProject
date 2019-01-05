package compressData;

import DataFrames.cnvFrame;
import DataFrames.compressFrame;
import MatchToReferenceGenome.matchToRefGenome;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Formatter;
import java.util.List;

public class compressData extends matchToRefGenome {

    protected void compress(List<cnvFrame> cnvCombined, FileInputStream refGenomeInput,
                            String gender, String match, String outputName,
                            JProgressBar progressBar, JTextArea console){

        compressFrame buffer = new compressFrame();
        buffer.setValues("", 0, 0, 0,
                "", "", ""
        );
        Formatter out = null;
        try {

            if (gender.equals("M")) {
                if (!cnvCombined.get(0).getChr().equals("X") && !cnvCombined.get(0).getChr().equals("Y")) {
                    out = new Formatter("../MetaCNV/Output Files/" + outputName + ".csv");
                } else {
                    out = new Formatter("../MetaCNV/Output Files/" + outputName + "XY.csv");
                }
            } else {
                out = new Formatter("../MetaCNV/Output Files/" + outputName + ".csv");
            }
            out.format("%s\t%s\t%s\t%s\t%s\t%s\t%s",
                    "chr", "start", "end", "CN", "RD.CN", "SV.CN", "Comments"
            );
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

                } else {

                    out.format("\n%s\t%s\t%s\t%s\t%s\t%s\t%s", buffer.getChr(), buffer.getStart(),
                            buffer.getEnd(), buffer.getValue(), buffer.getReadDepthValue(),
                            buffer.getSVdetectValue(), buffer.getComment()
                    );

                    buffer.setValues(itr.getChr(), itr.getStart(), itr.getEnd(), itr.getCombinedValue(),
                            rd.toString(), sv.toString(), itr.getComment()
                    );

                }
            }
        }
        out.close();
        if (match.equals("Yes")) {
            if (gender.equals("M")) {
                if (!buffer.getChr().equals("X") && !buffer.getChr().equals("Y")) {
                    progressBar.setString("1%");
                    progressBar.setValue(1);
                    progressBar.update(progressBar.getGraphics());
                    console.append("Matching chromosomes 1-22 to the reference genome...\n");
                    console.update(console.getGraphics());
                    matchCNVtoRefGenome("../MetaCNV/Output Files/"
                            + outputName + ".csv", outputName + ".csv",  refGenomeInput,
                            progressBar, gender
                    );
                } else {
                    progressBar.setString("90.8%");
                    progressBar.update(progressBar.getGraphics());
                    console.append("Matching chromosomes X and Y to the reference genome...\n");
                    console.update(console.getGraphics());
                    matchCNVtoRefGenome("../MetaCNV/Output Files/"
                            + outputName + "XY.csv", outputName + "XY.csv", refGenomeInput,
                            progressBar, gender
                    );
                }
            } else {
                progressBar.setString("1.5%");
                progressBar.setValue(1);
                progressBar.update(progressBar.getGraphics());
                console.append("Matching to the reference genome...\n");
                console.update(console.getGraphics());
                matchCNVtoRefGenome("../MetaCNV/Output Files/"
                        + outputName + ".csv", outputName + ".csv", refGenomeInput,
                        progressBar, gender
                );
            }
        } else {
            if (gender.equals("M")){
                if (!buffer.getChr().equals("X") && !buffer.getChr().equals("Y")) {
                    progressBar.setString("90%");
                    progressBar.setValue(90);
                    progressBar.update(progressBar.getGraphics());
                } else {
                    progressBar.setString("100%");
                    progressBar.setValue(100);
                    progressBar.update(progressBar.getGraphics());
                }
            } else {
                progressBar.setString("100%");
                progressBar.setValue(100);
                progressBar.update(progressBar.getGraphics());
            }
        }
    }
}
