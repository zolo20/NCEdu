package ru.ars.ncedu.task2;

import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class FilesDirectoryTest {

    private static final String pathResource =
            requireNonNull(FilesDirectoryTest.class.getClassLoader().getResource("")).getFile();
    private final String pathResourceDir1 = pathResource + File.separator + "Tests" + File.separator + "Dir1";
    private final String pathResourceDir2 = pathResource + File.separator + "Tests" + File.separator + "Dir2";
    private final String regex = ".*\\.xml";

    @BeforeClass
    public static void generateFileBeforeClass() throws IOException {
        Files.createDirectories(new File(pathResource + File.separator + "Tests" + File.separator + "Dir1").toPath());
        Files.createFile(new File(pathResource + File.separator + "Tests" +
                File.separator + "Dir1" + File.separator + "xmlTestDir1.xml").toPath());
        Files.createFile(new File(pathResource + File.separator + "Tests" +
                File.separator + "Dir1" + File.separator + "txtTestDir1.txt").toPath());

        Files.createDirectories(new File(pathResource + File.separator + "Tests" + File.separator + "Dir2").toPath());
        Files.createFile(new File(pathResource + File.separator + "Tests" +
                File.separator + "Dir2" + File.separator + "xmlTestDir2.xml").toPath());
        Files.createFile(new File(pathResource + File.separator + "Tests" +
                File.separator + "Dir2" + File.separator + "txtTestDir2.txt").toPath());

    }

    @Test
    public void copyAllTest() throws IOException {
        FilesDirectory.copyAll(pathResourceDir1, pathResourceDir2);

        List<String> source = Arrays.asList(requireNonNull(new File(pathResourceDir1).list()));
        List<String> target = Arrays.asList(requireNonNull(new File(pathResourceDir2).list()));
        int count = 0;
        for (String nameFileTarget : target) {
            if (source.contains(nameFileTarget)) {
                count++;
                Files.delete(new File(pathResourceDir2 + File.separator + nameFileTarget).toPath());
            }
        }
        Assert.assertEquals(count, source.size());
    }

    @Test
    public void moveAllTest() throws IOException {
        List<String> source = Arrays.asList(requireNonNull(new File(pathResourceDir1).list()));
        FilesDirectory.moveAll(pathResourceDir1, pathResourceDir2);
        List<String> target = Arrays.asList(requireNonNull(new File(pathResourceDir2).list()));

        int count = 0;
        if (FilesDirectory.getAllNameFileDirectory(pathResourceDir1).isEmpty()) {
            for (String nameFileSource : source) {
                if (target.contains(nameFileSource)) {
                    count++;
                    Files.move(new File(pathResourceDir2 + File.separator + nameFileSource).toPath(),
                            new File(pathResourceDir1 + File.separator + nameFileSource).toPath());
                }
            }
        }
        Assert.assertEquals(count, source.size());
    }

    @Test
    public void getNameFileDirectoryTest() {
        File[] files = new File(pathResourceDir1).listFiles();
        List<String> nameFiles = new LinkedList<>();
        if (files != null) {
            for (File file : files) {
                if (file.getName().matches(regex)) {
                    nameFiles.add(file.getAbsolutePath());
                }
            }
        }
        Assert.assertArrayEquals(nameFiles.toArray(), FilesDirectory.getNameFileDirectory(pathResourceDir1, regex).toArray());
    }

    @Test(expected = NullPointerException.class)
    public void copyTestNullPointerException() throws IOException {
        try {
            FilesDirectory.copy(null, pathResourceDir2, regex);
        } catch (NullPointerException e) {
            try {
                FilesDirectory.copy(pathResourceDir1, null, regex);
            } catch (NullPointerException e1) {
                FilesDirectory.copy(pathResourceDir1, pathResourceDir2, null);
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void moveTestNullPointerException() throws IOException {
        try {
            FilesDirectory.move(null, pathResourceDir2, regex);
        } catch (NullPointerException e) {
            try {
                FilesDirectory.move(pathResourceDir1, null, regex);
            } catch (NullPointerException e1) {
                FilesDirectory.move(pathResourceDir1, pathResourceDir2, null);
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void getNameFileDirectoryTestNullPointerException() {
        try {
            FilesDirectory.getNameFileDirectory(null, regex);
        } catch (NullPointerException e) {
            try {
                FilesDirectory.getNameFileDirectory(pathResourceDir1, null);
            } catch (NullPointerException e1) {
                FilesDirectory.getNameFileDirectory(null, null);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyTestIllegalArgumentException() throws IOException {
        try {
            FilesDirectory.copyAll(pathResourceDir1 + File.separator + "12", pathResourceDir2);
        } catch (IllegalArgumentException e) {
            try {
                FilesDirectory.copyAll(pathResourceDir1, pathResourceDir2 + File.separator + "12");
            } catch (IllegalArgumentException e1) {
                FilesDirectory.copyAll(pathResourceDir1 + File.separator + "12",
                        pathResourceDir2 + File.separator + "12");
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveTestIllegalArgumentException() throws IOException {
        try {
            FilesDirectory.moveAll(pathResourceDir1 + File.separator + "12", pathResourceDir2);
        } catch (IllegalArgumentException e) {
            try {
                FilesDirectory.moveAll(pathResourceDir1, pathResourceDir2 + File.separator + "12");
            } catch (IllegalArgumentException e1) {
                FilesDirectory.moveAll(pathResourceDir1 + File.separator + "12",
                        pathResourceDir2 + File.separator + "12");
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNameFileDirectoryTestIllegalArgumentException() {
        FilesDirectory.getNameFileDirectory(pathResourceDir1 + File.separator + "12", regex);
    }

    @AfterClass
    public static void deleteFileAfterClass() throws IOException {
        FilesDirectory.deleteDirectory(pathResource + File.separator + "Tests");
    }


}