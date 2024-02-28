/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tourmanagement.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import tourmanagement.database.DatabaseConnectivity;
import tourmanagement.model.Tour;
import tourmanagement.model.User;

/**
 *
 * @author 84346
 */
public class HomeController {
    private User user;
    
    private final String cusType[] = { "1 Day", "less than 5days", "less than 7days", "days" };
    
//    private List<Tour> tourList;
    
    private Tour selectedTour;
//  User Information
    @FXML
    private Label userName;
    
    @FXML
    private Label userRole;
    
    @FXML
    private Label userEmail;
    
    
    @FXML
    private HBox adminPermission;
    
    @FXML
    private TableView<Tour> tourListTable;

    @FXML
    private TableColumn<Tour, String> nameColumn;

    @FXML
    private TableColumn<Tour, String> typeColumn;

    @FXML
    private TableColumn<Tour, String> customerTypeColumn;

    @FXML
    private TableColumn<Tour, Date> startDayColumn;
    
    @FXML
    private TableColumn<Tour, Double> priceColumn;

    private ObservableList<Tour> tourList;
    
    private ObservableList<Tour> cartList;
    
  //Admin Field
    @FXML
    private TextField nameFieldAd;
    @FXML
    private ChoiceBox typeFieldAd;
    @FXML
    private RadioButton individualFieldAd;
    @FXML
    private RadioButton groupFieldAd;
    @FXML
    private ToggleGroup groupAd;
    @FXML
    private DatePicker startDayFieldAd;
    @FXML
    private TextField priceFieldAd;
    
  //Filter Field
    @FXML
    private TextField nameFieldSe;
    @FXML
    private ChoiceBox typeFieldSe;
    @FXML
    private CheckBox individualFieldSe;
    @FXML
    private CheckBox groupFieldSe;
    @FXML
    private DatePicker startDayFieldSe;
    @FXML
    private Spinner<Double> minPriceFieldSe;
    @FXML
    private Spinner<Double> maxPriceFieldSe;
    //Cart
    @FXML
    private Button addToCartBtn;
    
    @FXML
    private TableView<Tour> cartListTable;
    
    @FXML
    private TableColumn<Tour, String> nameColumn1;

    @FXML
    private TableColumn<Tour, String> typeColumn1;

    @FXML
    private TableColumn<Tour, String> customerTypeColumn1;

    @FXML
    private TableColumn<Tour, Date> startDayColumn1;
    
