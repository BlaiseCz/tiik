package tiik.lab1;

import tiik.lab1.file_reader.Lab1Presentator;

public class Main {
    public static void main(String... args) {
        String path1 = "tekst_pl.txt";
        String path2 = "tekst_inf_pl.txt";
        String path3 = "tekst_eng.txt";

        Lab1Presentator lab1Presentator = new Lab1Presentator();
        lab1Presentator.present(path1, path2, path3);
    }
}