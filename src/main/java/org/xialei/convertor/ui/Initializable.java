package org.xialei.convertor.ui;

/**
 * 通过反射实例化之后在非主线程回调，可以在此进行加载配置文件等操作
 */
public interface Initializable {
    void init() throws Exception;
}
