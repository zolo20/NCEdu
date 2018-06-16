package ru.ars.ncedu.task4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static java.util.Objects.requireNonNull;

import java.io.*;
import java.lang.reflect.Type;
import java.util.IllegalFormatCodePointException;
import java.util.IllegalFormatException;

public class GsonWorker {
    public static<T> void serializable(T nameClass, String jsonNameFile) {
        if (nameClass == null || jsonNameFile == null){
            throw new NullPointerException();
        }
        if (!jsonNameFile.contains(".json")){
            throw new IllegalArgumentException();
        }
        String pathResources = requireNonNull(nameClass.getClass().getClassLoader().getResource("")).getFile()
                + File.separator + jsonNameFile;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(Writer writer = new FileWriter(pathResources)) {
            gson.toJson(nameClass,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static<T> T deserializable(T nameClass, String jsonNameFile) {
        if (nameClass == null || jsonNameFile == null){
            throw new NullPointerException();
        }
        if (!jsonNameFile.contains(".json")){
            throw new IllegalArgumentException();
        }
        String pathResources = requireNonNull(nameClass.getClass().getClassLoader().getResource("")).getFile()
                + File.separator + jsonNameFile;
        Gson gson = new GsonBuilder().create();
        T nameClazz = null;
        try(Reader reader = new FileReader(pathResources)) {
            nameClazz = gson.fromJson(reader, (Type) nameClass.getClass());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return nameClazz;
    }
}
