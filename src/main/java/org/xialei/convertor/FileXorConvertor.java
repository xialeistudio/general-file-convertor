package org.xialei.convertor;

import org.xialei.convertor.ui.Initializable;

import java.io.*;
import java.util.Optional;
import java.util.logging.Logger;

public class FileXorConvertor implements FileConvertor, Initializable {
    private static final Logger logger = Logger.getLogger(FileXorConvertor.class.getName());
    private static final String CFG_XOR_KEY = "org.xialei.convertor.FileXorConvertor.xorKey";

    private int xorKey = 0;

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
        return "通用文件加解密器";
    }

    @Override
    public String description() {
        return "使用固定密钥对文件进行异或加解密";
    }

    @Override
    public void init() throws Exception {
        logger.entering(getClass().getName(), "init");

        Optional<String> xorKeyConfig = FileConvertors.shared().getConfig(CFG_XOR_KEY);
        xorKeyConfig.ifPresent(s -> xorKey = Integer.parseInt(s, 16));
    }
}
