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
public class Contract {
    public int id;
    public String date_start;
    public String date_end;
    public int id_renter;
    public int id_building;
    
    public Contract(String date_start, String date_end, int id_renter, int id_building) {
       this.date_start = date_start; 
       this.date_end = date_end; 
       this.id_renter = id_renter; 
       this.id_building = id_building;
    }
    
    @Override
    public String toString() {
        return Integer.toString(this.id);
    }
}