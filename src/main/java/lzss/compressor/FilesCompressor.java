package lzss.compressor;

import net.lingala.zip4j.exception.ZipException;

import java.io.IOException;

public interface FilesCompressor {
    void compress(String name, String... paths) throws
                                                ZipException,
                                                IOException;
    void uncompress(String name, String destination) throws
                                                     ZipException,
                                                     IOException;
}
