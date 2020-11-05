package lzss.paramsreader;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class CompressParamService extends ParamService {

    @Override
    public void run(String... args) throws
                                    ZipException,
                                    IOException {
        String fileName = args[1];
        String[] pathsToCompress = Arrays.copyOfRange(args, 2, args.length);
        packer.compress(fileName, pathsToCompress);
    }
}
