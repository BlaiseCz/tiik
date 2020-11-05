package lzss;

import lzss.compressor.Lzss;
import lzss.compressor.TiikCompressor;
import net.lingala.zip4j.exception.ZipException;

import java.io.*;

public class Main {
    public static void main(String...args) throws
                                           IOException,
                                           ZipException {
        final String path = "xd.txt";
        final String compressedFileName = "compressed";

        FileInputStream fileToCompress = readFile(path);
        ByteArrayOutputStream compressedFile = new Lzss(fileToCompress).compress();

        saveToFile(compressedFile, compressedFileName);

        FileInputStream compressedStream = readFile(compressedFileName);
        ByteArrayOutputStream uncompress = new Lzss(compressedStream).uncompress();


        saveToFile(uncompress, "odkompresowane.txt");

        new TiikCompressor().compress("/Users/krzysztofczarnecki/Documents/GitHub/tiik/src/main/resources/tekst_eng.txt",
                                      "/Users/krzysztofczarnecki/Documents/GitHub/tiik/src/main/resources/tekst_inf_pl.txt",
                                      "/Users/krzysztofczarnecki/Documents/GitHub/tiik/src/main/resources/tekst_pl.txt");

//        new TiikCompressor().uncompress("test/xd");

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
