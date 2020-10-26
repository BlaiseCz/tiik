package tiik.lab1.file_reader;

import lombok.SneakyThrows;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Lab1Presentator {


    public void present(String filePath) {
        System.out.println("PRESENTING FILE \n" + filePath);
        String file = Reader.getContent(filePath);
        Map<Character, Integer> stringIntegerMap = Converter.countCharacters(file);

        System.out.println("Częstość wystąpień:");
        Printer.print(stringIntegerMap);
        System.out.println("\n\n\n");

        String fileName = Path.of(filePath)
                       .getFileName()
                       .toString()
                       .split("\\.")[0];
        saveToCSV(stringIntegerMap, fileName);
    }

    public void present(String...filePaths) {
        Stream.of(filePaths)
              .forEach(this::present);
    }

    @SneakyThrows
    public void saveToCSV(Map<Character, Integer> stringIntegerMap, String fileName) {
        List<List<String>> rows = new ArrayList<>();
        rows.add(Arrays.asList("Znak", "Częstość wystąpień"));

        stringIntegerMap
                .forEach((k,v) -> rows.add(Arrays.asList(String.valueOf(k), String.valueOf(v))));


        String resultFileName = String.format("%s_result.csv", fileName);
        FileWriter csvWriter = new FileWriter(resultFileName);

        for (List<String> rowData : rows) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }
}
