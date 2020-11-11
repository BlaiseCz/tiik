package tiik.lab1.file_reader;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntropyCalculator {

    static List<CharacterDetails> list;
    public static double calculate(Map<Character, Integer> characterIntegerMap, int length) {
         list = getCharacterDetails(characterIntegerMap, (double) length);

        return calculateEntropy(list, length);
    }

    private static double calculateEntropy(List<CharacterDetails> list , int length) {

        double entropy = 0.0d;
        for(CharacterDetails characterDetails : list) {
            double buf = -characterDetails.getProbablility() * (log2(characterDetails.getProbablility()));
            characterDetails.setEntropy(buf);
            entropy += buf;
        }

        if (String.valueOf(entropy).equals("NaN"))
            return 0;

        return entropy;
    }

    private static List<CharacterDetails> getCharacterDetails(Map<Character, Integer> characterIntegerMap, double length) {
        List<CharacterDetails> list = new ArrayList<>();

        for (Map.Entry<Character, Integer> entry : characterIntegerMap.entrySet()) {
            int value = entry.getValue();
            double percentage = (value / length);
            list.add(new CharacterDetails(entry.getKey(), value, percentage));
        }

        return list;
    }

    public static double log2(double n) {
        return Math.log(n) / Math.log(2);
    }

    @SneakyThrows
    public static List<CharacterDetails> getCharacterDetailsList(){
        if(list!= null) {
            return list;
        }
        else {
            throw new IllegalAccessException();
        }
    }

}
