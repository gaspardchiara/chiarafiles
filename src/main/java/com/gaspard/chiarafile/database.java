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

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.Bytes;
import com.gaspard.chiarafile.data.chunkdata;
import com.gaspard.chiarafile.you.ConfigurationChiara;
import com.gaspard.chiarafile.you.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author gaspard
 */
public class database {

    private Cluster cluster;
    private Session session;
    public String useragent;
    public String ip;
    public int size;
    public String filename;
    public String type;

    public Part file;

    public void connectsecure(String node, String user, String pass) {
        cluster = Cluster.builder().addContactPoint(node).withCredentials(user, pass).build();
    }

    public void connect(String node) {
        cluster = Cluster.builder().addContactPoint(node).build();

    }

    public void getSession() {
        session = cluster.connect();
    }

    public void closeSession() {
        session.close();
    }

    public void close() {
        cluster.close();
    }

    public int deletefilewithid(String idelete, String pass) {

        ConfigurationChiara chiaraconfig = new ConfigurationChiara();

        connectsecure(chiaraconfig.getip(), chiaraconfig.getuser(), chiaraconfig.getpass());

        getSession();
        UUID idcast = UUID.fromString(idelete);
        UUID idpass = UUID.fromString(pass);

        File check = getfilewithidwithoutcreateco(idelete);
        if (check.delfile != null && check.delfile.equals(idpass)) {
            System.out.println("Delete ok");
            PreparedStatement statement = session
                    .prepare("DELETE  FROM chiara.files WHERE ID=?  IF EXISTS ;");

            BoundStatement boundStatement = new BoundStatement(statement);

            ResultSet res;
            res = session.execute(boundStatement.bind(
                    idcast
            ));

            close();

        } else {
            close();
            return 0;
        }

        return 1;
    }

    public File getfilewithidwithoutcreateco(String id) {

        PreparedStatement statement = session
                .prepare("SELECT * FROM chiara.files WHERE ID=?  ;");

        BoundStatement boundStatement = new BoundStatement(statement);
        UUID idcast = UUID.fromString(id);

        ResultSet res;
        res = session.execute(boundStatement.bind(
                idcast
        ));
        Row d = res.one();

        File end = new File();
        if (d == null) {
            end.idfichier = null;
            return end;
        } else {

            end.idfichier = d.getUUID("id");
            end.date = d.getString("date");
            end.size = d.getInt("size");
            end.type = d.getString("type");
            end.name = d.getString("name");
            end.delfile = d.getUUID("idelete");
            end.file = d.getBytes("data");
        }

        return end;

    }

    public File getfilewithid(String id) {
        ConfigurationChiara chiaraconfig = new ConfigurationChiara();

        connectsecure(chiaraconfig.getip(), chiaraconfig.getuser(), chiaraconfig.getpass());

        getSession();
        PreparedStatement statement = session
                .prepare("SELECT * FROM chiara.files WHERE ID=?  ;");

        BoundStatement boundStatement = new BoundStatement(statement);
        UUID idcast = UUID.fromString(id);

        ResultSet res;
        res = session.execute(boundStatement.bind(
                idcast
        ));
        Row d = res.one();

        File end = new File();
        if (d == null) {
            end.idfichier = null;
            return end;
        } else {

            end.idfichier = d.getUUID("id");
            end.date = d.getString("date");
            end.size = d.getInt("size");
            end.type = d.getString("type");
            end.name = d.getString("name");
            end.delfile = d.getUUID("idelete");
            end.file = d.getBytes("data");
        }

        close();

        return end;

    }

    public File checkidfile(String id) {

        ConfigurationChiara chiaraconfig = new ConfigurationChiara();

        connectsecure(chiaraconfig.getip(), chiaraconfig.getuser(), chiaraconfig.getpass());

        getSession();
        PreparedStatement statement = session
                .prepare("SELECT * FROM chiara.files WHERE ID=?  ;");

        BoundStatement boundStatement = new BoundStatement(statement);
        UUID idcast = UUID.fromString(id);

        ResultSet res;
        res = session.execute(boundStatement.bind(
                idcast
        ));
        Row d = res.one();

        File end = new File();
        if (d == null) {
            end.idfichier = null;
            return end;
        } else {

            end.idfichier = d.getUUID("id");
            end.date = d.getString("date");
            end.size = d.getInt("size");
            end.type = d.getString("type");
            end.delfile = d.getUUID("idelete");
            end.name = d.getString("name");

        }

        close();

        return end;
    }

    public void parsefilereceive(database filestruct, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Part filePart = filestruct.file;
        String fileName = "";
        fileName = filePart.getSubmittedFileName();
        String type = filePart.getContentType();
        long size = filePart.getSize();
        if (fileName.length() == 0) {
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);

            return;

        }
        if (size >= 320000000) {

            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            return;
        } else if (size == 0) {
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);

