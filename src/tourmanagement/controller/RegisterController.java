/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tourmanagement.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.UUID;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tourmanagement.database.DatabaseConnectivity;
import tourmanagement.model.User;
import tourmanagement.utils.HashPassword;

/**
 *
 * @author 84346
 */
public class RegisterController {
   @FXML
    private TextField nameField;
   @FXML
   private TextField emailField;
   @FXML
   private PasswordField passwordField;
   @FXML
   private PasswordField confirmedPasswordField;
   @FXML
   private Button loginBtn;
   @FXML
   private Label message;

    
    
    public void onClickRegisterBtn() throws IOException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException{
       if("".equals(nameField.getText())  || "".equals(emailField.getText()) || "".equals(passwordField.getText()) || "".equals(confirmedPasswordField.getText()) ){
           message.setText("");
           message.setText("Don't let any fields empty!");
           return;
       }
       if(DatabaseConnectivity.checkEmail(emailField.getText())){
           message.setText("");
           message.setText("This email has already exist!");
           return;
       }
       if(!passwordField.getText().equals(confirmedPasswordField.getText())){
           message.setText("");
           message.setText("confirmPassword is not valid");
           return;
       }
       
       String uniqueID = UUID.randomUUID().toString();
       String hashPassword = HashPassword.generateStorngPasswordHash(passwordField.getText());
       User user = new User(uniqueID,"customer",emailField.getText(),nameField.getText());
       DatabaseConnectivity.insertUser(uniqueID, nameField.getText(), "customer",emailField.getText() , hashPassword);
        
       System.out.println("Register Success");
       
       
       //Đóng cửa sổ hiện tại 
       emailField.getScene().getWindow().hide();
       //Chuyển sang màn hình chính
       gotoHomeScreen(user);
    }
    
    public void onClickLoginBtn() throws IOException{
       //Đóng cửa sổ hiện tại 
       emailField.getScene().getWindow().hide();
       //Chuyển sang màn hình đăng ký
       gotoLoginScreen();
    }
 
    
   public void gotoHomeScreen(User user)throws IOException, SQLException{
       //Tạo FXMLLoader tương ưng với Home.fxml
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Home.fxml"));
      // Lấy về đối tượng root layout 
      Parent root = (Parent) fxmlLoader.load();
      //Lấy về đối tượng HomeController
      HomeController controller = fxmlLoader.getController();

      //Truyền dữ liệu vào đối tượng controller 
      controller.initialUser(user);
      
      Stage homeStage = new Stage();
      homeStage.setTitle("HomeScreen");
      homeStage.setScene(new Scene(root));
      homeStage.show();
   }
    
    
    
   public void gotoLoginScreen() throws IOException {
        //Tạo FXMLLoader tương ưng với Home.fxml
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
      // Lấy về đối tượng root layout 
      Parent root = (Parent)fxmlLoader.load();
      //Lấy về đối tượng HomeController
      LoginController controller = fxmlLoader.getController();
      
      //Truyền dữ liệu vào đối tượng controller 
      
      Stage homeStage = new Stage();
      homeStage.setTitle("Login");
      homeStage.setScene(new Scene(root));
      homeStage.show();
   }
    
}
