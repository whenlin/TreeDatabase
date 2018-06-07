package birds;

/**
 * This class represents a bird record in the database. Each record consists of two
 * parts: a DataKey and the data associated with the DataKey.
 */
public class BirdRecord {

    public DataKey k;
    private String about;
    private String soundFileName;
    private String imageFileName;

    // default constructor
    public BirdRecord() {
       k = new DataKey();
       about = " ";
       soundFileName = " ";
       imageFileName = " ";
    }

     // Other constructors
 
    public BirdRecord(DataKey k, String about, String soundFileName, String imageFileName){
        this.k = k;
        this.about = about;
        this.soundFileName = soundFileName;
        this.imageFileName = imageFileName;
    }

    public String getAbout(){
        return about;
    }
    
    public void setAbout(String a){
        about = a;
    }
    
    public DataKey getKey(){
        return k;
    }
    
    public String getSoundFileName(){
        return soundFileName;
    }
    
    public void setSoundFileName(String sound){
        soundFileName = sound;
    }
    
    public String getImageFileName(){
        return imageFileName;
    }
    
    public void setImageFileName(String image){
        imageFileName = image;
    }

}
