//license header

package rimpro.pkg2.pkg0;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;
import java.net.URL;
import java.net.MalformedURLException;


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
                writer.append(referrals.get(i).getPostCode() + referrals.get(i).getPostcodeLetters() + ','); //Postcode
                writer.append(referrals.get(i).getCity() + ','); //City
                writer.append(referrals.get(i).getCountry() + ','); //Country
                writer.append(referrals.get(i).getPhone() + ','); //Phone number
                writer.append(referrals.get(i).getAssignedArea() + ','); //Assigned area
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
   
   public static boolean pushToGoogleSheets() throws AuthenticationException, MalformedURLException, IOException, ServiceException {
       try {
            SpreadsheetService service = new SpreadsheetService("RimPro 2.0");
            System.out.println("After service");
            service.setUserCredentials("belnethreferral@gmail.com", ""); // authorization
            System.out.println("Authorized");
            URL url = new URL("https://spreadsheets.google.com/feeds/spreadsheets/1cagRTmhzJT2eKmvq8m_-mpAVJDjsemY_tOhmsYhajjQ/private/full");
            SpreadsheetFeed feed = service.getFeed(url, SpreadsheetFeed.class);
            List<SpreadsheetEntry> spreadsheets = feed.getEntries();
            SpreadsheetEntry spreadsheet = spreadsheets.get(0);
            System.out.println("Accessing spreadsheet: " + spreadsheet.getTitle().getPlainText());
            /*List<WorksheetEntry> worksheets = spreadsheet.getWorksheets();
            WorksheetEntry worksheet = worksheets.get(0);
            if(worksheet.getTitle().getPlainText().equals("All Zones")) { // check to see if the worksheet is correct

            }
            else { // worksheet was not correct

            }*/
        } catch(Exception e) {
            System.out.println(e.toString());
        }
       
       return false;
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
   public static void main (String[] args) {
       try {
           pushToGoogleSheets();
       } catch(Exception e) {
           System.out.println(e.toString());
       }
       
   }
}