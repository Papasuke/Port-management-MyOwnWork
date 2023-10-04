package lib.crud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Read {
    public static ArrayList<String[]> readAllLine(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        String currentLine;
        ArrayList<String[]> products = new ArrayList<String[]>();
        String[] data;

        // Skip the first line
        br.readLine();

        while ((currentLine = br.readLine()) != null) {
            data = currentLine.split(",");
            products.add(data);
        }

        return products;
    }
}
