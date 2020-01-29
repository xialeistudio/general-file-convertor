package org.xialei.convertor.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.xialei.convertor.FileConvertor;
import org.xialei.convertor.ui.task.ConvertorLoaderTask;
import org.xialei.convertor.ui.task.FileConvertTask;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {
    public TextField inputSource;
    public TextField inputDest;
    public ComboBox<String> comboConvertors;
    public ProgressBar processBar;
    public Button buttonStart;
    public Label txtDescription;
    public Button buttonSource;
    public Button buttonDest;

    private List<FileConvertor> convertors;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initView();
        loadConvertors();
    }

    private void initView() {
        comboConvertors.valueProperty().addListener((observable, oldValue, newValue) -> {
            int index = comboConvertors.getSelectionModel().getSelectedIndex();
            FileConvertor convertor = convertors.get(index);
            txtDescription.setText(convertor.description());
        });
    }

    private void loadConvertors() {
        ConvertorLoaderTask task = new ConvertorLoaderTask("config.properties");
        task.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isPresent()) {
                MessageBox.info("无可用转换器, 请检查配置文件");
                return;
            }
            convertors = newValue.get();
            if (convertors.isEmpty()) {
                MessageBox.info("无可用转换器, 请检查配置文件");
                return;
            }
            List<String> nameList = convertors.stream().map(FileConvertor::name).collect(Collectors.toList());
            ObservableList<String> observableNameList = FXCollections.observableList(nameList);
            comboConvertors.setItems(observableNameList);
            comboConvertors.getSelectionModel().selectFirst();
        });
        task.exceptionProperty().addListener((observable, oldValue, newValue) -> MessageBox.info(newValue.getMessage()));
        new Thread(task).start();
    }

    public void handleSourceClicked(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(buttonStart.getScene().getWindow());
        if (file == null) {
            return;
        }
        inputSource.setText(file.getAbsolutePath());
        updateControlState();
    }

    public void handleDestClicked(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(buttonStart.getScene().getWindow());
        if (file == null) {
            return;
        }
        inputDest.setText(file.getAbsolutePath());
        updateControlState();
    }

    private void updateControlState() {
        buttonStart.setDisable(
                inputSource.getText().isEmpty()
                        || inputDest.getText().isEmpty()
                        || comboConvertors.getSelectionModel().isEmpty()
        );
    }

    public void handleStartClicked(ActionEvent actionEvent) {
        buttonSource.setDisable(true);
        buttonDest.setDisable(true);

        FileConvertor convertor = convertors.get(comboConvertors.getSelectionModel().getSelectedIndex());
        FileConvertTask task = new FileConvertTask(inputSource.getText(), inputDest.getText(), convertor);
        task.runningProperty().addListener((observable, oldValue, newValue) -> {
            buttonStart.setDisable(newValue);
            buttonSource.setDisable(newValue);
            buttonDest.setDisable(newValue);
        });
        task.progressProperty().addListener((observable, oldValue, newValue) -> processBar.setProgress(newValue.doubleValue()));
        task.exceptionProperty().addListener((observable, oldValue, newValue) -> MessageBox.info(newValue.getMessage()));
        task.onSucceededProperty().addListener((observable, oldValue, newValue) -> MessageBox.info("转换完成"));
        new Thread(task).start();
    }
}
