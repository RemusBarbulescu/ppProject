package Normalize;

import Combine.combineValues;
import DataFrames.newSegmentFrame;

import javax.swing.*;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@SuppressWarnings("ALL")
public abstract class normalizeValues extends combineValues {

    private Map<Double, Integer> frequency;
    private int localMaximum;
    private double localMaximumValue;


    protected void normalize(List<newSegmentFrame> newSegments, FileInputStream refGenomeInput,
                             String gender, String match, String outputName,
                            JProgressBar progressBar, JTextArea console){

        frequency = new HashMap<>();
        localMaximum = 0;
        localMaximumValue = 0;

        mapFrequencyValues(newSegments);

        getMostFrequentValue();

        short ploidity = 2;
        normalizeRDvalues(newSegments, ploidity);

        if (gender.equals("M")) {
            if (match.equals("Yes")) {
                progressBar.setString("0.3%");
                progressBar.update(progressBar.getGraphics());
            } else {
                progressBar.setString("40%");
                progressBar.setValue(40);
                progressBar.update(progressBar.getGraphics());
            }
            console.append("Combining values for chromosomes 1-22...\n");
            console.update(console.getGraphics());
            combine(newSegments, refGenomeInput, gender, match, outputName, progressBar, console);
        } else {
            if (match.equals("Yes")) {
                progressBar.setString("0.5%");
                progressBar.update(progressBar.getGraphics());
            } else {
                progressBar.setString("50%");
                progressBar.setValue(50);
                progressBar.update(progressBar.getGraphics());
            }
            console.append("Combining values...\n");
            console.update(console.getGraphics());
            combine(newSegments, refGenomeInput, gender, match, outputName, progressBar, console);
        }

    }

    protected void normalizeXY(List<newSegmentFrame> newSegmentsXY, FileInputStream refGenomeInput,
                               String gender, String match, String outputName,
                               JProgressBar progressBar, JTextArea console){

        frequency = new HashMap<>();
        localMaximum = 0;
        localMaximumValue = 0;

        mapFrequencyValues(newSegmentsXY);

        getMostFrequentValue();

        short ploidity = 1;
        normalizeRDvalues(newSegmentsXY, ploidity);

        if (match.equals("Yes")) {
            progressBar.setString("90.4%");
            progressBar.update(progressBar.getGraphics());
        } else {
            progressBar.setString("95%");
            progressBar.setValue(95);
            progressBar.update(progressBar.getGraphics());
        }
        console.append("Combining values for chromosomes X and Y ...\n");
        console.update(console.getGraphics());
        combine(newSegmentsXY, refGenomeInput, gender, match, outputName, progressBar, console);

    }

    private void mapFrequencyValues(List<newSegmentFrame> values){

        for (newSegmentFrame itr : values) {

            //normalizing SVdetect values in the process
            svNorm eFactor = (value) -> Math.pow(1 + (value / 100.0), 0.75 * value);
            BigDecimal bd = new BigDecimal(
                    eFactor.compute(itr.getSVdetectValue()) * itr.getSVdetectValue()
            );
            bd = bd.setScale(4, RoundingMode.HALF_UP);
            itr.setSVdetectValue((bd.doubleValue()));

            frequency.merge(itr.getReadDepthValue(), 1, Integer::sum);

        }

    }

    interface svNorm{
        double compute (double value);
    }

    interface rdNorm{
        double compute (double value);
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

            rdNorm factor = (value) -> Math.min(value / 2.0, 1.0);
            itr.setReadDepthValue(itr.getReadDepthValue() +
                    (factor.compute(itr.getReadDepthValue()) * bias));

        }

    }

}
