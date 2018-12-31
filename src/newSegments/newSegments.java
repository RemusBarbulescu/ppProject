package newSegments;
import DataFrames.cnvData;
import DataFrames.newSegmentFrame;
import crunchData.Crunch;

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


    public void getInputValues(FileInputStream readDepthInput,
                               FileInputStream SVdetectInput, String gender){

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

        System.out.println("Crunching values...");
        crunch(readDepth, SVdetect, newSegments, newSegmentsXY, gender);
        System.out.println("Normalizing values...");
        normalize(newSegments);
        if (gender.equals("male")) {
            System.out.println("Normalizing values for X and Y chromosomes...");
            normalizeXY(newSegmentsXY);
        }

    }


}
