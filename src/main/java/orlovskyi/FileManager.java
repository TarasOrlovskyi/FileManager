package orlovskyi;

import java.io.*;

public class FileManager {

    static int countDirs(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        int count = 0;
        for (File file1 : files) {
            if (file1.isDirectory()) {
                count++;
                count += countDirs(file1.getAbsolutePath());
            }
        }
        return count;
    }

    static int countFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        int count = 0;
        for (File file1 : files) {
            if (!file1.isDirectory()) {
                count++;
            } else {
                count += countFiles(file1.getAbsolutePath());
            }
        }
        return count;
    }

    public static void copy(String from, String to) {
        File pathFrom = new File(from);
        File[] files = pathFrom.listFiles();
        for (File file : files) {
            File pathTo = new File(to, file.getName());
            pathFrom = new File(from, file.getName());
            if (file.isDirectory()) {
                copyDir(pathTo);
                copy(String.valueOf(pathFrom), String.valueOf(pathTo));
            } else {
                copyFile(file, pathTo);
            }
        }
    }

    public static void move(String from, String to) {
        File pathFrom = new File(from);
        File[] files = pathFrom.listFiles();
        for (File file : files) {
            file.renameTo(new File(to, file.getName()));
        }
    }

    private static void copyFile(File pathFrom, File pathTo) {
        try (InputStream inputStream = new FileInputStream(pathFrom);
             OutputStream outputStream = new FileOutputStream(pathTo)) {
            int count;
            while ((count = inputStream.read()) != -1) {
                outputStream.write(count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyDir(File pathTo) {
        pathTo.mkdir();
    }
}
