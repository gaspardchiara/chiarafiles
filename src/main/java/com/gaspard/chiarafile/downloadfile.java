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


import com.gaspard.chiarafile.you.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import static java.util.Arrays.stream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gaspard
 */
public class downloadfile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // this class download the file to the client
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("id");
                if (id.length() >= 2 && id.length() <= 50 ) { 
                database data = new database();    
                    File end = data.getfilewithid(id);
  if (end.idfichier !=null ) {
// interesting web site :  http://ac31004.blogspot.com/2014/03/saving-image-in-cassandra-blob-field.html
   
OutputStream fileout = response.getOutputStream();
response.setContentType(end.type);
response.setContentLength(end.size);
String headertext = "Content-Disposition";
 String name = String.format("attachment; filename=\"%s\"", end.name);
                response.setHeader(headertext, name);
// issue resolved : https://stackoverflow.com/questions/579600/how-to-put-the-content-of-a-bytebuffer-into-an-outputstream
 WritableByteChannel channel = Channels.newChannel(fileout);

   channel.write(end.file);

fileout.close();
  } else {
          response.sendRedirect("/");

  }                  
                    
                    
                    
                    
                } else {
                
        response.sendRedirect("/");
                
                
                }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
