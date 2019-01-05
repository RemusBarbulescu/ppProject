package MatchToReferenceGenome;

import DataFrames.cnvData;
import DataFrames.genesFrame;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class matchToRefGenome {

    @SuppressWarnings("ConstantConditions")
    protected void matchCNVtoRefGenome(String cnvInputPath, String outputName,
                                       FileInputStream refGenomeInput, JProgressBar progressBar, String gender) {

        Map<Integer, Map<Integer, genesFrame>> genes = new HashMap<>(24);


        List<cnvData> cnv = new ArrayList<>();

        if (!genes.isEmpty()){
            System.out.println(outputName + "Not empty");
            System.exit(1);
        }
        if (!cnv.isEmpty()){
            System.out.println("cnv not empty");
            System.exit(1);
        }
        FileInputStream cnvInput = null;

        {
            try {
                cnvInput =
                        new FileInputStream(cnvInputPath);
            } catch (FileNotFoundException e) {
                System.out.println("File not found " + e.getMessage());
                System.exit(1);
            }
        }

        Scanner cnvScanner = new Scanner(cnvInput);
        Scanner geneScanner = new Scanner(refGenomeInput);

        String[] lineScan;
        int cnvIndex = 0;

        Formatter out = null;
        try {
            out = new Formatter("../MetaCNV/Output Files/Homo_sapiens.GRCh38.84_onlyExons_" +
                    outputName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 24; i++) {
            genes.put(i, new HashMap<>());
        }
        while (cnvScanner.hasNextLine()) {

            lineScan = cnvScanner.nextLine().split("\t");
            if (!lineScan[0].equals("chr") && !lineScan[0].equals("Chr")
                    && !lineScan[0].equals("CHR") && !lineScan[0].equals("chromosome")
                    && !lineScan[0].equals("Chromosome")){

                cnv.add(new cnvData());
                cnv.get(cnvIndex).setValues(lineScan[0], Integer.parseInt(lineScan[1]),
                        Integer.parseInt(lineScan[2]), Double.parseDouble(lineScan[3]));
                cnvIndex++;

            }
        }

        int[] index = new int[24];
        int check = 0;
        while (geneScanner.hasNextLine()) {

            System.out.println(check);
            check++;
            lineScan = geneScanner.nextLine().split("\t");
            if (!lineScan[1].equals("chr") && !lineScan[1].equals("Chr")
                    && !lineScan[1].equals("CHR") && !lineScan[1].equals("chromosome")
                    && !lineScan[1].equals("Chromosome")) {

                switch (lineScan[1]){

                    case "1":
                        genes.get(0).put(index[0], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[0]++;
                        break;

                    case "2":
                        genes.get(1).put(index[1], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[1]++;
                        break;

                    case "3":
                        genes.get(2).put(index[2], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[2]++;
                        break;

                    case "4":
                        genes.get(3).put(index[3], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[3]++;
                        break;

                    case "5":
                        genes.get(4).put(index[4], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[4]++;
                        break;

                    case "6":
                        genes.get(5).put(index[5], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[5]++;
                        break;

                    case "7":
                        genes.get(6).put(index[6], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[6]++;
                        break;

                    case "8":
                        genes.get(7).put(index[7], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[7]++;
                        break;

                    case "9":
                        genes.get(8).put(index[8], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[8]++;
                        break;

                    case "10":
                        genes.get(9).put(index[9], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[9]++;
                        break;

                    case "11":
                        genes.get(10).put(index[10], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[10]++;
                        break;

                    case "12":
                        genes.get(11).put(index[11], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[11]++;
                        break;

                    case "13":
                        genes.get(12).put(index[12], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[12]++;
                        break;

                    case "14":
                        genes.get(13).put(index[13], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[13]++;
                        break;

                    case "15":
                        genes.get(14).put(index[14], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[14]++;
                        break;

                    case "16":
                        genes.get(15).put(index[15], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[15]++;
                        break;

                    case "17":
                        genes.get(16).put(index[16], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[16]++;
                        break;

                    case "18":
                        genes.get(17).put(index[17], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[17]++;
                        break;

                    case "19":
                        genes.get(18).put(index[18], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[18]++;
                        break;

                    case "20":
                        genes.get(19).put(index[19], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[19]++;
                        break;

                    case "21":
                        genes.get(20).put(index[20], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[20]++;
                        break;

                    case "22":
                        genes.get(21).put(index[21], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[21]++;
                        break;

                    case "X":
                        genes.get(22).put(index[22], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[22]++;
                        break;

                    case "Y":
                        genes.get(23).put(index[23], new genesFrame(lineScan[1], lineScan[2], lineScan[3],
                                lineScan[5], lineScan[7], lineScan[11], Integer.parseInt(lineScan[12]),
                                Integer.parseInt(lineScan[13])));
                        index[23]++;
                        break;

                    default:
                        break;
                }
            }
        }


        double remainingPercentage;
        if (gender.equals("M")) {
            if (!cnv.get(0).getChr().equals("X") && !cnv.get(0).getChr().equals("Y")) {
                progressBar.setString("1.5%");
                progressBar.update(progressBar.getGraphics());
                remainingPercentage = 88.5; //because next after this is the processing of X and Y chr ~10%
            } else {
                progressBar.setString("91%");
                progressBar.update(progressBar.getGraphics());
                remainingPercentage = 9.0;
            }
        } else {
            progressBar.setString("2%");
            progressBar.setValue(2);
            progressBar.update(progressBar.getGraphics());
            remainingPercentage = 98.0;
        }

        for(int i = 0; i < cnv.size(); i++){

            double percent = ((double)i / (double)cnv.size()) * remainingPercentage;
            progressBarUpdate(percent, progressBar);

            switch (cnv.get(i).getChr()){

                case "1":
                    for(int j = 0; j < genes.get(0).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(0).get(j), out);
                    }
                case "2":
                    for(int j = 0; j < genes.get(1).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(1).get(j), out);
                    }
                case "3":
                    for(int j = 0; j < genes.get(2).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(2).get(j), out);
                    }
                case "4":
                    for(int j = 0; j < genes.get(3).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(3).get(j), out);
                    }
                case "5":
                    for(int j = 0; j < genes.get(4).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(4).get(j), out);
                    }
                case "6":
                    for(int j = 0; j < genes.get(5).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(5).get(j), out);
                    }
                case "7":
                    for(int j = 0; j < genes.get(6).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(6).get(j), out);
                    }
                case "8":
                    for(int j = 0; j < genes.get(7).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(7).get(j), out);
                    }
                case "9":
                    for(int j = 0; j < genes.get(8).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(8).get(j), out);
                    }
                case "10":
                    for(int j = 0; j < genes.get(9).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(9).get(j), out);
                    }
                case "11":
                    for(int j = 0; j < genes.get(10).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(10).get(j), out);
                    }
                case "12":
                    for(int j = 0; j < genes.get(11).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(11).get(j), out);
                    }
                case "13":
                    for(int j = 0; j < genes.get(12).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(12).get(j), out);
                    }
                case "14":
                    for(int j = 0; j < genes.get(13).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(13).get(j), out);
                    }
                case "15":
                    for(int j = 0; j < genes.get(14).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(14).get(j), out);
                    }
                case "16":
                    for(int j = 0; j < genes.get(15).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(15).get(j), out);
                    }
                case "17":
                    for(int j = 0; j < genes.get(16).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(16).get(j), out);
                    }
                case "18":
                    for(int j = 0; j < genes.get(17).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(17).get(j), out);
                    }
                case "19":
                    for(int j = 0; j < genes.get(18).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(18).get(j), out);
                    }
                case "20":
                    for(int j = 0; j < genes.get(19).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(19).get(j), out);
                    }
                case "21":
                    for(int j = 0; j < genes.get(20).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(20).get(j), out);
                    }
                case "22":
                    for(int j = 0; j < genes.get(21).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(21).get(j), out);
                    }
                case "X":
                    for(int j = 0; j < genes.get(22).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(22).get(j), out);
                    }
                case "Y":
                    for(int j = 0; j < genes.get(23).size(); j++) {
                        checkAndOutput(cnv.get(i), cnv.get(i + 1).getEnd(), genes.get(23).get(j), out);
                    }

            }

        }

    }

    @SuppressWarnings("Duplicates")
    private void checkAndOutput(cnvData cnvValues, int nextEndValue,
                                genesFrame genesValues, Formatter out){

        if (genesValues.getChr().equals(cnvValues.getChr()) &&
                genesValues.getStart() >= cnvValues.getStart() &&
                genesValues.getEnd() <= cnvValues.getEnd()){

            out.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%f\n",
                    genesValues.getChr(), genesValues.getGeneId(), genesValues.getBiotype(),
                    genesValues.getName(), genesValues.getStrand(), genesValues.getExonId(),
                    genesValues.getStart(), genesValues.getEnd(), cnvValues.getChr(),
                    cnvValues.getStart(), cnvValues.getEnd(), cnvValues.getValue(), 1.00
            );

        }

        if (genesValues.getChr().equals(cnvValues.getChr()) &&
                genesValues.getStart() >= cnvValues.getStart() &&
                genesValues.getEnd() > cnvValues.getEnd() &&
                genesValues.getStart() < cnvValues.getEnd() &&
                genesValues.getEnd() < nextEndValue){

            out.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%f\n",
                    genesValues.getChr(), genesValues.getGeneId(), genesValues.getBiotype(),
                    genesValues.getName(), genesValues.getStrand(), genesValues.getExonId(),
                    genesValues.getStart(), genesValues.getEnd(), cnvValues.getChr(),
                    cnvValues.getStart(), cnvValues.getEnd(), cnvValues.getValue(),
                    (float)(cnvValues.getEnd() - genesValues.getStart())/(float)(genesValues.getEnd() - genesValues.getStart())
            );

        }

        if (genesValues.getChr().equals(cnvValues.getChr()) &&
                genesValues.getStart() < cnvValues.getStart() &&
                genesValues.getEnd() > cnvValues.getEnd()){

            out.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%f\n",
                    genesValues.getChr(), genesValues.getGeneId(), genesValues.getBiotype(),
                    genesValues.getName(), genesValues.getStrand(), genesValues.getExonId(),
                    genesValues.getStart(), genesValues.getEnd(), cnvValues.getChr(),
                    cnvValues.getStart(), cnvValues.getEnd(), cnvValues.getValue(),
                    (float)(cnvValues.getEnd() - cnvValues.getStart())/(float)(genesValues.getEnd() - genesValues.getStart())
            );

        }

        if (genesValues.getChr().equals(cnvValues.getChr()) &&
                genesValues.getStart() < cnvValues.getStart() &&
                genesValues.getEnd() < cnvValues.getEnd() &&
                genesValues.getEnd() > cnvValues.getStart()){

            out.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%f\n",
                    genesValues.getChr(), genesValues.getGeneId(), genesValues.getBiotype(),
                    genesValues.getName(), genesValues.getStrand(), genesValues.getExonId(),
                    genesValues.getStart(), genesValues.getEnd(), cnvValues.getChr(),
                    cnvValues.getStart(), cnvValues.getEnd(), cnvValues.getValue(),
                    (float)(genesValues.getEnd() - cnvValues.getStart())/(float)(genesValues.getEnd() - genesValues.getStart())
            );

        }
    }

    private void progressBarUpdate(double percent, JProgressBar progressBar) {
       BigDecimal  progressBarPercentage = new BigDecimal(
               Double.parseDouble(progressBar.getString().replace("%", "")) + percent
       );
       progressBarPercentage  = progressBarPercentage.setScale(4, RoundingMode.HALF_UP);
        System.out.println(progressBarPercentage.toString() + "\n");
        progressBar.setString(progressBarPercentage.toString() + "%");
       if (Math.floor(progressBarPercentage.doubleValue()) == progressBar.getValue()+1){
           progressBar.setValue(progressBar.getValue() + 1);
       }
       progressBar.update(progressBar.getGraphics());
    }
}
