package lzss;

import lombok.extern.slf4j.Slf4j;
import lzss.paramsreader.RunParams;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class TiikCompressorRunner {
    public static void main(String... args) throws
                                            ZipException {
        try {
            RunParams runParams = RunParams.getByValue(args);
            runParams.getParamService().run(args);
        }
        catch (Exception e){
            e.printStackTrace();
            log.error("Operation didn't success");
            log.error(e.getMessage());
        }
    }
}
