package org.xialei.convertor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class FileConvertors {
    public static Optional<List<FileConvertor>> loadConvertors(String filename) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        File file = new File(filename);
        if (!file.isFile()) {
            return Optional.empty();
        }
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        }
        String classNameData = properties.getProperty("convertors");
        if (classNameData == null || classNameData.isEmpty()) {
            return Optional.empty();
        }
        String[] classNames = classNameData.split(",");
        List<FileConvertor> convertors = new ArrayList<>();
        for (String className : classNames) {
            Class<?> clazz = Class.forName(className);
            Object convertor = clazz.newInstance();
            if (convertor instanceof FileConvertor) {
                convertors.add((FileConvertor) convertor);
            }
        }
        return Optional.of(convertors);
    }
}
