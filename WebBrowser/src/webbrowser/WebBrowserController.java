/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webbrowser;

import classes.User;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Airinei
 */
public class WebBrowserController implements Initializable {
    
    @FXML
    private TextField linkArea;
    @FXML
    private Button goLink;
    @FXML
    private WebView WebView;
    @FXML
    private TextArea console;
    @FXML
    private Button refresh;
    
    private WebEngine engine;
    @FXML
    private Button home;
    
    private String homeString;
    
    private Socket socket;
    private OutputStream out;
    private ObjectOutputStream fout;
    private InputStream in;
    private ObjectInputStream fin;
    private boolean conectat;
    private User user;
    
    @FXML
    private Label usernameLabel;
    @FXML
    private Label usernameLabel1;
    @FXML
    private HBox hbox;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        engine=WebView.getEngine();
        engine.setJavaScriptEnabled(true);
       
        initiButtonsGraphic();
        
        setHomePage("http://google.com");
        loadHomePage();
        
        listenerChangingURL();
        
    }    

    public void setUser(User user) {
        this.user = user;
        console.appendText("\nUser connected " + user.getUsername());
        usernameLabel.setText(user.getUsername());
        
    }
    
    public void initiButtonsGraphic(){
        refresh.setGraphic(new ImageView("/resources/refresh.png"));
        refresh.setText("");
        
        home.setGraphic(new ImageView("/resources/home.png"));
        home.setText("");
    }
    
    public void loadHomePage(){
        engine.load(homeString);
        linkArea.setText(homeString);
    }
    
    public void listenerChangingURL(){
        engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
                linkArea.setText(engine.getLocation());
                console.appendText("\nAcces link " + linkArea.getText());
            }
        });
    }
    
    public void setHomePage(String hs){
        homeString=hs;
    }
    
    @FXML
    private void handlerGoLinkAction(ActionEvent event) {
        
        engine.load(linkArea.getText().startsWith("http://")?linkArea.getText():"http://"+linkArea.getText());
        
        if(!linkArea.getText().startsWith("http://")){
            String tmp="http://";
            tmp+=linkArea.getText();
            linkArea.setText(tmp);
        }
        
        console.appendText("\nGo to " + linkArea.getText());
        
    }

    @FXML
    private void handlerEnterPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){  
            handlerGoLinkAction(new ActionEvent());
        }
    }

    @FXML
    private void handlerRefreshAction(ActionEvent event) {
        
        engine.load(linkArea.getText());
        
        console.appendText("\nRefresh... ");
        
    }

    @FXML
    private void handlerHomeAction(ActionEvent event) {
        
        engine.load(homeString);
        
        console.appendText("\nGo to homepage...");
        
    }
    
    
    public void setConnection(Socket socket, OutputStream out, ObjectOutputStream fout, InputStream in, ObjectInputStream fin, boolean conectat, User user) {
        this.out = out;
        this.socket = socket;
        this.fout = fout;
        this.in = in;
        this.fin = fin;
        this.conectat = conectat;
        this.user = user;
        
        console.appendText("\nUser connected " + user.getUsername());
        usernameLabel.setText(user.getUsername());
        
        addBookmarks();
    }
    
    public void addBookmarks(){
        
        ArrayList<Button> buttons= new ArrayList<>();
        hbox.setSpacing(10.0);
        for ( int i=0;i<user.bookmarks.size();i++){
            Button btn = new Button();
            btn.setText(user.bookmarks.get(i).getBookmarkTitle());
            final int k=i;
            
            btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    
                    linkArea.setText(user.bookmarks.get(k).getBookmargLink());
                    //engine.load(user.bookmarks.get(k).getBookmargLink());
                    //console.appendText("Go to bookmark " + user.bookmarks.get(k).getBookmargLink());
                    handlerGoLinkAction(new ActionEvent());
                }
            });
            
            buttons.add(btn);
            hbox.getChildren().add(btn);
        }
        
    }
}
