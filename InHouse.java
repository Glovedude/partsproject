package model;

/**
 * Provides getters and setters for InHouse parts
 */

public class InHouse extends Part {

    private int machineID;
    public InHouse(int id, String name, int stock, double price, int min, int max, int machineID) {
        super(id, name, stock, price, min, max);
        this.machineID = machineID;
    }

    /**@return machineid*/
    public int getMachineID() {return machineID;}

    /**@param machineID the machineid to set*/
    public void setMachineID(int machineID) {this.machineID = machineID;}
}
