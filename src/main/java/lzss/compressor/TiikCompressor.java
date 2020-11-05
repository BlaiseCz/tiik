package lzss.compressor;

import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class TiikCompressor implements FilesCompressor {
    private static final String EXTENSION = "tiik";
    private static final String TMP_FILE_PREFIX = "tmp_";
    private ZipFile zipFile;

    @Override
    public void compress(String name, String... paths) throws
                                                       ZipException,
                                                       IOException {
        for (String path : paths) {
            FileInputStream fileToCompress = readFile(path);
            ByteArrayOutputStream compressedFile = new Lzss(fileToCompress).compress();
            saveToFile(compressedFile, getTmpFileName(path));
        }

        packFile(name, getTmpFileNames(paths));
        deleteTmpFiles(getTmpFileNames(paths));
    }

    @Override
    public void uncompress(String path, String destination) throws
                                                            ZipException,
                                                            IOException {
        unpackFiles(path, destination);
        String[] unpackedNames = getFilesFromDirectory(destination); // wez wszystkie pliki z tego folderu

        for (String name: unpackedNames) {
            FileInputStream fileToUncompress = readFile(destination + "/" + name);
            ByteArrayOutputStream uncompress = new Lzss(fileToUncompress).uncompress();
            saveToFile(uncompress, getFileNameFromTmp(destination, name));
            deleteTmpFiles(destination + "/" + name);
        }
    }

    ArrayList<File> getFilesFromString(String...paths) {
        return Stream.of(paths)
                     .map(File::new)
                     .collect(Collectors.toCollection(ArrayList::new));
    }

    private String[] getFilesFromDirectory(String destination) {
        return new File(destination).list();
    }

    private String getExtension(String name) {
        return String.format("%s.%s", name, EXTENSION);
    }

    private void packFile(String name, String... paths) throws
                                                        ZipException {
        zipFile = new ZipFile(getExtension(name));
        zipFile.createZipFile(getFilesFromString(paths), new ZipParameters());
    }

    private void unpackFiles(String path, String destination) throws
                                                              ZipException {
        zipFile = new ZipFile(path);
        zipFile.extractAll(destination);
    }

    private static void saveToFile(ByteArrayOutputStream compressedFile, String fileName) throws
                                                                                          IOException {
        try(OutputStream outputStream = new FileOutputStream(fileName)) {
            compressedFile.writeTo(outputStream);
        }
    }

    private String getTmpFileName(String path) {
        String fileName = new File(path).getName();
        return String.format("%s%s", TMP_FILE_PREFIX, fileName);
    }

    private String getFileNameFromTmp(String destination, String name) {
        return String.format("%s/%s", destination, name.substring(TMP_FILE_PREFIX.length()));
    }

    private String[] getTmpFileNames(String...paths) {
        String[] tmpPaths = new String[paths.length];

        for (int i = 0; i < paths.length; i++) {
            tmpPaths[i] = getTmpFileName(paths[i]);
        }

        return tmpPaths;
    }

    private static FileInputStream readFile(String path) throws
                                                         FileNotFoundException {
        File initialFile = new File(path);
        return new FileInputStream(initialFile);
    }

    private void deleteTmpFiles(String...tmpFileNames) {
        for (String name : tmpFileNames) {
            new File(name).delete();
        }
    }
}
