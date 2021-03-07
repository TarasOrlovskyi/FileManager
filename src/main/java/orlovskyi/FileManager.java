package orlovskyi;

import java.io.*;

public class FileManager {

    static int countDirs(String path) {
        File file = null;
        int count = 0;
        try {
            file = new File(path);
            if (!file.exists()) {
                throw new FileNotFoundException("File does not exist");
            }
            if (!file.isDirectory()) {
                return 0;
            }
            File[] files = file.listFiles();
            for (File file1 : files) {
                if (file1.isDirectory()) {
                    count++;
                    count += countDirs(file1.getAbsolutePath());
                }
            }
            return count;
        } catch (NullPointerException e) {
            System.out.println("mystery file/folder is -> " + file.getName());
            //e.printStackTrace();
            System.out.println(e);
            Throwable[] throwables = e.getSuppressed();
            System.out.println("length: " + throwables.length);
            for (Throwable throwable : throwables) {
                System.out.println(throwable);
            }
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
        return count;
    }

    static int countFiles(String path) {
        int count = 0;
        File file = null;
        try {
            file = new File(path);
            if (!file.exists()) {
                throw new FileNotFoundException("File does not exist");
            }
            if (!file.isDirectory()) {
                return 1;
            }
            File[] files = file.listFiles();
            for (File file1 : files) {
                if (!file1.isDirectory()) {
                    count++;
                } else {
                    count += countFiles(file1.getAbsolutePath());
                }
            }
        } catch (NullPointerException e) {
            System.out.println("mystery file/folder is -> " + file.getName());
//            e.printStackTrace();
            System.out.println(e);
            Throwable[] throwables = e.getSuppressed();
            System.out.println("length: " + throwables.length);
            for (Throwable throwable : throwables) {
                System.out.println(throwable);
            }
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
        return count;
    }

    public static void copy(String from, String to) {
        try {
            File pathFrom = new File(from);
            File pathTo = new File(to);
            if (!pathFrom.exists() || !pathTo.exists()) {
                throw new FileNotFoundException("File not found!");
            }
            if (pathFrom.isDirectory()) {
                File[] files = pathFrom.listFiles();
                for (File file : files) {
                    pathTo = new File(to, file.getName());
                    pathFrom = new File(from, file.getName());
                    if (file.isDirectory()) {
                        copyDir(pathTo);
                        copy(String.valueOf(pathFrom), String.valueOf(pathTo));
                    } else {
                        copyFile(file, pathTo);
                    }
                }
            } else {
                copyFile(pathFrom, new File(to, pathFrom.getName()));
            }
        } catch (NullPointerException npe) {
            System.out.println("File should be not null! pathFrom or pathTo is null!");
            npe.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }

    public static void move(String from, String to) {
        try {
            File pathFrom = new File(from);
            File pathTo = new File(to);
            if (!pathFrom.exists() || !pathTo.exists()) {
                throw new FileNotFoundException("File not found!");
            }
            if (pathFrom.isDirectory()) {
                File[] files = pathFrom.listFiles();
                for (File file : files) {
                    file.renameTo(new File(to, file.getName()));
                }
            } else if (pathFrom.exists()) {
                pathFrom.renameTo(new File(to, pathFrom.getName()));
            }
        } catch (NullPointerException npe) {
            System.out.println("File should be not null! pathFrom or pathTo is null!");
            npe.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
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
