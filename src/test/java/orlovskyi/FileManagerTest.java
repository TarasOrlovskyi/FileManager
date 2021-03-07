package orlovskyi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

class FileManagerTest {

    @BeforeEach
    void before() throws IOException {
        new File("DIR").mkdir();
        new File("DIR/1").mkdir();
        new File("DIR/1/2").mkdir();
        new File("DIR/1/2/3").mkdir();
        new File("DIR/4").mkdir();
        new File("DIR/4/5").mkdir();
        new File("DIR/4/5/6").mkdir();
        new File("DIR/7").mkdir();
        new File("DIR/7/8").mkdir();
        new File("DIR/7/8/9").mkdir();
        new File("DIR_COPY").mkdir();

        new File("DIR", "file1.txt").createNewFile();
        new File("DIR", "file2.txt").createNewFile();
        new File("DIR/1", "file3.txt").createNewFile();
        new File("DIR/1", "file4.txt").createNewFile();
        new File("DIR/1/2", "file5.txt").createNewFile();
        new File("DIR/1/2/3", "file6.txt").createNewFile();
        new File("DIR/1/2/3", "file7.txt").createNewFile();
        new File("DIR/4/5", "file8.txt").createNewFile();
        new File("DIR/7", "file9.txt").createNewFile();
        new File("DIR/7/8/9", "file10.txt").createNewFile();
        new File("DIR/7/8/9", "file11.txt").createNewFile();
        File file = new File("DIR", "fileWithContent.txt");
        file.createNewFile();

        byte[] contentInBytes = "Hello Java! Test File Manager!".getBytes();
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(contentInBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void countDirsTest() {
        assertEquals(17535, FileManager.countDirs("C:/Windows"));
    }

    @Test
    void countFilesTest() {
        assertEquals(82049, FileManager.countFiles("C:/Windows"));
    }

    @Test
    void copyTest() throws IOException {
        FileManager.copy("DIR", "DIR_COPY");
        assertEquals(9, FileManager.countDirs("DIR"));
        assertEquals(12, FileManager.countFiles("DIR"));
        assertEquals(9, FileManager.countDirs("DIR_COPY"));
        assertEquals(12, FileManager.countFiles("DIR_COPY"));

        assertEquals("Hello Java! Test File Manager!", getContent(new File("DIR_COPY", "fileWithContent.txt")));

        File file = new File("tempFile.txt");
        file.createNewFile();
        FileManager.copy(file.getPath(), "DIR_COPY");
        assertTrue(file.exists());
        file.delete();
        file = new File("DIR_COPY/tempFile.txt");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    void moveTest() throws IOException {

        assertEquals(9, FileManager.countDirs("DIR"));
        assertEquals(12, FileManager.countFiles("DIR"));

        FileManager.move("DIR", "DIR_COPY");

        assertEquals(0, FileManager.countDirs("DIR"));
        assertEquals(0, FileManager.countFiles("DIR"));
        assertEquals(9, FileManager.countDirs("DIR_COPY"));
        assertEquals(12, FileManager.countFiles("DIR_COPY"));

        assertEquals("Hello Java! Test File Manager!", getContent(new File("DIR_COPY/fileWithContent.txt")));

        File file = new File("tempFile.txt");
        file.createNewFile();
        assertTrue(file.exists());
        FileManager.move(file.getPath(), "DIR_COPY");
        assertFalse(file.exists());
        file = new File("DIR_COPY/tempFile.txt");
        assertTrue(file.exists());
        file.delete();
    }

    @AfterEach
    void after() {
        new File("DIR/1/2/3", "file6.txt").delete();
        new File("DIR/1/2/3", "file7.txt").delete();
        new File("DIR/7/8/9", "file10.txt").delete();
        new File("DIR/7/8/9", "file11.txt").delete();

        new File("DIR_COPY/1/2/3", "file6.txt").delete();
        new File("DIR_COPY/1/2/3", "file7.txt").delete();
        new File("DIR_COPY/7/8/9", "file10.txt").delete();
        new File("DIR_COPY/7/8/9", "file11.txt").delete();

        new File("DIR/1/2", "file5.txt").delete();
        new File("DIR/4/5", "file8.txt").delete();

        new File("DIR_COPY/1/2", "file5.txt").delete();
        new File("DIR_COPY/4/5", "file8.txt").delete();

        new File("DIR/1", "file3.txt").delete();
        new File("DIR/1", "file4.txt").delete();
        new File("DIR/7", "file9.txt").delete();

        new File("DIR_COPY/1", "file3.txt").delete();
        new File("DIR_COPY/1", "file4.txt").delete();
        new File("DIR_COPY/7", "file9.txt").delete();

        new File("DIR", "file1.txt").delete();
        new File("DIR", "file2.txt").delete();
        new File("DIR", "fileWithContent.txt").delete();

        new File("DIR_COPY", "file1.txt").delete();
        new File("DIR_COPY", "file2.txt").delete();
        new File("DIR_COPY", "fileWithContent.txt").delete();

        new File("DIR/1/2/3").delete();
        new File("DIR/1/2").delete();
        new File("DIR/1").delete();
        new File("DIR/4/5/6").delete();
        new File("DIR/4/5").delete();
        new File("DIR/4").delete();
        new File("DIR/7/8/9").delete();
        new File("DIR/7/8").delete();
        new File("DIR/7").delete();
        new File("DIR").delete();

        new File("DIR_COPY/1/2/3").delete();
        new File("DIR_COPY/1/2").delete();
        new File("DIR_COPY/1").delete();
        new File("DIR_COPY/4/5/6").delete();
        new File("DIR_COPY/4/5").delete();
        new File("DIR_COPY/4").delete();
        new File("DIR_COPY/7/8/9").delete();
        new File("DIR_COPY/7/8").delete();
        new File("DIR_COPY/7").delete();
        new File("DIR_COPY").delete();

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