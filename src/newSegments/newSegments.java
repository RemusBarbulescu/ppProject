package newSegments;
import DataFrames.*;
import crunchData.Crunch;


import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class newSegments extends Crunch {

    private List<cnvData> readDepth = new ArrayList<>();
    private List<newSegmentFrame> newSegments = new ArrayList<>();
    private List<newSegmentFrame> newSegmentsXY = new ArrayList<>();
    private List<cnvData> SVdetect = new ArrayList<>();


    public void getInputValues(FileInputStream readDepthInpt,
                               FileInputStream SVdetectInput){

        Scanner scanReadDepth = new Scanner(readDepthInpt);
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


        crunch(readDepth, SVdetect, newSegments, newSegmentsXY);
        normalize(newSegments);

    }


}
