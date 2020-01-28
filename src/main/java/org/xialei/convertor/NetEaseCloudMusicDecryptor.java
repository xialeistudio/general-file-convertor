package org.xialei.convertor;

import java.io.*;

public class NetEaseCloudMusicDecryptor extends FileConvertor {
    private static final int xorKey = 0xA3;

    public NetEaseCloudMusicDecryptor(File sourceFile, File destinationFile) {
        super(sourceFile, destinationFile);
    }

    @Override
    public void run() throws Exception {
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destinationFile))
        ) {
            int b;
            int convertedLength = 0;
            long totalLength = sourceFile.length();

            while ((b = bis.read()) != -1) {
                bos.write(b ^ xorKey);
                convertedLength++;
                if (progressListener != null && convertedLength % 100 == 0) {
                    progressListener.onProgress(convertedLength, totalLength);
                }
            }
            bos.flush();
        }
    }

    @Override
    public String name() {
        return "网易云音乐缓存文件解密器";
    }
}
