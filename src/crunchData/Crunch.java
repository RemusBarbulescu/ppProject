package crunchData;

import DataFrames.cnvData;
import DataFrames.newSegmentFrame;
import Normalize.normalizeValues;

import java.util.*;

public class Crunch extends normalizeValues {

    private List<newSegmentFrame> Buffer = new ArrayList<>();
    private List<newSegmentFrame> BufferXY = new ArrayList<>();


    protected void crunch(List<cnvData> readDepth, List<cnvData> SVdetect,
                          List<newSegmentFrame> newSegments, List<newSegmentFrame> newSegmentsXY){

        int index = 0, indexXY = 0;
        // Inserting start & end values on one column
        for (cnvData itr : readDepth) {


            if (!itr.getChr().equals("Y")
                    && !itr.getChr().equals("X")) {

                Buffer.add(new newSegmentFrame());
                Buffer.get(index).setValues(itr.getChr(),
                        itr.getStart(), 0,
                        itr.getValue(), 0.0f);
                index++;

            } else {
                BufferXY.add(new newSegmentFrame());
                BufferXY.get(indexXY).setValues(itr.getChr(),
                        itr.getStart(), 0,
                        itr.getValue(), 0.0f);
                indexXY++;
            }


            if (!itr.getChr().equals("Y")
                    && !itr.getChr().equals("X")) {

                Buffer.add(new newSegmentFrame());
                Buffer.get(index).setValues(itr.getChr(),
                        itr.getEnd(), 0,
                        itr.getValue(), 0.0f);
                index++;

            } else {
                BufferXY.add(new newSegmentFrame());
                BufferXY.get(indexXY).setValues(itr.getChr(),
                        itr.getEnd(), 0,
                        itr.getValue(), 0.0f);
                indexXY++;
            }

        }

        // Same, but now for SVdetect values
        for (cnvData itr : SVdetect) {

            if (!itr.getChr().equals("Y")
                    && !itr.getChr().equals("X")) {

                Buffer.add(new newSegmentFrame());
                Buffer.get(index).setValues(itr.getChr(),
                        itr.getStart(), 0,
                        0.0f, itr.getValue());
                index++;

            } else {
                BufferXY.add(new newSegmentFrame());
                BufferXY.get(indexXY).setValues(itr.getChr(),
                        itr.getStart(), 0,
                        0.0f, itr.getValue());
                indexXY++;
            }


            if (!itr.getChr().equals("Y")
                    && !itr.getChr().equals("X")) {

                Buffer.add(new newSegmentFrame());
                Buffer.get(index).setValues(itr.getChr(),
                        itr.getEnd(), 0,
                        0.0f, itr.getValue());
                index++;

            } else {
                BufferXY.add(new newSegmentFrame());
                BufferXY.get(indexXY).setValues(itr.getChr(),
                        itr.getEnd(), 0,
                        0.0f, itr.getValue());
                indexXY++;
            }

        }

        // Sorting data
        Buffer.sort(new newSegmentFrame.sortData());
        BufferXY.sort(new newSegmentFrame.sortData());


        newSegmentFrame buffer = new newSegmentFrame();
        int j = 0, k = 0;
        index = 0;
        for (int i = 0; i < Buffer.size()-1; i++){

            if (Buffer.get(i).getChr().equals(Buffer.get(i+1).getChr())){

                if (Buffer.get(i+1).getStart() - Buffer.get(i).getStart() > 1){

                    buffer.setChr(Buffer.get(i).getChr());
                    buffer.setStart(Buffer.get(i).getStart());
                    buffer.setEnd(Buffer.get(i+1).getStart());

                    j = assignCNV(j, buffer, readDepth, "rd");
                    k = assignCNV(k, buffer, SVdetect, "sv");

                    newSegments.add(new newSegmentFrame());
                    newSegments.get(index).setValues(buffer.getChr(), buffer.getStart(),
                            buffer.getEnd(), buffer.getReadDepthValue(),
                            buffer.getSVdetectValue());
                    index++;

                }
            }
        }

        j = 0;
        k = 0;
        index = 0;
        for (int i = 0; i < BufferXY.size()-1; i++){

            if (BufferXY.get(i).getChr().equals(BufferXY.get(i+1).getChr())){

                if (BufferXY.get(i+1).getStart() - BufferXY.get(i).getStart() > 1){

                    buffer.setChr(BufferXY.get(i).getChr());
                    buffer.setStart(BufferXY.get(i).getStart());
                    buffer.setEnd(BufferXY.get(i+1).getStart());

                    j = assignCNV(j, buffer, readDepth, "rd");
                    k = assignCNV(k ,buffer, SVdetect, "sv");


                    newSegmentsXY.add(new newSegmentFrame());
                    newSegmentsXY.get(index).setValues(buffer.getChr(), buffer.getStart(),
                            buffer.getEnd(), buffer.getReadDepthValue(),
                            buffer.getSVdetectValue());
                    index++;

                }
            }
        }


    }

    private int assignCNV(int j, newSegmentFrame buffer, List<cnvData> data, String who) {


        for (; j < data.size(); j++) {

            if (data.get(j).getChr().equals(buffer.getChr())) {

                if (buffer.getStart() >= data.get(j).getStart()
                        && buffer.getStart() <= data.get(j).getEnd()) {
                    if (who.equals("rd"))
                        buffer.setReadDepthValue(data.get(j).getValue());
                    else
                        buffer.setSVdetectValue(data.get(j).getValue());
                    break;

                } else {

                    if (data.get(j + 1).getStart() >= buffer.getStart()
                            && data.get(j + 1).getStart() <= buffer.getEnd()) {

                        if (who.equals("rd"))
                            buffer.setReadDepthValue(data.get(j+1).getValue());
                        else
                            buffer.setSVdetectValue(data.get(j+1).getValue());
                        j++;
                        break;

                    } else {

                        if (who.equals("rd"))
                            buffer.setReadDepthValue(0.0);
                        else
                            buffer.setSVdetectValue(0.0);
                        break;

                    }
                }

            }
        }
        return j;
    }

}
