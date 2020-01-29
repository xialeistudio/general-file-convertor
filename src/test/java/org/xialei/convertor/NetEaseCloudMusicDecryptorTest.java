package org.xialei.convertor;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class NetEaseCloudMusicDecryptorTest {
    private static NetEaseCloudMusicDecryptor decryptor;

    @BeforeClass
    public static void setUp() {
        decryptor = new NetEaseCloudMusicDecryptor();
    }

    @Test
    public void run() throws Exception {
        String home = System.getProperty("user.home");

        decryptor.convert(
                new File(home + "/Desktop/186436-128000-17a10cdcf43c5f428432591e2239a6ac.mp3.uc!"),
                new File(home + "/Desktop/test.mp3"),
                null
        );
    }

    @Test
    public void name() {
        Assert.assertEquals("网易云音乐缓存文件解密器", decryptor.name());
    }
}