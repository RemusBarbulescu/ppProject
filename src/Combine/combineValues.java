package Combine;

import DataFrames.cnvFrame;
import DataFrames.newSegmentFrame;
import compressData.compressData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class combineValues extends compressData{

    private Map<Double, Integer> frequency = new HashMap<>();
    private List<cnvFrame> cnvCombined = new ArrayList<>();
    //private boolean found = false;

    protected void combine(List<newSegmentFrame> newSegments) {

        for (newSegmentFrame itr : newSegments){
            this.frequency.merge(itr.getReadDepthValue(), 1, Integer::sum);
        }

        int localMinimum = Integer.MAX_VALUE;
        double amplificationThreshold = 0;
        double deletionsThreshold;

        for (Object o : frequency.entrySet()){

            Map.Entry pair = (Map.Entry) o;

            if ((double)pair.getKey() >= 2.0 && (double)pair.getKey() <= 2.35){
                if ((int)pair.getValue() < localMinimum){
                    localMinimum = (int)pair.getValue();
                    amplificationThreshold = (double)pair.getKey();
                }
            }
        }


        deletionsThreshold = 2 - (amplificationThreshold - 2);
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
                                    2.0, itr.getReadDepthValue(), itr.getSVdetectValue(),
                                    "Conflict1 (RD:AMP, SV:DEL)");
                            index++;

                        }else{
                            if (itr.getReadDepthValue() < amplificationThreshold &&
                                    itr.getSVdetectValue() >= amplificationThreshold){

                                cnvCombined.add(new cnvFrame());
                                cnvCombined.get(index).setValues(itr.getChr(), itr.getStart(), itr.getEnd(),
                                        2.0, itr.getReadDepthValue(), itr.getSVdetectValue(),
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
                                    2.0, itr.getReadDepthValue(), itr.getSVdetectValue(),
                                    "Unknown Error");
                            index++;

                        }

                    }
                }
            }
        }

        for (int i = 1; i < cnvCombined.size(); i++){

            if (cnvCombined.get(i).getComment().equals("Conflict1 (RD:AMP, SV:DEL)") &&
                    ((cnvCombined.get(i-1).getComment().equals("RD") && cnvCombined.get(i+1).getComment().equals("RD")) ||
                            (cnvCombined.get(i-1).getComment().equals("SV") && cnvCombined.get(i+1).getComment().equals("SV")))){

                cnvCombined.get(i).setCombinedValue(cnvCombined.get(i-1).getCombinedValue());
                cnvCombined.get(i).setComment("Removed(Conflict 1)  - " + cnvCombined.get(i-1).getComment());

            }

        }

        compress(cnvCombined);

    }

}
