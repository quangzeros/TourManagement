/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tourmanagement.model;

import java.util.Date;

/**
 *
 * @author 84346
 */
public class Tour {
    private String id;
    private String name;
    private String type;
    private String customerType;
    private Date startDay;
    private Double price;

    public Tour(String id, String name, String type, String customerType, Date startDay, Double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.customerType = customerType;
        this.startDay = startDay;
        this.price = price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCustomerType() {
        return customerType;
    }

    public Date getStartDay() {
        return startDay;
    }

    public Double getPrice() {
        return price;
    }

   
   
}