    @FXML
    private TableColumn<Tour, Double> priceColumn1;

    
    @SuppressWarnings("unchecked")
    @FXML
    public void initialize(){
        Platform.runLater(()->{
            
           //Nếu user là khách hàng(customer) thì sẽ ẩn đi chức năng thêm, sửa, xóa;
//        if("customer".equals(user.getRole())){
//             adminPermission.visibleProperty().setValue(Boolean.FALSE);
//        }    
           
            // In Danh Sách Các Tour lên bảng 
            nameColumn.setCellValueFactory(new PropertyValueFactory<Tour, String>("name"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<Tour, String>("type"));
            customerTypeColumn.setCellValueFactory(new PropertyValueFactory<Tour, String>("customerType"));
            startDayColumn.setCellValueFactory(new PropertyValueFactory<Tour, Date>("startDay"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<Tour, Double>("price"));
            tourListTable.setItems(tourList);
            
           //Hiển thị thông tin người dùng;
           userName.setText(this.user.getName());
           userEmail.setText(this.user.getEmail());
           userRole.setText(this.user.getRole());
           
           //Set các giá trị cho type Button
           typeFieldAd.setItems(FXCollections.observableArrayList(cusType));
           typeFieldSe.setItems(FXCollections.observableArrayList(cusType));
           
           //Set giá trị cho Spinner Price ở phần Search
           SpinnerValueFactory<Double> valueFactory =  new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0,1000.0,1.0 ,50.0);
           minPriceFieldSe.setValueFactory(valueFactory);
           SpinnerValueFactory<Double> valueFactory1 =  new SpinnerValueFactory.DoubleSpinnerValueFactory(1.0,1000.0,1000.0 ,50.0);
           maxPriceFieldSe.setValueFactory(valueFactory1);
           
           addToCartBtn.setDisable(true);
            
        });
    }
    
    public void initialUser(User user) throws SQLException{
        if("admin".equals(user.getRole())){
            System.out.println("Admin");
        }
        
        if("customer".equals(user.getRole()) ){
            System.out.println("Customer");
        }
        this.user = user;
      
        // Lấy dữ liệu Tour từ database đổ vào tourLIst
        this.tourList = this.getToursFromDb();
        
    }
  //Admin Services  
    public void onClickAddBtn() throws SQLException, ParseException{
        if("".equals(nameFieldAd.getText())  || "".equals(priceFieldAd.getText()) || typeFieldAd.getValue() == null || startDayFieldAd.getValue() == null ){
            System.out.println("ERROR");
            return;
        }
        String uniqueID = UUID.randomUUID().toString();
        String name = nameFieldAd.getText();
        String customerType = groupAd.getSelectedToggle().equals(individualFieldAd) ? "individual" : "group";
        String type = (String)typeFieldAd.getValue();
        String startDay = startDayFieldAd.getValue().toString();
        Double price = Double.valueOf(priceFieldAd.getText());
        //Add Tour vào Db
        DatabaseConnectivity.addTour(uniqueID, name, type , customerType ,startDay ,price);
        //Thêm vào bảng phía người dùng
        this.tourList = this.getToursFromDb();
        tourListTable.setItems(tourList);
        
        //làm sạch các Field
        nameFieldAd.setText("");
        typeFieldAd.setValue(null);
        startDayFieldAd.setValue(null);
        priceFieldAd.setText("");
        
            
    
        
        
//        groupAd.getSelectedToggle().equals(individualFieldAd)
        
    }

    public void onClickSelectedItem(){
        //Lấy các đối tượng từ bảng xuống Form để Update
        selectedTour = tourListTable.getSelectionModel().getSelectedItem();
        nameFieldAd.setText(selectedTour.getName());
        typeFieldAd.setValue(selectedTour.getType());
        groupAd.selectToggle("individual".equals(selectedTour.getCustomerType()) ? individualFieldAd : groupFieldAd);
        priceFieldAd.setText(selectedTour.getPrice().toString());
        startDayFieldAd.setValue(LocalDate.parse(selectedTour.getStartDay().toString(), DateTimeFormatter.ISO_DATE));
        addToCartBtn.setDisable(false);
        
    }
    
    public void onClickUpdateBtn() throws SQLException{
        //Update
        if(selectedTour == null){
            return;
        }
        String name = nameFieldAd.getText();
        String customerType = groupAd.getSelectedToggle().equals(individualFieldAd) ? "individual" : "group";
        String type = (String)typeFieldAd.getValue();
        String startDay = startDayFieldAd.getValue().toString();
        Double price = Double.valueOf(priceFieldAd.getText());
        //Add Tour vào Db
        DatabaseConnectivity.updateTour(selectedTour.getId(), name, type , customerType ,startDay ,price);
        //Thêm vào bảng phía người dùng
        this.tourList = this.getToursFromDb();
        tourListTable.setItems(tourList);
        
        
        
        
        
        
        //làm sạch các Field
        this.selectedTour = null;
        nameFieldAd.setText("");
        typeFieldAd.setValue(null);
        startDayFieldAd.setValue(null);
        priceFieldAd.setText("");
    }
    
    public void onClickDeleteBtn() throws SQLException{
        Tour selected = tourListTable.getSelectionModel().getSelectedItem();
        System.out.println(selected.getId());
        if(DatabaseConnectivity.deleteTour(selected.getId())){
           this.tourList = this.getToursFromDb();
            tourListTable.setItems(tourList);
        }
    }
    
    public ObservableList<Tour> getToursFromDb() throws SQLException{
         List<Tour> lt = DatabaseConnectivity.getTours();
         
         return FXCollections.observableArrayList(lt);
    }
    
  //Filter Services
    
    public void onClickSearchBtn() throws SQLException{
        FilteredList<Tour> filteredData = new FilteredList<>(getToursFromDb());
        
        filteredData.setPredicate( tour ->{
            
            boolean check = true;
            //
            if (!tour.getName().toLowerCase().contains(nameFieldSe.getText().toLowerCase())){
                         check = false;   
            }
            
            if (typeFieldSe.getValue() != null && !tour.getType().equals(typeFieldSe.getValue())){
                        check = false;   
            }
            
            if(!individualFieldSe.isSelected() && "individual".equals(tour.getCustomerType())){
                check = false;
            }
            
            if(!groupFieldSe.isSelected() && "group".equals(tour.getCustomerType())){
                check = false;
            }
            if(startDayFieldSe.getValue() != null){
                Date date = Date.from(startDayFieldSe.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                boolean result = date.compareTo(tour.getStartDay()) == 0;
                System.out.println(date.compareTo(tour.getStartDay()));
                if(!result){
                    check = false;
                }
            }
            
            if(tour.getPrice() > maxPriceFieldSe.getValue() || tour.getPrice() < minPriceFieldSe.getValue()){
                check = false;
            }
            
            
            
            return check;

        }
        );
        
         tourListTable.setItems(filteredData);
    }
    
  //AddToCart
    public void onClickAddToCartBtn() throws SQLException{
        if(selectedTour == null){
            return;
        }
        DatabaseConnectivity.addToCart(this.user.getId(), selectedTour.getId());
        addToCartBtn.setDisable(true);
    }
    
  //Turn to Cart Tab => ShowCart
    public void onClickCartTab() throws SQLException{
        
        // In Danh Sách Các Tour lên bảng 
            nameColumn1.setCellValueFactory(new PropertyValueFactory<Tour, String>("name"));
            typeColumn1.setCellValueFactory(new PropertyValueFactory<Tour, String>("type"));
            customerTypeColumn1.setCellValueFactory(new PropertyValueFactory<Tour, String>("customerType"));
            startDayColumn1.setCellValueFactory(new PropertyValueFactory<Tour, Date>("startDay"));
            priceColumn1.setCellValueFactory(new PropertyValueFactory<Tour, Double>("price"));
        List<Tour> lt = DatabaseConnectivity.getCartOfUser(this.user.getId());
        System.out.println(lt.getFirst().getName());
        this.cartList = FXCollections.observableArrayList(lt);
        cartListTable.setItems(cartList);
    }
  //ShowCart
    public void onClickTourTab() throws SQLException{
        this.tourList = this.getToursFromDb();
        tourListTable.setItems(tourList);
    }
}
