package lzss;

import java.io.*;

public class Main {
    public static void main(String...args) throws
                                           IOException {
        final String path = "xd.txt";
        final String compressedFileName = "compressed";

        FileInputStream fileToCompress = readFile(path);
        ByteArrayOutputStream compressedFile = new Lzss(fileToCompress).compress();

        saveToFile(compressedFile, compressedFileName);

        FileInputStream compressedStream = readFile(compressedFileName);
        ByteArrayOutputStream uncompress = new Lzss(compressedStream).uncompress();


        saveToFile(uncompress, "odkompresowane.txt");

    }

    private static void saveToFile(ByteArrayOutputStream compressedFile, String fileName) throws
                              IOException {
        try(OutputStream outputStream = new FileOutputStream(fileName)) {
            compressedFile.writeTo(outputStream);
        }
    }

    private static FileInputStream readFile(String path) throws
                                              FileNotFoundException {
        File initialFile = new File(path);
        return new FileInputStream(initialFile);
    }
}
