/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tourmanagement.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import tourmanagement.model.User;

/**
 *
 * @author 84346
 */
public class HomeController {
    private User user;
    
    @SuppressWarnings("unchecked")
    @FXML
    public void initialize(){
        Platform.runLater(()->{
            
        });
    }
    
    public void initialUser(User user){
        if("admin".equals(user.getRole())){
            System.out.println("Admin");
        }
        
        if("customer".equals(user.getRole()) ){
            System.out.println("Customer");
        }
        this.user = user;
    }
    
    
}
