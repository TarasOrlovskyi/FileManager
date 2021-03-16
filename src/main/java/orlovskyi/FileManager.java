package orlovskyi;

import java.io.*;

public class FileManager {

    static int countDirs(String path) {
        try {
            int count = 0;
            validateNullPath(path);
            File pathForCount = new File(path);
            validateFileExistence(pathForCount);
            if (!pathForCount.isDirectory()) {
                return 0;
            }
            File[] files = pathForCount.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        count++;
                        count += countDirs(file.getAbsolutePath());
                    }
                }
            } else {
                System.out.println("Attention! You don't have permission to read " + pathForCount);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    static int countFiles(String path) {
        try {
            int count = 0;
            validateNullPath(path);
            File pathForCount = new File(path);
            validateFileExistence(pathForCount);
            if (!pathForCount.isDirectory()) {
                return 1;
            }
            File[] files = pathForCount.listFiles();
            if (files!=null) {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        count++;
                    } else {
                        count += countFiles(file.getAbsolutePath());
                    }
                }
            } else {
                System.out.println("Attention! You don't have permission to read " + pathForCount);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void copy(String from, String to) {
        try {
            validateNullPath(from);
            validateNullPath(to);

            File pathFrom = new File(from);
            File pathTo = new File(to);

            validateFileExistence(pathFrom);
            validateFileExistence(pathTo);

            validateDirectoryPath(pathTo);

            if (pathFrom.isFile()) {
                copyFile(pathFrom, new File(to, pathFrom.getName()));
            } else {
                File[] files = pathFrom.listFiles();
                if (files!=null) {
                    for (File file : files) {
                        pathTo = new File(to, file.getName());
                        pathFrom = new File(from, file.getName());
                        if (file.isDirectory()) {
                            createDirInDestinationDir(pathTo);
                            copy(String.valueOf(pathFrom), String.valueOf(pathTo));
                        } else {
                            copyFile(file, pathTo);
                        }
                    }
                } else {
                    System.out.println("Attention! You don't have permission to read " + pathFrom);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void move(String from, String to) {
        try {
            validateNullPath(from);
            validateNullPath(to);

            File pathFrom = new File(from);
            File pathTo = new File(to);

            validateFileExistence(pathFrom);
            validateFileExistence(pathTo);

            validateDirectoryPath(pathTo);

            if (pathFrom.isDirectory()) {
                File[] files = pathFrom.listFiles();
                if (files!=null) {
                    for (File file : files) {
                        file.renameTo(new File(to, file.getName()));
                    }
                } else {
                    System.out.println("Attention! You don't have permission to read the " + pathFrom);
                }
            } else {
                pathFrom.renameTo(new File(to, pathFrom.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
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

    private static void createDirInDestinationDir(File pathTo) {
        pathTo.mkdir();
    }

    private static void validateNullPath(String path) throws Exception {
        if (path == null) {
            throw new Exception("Path is NULL. You should change the path to the correct one!");
        }
    }

    private static void validateFileExistence(File path) throws FileNotFoundException {
        if (!path.exists()) {
            throw new FileNotFoundException("File: " + path.getAbsolutePath() + " not found!");
        }
    }

    private static void validateDirectoryPath(File path) throws Exception {
        if (!path.isDirectory()) {
            throw new Exception("The copy path must be a directory!");
        }
    }
}
