/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation;

import economistworkstation.Entity.Building;
import economistworkstation.Entity.Month;
import economistworkstation.Entity.Renter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author fnajer
 */
public class ContractData {
    private final ObjectProperty cell;
    private final ObjectProperty month;
    private final ObjectProperty building;
    private final ObjectProperty renter;
    
    public ContractData() {
        this(null, null, null, null);
    }
    
    public ContractData(Cell cell) {
       this.cell = new SimpleObjectProperty(cell);
       this.month = null;
       this.building = null;
       this.renter = null;
    }
    
    public ContractData(Cell cell, Month month) {
       this.cell = new SimpleObjectProperty(cell);
       this.month = new SimpleObjectProperty(month);
       this.building = null;
       this.renter = null;
    }
    
    public ContractData(Cell cell, Month month, Building building) {
        this.cell = new SimpleObjectProperty(cell);
       this.month = new SimpleObjectProperty(month);
       this.building = new SimpleObjectProperty(building);
       this.renter = null;
    }
    
    public ContractData(Cell cell, Month month, Building building, Renter renter) {
        this.cell = new SimpleObjectProperty(cell);
       this.month = new SimpleObjectProperty(month);
       this.building = new SimpleObjectProperty(building);
       this.renter = new SimpleObjectProperty(renter);
    }
    
    public Cell getCell() {
        return (Cell) cell.get();
    }

    public void setCell(Cell cell) {
        this.cell.set(cell);
    }
    
    public Month getMonth() {
        return (Month) month.get();
    }

    public void setMonth(Month month) {
        this.month.set(month);
    }
     
    public Building getBuilding() {
        return (Building) building.get();
    }

    public void setBuilding(int building) {
        this.building.set(building);
    }
    
    public Renter getRenter() {
        return (Renter) renter.get();
    }

    public void setRenter(Renter renter) {
        this.renter.set(renter);
    }
    
}
