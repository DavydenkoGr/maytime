package org.mipt.maytime.loaders;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import org.mipt.maytime.annotations.Component;
import org.mipt.maytime.annotations.Configuration;
import org.mipt.maytime.exeptions.LoadFileException;

/**
 * Util which find all annotated classes in specified package
 *
 * @author Davydenko Grigorii
 * @author Vronskii Alexander
 */
public final class ComponentLoader {
    private static ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    /**
     * Empty ComponentLoader constructor
     */
    private ComponentLoader() {
    }

    /**
     * Method finds and saves names of all classes in the package
     * @param source name of the package controlled by context
     * @return list of class names
     * @throws LoadFileException if there is a problem with get classes name from specified packages
     */
    private static List<String> loadFileNames(String source) {
        List<String> fileNameList = new ArrayList<>();

        // Стек для пакетов
        Stack<String> nestedPackages = new Stack<>();
        nestedPackages.push(source);

        // Обход по исходному пакету с добавлением в стек
        while (nestedPackages.size() != 0) {
            // Получаем enumeration object, проходкой по которому заберем все файлы
            // Необходимо заменить "." на "/", так как этого требует getResources
            String currentPackage = nestedPackages.pop();
            try {
                Enumeration<URL> resources = classLoader.getResources(currentPackage.replace('.', '/'));

                while (resources.hasMoreElements()) {
                    File resourcesFile = new File(resources.nextElement().toURI());

                    for (File classFileOrPackage : Objects.requireNonNull(resourcesFile.listFiles())) {
                        // Если это вложенный пакет, добавим его в стек
                        if (classFileOrPackage.isDirectory()) {
                            nestedPackages.push(currentPackage + "." + classFileOrPackage.getName());
                            continue;
                        }

                        fileNameList.add(currentPackage + "." + classFileOrPackage.getName());
                    }
                }
            } catch (IOException | URISyntaxException exception) {
                throw new LoadFileException("Cannot load files from specified packages");
            }
        }

        return fileNameList;
    }

    /**
     * Method creates class instances of all annotated objects
     * @param source name of the package controlled by context
     * @return list of class instances
     * @throws LoadFileException if there is a problem with class creation
     */
    public static List<Class<?>> load(String source) {
        List<Class<?>> components = new ArrayList<>();
        List<String> fileNameList = loadFileNames(source);

        // Поиск по всем файлам в пакете
        for (String fileName: fileNameList) {
            // Является ли имя файла именем класса
            if (fileName.endsWith(".class")) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                
                try {
                    Class<?> classObject = Class.forName(className);
                    // Помечен ли класс аннотацией @Component
                    if (classObject.isAnnotationPresent(Component.class) 
                        || classObject.isAnnotationPresent(Configuration.class)) {
                        components.add(classObject);
                    }
                } catch (ClassNotFoundException exception) {
                    throw new LoadFileException("Cannot create class by name in package " + source);
                }
            }
        }

        return components;
    }
}
