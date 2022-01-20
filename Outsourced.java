package model;

/**
 * Provides setters and getters based on outsourced options
 */

public class Outsourced extends Part{

    private String companyName;

    public Outsourced(int id, String name, int stock, double price,  int min, int max, String companyName) {
        super(id, name, stock, price, min, max);
        this.companyName = companyName;


    }
        /**@return companyName*/
        public String getCompanyName() {return companyName;}

        /**@param companyName set companyname*/
        public void setCompanyName(String companyName) { this.companyName = companyName; }

}
