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
        System.out.println(importPackage(clazz) + "\n");
        System.out.println(stateClass(clazz) + "\n");
        System.out.println(fields(clazz));
        System.out.println(constructors(clazz));
        System.out.println(methods(clazz));
        System.out.print("}");
    }

    /**
     * @param clazz
     * the class of the type to cast this class object
     * @return
     * a string {@code package} name package, which contains a class
     */
    public static String packageName(Class clazz) {
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
    public static String importPackage(Class clazz) {
        String importsPkg = "";
        Set<String> imports = new HashSet<>();
        Class[] interfaces = clazz.getInterfaces();
        for (Class anInterface : interfaces) {
            if (!anInterface.getName().matches("java\\.lang\\.[A-Z].*")) {
                imports.add(anInterface.getName());
            }
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.getType().getName().matches("java\\.lang\\.[A-Z].*") &&
                    !field.getType().isArray() && !field.getType().isPrimitive()) {
                imports.add(field.getType().getName());
            }
        }

        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor c : constructors) {
            Class[] paramType = c.getParameterTypes();
            for (Class param : paramType) {
                if (!param.getName().matches("java\\.lang\\.[A-Z].*") &&
                        !param.isArray() && !param.isPrimitive()) {
                    imports.add(param.getName());
                }
            }

        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
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
                System.out.println(m.getReturnType().getName());
                imports.add(m.getReturnType().getName());
            }

            Class[] paramType = m.getParameterTypes();
            for (Class param : paramType) {
                if (!param.getName().matches("java\\.lang\\.[A-Z].*") &&
                        !param.isArray() && !param.isPrimitive()) {
                    imports.add(param.getName());
                }
            }
        }
        for (String anImport : imports) {
            importsPkg += "import " + anImport + ";" + "\n";
        }

        return importsPkg;
    }

    /**
     * @param clazz
     * the class of the type to cast this class object.
     * @return
     * a string about the state of class(Modifier, inheritable classes, implementable interfaces)
     */
    public static String stateClass(Class clazz) {
        String Modifiers = getModifiers(clazz.getModifiers());
        String nameClass = clazz.getSimpleName();
        String superClass = clazz.getSuperclass().getSimpleName().equals("Object") ? "" : " extends" + " " + clazz.getSuperclass().getSimpleName();

        Class[] interfaces = clazz.getInterfaces();
        String nameInterface = "";
        for (int i = 0; i < interfaces.length; i++) {
            nameInterface += " " + interfaces[i].getSimpleName();
            if (interfaces.length > 1 && i == interfaces.length - 2) {
                nameInterface += ",";
            }
        }
        String interfaceImpl = nameInterface.equals("") ? "" : "implements" + nameInterface;
        return Modifiers + "class" + " " + nameClass + superClass + " " + interfaceImpl + " " + "{";
    }

    /**
     * @param clazz
     * the class of the type to cast this class object.
     * @return
     * a string with modifiers, types, names fields.
     */
    public static String fields(Class clazz) {
        String nameFields = "";
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            nameFields += "\t" + getModifiers(field.getModifiers()) +field.getType().getSimpleName() +
                    " " + field.getName() + ";" + "\n";
        }
        return nameFields;
    }

    /**
     * @param clazz
     * the class of the type to cast this class object.
     * @return
     * a string modifier it access a constructor and parameters a constructor.
     */
    public static String constructors(Class clazz) {
        String constructor = "";
        Constructor[] constructors = clazz.getDeclaredConstructors();
        for (Constructor c : constructors) {
            constructor += "\t" + getModifiers(c.getModifiers()) + clazz.getSimpleName() +
                    "("+ getParameters(c.getParameters()) + ")" + " {  }" + "\n";
        }
        return constructor;
    }

    /**
     * @param clazz
     * the class of the type to cast this class object.
     * @return
     * a string modifiers a methods, returned type, and parameters.
     */
    public static String methods(Class clazz) {
        String nameMethods = "";
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            Annotation[] annotations = m.getAnnotations();
            for (Annotation annotation : annotations) {
                nameMethods += "\t" + "@" + annotation.annotationType().getSimpleName() + "\n";
            }
            nameMethods += "\t" +getModifiers(m.getModifiers()) + m.getReturnType().getSimpleName() + " " + m.getName() +
                    "(" + getParameters(m.getParameters()) + ") {  }" + "\n";
        }
        return nameMethods;
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
        String signature = "";
        for (int i = 0; i < parameters.length; i++) {
            signature += parameters[i].getType().getSimpleName() + " " + parameters[i].getName();
            if (parameters.length > 1 && i == parameters.length - 2) {
                signature += ", ";
            }
        }
        return signature;
    }
}