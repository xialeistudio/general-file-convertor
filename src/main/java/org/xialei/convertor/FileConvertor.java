package org.xialei.convertor;

import java.io.File;

/**
 * 文件转换器
 */
public abstract class FileConvertor {
    protected File sourceFile;
    protected File destinationFile;
    protected ProgressListener progressListener;

    public FileConvertor(File sourceFile, File destinationFile) {
        this.sourceFile = sourceFile;
        this.destinationFile = destinationFile;
    }

    public FileConvertor setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
        return this;
    }

    /**
     * 执行转换
     *
     * @throws Exception 转换异常
     */
    abstract public void run() throws Exception;

    /**
     * @return 用户可读的转换器名称，如『网易云音乐解密』
     */
    abstract public String name();

    /**
     * 进度监听
     */
    interface ProgressListener {
        void onProgress(double value, double maxValue);
    }
}
