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
public class User {
    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty patronymic;
    
    public User() {
        this("Ирина", "Ткаченко", "Анатольевна");
    }
    
    public User(String firstName, String lastName, String patronymic) {
       this.id = new SimpleIntegerProperty(0);
       this.firstName = new SimpleStringProperty(firstName);
       this.lastName = new SimpleStringProperty(lastName);
       this.patronymic = new SimpleStringProperty(patronymic);
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
    
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    
    public String getPatronymic() {
        return patronymic.get();
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }
    
    public String getFullName() {
        return firstName.get().charAt(0) + "." + 
                patronymic.get().charAt(0) + ". " + lastName.get();
    }
}
