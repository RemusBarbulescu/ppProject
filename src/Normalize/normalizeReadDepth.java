package Normalize;

import Combine.combineValues;
import DataFrames.newSegmentFrame;

import java.util.*;

public class normalizeReadDepth extends combineValues {

    private Map<Double, Integer> frequency = new HashMap<>();


    protected void normalize(List<newSegmentFrame> newSegments){


        for (newSegmentFrame itr : newSegments) {

            double eFactor = Math.pow(1 + (itr.getSVdetectValue() / 100.0), 0.75 * itr.getSVdetectValue());
            itr.setSVdetectValue((itr.getSVdetectValue() * eFactor));

            frequency.merge(itr.getReadDepthValue(), 1, Integer::sum);

        }


        int localMaximum = 0;
        double localMaximumValue = 0;

        for (Object o : frequency.entrySet()) {

            Map.Entry pair = (Map.Entry) o;
            if ((int) pair.getValue() > localMaximum) {
                localMaximum = (int) pair.getValue();
                localMaximumValue = (double) pair.getKey();
            }
        }

        short ploidity = 2;
        double bias = ploidity - localMaximumValue;

        for (newSegmentFrame itr : newSegments) {

            double factor = Math.min(itr.getReadDepthValue() / 2.0, 1.0);
            itr.setReadDepthValue(itr.getReadDepthValue() + (factor * bias));

        }

        combine(newSegments);

    }

}
