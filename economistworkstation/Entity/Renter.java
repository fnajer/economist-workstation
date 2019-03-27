/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author fnajer
 */
public class Renter {
    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty patronymic;
    private final StringProperty address;
    private final StringProperty birthday;
    private final StringProperty subject;
    
    public Renter() {
        this("egeg", "egeg", "egeg", "egeg", "egeg", "egeg");
    }
    
    public Renter(String firstName, String lastName, String patronymic, String address, String birthday, String subject) {
       this.id = new SimpleIntegerProperty(0);
       this.firstName = new SimpleStringProperty(firstName);
       this.lastName = new SimpleStringProperty(lastName);
       this.patronymic = new SimpleStringProperty(patronymic); 
       this.address = new SimpleStringProperty(address); 
       this.birthday = new SimpleStringProperty(birthday); 
       this.subject = new SimpleStringProperty(subject);  
    }
    
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }
    
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    
    public StringProperty firstNameProperty() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    
    public StringProperty lastNameProperty() {
        return lastName;
    }
    
    public String getPatronymic() {
        return patronymic.get();
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }
    
    public String getFullName() {
        return lastName.get() + ' ' + firstName.get().charAt(0) + ". " + 
                patronymic.get().charAt(0) + '.';
    }
    
    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
    
    public String getBirthday() {
        return birthday.get();
    }

    public void setBirthday(String birthday) {
        this.birthday.set(birthday);
    }
    
    public String getSubject() {
        return subject.get();
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }
    
    @Override
    public String toString() {
        return String.format("%s %s %s - %sг.", 
                getLastName(), getFirstName(), getPatronymic(), getBirthday());
    }
}