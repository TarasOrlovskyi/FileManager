package orlovskyi;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

class FileManagerTest {

    @BeforeEach
    void before() throws IOException {
        File dir = new File("DIR");
        File file = null;
        dir.mkdir();
        for (int i = 1; i < 10; i++) {
            if (i < 4) {
                dir = new File("DIR", String.valueOf(i));
                file = new File("DIR", "file" + i + ".txt");
            } else if (i < 8) {
                dir = new File("DIR/1/" + i);
                file = new File("DIR/1", "file" + i + ".txt");
            } else {
                dir = new File("DIR/2/" + i);
                file = new File("DIR/2", "file" + i + ".txt");
            }
            dir.mkdir();
            file.createNewFile();
        }
        dir = new File("DIR_COPY");
        dir.mkdir();
        String contentForFile = "Never give up!!!";
        byte[] byteContent = contentForFile.getBytes();
        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(byteContent);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    void countDirsTest() {
        assertEquals(9, FileManager.countDirs("DIR"));
    }

    @Test
    void countFilesTest() {
        assertEquals(9, FileManager.countFiles("DIR"));
    }

    @Test
    void copyTest() {
        FileManager.copy("DIR", "DIR_COPY");
        assertEquals(9, FileManager.countDirs("DIR"));
        assertEquals(9, FileManager.countFiles("DIR"));
        assertEquals(9, FileManager.countDirs("DIR_COPY"));
        assertEquals(9, FileManager.countFiles("DIR_COPY"));

        assertEquals("Never give up!!!", getContent(new File("DIR_COPY/2/file9.txt")));

        //? why appear NullPointerException when i try hand over a file

//        File file = new File("tempFile.txt");
//        file.createNewFile();
//        FileManager.copy(file.getPath(), "DIR_COPY");
//        assertTrue(file.exists());
//        file.delete();
//        file = new File("DIR_COPY/tempFile.txt");
//        assertTrue(file.exists());
//        file.delete();

    }

    @Test
    void moveTest() {
        assertEquals(9, FileManager.countDirs("DIR"));
        assertEquals(9, FileManager.countFiles("DIR"));

        FileManager.move("DIR", "DIR_COPY");

        assertEquals(0, FileManager.countDirs("DIR"));
        assertEquals(0, FileManager.countFiles("DIR"));
        assertEquals(9, FileManager.countDirs("DIR_COPY"));
        assertEquals(9, FileManager.countFiles("DIR_COPY"));

        assertEquals("Never give up!!!", getContent(new File("DIR_COPY/2/file9.txt")));
    }

    @AfterEach
    void after() {
        File path;
        File pathCopy;
        for (int i = 1; i < 10; i++) {
            if (i<=3) {
                path = new File("DIR", "file" + i + ".txt");
                pathCopy = new File("DIR_COPY", "file" + i + ".txt");
            } else if (i<=7){
                path = new File("DIR/1", "file" + i + ".txt");
                pathCopy = new File("DIR_COPY/1","file" + i + ".txt");
            } else {
                path = new File("DIR/2", "file" + i + ".txt");
                pathCopy = new File("DIR_COPY/2", "file" + i + ".txt");
            }
            path.delete();
            pathCopy.delete();
        }
        for (int i = 9; i > 0; i--) {
            if (i>=8){
                path = new File("DIR/2/" + i);
                pathCopy = new File("DIR_COPY/2/" + i);
            } else if (i>=4){
                path = new File("DIR/1/" + i);
                pathCopy = new File("DIR_COPY/1/" + i);
            } else {
                path = new File("DIR/" + i);
                pathCopy = new File("DIR_COPY/" + i);
            }
            path.delete();
            pathCopy.delete();
        }
        path = new File("DIR");
        path.delete();
        pathCopy = new File("DIR_COPY");
        pathCopy.delete();
    }

    private String getContent(File file){
        byte[] buffer = new byte[(int) file.length()];
        StringBuilder stringBuilder = new StringBuilder();
        int count;
        try (InputStream inputStream = new FileInputStream(file)){
            while ((count=inputStream.read(buffer))!=-1){
                stringBuilder.append(new String(buffer, 0, count));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}