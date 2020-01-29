package org.xialei.convertor;

import java.io.File;

/**
 * 文件转换器
 */
public interface FileConvertor {
    void convert(File in, File out, ProgressListener progressListener) throws Exception;

    String name();

    interface ProgressListener {
        void onProgress(double value, double maxValue);
    }
}
