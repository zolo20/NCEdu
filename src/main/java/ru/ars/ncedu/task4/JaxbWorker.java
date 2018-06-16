package ru.ars.ncedu.task4;

import javax.xml.bind.*;

import java.io.File;

import static java.util.Objects.requireNonNull;

public class JaxbWorker {

    public static<T> void serializable(T nameClass) throws JAXBException {
        String pathResources = requireNonNull(nameClass.getClass().getClassLoader().getResource("")).getFile()
                + File.separator + "JAXB.xml";
        JAXBContext context = JAXBContext.newInstance(nameClass.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        JAXB.marshal(nameClass, new File(pathResources));
    }

    @SuppressWarnings("unchecked")
    public static<T> T deserializable(T nameClass) throws JAXBException {
            String pathResources = requireNonNull(nameClass.getClass().getClassLoader().getResource("")).getFile()
                    + File.separator + "JAXB.xml";
            JAXBContext context = JAXBContext.newInstance(nameClass.getClass());

            Unmarshaller unmarshaller = context.createUnmarshaller();
            T nameClazz = (T) unmarshaller.unmarshal(new File(pathResources));
            return nameClass;

    }
}