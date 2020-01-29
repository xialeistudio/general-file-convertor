package org.xialei.convertor;

import org.xialei.convertor.ui.Initializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class FileConvertors {
    private static FileConvertors shared;

    private Properties properties = new Properties();

    private FileConvertors() {
        File file = new File("config.properties");
        if (!file.isFile()) {
            return;
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileConvertors shared() {
        if (shared == null) {
            synchronized (FileConvertors.class) {
                if (shared == null) {
                    shared = new FileConvertors();
                }
            }
        }
        return shared;
    }

    public Optional<List<FileConvertor>> loadConvertors() throws Exception {
        List<String> classNames;
        String classNameData = properties.getProperty("convertors", "");
        if (classNameData.isEmpty()) {
            classNames = Arrays.asList(NetEaseCloudMusicDecryptor.class.getName(), FileXorConvertor.class.getName());
        } else {
            classNames = Arrays.asList(classNameData.split(","));
        }
        List<FileConvertor> convertors = new ArrayList<>();
        for (String className : classNames) {
            Class<?> clazz = Class.forName(className);
            Object convertor = clazz.newInstance();
            if (convertor instanceof Initializable) {
                ((Initializable) convertor).init();
            }
            if (convertor instanceof FileConvertor) {
                convertors.add((FileConvertor) convertor);
            }
        }
        return Optional.of(convertors);
    }

    public Optional<String> getConfig(String key) {
        return Optional.ofNullable(properties.getProperty(key));
    }
}
