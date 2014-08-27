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

package rimpro.pkg2.pkg0;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author Boatswain & Record Keeper
 */
public class Area_number { //readFile() must be called before class can be used
    
    private TreeMap<String, List<Long> > phoneMap = new TreeMap();
    
    public void readFile() {       
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Z:\\12 Supervisor\\Referral Improvement Program\\RImPro 2.0\\src\\rImpro\\pkg2\\pkg0\\Area_phone_index.csv"));
            String areaPhone;
            while ((areaPhone = reader.readLine()) != null) {
                List<String> line = Arrays.asList(areaPhone.split(","));
                List<Long> phoneList = new ArrayList();
                String area = line.get(0);
                phoneList.add(Long.parseLong(line.get(1)));
                if(line.size() >= 3)
                    phoneList.add(Long.parseLong(line.get(2)));
                if(line.size() == 4)
                    phoneList.add(Long.parseLong(line.get(3)));
                phoneMap.put(area, phoneList);
            }
            reader.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    
    public Long chooseNumber(String assignedArea){
        List<Long> temp = phoneMap.get(assignedArea);
        Random r = new Random();
        return temp.get(r.nextInt(temp.size()));
    }
    
    public void print() {
        for(Map.Entry<String, List<Long> > entry : phoneMap.entrySet()) {
            System.out.println(entry.getKey());
            for(Long numberList : entry.getValue())
                if(numberList != null)
                    System.out.println(numberList.toString());
        }
    }
}