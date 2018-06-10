package ru.ars.ncedu.task3;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class OutputCode {

    /**
     * Outputs the class code on the console.
     *
     * @param clazz
     * the class of the type to cast this class object
     */
    public static void outputConsoleCode(Class clazz) {
        System.out.println(packageName(clazz) + "\n");
        System.out.println(importPackage(clazz));
        System.out.println(stateClass(clazz));
        System.out.println(fields(clazz) + "\n");
        System.out.println(constructors(clazz));
        System.out.print(methods(clazz));
        System.out.print("}");
    }

    /**
     * @param clazz
     * the class of the type to cast this class object
     * @return
     * a string {@code package} name package, which contains a class
     */
    private static String packageName(Class clazz) {
        Package pkg = clazz.getPackage();
        return pkg.toString() + ";";
    }

    /**
     * Collects names of packages, at implemented interfaces, at types field,
     * at parameters constructors, at parameters and returned types methods and at annotation,
     * which excluded at package {@code java.lang}.
     *
     * @param clazz
     * the class of the type to cast this class object.
     * @return
     * a string {@code import} names of imported packages.
     */
    private static String importPackage(Class clazz) {
        StringBuilder importsPkg = new StringBuilder();
        Set<String> imports = new HashSet<>();
        for (Class anInterface : clazz.getInterfaces()) {
            if (!anInterface.getName().matches("java\\.lang\\.[A-Z].*")) {
                imports.add(anInterface.getName());
            }
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.getType().getName().matches("java\\.lang\\.[A-Z].*") &&
                    !field.getType().isArray() && !field.getType().isPrimitive()) {
                imports.add(field.getType().getName());
            }
        }

        for (Constructor c : clazz.getDeclaredConstructors()) {
            Class[] paramType = c.getParameterTypes();
            for (Class param : paramType) {
                if (!param.getName().matches("java\\.lang\\.[A-Z].*") &&
                        !param.isArray() && !param.isPrimitive()) {
                    imports.add(param.getName());
                }
            }

        }

        for (Method m : clazz.getDeclaredMethods()) {
            Annotation[] annotations = m.getAnnotations();
            for (Annotation annotation : annotations) {
                if (!annotation.annotationType().getName().matches("java\\.lang\\.[A-Z].*") &&
                        !annotation.annotationType().isArray() && !annotation.annotationType().isPrimitive()) {
                    imports.add(annotation.annotationType().getName());
                }
            }

            if (!m.getReturnType().getName().matches("java\\.lang\\.[A-Z].*") &&
                    !m.getReturnType().getName().matches("void") &&
                    !m.getReturnType().isArray() && !m.getReturnType().isPrimitive()) {
                imports.add(m.getReturnType().getName());
            }

            for (Class param : m.getParameterTypes()) {
                if (!param.getName().matches("java\\.lang\\.[A-Z].*") &&
                        !param.isArray() && !param.isPrimitive()) {
                    imports.add(param.getName());
                }
            }
        }
        for (String anImport : imports) {
            importsPkg.append("import ").append(anImport).append(";").append("\n");
        }

        return importsPkg.toString();
    }

    /**
     * @param clazz
     * the class of the type to cast this class object.
     * @return
     * a string about the state of class(Modifier, inheritable classes, implementable interfaces)
     */
    private static String stateClass(Class clazz) {
        StringBuilder modifiers = new StringBuilder(getModifiers(clazz.getModifiers()));
        StringBuilder nameClass = new StringBuilder(clazz.getSimpleName());
        String superClass = clazz.getSuperclass() == null ? "" :
                clazz.getSuperclass().getSimpleName().equals("Object") ? "" :
                        " extends" + " " + clazz.getSuperclass().getSimpleName();
        StringBuilder superClassBuilder = new StringBuilder(superClass);

        Class[] interfaces = clazz.getInterfaces();
        StringBuilder nameInterface = new StringBuilder();
        for (int i = 0; i < interfaces.length; i++) {
            nameInterface.append(" ").append(interfaces[i].getSimpleName());
            if (interfaces.length > 1 && i == interfaces.length - 2) {
                nameInterface.append(",");
            }
        }
        String interfaceImpl = nameInterface.toString().equals("") ? "" : "implements" + nameInterface;
        StringBuilder interfaceImplBuilder = new StringBuilder(interfaceImpl);

        return modifiers.append("class").append(" ").append(nameClass).append(superClassBuilder).append(" ").
                append(interfaceImplBuilder).append(" ").append("{").toString();
    }

    /**
     * @param clazz
     * the class of the type to cast this class object.
     * @return
     * a string with modifiers, types, names fields.
     */
    private static String fields(Class clazz) {
        StringBuilder nameFields = new StringBuilder();
        for (Field field : clazz.getDeclaredFields()) {
            nameFields.append("\t").append(getModifiers(field.getModifiers())).
                    append(field.getType().getSimpleName()).append(" ").append(field.getName()).append(";").append("\n");
        }
        return nameFields.toString();
    }

    /**
     * @param clazz
     * the class of the type to cast this class object.
     * @return
     * a string modifier it access a constructor and parameters a constructor.
     */
    private static String constructors(Class clazz) {
        StringBuilder constructor = new StringBuilder();
        for (Constructor c : clazz.getDeclaredConstructors()) {
            constructor.append("\t").append(getModifiers(c.getModifiers())).append(clazz.getSimpleName()).
                    append("(").append(getParameters(c.getParameters())).append(")").append(" {  }").append("\n").append("\n");
        }
        return constructor.toString();
    }

    /**
     * @param clazz
     * the class of the type to cast this class object.
     * @return
     * a string modifiers a methods, returned type, and parameters.
     */
    private static String methods(Class clazz) {
        StringBuilder nameMethods = new StringBuilder();
        for (Method m : clazz.getDeclaredMethods()) {
            Annotation[] annotations = m.getAnnotations();
            for (Annotation annotation : annotations) {
                nameMethods.append("\t" + "@").append(annotation.annotationType().getSimpleName()).append("\n");
            }
            nameMethods.append("\t").append(getModifiers(m.getModifiers())).
                    append(m.getReturnType().getSimpleName()).append(" ").append(m.getName()).
                    append("(").append(getParameters(m.getParameters())).append(") {  }").append("\n").append("\n");
        }
        return nameMethods.toString();
    }

    /**
     * @param valueMod
     * a set of modifiers
     * @return
     * a string modifiers
     */
    private static String getModifiers(int valueMod) {
        String modifiers = "";
        if (Modifier.isPublic(valueMod)) modifiers += "public ";
        if (Modifier.isProtected(valueMod)) modifiers += "protected ";
        if (Modifier.isPrivate(valueMod)) modifiers += "private ";
        if (Modifier.isStatic(valueMod)) modifiers += "static ";
        if (Modifier.isAbstract(valueMod)) modifiers += "abstract ";
        if (Modifier.isFinal(valueMod)) modifiers += "final ";
        if (Modifier.isTransient(valueMod)) modifiers += "transient ";
        if (Modifier.isVolatile(valueMod)) modifiers += "volatile ";
        return modifiers;
    }

    /**
     * @param parameters
     * array parameters a methods, constructor.
     * @return
     * a string types a parameters
     */
    private static String getParameters(Parameter[] parameters) {
        StringBuilder signature = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            signature.append(parameters[i].getType().getSimpleName()).append(" ").append(parameters[i].getName());
            if (parameters.length > 1 && i == parameters.length - 2) {
                signature.append(", ");
            }
        }
        return signature.toString();
    }
}