package newSegments;
import DataFrames.cnvData;
import DataFrames.newSegmentFrame;
import UserInterface.TextAreaStyle;
import crunchData.Crunch;

import javax.swing.*;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class newSegments extends Crunch {

    private List<cnvData> readDepth = new ArrayList<>();
    private List<newSegmentFrame> newSegments = new ArrayList<>();
    private List<newSegmentFrame> newSegmentsXY = new ArrayList<>();
    private List<cnvData> SVdetect = new ArrayList<>();


    @SuppressWarnings("Duplicates")
    public void getInputValues(FileInputStream readDepthInput,
                               FileInputStream SVdetectInput, FileInputStream refGenomeInput,
                               String gender, String match, String outputName,
                               JProgressBar progressBar, TextAreaStyle console){

        Scanner scanReadDepth = new Scanner(readDepthInput);
        Scanner scanSVdetect = new Scanner(SVdetectInput);

        String[] lineScan;
        int index = 0;

        while (scanReadDepth.hasNextLine()){

            lineScan = scanReadDepth.nextLine().split("\t");
            if (!lineScan[0].equals("chr") && !lineScan[0].equals("Chr")
                    && !lineScan[0].equals("CHR") && !lineScan[0].equals("chromosome")
                    && !lineScan[0].equals("Chromosome")) {

                if (lineScan[0].length() > 2)
                    lineScan[0] = lineScan[0].replace("chr", "");

                BigDecimal bd = new BigDecimal(Double.parseDouble(lineScan[4]));
                bd = bd.setScale(4, RoundingMode.HALF_UP);
                readDepth.add(new cnvData());
                readDepth.get(index).setValues(lineScan[0], Integer.parseInt(lineScan[1]),
                        Integer.parseInt(lineScan[2]), bd.doubleValue());
                index++;

            }

        }

        index = 0;
        while (scanSVdetect.hasNextLine()){

            lineScan = scanSVdetect.nextLine().split("\t");
            if (!lineScan[0].equals("chr") && !lineScan[0].equals("Chr")
                    && !lineScan[0].equals("CHR") && !lineScan[0].equals("chromosome")
                    && !lineScan[0].equals("Chromosome")) {

                if (lineScan[0].length() > 2)
                    lineScan[0] = lineScan[0].replace("chr", "");

                BigDecimal bd = new BigDecimal(Double.parseDouble(lineScan[5]));
                bd = bd.setScale(4, RoundingMode.HALF_UP);
                SVdetect.add(new cnvData());
                SVdetect.get(index).setValues(lineScan[0], Integer.parseInt(lineScan[1]),
                        Integer.parseInt(lineScan[2]), bd.doubleValue());
                index++;

            }
        }

        // Sorting data
        readDepth.sort(new cnvData.sortData());
        SVdetect.sort(new cnvData.sortData());

        if (match.equals("Yes")) {
            progressBar.setString("0.1%");
            progressBar.update(progressBar.getGraphics());
        } else {
            progressBar.setString("14.2%");
            progressBar.setValue(14);
            progressBar.update(progressBar.getGraphics());
        }
        console.append("\t-Crunching values...\n");
        console.update(console.getGraphics());
        crunch(readDepth, SVdetect, newSegments, newSegmentsXY, gender);

        if (gender.equals("M")) {
            if (match.equals("Yes")) {
                progressBar.setString("0.2%");
                progressBar.update(progressBar.getGraphics());
            } else {
                progressBar.setString("26%");
                progressBar.setValue(26);
                progressBar.update(progressBar.getGraphics());
            }
            console.append("Normalizing values for chromosomes 1-22...\n");
            console.update(console.getGraphics());
            normalize(newSegments, refGenomeInput, gender, match, outputName, progressBar, console);

            if (match.equals("Yes")) {
                progressBar.setString("90.1%");
                progressBar.update(progressBar.getGraphics());
            } else {
                progressBar.setString("91%");
                progressBar.setValue(91);
                progressBar.update(progressBar.getGraphics());
            }
            console.append("Normalizing values for chromosomes X and Y...\n");
            console.update(console.getGraphics());
            normalizeXY(newSegmentsXY, refGenomeInput, gender, match, outputName, progressBar, console);
        } else {
            if (match.equals("Yes")) {
                progressBar.setString("0.3%");
                progressBar.update(progressBar.getGraphics());
            } else {
                progressBar.setString("35%");
                progressBar.setValue(35);
                progressBar.update(progressBar.getGraphics());
            }
            console.append("Normalizing values...\n");
            console.update(console.getGraphics());
            normalize(newSegments, refGenomeInput, gender, match, outputName, progressBar, console);
        }

    }
}
