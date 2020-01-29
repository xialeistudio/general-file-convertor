package org.xialei.convertor.ui;

import javafx.scene.control.Alert;

public class MessageBox {
    public static void info(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
