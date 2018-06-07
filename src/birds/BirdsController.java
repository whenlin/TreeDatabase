/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package birds;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/**
 *
 * @author Abdelkader
 */
public class BirdsController implements Initializable {

    private OrderedDictionary dictionary = new OrderedDictionary();
    private Boolean loaded = false;
  //  String soundFilePath = "C:\\Users\\whenlin\\Documents\\NetBeansProjects\\Birds\\src\\images\\";
  //  String imageFilePath = "C:\\Users\\whenlin\\Documents\\NetBeansProjects\\Birds\\src\\images\\";
    private final String soundFilePath = "/sounds/";
    private final String imageFilePath = "/images/";
    
    private MediaPlayer audio;
    private List<BirdRecord> list = new ArrayList<>();
    private BirdRecord currentBird;
    
    @FXML
    private MenuBar mainMenu;
    
    @FXML
    private Label birdName;
    
    @FXML
    private Label aboutInfo;
    
    @FXML
    private TextField birdNameTextField;
    
    @FXML
    private final ChoiceBox<String> birdSizePicker = new ChoiceBox<>();

    @FXML
    private ImageView birdImage = new ImageView();
    
    @FXML
    public void exit() {
        Stage stage = (Stage) mainMenu.getScene().getWindow();
        stage.close();
    }
    
    private void inOrder(OrderedDictionary.Node<BirdRecord> node, List<BirdRecord> list){
        
        if(node == null)
            return;
        
        inOrder(node.getRight(), list);
        list.add(node.getData());
        inOrder(node.getLeft(), list);
        
    }

    @FXML
    public void loadDictionary() throws DictionaryException {
        
        int  counter = 0; //this will track the iterations as every 3 iterations is equivalent to 1 birdrecord
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader("C:/Users/whenlin/Documents/NetBeansProjects/Birds/src/resources/BirdsDatabase.txt"));
            String line = reader.readLine();
            
            int size = 0;
            String name = " ";
            String abt = " ";
            
            while(line != null){
                
                int mod = counter % 3;
                
                switch(mod){
                    case 0:   
                      size = Integer.parseInt(line);                
                 //store birdsize here 
                        break;
                        
                    case 1:
                      name = line;  //store birdname here
                        break;
                        
                    case 2:
                    abt = line;  //storing abt here
                    DataKey key = new DataKey(name, size); //inserting name and size into datakey
                    
                    String sound = this.soundFilePath + name + ".mp3";
                    String image = this.imageFilePath + name + ".jpg";
                    
                    dictionary.insert(new BirdRecord(key, abt, sound, image));    //inserting a new birdrecord with all this info into dictionary
                        break;
                        
                    default:
                        break;
                }
                
               line = reader.readLine(); //read the next line
               if(line.equals(""))
                   break;
               
               counter++; //increment counter
            
            }
           reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }
       
        currentBird = this.dictionary.getRoot();
        inOrder(this.dictionary.getRootNode(), list); //populates a list with the tree nodes so that the tree can be traversed inorder
        
        loaded = true;
        
        if(loaded){
            this.birdName.setVisible(true);
            this.aboutInfo.setVisible(true);
        }
       
        this.birdName.setText(currentBird.getKey().getBirdName());
        this.aboutInfo.setText(currentBird.getAbout());
        
        
       
     String str = "/images/" + this.currentBird.getKey().getBirdName() + ".jpg";
     String url = "/sounds/" + this.currentBird.getKey().getBirdName() + ".mp3";
     Image img = new Image(str);
     this.birdImage.setImage(img);
     URL res = null;
     
//        try {
//            url = new URL(url_);
//        } catch (MalformedURLException ex) {
//            ex.printStackTrace();
//        }

        
     Media sound = new Media(url);
     this.audio = new MediaPlayer(sound);
    }
    
    @FXML
    public void first(){
        try{
            
      BirdRecord record1 = this.dictionary.smallest();
      BirdRecord record = list.get(0);
              
      this.birdName.setText(record.getKey().getBirdName());
        this.aboutInfo.setText(record.getAbout());
        currentBird = record;
        
     //   String fileURL = "file:" + this.imageFilePath + this.birdName.getText() + ".jpg";
        String url = "/images/" + currentBird.getKey().getBirdName() + ".jpg";
        
        Image image1 = new Image(url);
        this.birdImage.setImage(image1);
        
        } catch (DictionaryException e){
            e.printStackTrace();
        }

    }
    
    @FXML
    public void last(){
        try{
        BirdRecord record1 = this.dictionary.largest();
        BirdRecord record = list.get((list.size() - 1));
        
        this.birdName.setText(record.getKey().getBirdName());
        this.aboutInfo.setText(record.getAbout());
        currentBird = record;
        
         String url = "/images/" + currentBird.getKey().getBirdName() + ".jpg";
        
        Image image1 = new Image(url);
        this.birdImage.setImage(image1);
        
        } catch (DictionaryException e){
            e.printStackTrace();
        }
    }
    
    
    @FXML
    public void next(){
   //    int s = currentBird.getKey().getBirdSize();
     //  String name = currentBird.getKey().getBirdName();     
      //  BirdRecord record1 = this.dictionary.successor(new DataKey(name, s)); 
      
        int index = list.indexOf(currentBird);   //obtain index of currentbird in list
        
        if(index < (list.size() - 1)){
        index++; //go to next index
        BirdRecord record = list.get(index); //retrieves the next bird
        
        this.birdName.setText(record.getKey().getBirdName());  //sets birds name
        this.aboutInfo.setText(record.getAbout());                // sets birds about info
        
        String url = "/images/"+ record.getKey().getBirdName() + ".jpg" ;
        Image img = new Image(url);
        this.birdImage.setImage(img);                              //sets the image
        
        currentBird = record;
        } 
       
    }
    
    @FXML
    public void previous(){
        int s = currentBird.getKey().getBirdSize();
       String name = currentBird.getKey().getBirdName();

        try{
         int index = list.indexOf(currentBird);
         
         if(index > 0){
        index--;   //decrements by one ,  to the previous index
        BirdRecord record1 = this.dictionary.predecessor(new DataKey(name, s)); 
        
        BirdRecord record = list.get(index);       //retrieves the bird record
        
        
        //setting the bird display appropriately
        this.birdName.setText(record.getKey().getBirdName());
        this.aboutInfo.setText(record.getAbout());
        String url = this.imageFilePath + record.getKey().getBirdName() + ".jpg";
        Image image = new Image(url);
        this.birdImage.setImage(image);
        
        currentBird = record;
        
         }
         
        } catch (DictionaryException e){
            e.printStackTrace();
        }
        
    }
    
    @FXML
    public void find(){
        try{
        String name = this.birdNameTextField.getText();
       String size = this.birdSizePicker.getValue();
       
        if(!name.isEmpty()){
        
       int s = 0;
       
       switch(size){
           case "Small":
             s = 1;
             break;
             
           case "Medium":
               s = 2;
               break;
               
           case "Large":
               s = 3;
               break;
               
           default:
               s = 1;
       }
       
       
        BirdRecord n = this.dictionary.find(new DataKey(name, s));
        
        this.birdName.setText(n.getKey().getBirdName());
        this.aboutInfo.setText(n.getAbout());
        currentBird = n;
        }
        
        } catch(DictionaryException e){
            e.printStackTrace();
        }
    }
    
    @FXML
    public void play(){
        if(this.audio.getMedia() != null){
            this.audio.play();
        }
    }
    
    @FXML
    public void stop(){
        this.audio.stop();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
