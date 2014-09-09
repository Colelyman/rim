package rimpro.pkg2.pkg0;

/*
 * Copyright (C) 2014 Secretary
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author Boatswain & Record Keeper
 */
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Referral {
    private String name, streetName, city, country, assignedArea, postcodeLetters, postCode, email, phone, zone;
    private Long areaPhone;
    private Integer number;
    private String date, id, fullPostcode;
    private SimpleDateFormat dateFormat;
    private boolean sent, valid;
    private static int counter = 0;

     public Referral() {
        this.areaPhone = new Long(0);
        this.dateFormat = new SimpleDateFormat("dd/M/yy-HH:mm");
        this.date = dateFormat.format(new Date());
        this.id = date;
        this.sent = false;
        this.valid = false;
        this.id += "-" + counter;
        this.zone = "null";
        counter++;
    }
    
    
    public Referral(String name, String address, Integer number, String postCode, String city, String country, String phone, String postcodeLetters, String email) {
        this.areaPhone = new Long(0);
        this.name = name;
        this.streetName = address;
        this.number = number;
        this.postCode = postCode;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.postcodeLetters = postcodeLetters;
        this.email = email;
        this.dateFormat = new SimpleDateFormat("dd/M/yy-HH:mm");
        this.date = dateFormat.format(new Date());
        this.id = date;
        this.sent = false;
        this.valid = false;
        this.id += "-" + counter;
        this.zone = "null";
        counter++;
    }

    public Integer getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public boolean isNLD() { // returns true if the country does not begin with 'b'
        try {
            if(country.equals("NO COUNTRY") || country.length() == 0) {
                throw new myException("The country is invalid!! Referral id: " + this.id);
            }
        }
        catch (myException e) {
            System.out.println(e);
            return false;
        }
        if(country.substring(0, 1).equals("b"))
            return false;
        else
            return true;
    }
    
    public String getCountry() {
        return country;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate() {
        return date;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getAddress(){
        return this.getName() + "\n" + this.getStreetName() + "\n" + this.getPostCode() + this.getPostcodeLetters() + " " + this.getCity() + "\n" + this.getCountry()+ "\n0" + this.getPhone() + "\n" + this.getEmail();
    }
    
    public Long getAreaPhone(){
        return areaPhone;
    }

    public String getAssignedArea() {
        return assignedArea;
    }

    public String getPostcodeLetters() {
        if(this.isNLD())
            return postcodeLetters;
        else
            return "";
    }
    
    public String getFullPostcode() {
        return fullPostcode;
    }
    
    public String getZone() {
        if(this.isValid())
            return zone;
        else
            return "null";
    }
    
    public boolean isSent() {
        return this.sent;
    }
    
    public boolean isValid() {
        return this.valid;
    }

    public void setFullPostcode(String fullPostcode) {
        this.fullPostcode = fullPostcode;
        try {
            for(int i = 0; this.fullPostcode.length() >= 4 && i < 4; i++) {
                if(!this.fullPostcode.substring(i, i + 1).matches("[0-9]") || this.fullPostcode.substring(i, i + 1).matches("\\s")) {
                    this.setPostCode("0000");
                    throw new myException("Invalid postcode, the first four characters are not numbers. The postcode given was: " + fullPostcode);
                }
            }
            
            if(this.fullPostcode.length() < 4) {
                setPostCode("0000");
                setValid(false);
                throw new myException("Invalid postcode, postcode is too short. The postcode given was: " + fullPostcode);
            }
            setPostCode(this.fullPostcode.substring(0, 4));
            if (this.fullPostcode.length() == 4)
                return;
            else if (this.fullPostcode.length() > 5) { // if true, then the postcode has letters
                if(this.fullPostcode.length() == 6) // no space between the letters
                    setPostcodeLetters(this.fullPostcode.substring(4,6));
                else if(this.fullPostcode.length() == 7) // space between the letters
                    setPostcodeLetters(this.fullPostcode.substring(5,7));
                else 
                    throw new myException("Invalid postcode, problem reading the letters.");
                this.fullPostcode = this.postCode.toString() + " " + this.postcodeLetters;
            }
            
            else {
                setPostCode("0000");
                setValid(false);
                throw new myException("Invalid postcode, postcode too short.");
            }
        } catch (myException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = Character.toUpperCase(country.charAt(0)) + country.substring(1);
    }

    public void setAssignedArea(String assignedArea) {
        this.assignedArea = assignedArea;
    }

    public void setPostcodeLetters(String postcodeLetters) {
        this.postcodeLetters = postcodeLetters;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAreaPhone(Long assignedNumber) {
        this.areaPhone = assignedNumber;
    }
    
    public void setSent(boolean sent) {
        this.sent = sent;
    }
    
    public void setValid(boolean valid) {
        this.valid = valid;
    } 
    
    public void setZone(String zone) {
        this.zone = zone;
    }
    
    public void print() {
        System.out.println("Name: " + this.name);
        System.out.println("Address: " + this.streetName);
        System.out.println("City: " + this.city + " " + this.postCode.toString());
        System.out.println("Phone number: " + this.phone);
        System.out.println("Country: " + this.country);
        System.out.println("Date: " + this.date + " ID: " + this.id);
        System.out.println("Area phone: " + this.areaPhone);
    } //can't these all be replaced by getAddress??
    
    @Override
    public String toString() {
        String str = "Name: " + this.name;
        str += "\nAddress: " + this.streetName;
        str += "\nCity: " + this.city + " " + this.getFullPostcode();
        str += "\nPhone: " + this.phone;
        str += "\nCountry: " + this.country;
        str += "\nID: " + this.id;
        
        return str;
    }
}
