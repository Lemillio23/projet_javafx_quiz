package com.example.authentification;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class AdminController implements Initializable {

    @FXML
    private TableView<Admin> adminTable;
    @FXML
    private TableColumn<Admin, Integer> adminIdColumn;
    @FXML
    private TableColumn<Admin, String> adminNameColumn;
    @FXML
    private TableColumn<Admin, String> adminEmailColumn;
    @FXML
    private TableColumn<Admin, String> adminPasswordColumn;
    @FXML
    private TextField adminNameField;
    @FXML
    private TextField adminEmailField;
    @FXML
    private TextField adminPasswordField;
    @FXML
    private Button addAdminButton;
    @FXML
    private Button updateAdminButton;
    @FXML
    private Button deleteAdminButton;

    private AdminDAO adminDAO;
    private ObservableList<Admin> adminList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminDAO = new AdminDAO();
        adminList = FXCollections.observableArrayList(adminDAO.getAllAdmins());

        adminIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        adminNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        adminEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        adminPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        adminTable.setItems(adminList);
        adminTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                adminNameField.setText(newValue.getName());
                adminEmailField.setText(newValue.getEmail());
                adminPasswordField.setText(newValue.getPassword());
            }
        });
    }

    @FXML
    private void handleAddAdminButton(MouseEvent event) {
        String name = adminNameField.getText();
        String email = adminEmailField.getText();
        String password = adminPasswordField.getText();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            Admin newAdmin = new Admin(name, email, password);
            int rowsInserted = adminDAO.addAdmin(newAdmin);

            if (rowsInserted > 0) {
                adminList.add(newAdmin);
                clearFields();
                showAlert("Admin added successfully", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Failed to add admin", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Please fill all fields", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleUpdateAdminButton(MouseEvent event) {
        Admin selectedAdmin = adminTable.getSelectionModel().getSelectedItem();

        if (selectedAdmin != null) {
            String name = adminNameField.getText();
            String email = adminEmailField.getText();
            String password = adminPasswordField.getText();

            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                selectedAdmin.setName(name);
                selectedAdmin.setEmail(email);
                selectedAdmin.setPassword(password);

                int rowsUpdated = adminDAO.updateAdmin(selectedAdmin);

                if (rowsUpdated > 0) {
                    adminList.set(adminList.indexOf(selectedAdmin), selectedAdmin);
                    clearFields();
                    showAlert("Admin updated successfully", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Failed to update admin", Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Please fill all fields", Alert.AlertType.WARNING);
            }
        } else {
            showAlert("Please select an admin to update", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleDeleteAdminButton(MouseEvent event) {
        Admin selectedAdmin = adminTable.getSelectionModel().getSelectedItem();

        if (selectedAdmin != null) {
            int rowsDeleted = adminDAO.deleteAdmin(selectedAdmin);

            if (rowsDeleted > 0) {
                adminList.remove(selectedAdmin);
                clearFields();
                showAlert("Admin deleted successfully", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Failed to delete admin", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Please select an admin to delete", Alert.AlertType.WARNING);
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        adminNameField.clear();
        adminEmailField.clear();
        adminPasswordField.clear();
    }

}