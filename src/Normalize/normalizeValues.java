package Normalize;

import Combine.combineValues;
import DataFrames.newSegmentFrame;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class normalizeValues extends combineValues {

    private Map<Double, Integer> frequency;
    private int localMaximum;
    private double localMaximumValue;


    protected void normalize(List<newSegmentFrame> newSegments){

        frequency = new HashMap<>();
        localMaximum = 0;
        localMaximumValue = 0;

        mapFrequencyValues(newSegments);

        getMostFrequentValue();

        short ploidity = 2;
        normalizeRDvalues(newSegments, ploidity);

        System.out.println("Combining values...");
        combine(newSegments);

    }

    protected void normalizeXY(List<newSegmentFrame> newSegmentsXY){

        frequency = new HashMap<>();
        localMaximum = 0;
        localMaximumValue = 0;

        mapFrequencyValues(newSegmentsXY);

        getMostFrequentValue();

        short ploidity = 1;
        normalizeRDvalues(newSegmentsXY, ploidity);

        System.out.println("Combining values for X and Y chromosomes...");
        combine(newSegmentsXY);

    }

    private void mapFrequencyValues(List<newSegmentFrame> values){

        for (newSegmentFrame itr : values) {

            //normalizing SVdetect values in the process
            double eFactor = Math.pow(1 + (itr.getSVdetectValue() / 100.0), 0.75 * itr.getSVdetectValue());
            BigDecimal bd = new BigDecimal(eFactor * itr.getSVdetectValue());
            bd = bd.setScale(4, RoundingMode.HALF_UP);
            itr.setSVdetectValue((bd.doubleValue()));

            frequency.merge(itr.getReadDepthValue(), 1, Integer::sum);

        }

    }

    private void getMostFrequentValue(){

        for (Object o : frequency.entrySet()) {

            Map.Entry pair = (Map.Entry) o;
            if ((int) pair.getValue() > localMaximum) {
                localMaximum = (int) pair.getValue();
                localMaximumValue = (double) pair.getKey();
            }
        }

    }

    private void normalizeRDvalues(List<newSegmentFrame> values, int ploidity){

        double bias = ploidity - localMaximumValue;

        for (newSegmentFrame itr : values) {

            double factor = Math.min(itr.getReadDepthValue() / 2.0, 1.0);
            itr.setReadDepthValue(itr.getReadDepthValue() + (factor * bias));

        }

    }

}
