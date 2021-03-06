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
package com.gaspard.chiarafile.you;

/**
 *
 * @author gaspard
 */
public class ConfigurationChiara {
 public static String nodeip = "127.0.0.1";  // put your ip cassandra node here
 public static String user = "changeit";   
 public static String password = "changeit";
 public static String hostname = "chiarafiles.org";
 public static String userchiaradatabase =  "changeit"   ;
 public static String passwordchiaradatabase = "changeit";

    public  String getUserchiaradatabase() {
        return userchiaradatabase;
    }

    public  void setUserchiaradatabase(String userchiaradatabase) {
        ConfigurationChiara.userchiaradatabase = userchiaradatabase;
    }

    public static String getPasswordchiaradatabase() {
        return passwordchiaradatabase;
    }

    public static void setPasswordchiaradatabase(String passwordchiaradatabase) {
        ConfigurationChiara.passwordchiaradatabase = passwordchiaradatabase;
    }
 
 
   public String getip () {
     return this.nodeip;
  } 
 public String getpass () {
 return this.password;
 }
 
 public String getuser () {
 return this.user;
 
 }
 public String gethostname() {
 return this.hostname;
 }
 
}
