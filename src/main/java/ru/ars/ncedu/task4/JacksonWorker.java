package ru.ars.ncedu.task4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;

import static java.util.Objects.requireNonNull;

public class JacksonWorker {

    public static <T> void serializable(T nameClass, String jsonNameFile) {
        if (nameClass == null || jsonNameFile == null) {
            throw new NullPointerException();
        }
        if (!jsonNameFile.contains(".json")) {
            throw new IllegalArgumentException();
        }

        String pathResources = requireNonNull(nameClass.getClass().getClassLoader().getResource("")).getFile()
                + File.separator + jsonNameFile;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try (Writer writer = new FileWriter(pathResources)) {
            objectMapper.writeValue(writer, nameClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserializable(T nameClass, String jsonNameFile) {
        if (nameClass == null || jsonNameFile == null) {
            throw new NullPointerException();
        }
        if (!jsonNameFile.contains(".json")) {
            throw new IllegalArgumentException();
        }

        String pathResources = requireNonNull(nameClass.getClass().getClassLoader().getResource("")).getFile()
                + File.separator + jsonNameFile;
        ObjectMapper objectMapper = new ObjectMapper();
        T nameClazz = null;
        try (Reader reader = new FileReader(pathResources)) {
            nameClazz = (T) objectMapper.readValue(reader, nameClass.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nameClazz;
    }
}
