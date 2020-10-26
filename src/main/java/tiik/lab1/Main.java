package tiik.lab1;

import tiik.lab1.file_reader.Lab1Presentator;

public class Main {
    public static void main(String... args) {
        String path1 = "/Users/krzysztofczarnecki/Desktop/artykuł/informacje.txt";
        String path2 = "/Users/krzysztofczarnecki/Desktop/artykuł/informacje2.txt";
//        String path3 = "/Users/krzysztofczarnecki/Desktop/artykuł/informacje.txt";

        Lab1Presentator lab1Presentator = new Lab1Presentator();
        lab1Presentator.present(path1, path2);
    }
}