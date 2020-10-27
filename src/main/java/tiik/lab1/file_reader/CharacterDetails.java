package tiik.lab1.file_reader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class CharacterDetails {
    private Character character;
    private int occurances;
    private double probablility;
    private double entropy;

    public CharacterDetails(Character character, int occurances, double probablility) {
        this.character = character;
        this.occurances = occurances;
        this.probablility = probablility;
    }

    public void incrementOccurances() {
        occurances++;
    }
}
