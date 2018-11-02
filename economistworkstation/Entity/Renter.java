/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

/**
 *
 * @author fnajer
 */
public class Renter {
    public int id;
    public String name;
    public String surname;
    public String patronymic;
    public String address;
    public String birthday;
    public String person;
    
    public Renter(String name, String surname, String patronymic, String address, String birthday, String person) {
       this.name = name; 
       this.surname = surname; 
       this.patronymic = patronymic; 
       this.address = address; 
       this.birthday = birthday; 
       this.person = person;  
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}