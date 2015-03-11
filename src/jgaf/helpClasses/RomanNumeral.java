/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.helpClasses;

/**
 *
 * @author LordDrake
 */
public class RomanNumeral {
    
    final static RomanNumeral[] table = {
    new RomanNumeral(1000, "M"),
    new RomanNumeral( 900, "CM"),
    new RomanNumeral( 500, "D"),
    new RomanNumeral( 400, "CD"),
    new RomanNumeral( 100, "C"),
    new RomanNumeral( 90, "XC"),
    new RomanNumeral( 50, "L"),
    new RomanNumeral( 40, "XL"),
    new RomanNumeral( 10, "X"),
    new RomanNumeral( 9, "IX"),
    new RomanNumeral( 5, "V"),
    new RomanNumeral( 4, "IV"),
    new RomanNumeral( 1, "I")
    };
    
    int number; 
    String romanNumber; 
    
    public RomanNumeral(int dec, String rom) {
        this.number = dec;
        this.romanNumber = rom;
    }
    
    public RomanNumeral(int dec){
        this.number = dec;
        this.romanNumber = convertIntegerToRoman(dec);
    }
    public RomanNumeral(){
        this.number = -1;
        this.romanNumber = "";
    }

    public static String convertIntegerToRoman(int n) 
                                                throws IllegalArgumentException{
        if (n >= 5000 || n < 1) {
            throw new IllegalArgumentException("Numbers must be in range 1-4999");
        }
        StringBuilder sb = new StringBuilder();
        for (RomanNumeral rvalue : table) {
            while (n >= rvalue.number) {
                n -= rvalue.number; 
                sb.append(rvalue.romanNumber); 
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "number=" + number + ", romanNumber=" + romanNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RomanNumeral other = (RomanNumeral) obj;
        if (this.number != other.number) {
            return false;
        }
        if ((this.romanNumber == null) ? (other.romanNumber != null) : !this.romanNumber.equals(other.romanNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.number;
        return hash;
    }

    public int getNumber() {
        return number;
    }

    public String getRomanNumber() {
        return romanNumber;
    }
    
    
            
}
