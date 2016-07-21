/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import classes.Bookmark;
import classes.User;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JOptionPane;
//import org.apache.derby.jdbc.ClientDataSource;
/**
 *
 * @author Airinei
 */
public class server extends javax.swing.JFrame {
    
    private Connection conn;
    private ServerSocket servSocket;
    private Socket socket;
    private OutputStream outputStream;
    private ObjectOutputStream fout;
    private InputStream inputStream;
    private ObjectInputStream fin;
    private boolean started;
    
    /**
     * Creates new form server
     */
    public server() {
        initComponents();
        
        jOut.append("Server application started...\n");
        logs("Server application started...\n");
        //Database coonection
        try {
             jOut.append("Connecting to database...\n");
             logs("Connecting to database...\n");
             //derby
//            MysqlDataSource ds = new MysqlDataSource();
//            ds.setServerName("localhost");
//            ds.setUser("server");
//            ds.setPassword("server");
//            ds.setDatabaseName("browserUsers");
//            ds.setPortNumber(1527);
            
            
            //mysql
            Properties prop = loadDBproperties("/resources/bdProp.properties");
            MysqlDataSource ds = new MysqlDataSource();
//            
//            ds.setServerName("db4free.net");//prop.getProperty("serverName"));
//            ds.setUser("airinei17");//prop.getProperty("user"));
//            ds.setPassword("server");//prop.getProperty("password"));
//            ds.setDatabaseName("browser");//prop.getProperty("databaseName"));
//            
            ds.setServerName(prop.getProperty("serverName"));
            ds.setUser(prop.getProperty("user"));
            ds.setPassword(prop.getProperty("password"));
            ds.setDatabaseName(prop.getProperty("databaseName"));
            
            
            ds.setPortNumber(3306);
            conn = ds.getConnection();
            jOut.append("Connected to Database\n");
            logs("Connected to Database\n");
            
        } catch (Exception ex) {
            jOut.append(ex + "\n");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnStart = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jOut = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnStart.setText("START SERVER");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        jOut.setColumns(20);
        jOut.setRows(5);
        jScrollPane1.setViewportView(jOut);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
       
        if (!started) {
            try {
                servSocket = new ServerSocket(2000);
                socket = servSocket.accept();
                
                jOut.append("Server running...\n");
                logs("Server running...\n");
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                
                fin = new ObjectInputStream(inputStream);
                fout = new ObjectOutputStream(outputStream);
                
                started = true;
                
                Thread responseThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        response();
                    }
                });
                
                responseThread.start();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }

        } else {
            JOptionPane.showMessageDialog(this, "Server already started!");
            logs("Server already started!");
        }

    }//GEN-LAST:event_btnStartActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new server().setVisible(true);
            }
        });
    }
    
    public void logs(String log){
         try{
            
            //Specify the file name and path here
            File file =new File("log.txt");

            /* This logic is to create the file if the
             * file is not already present
             */
            if(!file.exists()){
               file.createNewFile();
    	}

    	//Here true is to append the content to file
    	FileWriter fileW = new FileWriter(file,true);
    	//BufferedWriter writer give better performance
    	BufferedWriter bw = new BufferedWriter(fileW);
        
        java.util.Date date= new java.util.Date();
	log=log + " " + (new Timestamp(date.getTime())).toString() + " \n";
       
    	bw.write(log);
        bw.newLine();
    	//Closing BufferedWriter Stream
    	bw.close();

	System.out.println("Log created...");

      }catch(IOException ioe){
         System.out.println("Exception ");
    	 ioe.printStackTrace();
       }
    }
    
    public Properties loadDBproperties(String pFile){
        
        Properties prop = new Properties();
	InputStream input = null;

	try {

		input = getClass().getResourceAsStream(pFile);
		prop.load(input);
		
                jOut.append("Properties loaded from " + pFile + "\n");
                logs("Properties loaded from " + pFile + "\n");

	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        
        return prop;
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
        // TODO add your handling code here:
        try {
            conn.close();
        } catch (Exception ex) {
        }
        try {
            servSocket.close();
        } catch (Exception ex) {
        }
        try {
            socket.close();
        } catch (Exception ex) {
        }
    }                                       

    private void response() {
        while (true) {
            try {
                if (inputStream.available() != 0) {
                    jOut.append("\n\n...................................\n");
                    jOut.append("New request received\n");
                    logs("New request received\n");
                    //Decoding the username and password recieved
                    String userCredentials = (String) fin.readObject();
                    jOut.append(userCredentials+"\n");
                    String[] tmp=userCredentials.split("/");
                    String x=tmp[0];
                    String username=tmp[1];
                    String password=tmp[2];
                    jOut.append("--");
                    
                    switch(x){
                        case "1":
                            
                            logare(username,password);
                            break;
                        case "2":
                            
                            creareCont(username,password);
                            break;
                    }
                    
                }
                else {
                    Thread.sleep(200);
                }
            } catch (Exception ex) {
                jOut.append(ex + "\n");
            }
        }

    }
    
    public void creareCont(String username, String password) throws IOException, SQLException{
        jOut.append("\nCreating account...");
        logs("\nCreating account...");
        Statement sttm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE);
        ResultSet r1= sttm.executeQuery("select COUNT(*) from USERS ");
        r1.first();
        
        int id=r1.getInt(1);
        id++;
        
        sttm.executeUpdate("insert into USERS values (" + id + ",'" + username + "','" + password +"',0)");
        
        
        fout.writeObject(new String("OK"));
        jOut.append("\nAccount created, press LogIn..");
        logs("\nAccount created, press LogIn..");
    }
    
    public void logare(String username, String password) throws SQLException, IOException{
        jOut.append("Username recieved is " + username + " and password " + password + ".\n");
        logs("Username recieved is " + username + " and password " + password + ".\n");
        //Declare statement
        Statement sttm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE);
        jOut.append("Checking password...\n");
        logs("Checking password...\n");
        //Quering the DB to obtain the password
        ResultSet pass = sttm.executeQuery("select * from USERS where username like '%"
                + username + "%'");
        pass.first();
        jOut.append("Veryfing matching...\n");
        logs("Veryfing matching...\n");
        String passw =pass.getString(3);
        pass.close();

        //Checking if the password is OK
        if(passw.equals(password)){

            //Messages to see if all is alright
            jOut.append("Username and password match...\n");
            logs("Username and password match...\n");
            fout.writeObject(new String("OK"));

            //Extract user from DB
            ResultSet r = sttm.executeQuery("select * from USERS where username like '%"
                    + username + "%'");
            r.first();

            //Create User Object
            User user = new User(r.getInt(1),r.getString(2),r.getString(3),r.getInt(4));
            r.close();

            //Extract bookmarks in an ArrayList
            ArrayList<Bookmark> bks = new ArrayList<Bookmark>();
            ResultSet bk = sttm.executeQuery("select * from BOOKMARKS where userId ="
                    + user.getId() );
            bk.beforeFirst();

            //Adding every bookmark from the DB to the ArrayList
            while (bk.next()) {
                Bookmark b = new Bookmark(bk.getInt(1),bk.getString(2),bk.getString(3),bk.getInt(4));
                bks.add(b);
            }
            bk.close();

            //Adding the bookmarks to the user object
            user.setBookmarks(bks);

            //Write the Object to the socket
            fout.writeObject(user);
        }else{
            jOut.append("Username and password don't match...\n");
            logs("Username and password don't match...\n");
            fout.writeObject(new String("WRONG"));
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JTextArea jOut;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
