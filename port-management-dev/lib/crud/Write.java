package lib.crud;

import java.io.*;
import java.util.ArrayList;

public class Write {
    public static void write(String filePath, String attributes, String obj) throws IOException {
        File file = new File(filePath);
        FileWriter csvFile = new FileWriter(file, true);

        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        if(reader.readLine() == null){
            csvFile.append(attributes);
            csvFile.append("\n");
        }

        reader.close();

        try {
            ArrayList<String> db = new ArrayList<>();

            db.add(obj);

            for (String s : db) {
                csvFile.append(String.valueOf(s));
                csvFile.append("\n");
            }
            csvFile.close();
        }catch (Exception e){
            e.getStackTrace();
        }
    }


    public static void RemoveDataFromFile(String filePath) {
        try {
            // Open the file in write mode
            FileWriter writer = new FileWriter(filePath);

            // Open the file in read-write mode
            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");

            // Get the length of the file
            long fileLength = raf.length();

            // Clear the file by setting its length to 0
            raf.setLength(0);

            // Write the first line back to the file
            String firstLine = raf.readLine();
            if (firstLine != null) {
                writer.write(firstLine);
            }

            raf.close();
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

}
