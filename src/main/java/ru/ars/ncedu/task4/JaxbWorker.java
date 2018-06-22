package ru.ars.ncedu.task4;

import javax.xml.bind.*;

import java.io.File;

import static java.util.Objects.requireNonNull;

public class JaxbWorker {

    public static <T> void serializable(T nameClass, String xmlNameFile) throws JAXBException {
        if (nameClass == null || xmlNameFile == null) {
            throw new NullPointerException();
        }
        if (!xmlNameFile.contains(".xml")) {
            throw new IllegalArgumentException();
        }

        String pathResources = requireNonNull(nameClass.getClass().getClassLoader().getResource("")).getFile() +
                File.separator + xmlNameFile;
        JAXBContext context = JAXBContext.newInstance(nameClass.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(nameClass, new File(pathResources));
    }

    public static <T> T deserializable(Class<T> clazz, String xmlNameFile) {
        if (clazz == null || xmlNameFile == null) {
            throw new NullPointerException();
        }
        if (!xmlNameFile.contains(".xml")) {
            throw new IllegalArgumentException();
        }

        String pathResources = requireNonNull(clazz.getClassLoader().getResource("")).getFile() +
                File.separator + xmlNameFile;
        return JAXB.unmarshal(new File(pathResources), clazz);
    }
}