package lzss;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String...args) throws
                                           IOException {
        final String path = "/Users/krzysztofczarnecki/Documents/GitHub/tiik/xd.txt";
        String src = Files.readString(Path.of(path));


        File initialFile = new File(path);
        InputStream targetStream = new FileInputStream(initialFile);

        ByteArrayOutputStream compress = new Lzss(targetStream).compress();

        try(OutputStream outputStream = new FileOutputStream("thefilename")) {
            compress.writeTo(outputStream);
        }


        File compressedFile = new File("/Users/krzysztofczarnecki/Documents/GitHub/tiik/thefilename");
        InputStream compressedStream = new FileInputStream(compressedFile);
        ByteArrayOutputStream uncompress = new Lzss(compressedStream).uncompress();


        System.out.println(src.equals(uncompress.toString(Charset.defaultCharset())));
    }
}
