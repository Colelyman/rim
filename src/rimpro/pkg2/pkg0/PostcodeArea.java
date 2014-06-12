/*
 * Copyright (C) 2014 DandC89
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
import java.io.*;

/**
 *
 * @author Boatswain & Record Keeper
 */
public class PostcodeArea {
    
    public String getArea(int postcode, boolean nederland) { // nederland == true, in the Netherlands. nederland == false, in Belgium
        String area = "null";
        String fileName = "Z:\\12 Supervisor\\Referral Improvement Program\\RImPro 2.0\\src\\rImpro\\pkg2\\pkg0\\Ward_postcode_index_2_without_postcode.csv";
        if(!nederland) // if nederland == false then it is in Belgium
            fileName = "Z:\\12 Supervisor\\Referral Improvement Program\\RImPro 2.0\\src\\rImpro\\pkg2\\pkg0\\Ward_postcode_index_2_without_postcode_belgium.csv";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            for (int i = 0; i < (postcode - 999); i++)
                area = reader.readLine();
            if(area.equals("null"))
                throw new myException("Post Code: " + postcode + " is not valid");
        } catch (myException|IOException e) { // custom Exception
            System.out.println(e.toString());
        }
        return area;
    }

}


