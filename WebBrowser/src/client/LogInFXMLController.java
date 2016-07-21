/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import classes.Bookmark;
import classes.User;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import webbrowser.WebBrowserController;

/**
 * FXML Controller class
 *
 * @author Airinei
 */
public class LogInFXMLController implements Initializable {
    
    
    private Socket soc;
    private OutputStream outPut;
    private ObjectOutputStream fout;
    private InputStream in;
    private ObjectInputStream fin;
    private boolean conectat;
    private User usr;
    
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private Button connect;
    @FXML
    private Button login;
    @FXML
    private TextArea console;
    @FXML
    private Button openBrowser;
    @FXML
    private Button createAccout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        openBrowser.setVisible(false);
    }    

    @FXML
    private void handlerConnectAction(ActionEvent event) {
        if (!conectat) {
            try {
                soc = new Socket("localhost", 2000);
                outPut = soc.getOutputStream();
                fout = new ObjectOutputStream(outPut);
                in = soc.getInputStream();
                fin = new ObjectInputStream(in);
                conectat = true;
                console.appendText("Connected to server:  " + soc.getLocalAddress()+ ":" +soc.getPort()+"\n");
                        
            } catch (Exception ex) {
                console.appendText(ex.toString());
            }
        }else{
            console.appendText("Connexiune deja existenta\n");
        }
    }

    @FXML
    private void handlerLoginAction(ActionEvent event) {
        try {
            String user="1/"+usernameTxt.getText()+"/"+passwordTxt.getText();
            fout.writeObject(user);
            
            String result = (String) fin.readObject();
            
            if(result.equals("OK")){
                console.appendText("Username or password match fine...\n");
                usr = (User) fin.readObject();
                console.appendText(usr + "\n");  
                openBrowser.setVisible(true);
                login.setVisible(false);
                createAccout.setVisible(false);
            }else{
                console.appendText("Username or password don't match...\n");
            }
            console.appendText("\n");
        } catch (Exception ex) {
            console.appendText(ex.toString());
        }
    }

    @FXML
    private void handlerOpenBrowser(ActionEvent event) {
        try {
                //WebBrowserController controller= new WebBrowserController("vlad");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/webbrowser/WebBrowser.fxml"));
                
        
                Parent root1 = (Parent) fxmlLoader.load();
                
                WebBrowserController controller = fxmlLoader.<WebBrowserController >getController();
                controller.setConnection(soc, outPut, fout, in, fin, conectat, usr);
                                
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));  
                stage.getIcons().add(new Image("/resources/logo.png"));
                stage.setTitle("Web Browser - Airinei Vlad");
                stage.setMaximized(true);
                
                stage.show();
                
                final Node source = (Node) event.getSource();
                final Stage stage1 = (Stage) source.getScene().getWindow();
                stage1.close();
                
        } catch(Exception e) {
            console.appendText(e.toString());
        }
    }

    @FXML
    private void handlerCreateAction(ActionEvent event) {
          try {
            String username=usernameTxt.getText();
            String password=passwordTxt.getText();
            String user="2/"+username+"/"+password;
            fout.writeObject(user);
            
            String result = (String) fin.readObject();
            if(result.equals("OK")){
                console.appendText("Account created...\n");
                ArrayList<Bookmark> tmp =null;
                usr = new User(1,username,password,0,tmp);
                
                handlerLoginAction(new ActionEvent());
                login.setVisible(false);
                createAccout.setVisible(false);
                //console.appendText("User created " + usr + "\n");  
                //openBrowser.setVisible(true);
            }else{
                console.appendText("Account not created...\n");
            }
            console.appendText("\n");
            
        } catch (Exception ex) {
            console.appendText(ex.toString());
        }
    }
    
}
