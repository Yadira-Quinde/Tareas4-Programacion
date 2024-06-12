package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {

    private TextArea textArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        // BorderPane
        BorderPane root = new BorderPane();

        // barra de menú
        MenuBar menuBar = new MenuBar();

        // Menú Archivo
        Menu menuFile = new Menu("Archivo");
        MenuItem newFile = new MenuItem("Nuevo");
        MenuItem openFile = new MenuItem("Abrir");
        MenuItem saveFile = new MenuItem("Guardar");
        MenuItem exitFile = new MenuItem("Salir");

        newFile.setOnAction(e -> {
            textArea.clear();
            System.out.println("Nuevo archivo creado");
        });

        openFile.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
                    textArea.setText(content);
                    System.out.println("Archivo abierto: " + file.getPath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        saveFile.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    Files.write(Paths.get(file.getPath()), textArea.getText().getBytes());
                    System.out.println("Archivo guardado: " + file.getPath());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("Archivo guardado");
                    alert.showAndWait();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exitFile.setOnAction(e -> primaryStage.close());

        menuFile.getItems().addAll(newFile, openFile, saveFile, new SeparatorMenuItem(), exitFile);

        // Menú Editar
        Menu menuEdit = new Menu("Editar");
        MenuItem cutEdit = new MenuItem("Cortar");
        MenuItem copyEdit = new MenuItem("Copiar");
        MenuItem pasteEdit = new MenuItem("Pegar");

        cutEdit.setOnAction(e -> {
            textArea.cut();
            System.out.println("Cortar");
        });

        copyEdit.setOnAction(e -> {
            textArea.copy();
            System.out.println("Copiar");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Texto copiado");
            alert.showAndWait();
        });

        pasteEdit.setOnAction(e -> {
            textArea.paste();
            System.out.println("Pegar");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Texto pegado");
            alert.showAndWait();
        });

        menuEdit.getItems().addAll(cutEdit, copyEdit, pasteEdit);

        // Menú Ayuda
        Menu menuHelp = new Menu("Ayuda");
        MenuItem aboutHelp = new MenuItem("Acerca de");

        aboutHelp.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Acerca de");
            alert.setHeaderText(null);
            alert.setContentText("Esta es la tarea profesor");
            alert.showAndWait();
        });

        menuHelp.getItems().add(aboutHelp);

        // Añadir los menús a la barra de menú
        menuBar.getMenus().addAll(menuFile, menuEdit, menuHelp);

        // Añadir la barra de menú al BorderPane
        root.setTop(menuBar);
        root.setCenter(textArea);

        // Crear la escena y añadir el BorderPane
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Menú en JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
