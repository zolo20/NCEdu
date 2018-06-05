package ru.ars.ncedu.task2;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class FilesDirectoryTest {

    final String pathResourceSource =
            requireNonNull(getClass().getClassLoader().getResource("testDir")).getFile() + File.separator + "123";
    final String pathResourceTarget =
            requireNonNull(getClass().getClassLoader().getResource("testDir")).getFile() + File.separator + "1234";
    final String regex = ".*\\.xml";


    @Test
    public void copyTest() throws IOException {
        FilesDirectory.copy(pathResourceSource, pathResourceTarget);
        List<String> source = Arrays.asList((requireNonNull(new File(pathResourceSource).list())));
        List<String> target = Arrays.asList(requireNonNull(new File(pathResourceTarget).list()));

        int count = 0;
        for (String nameFileTarget : target) {
            if (source.contains(nameFileTarget)) {
                count++;
                Files.delete(new File(pathResourceTarget + File.separator + nameFileTarget).toPath());
            }
        }
        Assert.assertEquals(count, source.size());
    }

    @Test
    public void moveTest() throws IOException {
        List<String> source = Arrays.asList(requireNonNull(new File(pathResourceSource).list()));
        FilesDirectory.move(pathResourceSource, pathResourceTarget);
        List<String> target = Arrays.asList(requireNonNull(new File(pathResourceTarget).list()));

        int count = 0;
        if (FilesDirectory.getAllNameFileDirectory(pathResourceSource).isEmpty()) {
            for (String nameFileSource : source) {
                if (target.contains(nameFileSource)) {
                    count++;
                    Files.move(new File(pathResourceTarget + File.separator + nameFileSource).toPath(),
                            new File(pathResourceSource + File.separator + nameFileSource).toPath(),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        Assert.assertEquals(count, source.size());
    }

    @Test
    public void getNameFileDirectoryTest(){
        String[] nameFiles = new File(pathResourceSource).list();
        int count = 0;
        for (String nameFile : requireNonNull(nameFiles)) {
            if (nameFile.matches(regex)) {
                count++;
            }
        }
        Assert.assertEquals(count, FilesDirectory.getNameFileDirectory(pathResourceSource, regex).size());
    }

    @Test(expected = NullPointerException.class)
    public void copyTestNullPointerException() {
        try {
            FilesDirectory.copy(null, pathResourceTarget);
        }catch (NullPointerException e){
            try {
                FilesDirectory.copy(pathResourceSource, null);
            }catch (NullPointerException e1) {
                FilesDirectory.copy(null, null);
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void moveTestNullPointerException() {
        try {
            FilesDirectory.move(null, pathResourceTarget);
        }catch (NullPointerException e){
            try {
                FilesDirectory.move(pathResourceSource, null);
            }catch (NullPointerException e1) {
                FilesDirectory.move(null, null);
            }
        }
    }

    @Test(expected = NullPointerException.class)
    public void getNameFileDirectoryTestNullPointerException() {
        try {
            FilesDirectory.getNameFileDirectory(null, regex);
        }catch (NullPointerException e){
            try {
                FilesDirectory.getNameFileDirectory(pathResourceSource, null);
            }catch (NullPointerException e1) {
                FilesDirectory.getNameFileDirectory(null, null);
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void copyTestIllegalArgumentException() {
        try {
            FilesDirectory.copy(pathResourceSource + File.separator + "12", pathResourceTarget);
        }catch (IllegalArgumentException e){
            try {
                FilesDirectory.copy(pathResourceSource, pathResourceTarget + File.separator + "12");
            }catch (IllegalArgumentException e1) {
                FilesDirectory.copy(pathResourceSource + File.separator + "12",
                        pathResourceTarget + File.separator + "12");
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void moveTestIllegalArgumentException() {
        try {
            FilesDirectory.move(pathResourceSource + File.separator + "12", pathResourceTarget);
        }catch (IllegalArgumentException e){
            try {
                FilesDirectory.move(pathResourceSource, pathResourceTarget + File.separator + "12");
            }catch (IllegalArgumentException e1) {
                FilesDirectory.move(pathResourceSource + File.separator + "12",
                        pathResourceTarget + File.separator + "12");
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNameFileDirectoryTestIllegalArgumentException() {
        FilesDirectory.getNameFileDirectory(pathResourceSource + File.separator + "12", regex);
    }
}