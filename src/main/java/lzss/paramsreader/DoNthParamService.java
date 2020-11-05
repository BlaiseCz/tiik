package lzss.paramsreader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoNthParamService extends ParamService {

    @Override
    public void run(String... args) {
        log.error("No args passed. Doing nothing.");
        log.info("########################################");
        log.info("#####           HELP               #####");
        log.info("########################################");
        log.info("");
        log.info("########################################");
        log.info("#####         COMPRESS             #####");
        log.info("##### java -jar -c <file_name> <file_to_pack_1> <file_to_pack_2> <...> <file_to_pack_n> #####");
        log.info("########################################");
        log.info("");
        log.info("########################################");
        log.info("#####         COMPRESS ALL         #####");
        log.info("##### java -jar -ca <file_name> <dir_to_pack_files> #####");
        log.info("########################################");
        log.info("");
        log.info("########################################");
        log.info("#####        UNCOMPRESS            #####");
        log.info("##### java -jar -dc <file_name_to_uncompress> <destingation_folder> #####");
        log.info("########################################");
    }
}
