package lzss.paramsreader;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;

import java.io.IOException;

@Slf4j
public class UncompressParamService extends ParamService {

    @Override
    public void run(String... args) throws
                                    ZipException,
                                    IOException {
        String fileToUncompressName = args[1];
        String directoryToUncompress = args[2];
        packer.uncompress(fileToUncompressName, directoryToUncompress);
    }
}
