/*
 * Copyright (C) 2020 gaspard chiara
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
package com.gaspard.chiarafile;

import com.gaspard.chiarafile.you.ConfigurationChiara;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import javax.servlet.http.HttpServlet;



/**
 *
 * @author gaspard
 */
public class schedule extends HttpServlet {

  public void createdatabaseschema () {
  
   database x = new database ();
      ConfigurationChiara  chiaraconfig = new ConfigurationChiara();  
      x.connectsecure(chiaraconfig.getip(),chiaraconfig.getuser(),chiaraconfig.getpass());
  x.getSession();
 x.setdatabaseconfig();
  
  x.close();
  }

    /**
     *
     * @throws ServletException
     * @throws SchedulerException
     */
    public void init() 
    {     

     createdatabaseschema();

    }

}
