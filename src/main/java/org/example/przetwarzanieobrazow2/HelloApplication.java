package org.example.przetwarzanieobrazow2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class HelloApplication extends Application {

    private ImageView originalImageView = new ImageView();
    private ImageView modifiedImageView = new ImageView();
    private ComboBox<String> operationComboBox = new ComboBox<>();
    private Button executeButton = new Button("Wykonaj");
    private Button saveButton = new Button("Zapisz");
    private File currentImageFile;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Przetwarzanie Obrazów");

        // Logo (ładowanie z zasobów i obsługa braku pliku)
        ImageView logoView;
        try {
            Image logo = new Image(getClass().getResourceAsStream("/org/example/przetwarzanieobrazow2/logo_pwr.png"));
            if (logo.isError()) throw new Exception("Nie można załadować logo!");
            logoView = new ImageView(logo);
        } catch (Exception e) {
            showToast("Nie znaleziono pliku logo_pwr.png w zasobach!");
            logoView = new ImageView(); // pusty
        }
        logoView.setFitWidth(200);
        logoView.setPreserveRatio(true);

        HBox topBox = new HBox(10, logoView, new Label("Witaj w edytorze obrazów!"));
        topBox.setAlignment(Pos.CENTER_LEFT);
        topBox.setPadding(new Insets(10));

        Label authorLabel = new Label("Autor: Kacper Karkosz");

        // Lista rozwijana z operacjami
        operationComboBox.getItems().addAll("Negatyw", "Progowanie", "Konturowanie", "Obrót");
        operationComboBox.setPromptText("Wybierz operację");

        // Przyciski
        Button loadButton = new Button("Wczytaj obraz");
        saveButton.setDisable(true);
        executeButton.setOnAction(e -> handleExecute());
        loadButton.setOnAction(e -> handleLoadImage());
        saveButton.setOnAction(e -> handleSaveImage());

        HBox buttonBox = new HBox(10, loadButton, executeButton, saveButton);
        buttonBox.setAlignment(Pos.CENTER);

        originalImageView.setFitWidth(300);
        originalImageView.setPreserveRatio(true);
        modifiedImageView.setFitWidth(300);
        modifiedImageView.setPreserveRatio(true);

        VBox originalBox = new VBox(5, new Label("Oryginalny obraz"), originalImageView);
        VBox modifiedBox = new VBox(5, new Label("Przetworzony obraz"), modifiedImageView);
        originalBox.setAlignment(Pos.CENTER);
        modifiedBox.setAlignment(Pos.CENTER);

        HBox imageBox = new HBox(20, originalBox, modifiedBox);
        imageBox.setAlignment(Pos.CENTER);

        // Główna zawartość, już bez topBox (który idzie do root.setTop)
        VBox centerPane = new VBox(10);
        centerPane.setAlignment(Pos.TOP_CENTER);
        centerPane.setPadding(new Insets(10));
        centerPane.getChildren().addAll(operationComboBox, buttonBox, imageBox);

        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(centerPane);
        root.setBottom(authorLabel);
        BorderPane.setAlignment(authorLabel, Pos.CENTER);
        BorderPane.setMargin(authorLabel, new Insets(10));

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private void handleLoadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Pliki JPG", "*.jpg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            if (!selectedFile.getName().toLowerCase().endsWith(".jpg")) {
                showToast("Niedozwolony format pliku");
                return;
            }
            try {
                Image image = new Image(new FileInputStream(selectedFile));
                originalImageView.setImage(image);
                modifiedImageView.setImage(image);
                currentImageFile = selectedFile;
                saveButton.setDisable(false);
                showToast("Pomyślnie załadowano plik");
            } catch (FileNotFoundException e) {
                showToast("Nie udało się załadować pliku");
            }
        }
    }

    private void handleExecute() {
        String operation = operationComboBox.getValue();
        if (operation == null) {
            showToast("Nie wybrano operacji do wykonania");
            return;
        }
        // Tutaj można podpiąć wykonanie konkretnych operacji na obrazie
        showToast("Wykonano operację: " + operation);
    }

    private void handleSaveImage() {
        if (modifiedImageView.getImage() == null) return;

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Zapisz obraz");

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));

        TextField nameField = new TextField();
        nameField.setPromptText("Nazwa pliku (3-100 znaków)");

        Label validationLabel = new Label();
        validationLabel.setStyle("-fx-text-fill: red;");

        Button saveBtn = new Button("Zapisz");
        Button cancelBtn = new Button("Anuluj");

        saveBtn.setOnAction(e -> {
            String fileName = nameField.getText();
            if (fileName.length() < 3) {
                validationLabel.setText("Wpisz co najmniej 3 znaki");
                return;
            }
            File outputFile = new File(System.getProperty("user.home") + "/Desktop/" + fileName + ".jpg");
            if (outputFile.exists()) {
                showToast("Plik " + fileName + ".jpg już istnieje w systemie. Podaj inną nazwę pliku!");
                return;
            }
            try {
                Files.copy(currentImageFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                showToast("Zapisano obraz w pliku " + fileName + ".jpg");
                dialog.close();
            } catch (Exception ex) {
                showToast("Nie udało się zapisać pliku " + fileName + ".jpg");
            }
        });

        cancelBtn.setOnAction(e -> dialog.close());

        dialogVBox.getChildren().addAll(nameField, validationLabel, new HBox(10, saveBtn, cancelBtn));
        dialog.setScene(new Scene(dialogVBox));
        dialog.show();
    }

    private void showToast(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}