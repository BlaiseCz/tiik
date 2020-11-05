package lzss.paramsreader;

import lzss.compressor.FilesCompressor;
import lzss.compressor.TiikCompressor;
import net.lingala.zip4j.exception.ZipException;

import java.io.IOException;

public abstract class ParamService {
    FilesCompressor packer = new TiikCompressor();
    public abstract void run(String... args) throws
                                             ZipException,
                                             IOException;
}
