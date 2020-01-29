package org.xialei.convertor.ui.task;

import javafx.concurrent.Task;
import org.xialei.convertor.FileConvertor;

import java.io.File;

public class FileConvertTask extends Task<Void> {
    private File in;
    private File out;
    private FileConvertor convertor;

    public FileConvertTask(String in, String out, FileConvertor convertor) {
        super();
        this.in = new File(in);
        this.out = new File(out);
        this.convertor = convertor;
    }

    @Override
    protected Void call() throws Exception {
        convertor.convert(in, out, this::updateProgress);
        return null;
    }
}
