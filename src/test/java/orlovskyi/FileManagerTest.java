package orlovskyi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

class FileManagerTest {
    private static final String PATH_TO_TEST_DIRS = "src/test/resources/";


    @BeforeEach
    void before() throws IOException {
        new File(PATH_TO_TEST_DIRS + "DIR").mkdir();
        new File(PATH_TO_TEST_DIRS + "DIR/1").mkdir();
        new File(PATH_TO_TEST_DIRS + "DIR/1/2").mkdir();
        new File(PATH_TO_TEST_DIRS + "DIR/1/2/3").mkdir();
        new File(PATH_TO_TEST_DIRS + "DIR/4").mkdir();
        new File(PATH_TO_TEST_DIRS + "DIR/4/5").mkdir();
        new File(PATH_TO_TEST_DIRS + "DIR/4/5/6").mkdir();
        new File(PATH_TO_TEST_DIRS + "DIR/7").mkdir();
        new File(PATH_TO_TEST_DIRS + "DIR/7/8").mkdir();
        new File(PATH_TO_TEST_DIRS + "DIR/7/8/9").mkdir();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY").mkdir();
        new File(PATH_TO_TEST_DIRS + "empty_dir").mkdir();

        new File(PATH_TO_TEST_DIRS + "DIR", "file1.txt").createNewFile();
        new File(PATH_TO_TEST_DIRS + "DIR", "file2.txt").createNewFile();
        new File(PATH_TO_TEST_DIRS + "DIR/1", "file3.txt").createNewFile();
        new File(PATH_TO_TEST_DIRS + "DIR/1", "file4.txt").createNewFile();
        new File(PATH_TO_TEST_DIRS + "DIR/1/2", "file5.txt").createNewFile();
        new File(PATH_TO_TEST_DIRS + "DIR/1/2/3", "file6.txt").createNewFile();
        new File(PATH_TO_TEST_DIRS + "DIR/1/2/3", "file7.txt").createNewFile();
        new File(PATH_TO_TEST_DIRS + "DIR/4/5", "file8.txt").createNewFile();
        new File(PATH_TO_TEST_DIRS + "DIR/7", "file9.txt").createNewFile();
        new File(PATH_TO_TEST_DIRS + "DIR/7/8/9", "file10.txt").createNewFile();
        new File(PATH_TO_TEST_DIRS + "DIR/7/8/9", "file11.txt").createNewFile();
        File file = new File(PATH_TO_TEST_DIRS + "DIR", "fileWithContent.txt");
        file.createNewFile();

        byte[] contentInBytes = "Hello Java! Test File Manager!".getBytes();
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(contentInBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void countDirsInDirectoryTest() {
        assertEquals(9, FileManager.countDirs(PATH_TO_TEST_DIRS + "DIR"));
    }

    @Test
    void countDirsInEmptyDirTest() {
        assertEquals(0, FileManager.countDirs(PATH_TO_TEST_DIRS + "empty_dir"));
    }

    @Test
    void countDirsWithFileAsParameterTest() {
        assertEquals(0, FileManager.countDirs(PATH_TO_TEST_DIRS + "DIR/fileWithContent.txt"));
    }

    @Test
    void countDirsWithNullPathTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.countDirs(null);
        });
    }

    @Test
    void countDirsWithNonexistentPathTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.countDirs("nonexistent/path");
        });
    }

    @Test
    void countFilesInDirectoryTest() {
        assertEquals(12, FileManager.countFiles(PATH_TO_TEST_DIRS + "DIR"));
    }

    @Test
    void countFilesInEmptyDirectoryTest() {
        assertEquals(0, FileManager.countFiles(PATH_TO_TEST_DIRS + "empty_dir"));
    }

    @Test
    void countFilesWithFileAsParameterTest() {
        assertEquals(1, FileManager.countFiles(PATH_TO_TEST_DIRS + "DIR/fileWithContent.txt"));
    }

    @Test
    void countFilesWithNullPathTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.countFiles(null);
        });
    }

    @Test
    void countFilesWithNonexistentPathTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.countFiles(PATH_TO_TEST_DIRS + "nonexistent/path");
        });
    }

    @Test
    void copyFromFolderTest() {
        FileManager.copy(PATH_TO_TEST_DIRS + "DIR", PATH_TO_TEST_DIRS + "DIR_COPY");
        assertEquals(9, FileManager.countDirs(PATH_TO_TEST_DIRS + "DIR"));
        assertEquals(12, FileManager.countFiles(PATH_TO_TEST_DIRS + "DIR"));
        assertEquals(9, FileManager.countDirs(PATH_TO_TEST_DIRS + "DIR_COPY"));
        assertEquals(12, FileManager.countFiles(PATH_TO_TEST_DIRS + "DIR_COPY"));

        assertEquals("Hello Java! Test File Manager!", getContent(new File(PATH_TO_TEST_DIRS + "DIR_COPY", "fileWithContent.txt")));
    }

    @Test
    void copyFileTest() throws IOException {
        File file = new File(PATH_TO_TEST_DIRS + "tempFile.txt");
        file.createNewFile();
        assertTrue(file.exists());

        FileManager.copy(file.getPath(), PATH_TO_TEST_DIRS + "DIR_COPY");

        file.delete();

        file = new File(PATH_TO_TEST_DIRS + "DIR_COPY/tempFile.txt");
        assertTrue(file.exists());
        file.delete();
    }


    @Test
    void copyWithNullParameterFromWhereCopyTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.copy(null, PATH_TO_TEST_DIRS + "DIR_COPY");
        });
    }

    @Test
    void copyWithNullParameterWhereCopyTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.copy(PATH_TO_TEST_DIRS + "DIR", null);
        });
    }

    @Test
    void copyWithNonexistentPathFromWhereCopyTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.copy(PATH_TO_TEST_DIRS + "nonexistent/path", PATH_TO_TEST_DIRS + "DIR_COPY");
        });
    }

    @Test
    void copyWithNonexistentPathWhereCopyTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.copy(PATH_TO_TEST_DIRS + "DIR", PATH_TO_TEST_DIRS + "nonexistent/path");
        });
    }

    @Test
    void copyWithFilePathWhereCopyTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.copy(PATH_TO_TEST_DIRS + "DIR", PATH_TO_TEST_DIRS + "DIR/fileWithContent.txt");
        });
    }

    @Test
    void moveFromFolderTest() {

        assertEquals(9, FileManager.countDirs(PATH_TO_TEST_DIRS + "DIR"));
        assertEquals(12, FileManager.countFiles(PATH_TO_TEST_DIRS + "DIR"));

        FileManager.move(PATH_TO_TEST_DIRS + "DIR", PATH_TO_TEST_DIRS + "DIR_COPY");

        assertEquals(0, FileManager.countDirs(PATH_TO_TEST_DIRS + "DIR"));
        assertEquals(0, FileManager.countFiles(PATH_TO_TEST_DIRS + "DIR"));
        assertEquals(9, FileManager.countDirs(PATH_TO_TEST_DIRS + "DIR_COPY"));
        assertEquals(12, FileManager.countFiles(PATH_TO_TEST_DIRS + "DIR_COPY"));

        assertEquals("Hello Java! Test File Manager!", getContent(new File(PATH_TO_TEST_DIRS + "DIR_COPY/fileWithContent.txt")));
    }

    @Test
    void moveFileTest() throws IOException {
        File file = new File(PATH_TO_TEST_DIRS + "tempFile.txt");
        file.createNewFile();
        assertTrue(file.exists());

        FileManager.move(file.getPath(), PATH_TO_TEST_DIRS + "DIR_COPY");

        assertFalse(file.exists());

        file = new File(PATH_TO_TEST_DIRS + "DIR_COPY/tempFile.txt");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    void moveWithNullParameterFromWhereMoveTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.move(null, PATH_TO_TEST_DIRS + "DIR_COPY");
        });
    }

    @Test
    void moveWithNullParameterWhereMoveTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.move(PATH_TO_TEST_DIRS + "DIR", null);
        });
    }

    @Test
    void moveWithNonexistentPathFromWhereMoveTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.move(PATH_TO_TEST_DIRS + "nonexistent/path", PATH_TO_TEST_DIRS + "DIR_COPY");
        });
    }

    @Test
    void moveWithNonexistentPathWhereMoveTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.move(PATH_TO_TEST_DIRS + "DIR", PATH_TO_TEST_DIRS + "nonexistent/path");
        });
    }

    @Test
    void moveWithFilePathWhereMoveTest() {
        assertThrows(RuntimeException.class, () -> {
            FileManager.move(PATH_TO_TEST_DIRS + "DIR", PATH_TO_TEST_DIRS + "DIR/fileWithContent.txt");
        });
    }

    @AfterEach
    void after() {
        new File(PATH_TO_TEST_DIRS + "DIR/1/2/3", "file6.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/1/2/3", "file7.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/7/8/9", "file10.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/7/8/9", "file11.txt").delete();

        new File(PATH_TO_TEST_DIRS + "DIR_COPY/1/2/3", "file6.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/1/2/3", "file7.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/7/8/9", "file10.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/7/8/9", "file11.txt").delete();

        new File(PATH_TO_TEST_DIRS + "DIR/1/2", "file5.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/4/5", "file8.txt").delete();

        new File(PATH_TO_TEST_DIRS + "DIR_COPY/1/2", "file5.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/4/5", "file8.txt").delete();

        new File(PATH_TO_TEST_DIRS + "DIR/1", "file3.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/1", "file4.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/7", "file9.txt").delete();

        new File(PATH_TO_TEST_DIRS + "DIR_COPY/1", "file3.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/1", "file4.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/7", "file9.txt").delete();

        new File(PATH_TO_TEST_DIRS + "DIR", "file1.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR", "file2.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR", "fileWithContent.txt").delete();

        new File(PATH_TO_TEST_DIRS + "DIR_COPY", "file1.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY", "file2.txt").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY", "fileWithContent.txt").delete();

        new File(PATH_TO_TEST_DIRS + "DIR/1/2/3").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/1/2").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/1").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/4/5/6").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/4/5").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/4").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/7/8/9").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/7/8").delete();
        new File(PATH_TO_TEST_DIRS + "DIR/7").delete();
        new File(PATH_TO_TEST_DIRS + "DIR").delete();

        new File(PATH_TO_TEST_DIRS + "DIR_COPY/1/2/3").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/1/2").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/1").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/4/5/6").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/4/5").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/4").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/7/8/9").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/7/8").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY/7").delete();
        new File(PATH_TO_TEST_DIRS + "DIR_COPY").delete();

        new File(PATH_TO_TEST_DIRS + "empty_dir").delete();
    }

    private String getContent(File file) {
        byte[] buffer = new byte[(int) file.length()];
        StringBuilder stringBuilder = new StringBuilder();
        int count;
        try (InputStream inputStream = new FileInputStream(file)) {
            while ((count = inputStream.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer, 0, count));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}