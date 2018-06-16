package ru.ars.ncedu.task4;

import javax.xml.bind.*;

import java.io.File;
import java.lang.reflect.Type;

import static java.util.Objects.requireNonNull;

public class JaxbWorker {

    public static<T> void serializable(T nameClass, String xmlNameFile) throws JAXBException {
        if (nameClass == null || xmlNameFile == null){
            throw new NullPointerException();
        }
        if (!xmlNameFile.contains(".xml")){
            throw new IllegalArgumentException();
        }
        String pathResources = requireNonNull(nameClass.getClass().getClassLoader().getResource("")).getFile()
                + File.separator + xmlNameFile;
        JAXBContext context = JAXBContext.newInstance(nameClass.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        JAXB.marshal(nameClass, new File(pathResources));
    }

    @SuppressWarnings("unchecked")
    public static<T> T deserializable(T nameClass, String xmlNameFile) throws JAXBException {
        if (nameClass == null || xmlNameFile == null){
            throw new NullPointerException();
        }
        if (!xmlNameFile.contains(".xml")){
            throw new IllegalArgumentException();
        }
            String pathResources = requireNonNull(nameClass.getClass().getClassLoader().getResource("")).getFile()
                    + File.separator + xmlNameFile;
            JAXBContext context = JAXBContext.newInstance(nameClass.getClass());

            Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(new File(pathResources));
    }
}