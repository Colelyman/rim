//license header

package rimpro.pkg2.pkg0;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Boatswain & Record Keeper
 */
public class DataToCsv {
    List<Referral> referrals = new ArrayList();
    
    public DataToCsv(List referrals){
        this.referrals = referrals;
    }
 
   public void GenerateCsvFile() {
	try
	{
	    FileWriter writer = new FileWriter("Z:\\12 Supervisor\\Referral Improvement Program\\ReferralLog.csv", true);
            for (int i = 0; i < referrals.size(); i++){
                writer.append(referrals.get(i).getId() + ','); //ReferralID
                writer.append(referrals.get(i).getName() + ','); //Name
                writer.append(referrals.get(i).getStreetName() + ','); //Street name
                writer.append(referrals.get(i).getFullPostcode() + ','); //Postcode
                writer.append(referrals.get(i).getCity() + ','); //City
                writer.append(referrals.get(i).getCountry() + ','); //Country
                writer.append(referrals.get(i).getPhone() + ','); //Phone number
                writer.append(referrals.get(i).getAssignedArea() + ','); //Assigned area
                writer.append(referrals.get(i).getZone() + ','); //Zone
                if(referrals.get(i).isValid())
                    writer.append(referrals.get(i).getAreaPhone().toString() + ','); //The phone number that the referral is sent to
                if(referrals.get(i).isSent())
                    writer.append("SENT,\n");
                else
                    writer.append("NOT SENT,\n");
            }
 
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 
    }
   
   public void convertBelgPostcodes() {
       try {
           FileWriter writer = new FileWriter("Z:\\12 Supervisor\\Referral Improvement Program\\Generated_Belgian_Postcodes.csv", false);
           BufferedReader reader = new BufferedReader(new FileReader("Z:\\12 Supervisor\\Referral Improvement Program\\Belgium_raw.csv"));
           String str, city;
           int code = 0;
           int current = 1000;
           while((str = reader.readLine()) != null) {
               code = Integer.parseInt(str.split(",")[0]);
               city = str.split(",")[1];
               if(code == current) {
                   writer.append(code + "," + city + '\n');
                   current++;
               }
               else {
                   while(code != current) {
                       writer.append(current + "," + "null" + '\n');
                       current++;
                   }
                   writer.append(code + "," + city + '\n');
                   current++;
               }
           }
       }
       catch(IOException e)
       {
           e.printStackTrace();
       }
       
   }
   public void main (String[] args) {
       convertBelgPostcodes();
   }
}