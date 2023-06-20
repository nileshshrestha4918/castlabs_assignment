package com.nilesh.castlab.utils;

import com.nilesh.castlab.model.Mp4Box;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MP4AnalyzerHelper {

    private static List<String> parentURL = List.of("moov", "trak","udta");

    public static void parseBoxes(InputStream inputStream, Mp4Box parentBox, List<Mp4Box> mp4Boxes) throws IOException {

        while (inputStream.available() > 0){
            inputStream.skip(parentBox.getBoxSize() - 8);

            if(inputStream.available() > 8){

                int boxSize = MP4AnalyzerHelper.readBoxSize(inputStream);
                String boxType = MP4AnalyzerHelper.readBoxType(inputStream);
                Mp4Box box = new Mp4Box(boxType, boxSize);

                if (MP4AnalyzerHelper.isParentBox(boxType)){
                    mp4Boxes.add(parseChildBoxes(inputStream, box, mp4Boxes, boxSize));
                }else{
                    mp4Boxes.add(box);
                    parseBoxes(inputStream, box, mp4Boxes);
                }

                System.out.println(boxType);
            }
        }
    }

    private static Mp4Box parseChildBoxes(InputStream inputStream, Mp4Box parentBox, List<Mp4Box> mp4Boxes, int parentBoxSize) throws IOException {
        long remainingBytes = parentBoxSize - 8;

        while (remainingBytes > 0) {

            if (inputStream.available() > 8) {
                int boxSize = MP4AnalyzerHelper.readBoxSize(inputStream);
                String boxType = MP4AnalyzerHelper.readBoxType(inputStream);
                Mp4Box box = new Mp4Box(boxType, boxSize);
                parentBox.addChildBox(box);

                if (MP4AnalyzerHelper.isParentBox(boxType)) {
                    parseChildBoxes(inputStream, box, mp4Boxes, boxSize);
                }else{
                    inputStream.skip(boxSize - 8);
                }

                remainingBytes -= boxSize;

                if (remainingBytes < 8) {
                    break;
                }
            }
        }
        return parentBox;
    }

    public static int readBoxSize(InputStream inputStream) throws IOException {
        byte[] boxSizeBytes = new byte[4];
        inputStream.read(boxSizeBytes);
        return byteArrayToInt(boxSizeBytes);
    }

    public static String readBoxType(InputStream inputStream) throws IOException {
        byte[] boxTypeBytes = new byte[4];
        inputStream.read(boxTypeBytes);
        return byteToString(boxTypeBytes);
    }

    private static int byteArrayToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                (bytes[3] & 0xFF);
    }

    private static String byteToString(byte[] byteArray) {
        StringBuilder asciiString = new StringBuilder();

        for (byte b : byteArray) {
            int decimal = b & 0xFF;
            char character = (char) decimal;
            asciiString.append(character);
        }

        return asciiString.toString();
    }

    public static boolean isParentBox(String boxType) {
        return parentURL.contains(boxType);
    }
}