            return;
        }
        filestruct.filename = fileName;
        filestruct.type = type;
        filestruct.size = (int) size;

        File filereturnconfig = addrecordfile(filestruct);

        if (filereturnconfig == null) {
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);

            return;

        }
        ConfigurationChiara configtopic = new ConfigurationChiara();
        String hostname = configtopic.gethostname();
        request.setAttribute("filename", filereturnconfig.name);
        String deleteink = "" + configtopic.gethostname() + "/deletefile?id=" + filereturnconfig.idfichier + "&pass=" + filereturnconfig.delfile + "";
        request.setAttribute("displaydellink", deleteink);
        request.setAttribute("dellink", "/deletefile?id=" + filereturnconfig.idfichier + "&pass=" + filereturnconfig.delfile + "");

        request.setAttribute("displaysharelink", "" + hostname + "/download?id=" + filereturnconfig.idfichier);
        request.setAttribute("sharelink", "/download?id=" + filereturnconfig.idfichier);
        request.getRequestDispatcher("/WEB-INF/succes.jsp").forward(request, response);
    }

    public void addblobchunk(File filestruct, byte[] blob) {
        ConfigurationChiara chiaraconfig = new ConfigurationChiara();
        connectsecure(chiaraconfig.getip(), chiaraconfig.getuser(), chiaraconfig.getpass());
        getSession();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        String todayAsString = dateFormat.format(date);

        ByteBuffer bufferblob = ByteBuffer.wrap(blob);
        PreparedStatement statement = session
                .prepare("INSERT INTO chiara.files "
                        + "(id, name, data,size,date,type,idelete,insertid) "
                        + "VALUES (?, ?, ?,?,?,?,?,?);");
        UUID insertid = UUID.randomUUID();

        BoundStatement boundStatement = new BoundStatement(statement);
        ResultSet res;
        res = session.execute(boundStatement.bind(
                filestruct.idfichier,
                filestruct.name, bufferblob, filestruct.size, filestruct.date, filestruct.type, filestruct.delfile, insertid));
        System.out.println("Demande denvoie d'un ficier blob");

        close();
    }

    public void adduser(database filestruct, UUID FIchierid) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        String todayAsString = dateFormat.format(date);
        PreparedStatement statement = session
                .prepare("INSERT INTO chiara.users "
                        + "(idfichier, ip, useragent,date,iduser) "
                        + "VALUES (?, ?, ?,?,?);");

        BoundStatement boundStatement = new BoundStatement(statement);
        UUID userid = UUID.randomUUID();

        ResultSet res;
        res = session.execute(boundStatement.bind(
                FIchierid,
                filestruct.ip, filestruct.useragent, todayAsString, userid));

    }

    public File addrecordfile(database filestruct) throws IOException {
        chunkdata testchunk = new chunkdata();

        File end = new File();
        ConfigurationChiara chiaraconfig = new ConfigurationChiara();

        connectsecure(chiaraconfig.getip(), chiaraconfig.getuser(), chiaraconfig.getpass());
        getSession();
        Part filePart = filestruct.file;
        InputStream fichier = filePart.getInputStream();
        byte[] b = new byte[fichier.available() + 1];
        int length = b.length;
        fichier.read(b);
        ByteBuffer blob = ByteBuffer.wrap(b);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        String todayAsString = dateFormat.format(date);

        PreparedStatement statement = session
                .prepare("INSERT INTO chiara.files "
                        + "(id, name, data,size,date,type,idelete) "
                        + "VALUES (?, ?, ?,?,?,?,?);");

        BoundStatement boundStatement = new BoundStatement(statement);
        UUID fileid = UUID.randomUUID();
        UUID uuidel = UUID.randomUUID();

        end.idfichier = fileid;
        end.type = filestruct.type;
        end.name = filestruct.filename;
        end.delfile = uuidel;

        ResultSet res;
        res = session.execute(boundStatement.bind(
                fileid,
                filestruct.filename, blob, filestruct.size, todayAsString, filestruct.type, uuidel));
// ajouter utilisateur
        if (res != null) {
            adduser(filestruct, fileid);
        } else {
            close();
            end = null;
            return end;
        }
        close();

        return end;
    }

    public void setdatabaseconfig() {
        session.execute("CREATE KEYSPACE IF NOT EXISTS chiara WITH replication "
                + "= {'class':'SimpleStrategy', 'replication_factor':1};");
        session.execute("CREATE TABLE IF NOT EXISTS chiara.files ("
                + "id uuid PRIMARY KEY," + "name text," + "size int,"
                + "data blob," + "date text," + "type text," + "idelete uuid" + ");");

        session.execute("CREATE TABLE IF NOT EXISTS chiara.users ("
                + "idfichier uuid PRIMARY KEY," + "ip text," + "useragent text,"
                + "date text," + "iduser uuid" + ");");
    }

}
