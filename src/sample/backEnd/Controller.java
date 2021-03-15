package sample.backEnd;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Controller {

    @FXML
    private TextArea firstTextArea;
    @FXML
    private TextArea secondTextArea;
    @FXML
    private TextField textBoxForKey;
    @FXML
    private RadioButton ECBButton;
    @FXML
    private RadioButton CBCButton;
    @FXML
    private Button chooseFileButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button encryptButton;
    @FXML
    private Button decrpytButton;

    String fileName;


    public void encryptButtonPressed(ActionEvent actionEvent) {
        if (ECBButton.isSelected()) {
            try {
                String text = firstTextArea.getText();
                String key = textBoxForKey.getText();

                String encryptedText = String.valueOf(AESEncryption.encryptWithECB(text, key));
                secondTextArea.clear();
                secondTextArea.setText(encryptedText);
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
        else if (CBCButton.isSelected()) {
            try {
                String text = firstTextArea.getText();
                String key = textBoxForKey.getText();

                String encryptedText = String.valueOf(AESEncryption.encryptWithCBC(text, key));
                secondTextArea.clear();
                secondTextArea.setText(encryptedText);
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }

    }

    public void decryptButtonPressed(ActionEvent actionEvent) {
        if (ECBButton.isSelected()) {
            try {
                String text = firstTextArea.getText();
                String key = textBoxForKey.getText();

                String decryptedText = String.valueOf(AESEncryption.decryptWithECB(text, key));
                secondTextArea.clear();
                secondTextArea.setText(decryptedText);
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
        else if (CBCButton.isSelected()) {
            try {
                String text = firstTextArea.getText();
                String key = textBoxForKey.getText();

                String encryptedText = String.valueOf(AESEncryption.decryptWithCBC(text, key));
                secondTextArea.clear();
                secondTextArea.setText(encryptedText);
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }

    public void chooseFileButtonPressed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                "TXT files (*.txt)", "*.txt");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.dir"))
        );
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(null);
        fileName = file.getAbsolutePath();
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(fileName)));
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
        firstTextArea.clear();
        firstTextArea.setText(text);
    }

    public void saveButtonPressed(ActionEvent actionEvent) {
        String encrytpedText = secondTextArea.getText();
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            saveEncryptedTextToFile(encrytpedText, file);
        }
    }
    public void saveEncryptedTextToFile(String text, File file) {
        try {
            PrintWriter printWriter;
            printWriter = new PrintWriter(file);
            printWriter.print(text);
            printWriter.close();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
