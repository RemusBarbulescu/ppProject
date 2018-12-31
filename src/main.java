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
        String gender = "female";
        System.out.println("Reconstructing the segmentation...");
        ns.getInputValues(readDepthInput, SVdetectInput, gender);
        System.out.println("Finnish");

    }

}
