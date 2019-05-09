/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation;

import economistworkstation.Entity.Building;
import economistworkstation.Entity.Contract;
import economistworkstation.Entity.Period;
import economistworkstation.Entity.Renter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author fnajer
 */
public class ContractData {
    private final ObjectProperty cell;
    private final ObjectProperty period;
    private final ObjectProperty building;
    private final ObjectProperty renter;
    private final ObjectProperty contract;
    private final ObjectProperty workbook;
    
    public ContractData() {
        this(null, null, null, null, null, null);
    }
    
      
    public ContractData(Cell cell, Period period, Building building, Renter renter, 
            Contract contract, Workbook workbook) {
       this.cell = new SimpleObjectProperty(cell);
       this.period = new SimpleObjectProperty(period);
       this.building = new SimpleObjectProperty(building);
       this.renter = new SimpleObjectProperty(renter);
       this.contract = new SimpleObjectProperty(contract);
       this.workbook = new SimpleObjectProperty(workbook);
    }

    
    public Cell getCell() {
        return (Cell) cell.get();
    }

    public void setCell(Cell cell) {
        this.cell.set(cell);
    }
    
    public Period getPeriod() {
        return (Period) period.get();
    }

    public void setPeriod(Period period) {
        this.period.set(period);
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
    
    public Contract getContract() {
        return (Contract) contract.get();
    }

    public void setContract(Contract contract) {
        this.contract.set(contract);
    }
    
    public Workbook getWorkbook() {
        return (Workbook) workbook.get();
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook.set(workbook);
    }
    
}
