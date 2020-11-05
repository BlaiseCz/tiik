package lzss.paramsreader;

import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;

public class CompressAllParamsService extends ParamService {
    @Override
    public void run(String... args) throws
                                    ZipException,
                                    IOException {
        String fileName = args[1];
        String[] pathsToCompress = getNamesMerged(args[2], getAllFiles(args[2]));
        packer.compress(fileName, pathsToCompress);
    }

    private String[] getAllFiles(String dest) {
        return new File(dest).list();
    }

    private String[] getNamesMerged(String desc, String...paths) {
        String[] tmpPaths = new String[paths.length];

        for (int i = 0; i < paths.length; i++) {
            tmpPaths[i] = merge(desc, paths[i]);
        }

        return tmpPaths;
    }

    private String merge(String desc, String name) {
        return String.format("%s/%s", desc, name);
    }
}
