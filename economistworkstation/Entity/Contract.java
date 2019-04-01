/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author fnajer
 */
public class Contract {
    private final IntegerProperty id;
    private final StringProperty dateStart;
    private final StringProperty dateEnd;
    private final IntegerProperty idRenter;
    private final IntegerProperty idBuilding;
    private Renter renter;
    
    public Contract() {
        this(null, null, 0, 0);
    }
    
    public Contract(String dateStart, String dateEnd, int idRenter, int idBuilding) {
       this.id = new SimpleIntegerProperty(0);
       this.dateStart = new SimpleStringProperty(dateStart);
       this.dateEnd = new SimpleStringProperty(dateEnd);
       this.idRenter = new SimpleIntegerProperty(idRenter); 
       this.idBuilding = new SimpleIntegerProperty(idBuilding);
       this.renter = null;
    }
    
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }
    
    public IntegerProperty idProperty() {
        return id;

    }
    
    public String getDateStart() {
        return dateStart.get();
    }

    public void setDateStart(String dateStart) {
        this.dateStart.set(dateStart);
    }
    
    public StringProperty dateStartProperty() {
        return dateStart;
    }
    
    public String getDateEnd() {
        return dateEnd.get();
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd.set(dateEnd);
    }
    
    public StringProperty dateEndProperty() {
        return dateEnd;
    }
     
    public int getIdRenter() {
        return idRenter.get();
    }

    public void setIdRenter(int idRenter) {
        this.idRenter.set(idRenter);
    }
    
    public IntegerProperty idRenterProperty() {
        return idRenter;
    }
    
    public int getIdBuilding() {
        return idBuilding.get();
    }

    public void setIdBuilding(int idBuilding) {
        this.idBuilding.set(idBuilding);
    }
    
    public Renter getRenter() {
        return renter;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }
    
    public StringProperty renterProperty() {
        return new SimpleStringProperty(renter.getFullName());
    }
    
    @Override
    public String toString() {
        return String.format("Договор №%d - %s", getId(), getDateStart());
    }
}