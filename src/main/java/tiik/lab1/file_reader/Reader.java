package tiik.lab1.file_reader;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

public class Reader {


    @SneakyThrows
    public static String getContent(String path) {
        return Files.readString(Path.of(path));
    }
}
