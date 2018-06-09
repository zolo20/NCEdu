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

    private final String pathResourceSource =
            requireNonNull(getClass().getClassLoader().getResource("testDir")).getFile() + File.separator + "123";
    private final String pathResourceTarget =
            requireNonNull(getClass().getClassLoader().getResource("testDir")).getFile() + File.separator + "1234";
    private final String regex = ".*\\.xml";



}