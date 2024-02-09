/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gin_payroll;

import com.mycompany.model.User;
import com.mycompany.utility.AlertUtils;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author aavin
 */
public class GenericController {

    private User currentUser;

    public void changeView(Pane tempPane, String fxml) throws IOException {
        Stage stage = (Stage) tempPane.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        GenericController fxmlController = fxmlLoader.getController();
        fxmlController.customInitialize(currentUser);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void customInitialize(Object... obj) {
        currentUser = (User) obj[0];
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setDataForHeader(VBox vbox, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml + ".fxml"));
            Parent child = loader.load();
            GenericController childController = loader.getController();
            childController.customInitialize(currentUser);
            vbox.getChildren().clear();
            vbox.getChildren().add(child);
        } catch (IOException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

}
