package org.xialei.convertor.ui.task;

import javafx.concurrent.Task;
import org.xialei.convertor.FileConvertor;
import org.xialei.convertor.FileConvertors;

import java.util.List;
import java.util.Optional;

public class ConvertorLoaderTask extends Task<Optional<List<FileConvertor>>> {
    private String filename;

    public ConvertorLoaderTask(String filename) {
        this.filename = filename;
    }

    @Override
    protected Optional<List<FileConvertor>> call() throws Exception {
        return FileConvertors.loadConvertors(filename);
    }
}
