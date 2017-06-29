package br.ufs.demos.rxmvp.playground;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by bira on 6/28/17.
 */

public class FilesFromTestResources {

    public static String readFile(String fileName) {

        StringBuilder result = new StringBuilder();
        ClassLoader classLoader = FilesFromTestResources.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();
            return result.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Cannot read file" + fileName);
    }
}
