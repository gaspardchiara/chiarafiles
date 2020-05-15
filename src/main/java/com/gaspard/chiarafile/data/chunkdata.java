/*
 * Copyright (C) 2020 gaspard
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
package com.gaspard.chiarafile.data;

import com.gaspard.chiarafile.database;
import com.gaspard.chiarafile.you.ConfigurationChiara;
import com.gaspard.chiarafile.you.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author gaspard
 */
// Test of chunk data 
public class chunkdata {
    
 public File generatefilestruct (database filestruct) {
 File end  = new File();
 UUID fileid = UUID.randomUUID();
  UUID uuidel = UUID.randomUUID();
Part filepart = filestruct.file;
      DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  Date date = new Date();

 String todayAsString = dateFormat.format(date);
 
     end.type = filestruct.type;
     end.name = filestruct.filename;
     end.delfile = uuidel;
     end.size = (int)filepart.getSize();
      end.idfichier = fileid ;      
end.type = filepart.getContentType();
  end.name = filepart.getSubmittedFileName();  
     end.date =todayAsString ;
     
     

     
     
 return end;
 }   
    
    
   public int parsefilereceivetobyte (database filestruct,HttpServletRequest request, HttpServletResponse response) throws IOException {
    Part x = filestruct.file;
    File filestructreal = generatefilestruct(filestruct);
    InputStream filetobyte = x.getInputStream();
            int bytetoread =   filetobyte.available();
int decoupageeffectif = bytetoread/2000;
database functionx = new database();

    byte decoupage[]= new byte[bytetoread];
    decoupage  =IOUtils.toByteArray(filetobyte);
    System.out.println("nombre de cdecoupage en bytes ");
        System.out.println(decoupageeffectif);

for (int y = 0;y<decoupageeffectif;y++) {

byte[] writetodatabase= new byte[2000];

for(int z = 0;z<2000;z++) {

writetodatabase[z] =decoupage[y+z];
}
//System.out.println(writetodatabase[y]);
// write here to database the chunk data we write to database : writetodatabase
functionx.addblobchunk(filestructreal,writetodatabase);

}

    return 0;
    }
    
}
