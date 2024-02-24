/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tourmanagement.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author 84346
 */
public class RegisterController {
    
   @FXML
   private TextField emailField;
   @FXML
   private PasswordField passwordField;
   @FXML
   private PasswordField confirmedPasswordField;
   @FXML
   private Button loginBtn;

    
    
    public void onClickRegisterBtn() throws IOException{
       System.out.println("Hello World");
       
       
       //Đóng cửa sổ hiện tại 
       emailField.getScene().getWindow().hide();
       //Chuyển sang màn hình chính
       gotoHomeScreen();
    }
    
    public void onClickLoginBtn() throws IOException{
       //Đóng cửa sổ hiện tại 
       emailField.getScene().getWindow().hide();
       //Chuyển sang màn hình đăng ký
       gotoLoginScreen();
    }
 
    
    public void gotoHomeScreen()throws IOException{
       //Tạo FXMLLoader tương ưng với Home.fxml
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Home.fxml"));
      // Lấy về đối tượng root layout 
      Parent root = (Parent)fxmlLoader.load();
      //Lấy về đối tượng HomeController
      HomeController controller = fxmlLoader.getController();
      
      //Truyền dữ liệu vào đối tượng controller 
      
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
