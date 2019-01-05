package Combine;

import DataFrames.cnvFrame;
import DataFrames.newSegmentFrame;
import compressData.compressData;

import javax.swing.*;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class combineValues extends compressData{

    @SuppressWarnings("Duplicates")
    protected void combine(List<newSegmentFrame> newSegments, FileInputStream refGenomeInput,
                           String gender, String match, String outputName,
                           JProgressBar progressBar, JTextArea console) {

        Map<Double, Integer> frequency = new HashMap<>();
        List<cnvFrame> cnvCombined = new ArrayList<>();

        for (newSegmentFrame itr : newSegments){
            frequency.merge(itr.getReadDepthValue(), 1, Integer::sum);
        }

        int localMinimum = Integer.MAX_VALUE;
        double amplificationThreshold = 0;
        double deletionsThreshold;
        double ploidity;
        if (!newSegments.get(0).getChr().equals("X") && !newSegments.get(0).getChr().equals("Y")) {

            ploidity = 2.0;

            for (Object o : frequency.entrySet()){

                Map.Entry pair = (Map.Entry) o;

                if ((double)pair.getKey() >= 2.0 && (double)pair.getKey() <= 2.35){
                    if ((int)pair.getValue() < localMinimum){
                        localMinimum = (int)pair.getValue();
                        amplificationThreshold = (double)pair.getKey();
                    }
                }
            }

            deletionsThreshold = ploidity - (amplificationThreshold - ploidity);

        }else {
            ploidity = 1.0;
            for (Object o : frequency.entrySet()){

                Map.Entry pair = (Map.Entry) o;

                if ((double)pair.getKey() >= 1.0 && (double)pair.getKey() <= 1.35){
                    if ((int)pair.getValue() < localMinimum){
                        localMinimum = (int)pair.getValue();
                        amplificationThreshold = (double)pair.getKey();
                    }
                }
            }

            deletionsThreshold = ploidity - (amplificationThreshold - ploidity);
        }

        int index = 0;

        for (newSegmentFrame itr : newSegments){

            if (itr.getReadDepthValue() == 0.0 && itr.getSVdetectValue() == 0.0){

                cnvCombined.add(new cnvFrame());
                cnvCombined.get(index).setValues(itr.getChr(), itr.getStart(), itr.getEnd(),
                        0.0, itr.getReadDepthValue(), itr.getSVdetectValue(),
                        "No data from callers");
                index++;

            }else {
                if (itr.getReadDepthValue() == 0.0 && itr.getSVdetectValue() != 0.0){
                    if (itr.getSVdetectValue() < amplificationThreshold){
                        cnvCombined.add(new cnvFrame());
                        cnvCombined.get(index).setValues(itr.getChr(), itr.getStart(), itr.getEnd(),
                                0.0, itr.getReadDepthValue(), itr.getSVdetectValue(),
                                "No data from callers");
                        index++;
                    }else{
                        BigDecimal bd = new BigDecimal(itr.getSVdetectValue());
                        bd = bd.setScale(1, RoundingMode.HALF_UP);
                        cnvCombined.add(new cnvFrame());
                        cnvCombined.get(index).setValues(itr.getChr(), itr.getStart(), itr.getEnd(),
                                bd.doubleValue(), itr.getReadDepthValue(), itr.getSVdetectValue(),
                                "SV(RD NULL)");
                        index++;
                    }
                }else{
                    if (itr.getReadDepthValue() != 0.0 && itr.getSVdetectValue() != 0.0){
                        if (itr.getReadDepthValue() >= amplificationThreshold &&
                                itr.getSVdetectValue() <= amplificationThreshold){

                            cnvCombined.add(new cnvFrame());
                            cnvCombined.get(index).setValues(itr.getChr(), itr.getStart(), itr.getEnd(),
                                    ploidity, itr.getReadDepthValue(), itr.getSVdetectValue(),
                                    "Conflict1 (RD:AMP, SV:DEL)");
                            index++;

                        }else{
                            if (itr.getReadDepthValue() < amplificationThreshold &&
                                    itr.getSVdetectValue() >= amplificationThreshold){

                                cnvCombined.add(new cnvFrame());
                                cnvCombined.get(index).setValues(itr.getChr(), itr.getStart(), itr.getEnd(),
                                        ploidity, itr.getReadDepthValue(), itr.getSVdetectValue(),
                                        "Conflict2 (RD:DEL, SV:AMP)");
                                index++;

                            }else{
                                if (itr.getReadDepthValue() < amplificationThreshold &&
                                        itr.getSVdetectValue() < amplificationThreshold){

                                    BigDecimal bd = new BigDecimal(itr.getReadDepthValue());
                                    bd = bd.setScale(1, RoundingMode.HALF_UP);
                                    cnvCombined.add(new cnvFrame());
                                    cnvCombined.get(index).setValues(itr.getChr(), itr.getStart(), itr.getEnd(),
                                            bd.doubleValue(), itr.getReadDepthValue(), itr.getSVdetectValue(),
                                            "RD");
                                    index++;

                                }else{
                                    if (itr.getReadDepthValue() >= deletionsThreshold &&
                                            itr.getSVdetectValue() >= amplificationThreshold){

                                        BigDecimal bd = new BigDecimal(itr.getSVdetectValue());
                                        bd = bd.setScale(1, RoundingMode.HALF_UP);
                                        cnvCombined.add(new cnvFrame());
                                        cnvCombined.get(index).setValues(itr.getChr(), itr.getStart(), itr.getEnd(),
                                                bd.doubleValue(), itr.getReadDepthValue(), itr.getSVdetectValue(),
                                                "SV");
                                        index++;
                                    }
                                }
                            }
                        }
                    }else{

                        if (itr.getReadDepthValue() != 0.0 && itr.getSVdetectValue() == 0.0){

                            BigDecimal bd = new BigDecimal(itr.getReadDepthValue());
                            bd = bd.setScale(1, RoundingMode.HALF_UP);
                            cnvCombined.add(new cnvFrame());
                            cnvCombined.get(index).setValues(itr.getChr(), itr.getStart(), itr.getEnd(),
                                    bd.doubleValue(), itr.getReadDepthValue(), itr.getSVdetectValue(),
                                    "RD(SV NULL)");
                            index++;

                        }else {

                            cnvCombined.add(new cnvFrame());
                            cnvCombined.get(index).setValues(itr.getChr(), itr.getStart(), itr.getEnd(),
                                    ploidity, itr.getReadDepthValue(), itr.getSVdetectValue(),
                                    "Unknown Error");
                            index++;

                        }

                    }
                }
            }
        }

        for (int i = 1; i < cnvCombined.size()-1; i++){

            if (cnvCombined.get(i).getComment().equals("Conflict1 (RD:AMP, SV:DEL)") &&
                    ((cnvCombined.get(i-1).getComment().equals("RD") && cnvCombined.get(i+1).getComment().equals("RD")) ||
                            (cnvCombined.get(i-1).getComment().equals("SV") && cnvCombined.get(i+1).getComment().equals("SV")))){

                cnvCombined.get(i).setCombinedValue(cnvCombined.get(i-1).getCombinedValue());
                cnvCombined.get(i).setComment("Removed(Conflict 1)  - " + cnvCombined.get(i-1).getComment());

            }

        }

        if (gender.equals("M")) {
            if (!cnvCombined.get(0).getChr().equals("X") && !cnvCombined.get(0).getChr().equals("Y")) {
                if (match.equals("Yes")) {
                    progressBar.setString("0.6%");
                    progressBar.update(progressBar.getGraphics());
                } else {
                    progressBar.setString("65%");
                    progressBar.setValue(65);
                    progressBar.update(progressBar.getGraphics());
                }
                console.append("Compressing final values for chromosomes 1-22...\n");
                console.update(console.getGraphics());
                compress(cnvCombined, refGenomeInput, gender, match, outputName, progressBar, console);
            } else {
                if (match.equals("Yes")) {
                    progressBar.setString("90.6%");
                    progressBar.update(progressBar.getGraphics());
                } else {
                    progressBar.setString("98%");
                    progressBar.setValue(98);
                    progressBar.update(progressBar.getGraphics());
                }
                console.append("Compressing final values for chromosomes X and Y...\n");
                console.update(console.getGraphics());
                compress(cnvCombined, refGenomeInput, gender, match, outputName, progressBar, console);
            }
        } else {
            if (match.equals("Yes")) {
                progressBar.setString("0.8%");
                progressBar.update(progressBar.getGraphics());
            } else {
                progressBar.setString("75%");
                progressBar.setValue(75);
                progressBar.update(progressBar.getGraphics());
            }
            console.append("Compressing final values...\n");
            console.update(console.getGraphics());
            compress(cnvCombined, refGenomeInput, gender, match, outputName, progressBar, console);
        }

    }

}
