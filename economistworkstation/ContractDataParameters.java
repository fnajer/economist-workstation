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
import economistworkstation.Model.BuildingModel;
import economistworkstation.Model.PeriodModel;
import economistworkstation.Model.RenterModel;
import java.time.LocalDate;
import javafx.collections.ObservableList;

/**
 *
 * @author fnajer
 */
public class ContractDataParameters {
    private ContractData dataSingle;
    private ObservableList<ContractData> dataList;
    
    private String savePath;

    public ContractData getDataSingle() {
        return dataSingle;
    }
    public void setDataSingle(ContractData dataSingle) {
        this.dataSingle = dataSingle;
    }

    public ObservableList<ContractData> getDataList() {
        return dataList;
    }
    public void setDataList(ObservableList<ContractData> dataList) {
        this.dataList = dataList;
    }
    
    public void constructDataObject(Contract contract, Period period) {
        Renter renter = RenterModel.getRenter(contract.getIdRenter());
        Building building = BuildingModel.getBuilding(contract.getIdBuilding());
        setDataSingle(new ContractData(null, period, 
                            building, renter, contract));
    }
    
    public void constructDataList(LocalDate date, String savePath) {
//        LocalDate date = LocalDate.parse("2019-09-01");
        this.savePath = savePath;
        setDataList(PeriodModel.getContractData(date));
    }
    
    public String getSavePath() {
        return savePath;
    }
    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
