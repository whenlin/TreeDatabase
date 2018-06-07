package birds;

public class DataKey {
    
        public int birdSize;
        public String birdName;
        
    
	// default constructor
	public DataKey() {
	 
	}
        
	// other constructors
        
        public DataKey(String birdName, int birdSize){
            this.birdName = birdName;
            this.birdSize = birdSize;
        }
        
	/**
	 * Returns 0 if this DataKey is equal to k, returns -1 if this DataKey is smaller
	 * than k, and it returns 1 otherwise. 
	 */
	public int compareTo(DataKey k) {  
            
            if(this.getBirdSize() == k.getBirdSize()){
             
                if(this.getBirdName().equals(k.getBirdName()))
                return 0;
                else if(this.getBirdName().compareTo(k.getBirdName()) > 0)
                return 1;
                else if(this.getBirdName().compareTo(k.getBirdName()) < 0)
                    return -1;
                
            }
            else if(this.getBirdSize() > k.getBirdSize())
                return 1;
            
            return -1;
          
	}
        
        public String getBirdName(){
            return birdName;
        }
        
        public void setBirdName(String bird){
            birdName = bird;
        }
        
        public int getBirdSize(){
            return birdSize;
        }
        
        public void setBirdSize(int size){
            birdSize = size;
        }
}
