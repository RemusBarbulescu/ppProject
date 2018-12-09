import newSegments.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class main {

    public static void main(String[] args) {

        //Opening Input Files
        FileInputStream readDepthInput = null, SVdetectInput = null;

        {
            try {
                readDepthInput =
                        new FileInputStream("../MetaCNV/Input Files/readDepth/lcm1.2_TUM.dat");
                SVdetectInput =
                        new FileInputStream("../MetaCNV/Input Files/SVdetect/lcm1.2_TUM.dat");
            } catch (FileNotFoundException e) {
                System.out.println("File not found " + e.getMessage());
                System.exit(1);
            }
        }

        newSegments ns = new newSegments();
        ns.getInputValues(readDepthInput, SVdetectInput);
        System.out.println("Finnish");

//        Formatter out = null;
//        try {
//            out = new Formatter("../MetaCNV/Output Files/test.txt");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        for (newSegmentFrame newSeg : Buffer) {
//            out.format("%s\t%s\t%s\t%s\t%s\n", newSeg.getChr(), newSeg.getStart(),
//                    newSeg.getEnd(), newSeg.getReadDepthValue(),
//                    newSeg.getSVdetectValue());
//        }
//
//        out.close();
    }

}
