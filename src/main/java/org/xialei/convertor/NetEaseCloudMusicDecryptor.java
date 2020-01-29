package org.xialei.convertor;

import java.io.*;

public class NetEaseCloudMusicDecryptor implements FileConvertor {
    private static final int xorKey = 0xA3;

    @Override
    public void convert(File in, File out, ProgressListener listener) throws Exception {
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(in));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(out))
        ) {
            int b;
            int convertedLength = 0;
            long totalLength = in.length();

            while ((b = bis.read()) != -1) {
                bos.write(b ^ xorKey);
                convertedLength++;
                if (listener != null && convertedLength % 100 == 0) {
                    listener.onProgress(convertedLength, totalLength);
                }
            }
            bos.flush();
        }
    }

    @Override
    public String name() {
        return "网易云音乐缓存解密器";
    }

    @Override
    public String description() {
        return "将网易云音乐加密的缓存文件解密为原始音乐文件";
    }
}
