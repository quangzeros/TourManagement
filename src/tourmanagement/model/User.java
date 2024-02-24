/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tourmanagement.model;

/**
 *
 * @author 84346
 */
public class User {
    protected String id;
    protected String role;
    protected String email;
    protected String name;

    public String getRole() {
        return role;
    }
    
    public User(String id ,String role,String email, String name){
        this.id = id;
        this.role = role;
        this.email = email;
        this.name = name;
    }
}
