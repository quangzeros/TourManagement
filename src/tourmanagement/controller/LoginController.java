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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tourmanagement.model.User;



/**
 *
 * @author 84346
 */
public class LoginController {
   @FXML
   private TextField emailField;
   @FXML
   private PasswordField passwordField;
   @FXML
   private Button loginBtn;
   @FXML
   private Label message;
   
   public void onClickLoginBtn()throws IOException{
       if("quang".equals(emailField.getText())){
           message.setText("");
           message.setText("Please Check Again Your Email Or Password!");
           return;
       }
       
       
       //Đóng cửa sổ hiện tại 
       emailField.getScene().getWindow().hide();
       //Chuyển sang màn hình chính
       gotoHomeScreen(new User("1","admin","nguyenkhacquang176@gmail.com","Quang"));
   }
   @FXML
   public void onClickRegisterBtn()throws IOException{
       //Đóng cửa sổ hiện tại 
       emailField.getScene().getWindow().hide();
       //Chuyển sang màn hình đăng ký
       gotoRegisterScreen();
   }
   
   public void gotoHomeScreen(User user)throws IOException{
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
   
   public void gotoRegisterScreen() throws IOException {
        //Tạo FXMLLoader tương ưng với Home.fxml
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/Register.fxml"));
      // Lấy về đối tượng root layout 
      Parent root = (Parent)fxmlLoader.load();
      //Lấy về đối tượng HomeController
      RegisterController controller = fxmlLoader.getController();
      
      //Truyền dữ liệu vào đối tượng controller 
      
      
      Stage homeStage = new Stage();
      homeStage.setTitle("Register");
      homeStage.setScene(new Scene(root));
      homeStage.show();
   }
}
