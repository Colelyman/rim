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
    static List<Referral> referrals = new ArrayList();
    
    public DataToCsv(List referrals){
        this.referrals = referrals;
    }
   
 
   public void GenerateCsvFile(String file) {
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
                writer.append(referrals.get(i).getZone() + ','); //Assigned zone
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
   
   public boolean pushToGoogleSheets() throws AuthenticationException, MalformedURLException, IOException, ServiceException {
       try {
            SpreadsheetService service = new SpreadsheetService("RimPro 2.0");
            service.setUserCredentials("belnethreferral@gmail.com", "Moroni10"); // authorization
            URL url = new URL("https://spreadsheets.google.com/feeds/worksheets/1cagRTmhzJT2eKmvq8m_-mpAVJDjsemY_tOhmsYhajjQ/private/full"); // fetch spreadsheet
            WorksheetFeed feed = service.getFeed(url, WorksheetFeed.class); // gets worksheets in SpreadSheet
            List<WorksheetEntry> worksheets = feed.getEntries(); // puts worksheets into List
            WorksheetEntry allZones = worksheets.get(0);
            if(!worksheets.get(0).getTitle().getPlainText().equals("All Zones")) { // assigns allZones to "All Zones" worksheet
                for(int i = 1; i < worksheets.size(); i++) {
                    if(worksheets.get(i).getTitle().getPlainText().equals("All Zones"))
                        allZones = worksheets.get(i);
                }
            }
            
            URL listFeedUrl = allZones.getListFeedUrl();
            ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
            for(Referral ref : referrals) {
                ListEntry row = new ListEntry();
                row.getCustomElements().setValueLocal("Referral", ref.getId());
                row.getCustomElements().setValueLocal("Name", ref.getName());
                row.getCustomElements().setValueLocal("Address1", ref.getStreetName());
                row.getCustomElements().setValueLocal("Address2", ref.getFullPostcode());
                row.getCustomElements().setValueLocal("City", ref.getCity());
                row.getCustomElements().setValueLocal("Country", ref.getCountry());
                row.getCustomElements().setValueLocal("Phone", ref.getPhone());
                row.getCustomElements().setValueLocal("Area", ref.getAssignedArea());
                row.getCustomElements().setValueLocal("ReferralSentTo", ref.getAreaPhone().toString());
                row.getCustomElements().setValueLocal("Zone", ref.getZone());
                if(ref.isSent())
                    row.getCustomElements().setValueLocal("SentOrNotSent", "SENT");
                else
                    row.getCustomElements().setValueLocal("SentOrNotSent", "NOT SENT");

                row = service.insert(listFeedUrl, row);
            }
        } catch(Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
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
}