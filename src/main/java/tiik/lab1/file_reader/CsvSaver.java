package tiik.lab1.file_reader;

import lombok.SneakyThrows;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CsvSaver {
    private static final String DELIMITER = "\t";
    private static final String NEW_LINE = "\n";

    @SneakyThrows
    public void saveToCSV(List<CharacterDetails> characterDetailsList, String fileName) {
        FileWriter csvWriter = new FileWriter(prepareFileName(fileName));

        List<List<String>> rows = new ArrayList<>();
        rows.add(prepareHeader());

        sortByCharacters(characterDetailsList);
        characterDetailsList.forEach(el -> rows.add(prepareRow(el)));

        for (List<String> rowData : rows) {
            csvWriter.append(joinRowData(rowData));
        }

        csvWriter.flush();
        csvWriter.close();
    }

    private String prepareFileName(String fileName) {
        return String.format("%s_result.csv", fileName);
    }

    private List<String> prepareHeader() {
        return Arrays.asList("Znak", "Ilość wystąpień", "Prawdopodobieństwo", "Entropia");
    }

    private List<String> prepareRow(CharacterDetails elem) {
        double entropia = 0;

        if (!String.valueOf(elem.getEntropy()).equals("NaN"))
            entropia = elem.getEntropy();

        return Arrays.asList(
                String.valueOf(elem.getCharacter()),
                String.valueOf(elem.getOccurances()),
                BigDecimal.valueOf(elem.getProbablility()).toPlainString().replace(".", ","),
                BigDecimal.valueOf(entropia).toPlainString().replace(".", ","));
    }

    private String joinRowData(List<String> rowData) {
        return String.join(DELIMITER, rowData) + NEW_LINE;
    }

    private void sortByCharacters(List<CharacterDetails> characterDetailsList) {
        Comparator<CharacterDetails> compareByCharacter = Comparator.comparing(CharacterDetails::getCharacter);
        characterDetailsList.sort(compareByCharacter);
    }
}
