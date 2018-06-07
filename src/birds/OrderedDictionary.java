/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package birds;



/**
 *
 * @author whenlin
 */
public class OrderedDictionary implements OrderedDictionaryADT{

    Node<BirdRecord> root; //root node of binary tree
    
    public OrderedDictionary(){
        root = null;
    }
    
    public BirdRecord getRoot(){
        return root.getData();
    }
    
    public Node<BirdRecord> getRootNode(){
        return root;
    }
    
    public OrderedDictionary(Node<BirdRecord> n){
        root = n;
    }
    
    public BirdRecord look(DataKey k){
        int comparison = k.compareTo(root.getData().k); //compares the datakey with the root node
            BirdRecord result = null;
            
            if(comparison == 0){
                return root.getData();
            }
            else if(comparison > 0){  //I switched the greater and lesser signs n.b. AND IT WORKED!!!
                result = findUnder(root.getLeft(), k);
            }
            else if(comparison < 0){
                result = findUnder(root.getRight(), k);
            }
            
        if(result == null){
            return null;
        }
            
        return result;
    }
    
    @Override
    public BirdRecord find(DataKey k) throws DictionaryException {
        
            int comparison = k.compareTo(root.getData().k); //compares the datakey with the root node
            BirdRecord result = null;
            
            if(comparison == 0){
                return root.getData();
            }
            else if(comparison > 0){  //I switched the greater and lesser signs n.b. AND IT WORKED!!!
                result = findUnder(root.getLeft(), k);
            }
            else if(comparison < 0){
                result = findUnder(root.getRight(), k);
            }
            
        if(result == null){
            throw new DictionaryException("Key not found");
        }
            
        
        return result;
        
    }
    
    private BirdRecord findUnder(Node<BirdRecord> node, DataKey key){
        
        if(node == null){
            return null;
        } 
        
        int result = node.getData().k.compareTo(key); //comparing node's data to the datakey's
        
        if(result == 0){
            return node.getData();
        } else if(result > 0){
            return findUnder(node.getRight(), key);
        } else if(result < 0){
            return findUnder(node.getLeft(), key);
        } else 
            return node.getData();
        
    }

    @Override
    public void insert(BirdRecord r) throws DictionaryException {       
        if(root == null){
            root = new Node(r,null);
        } else{
            insertBelow(root, r);
        } 
    }
    
    private void insertBelow(Node<BirdRecord> node, BirdRecord b) throws DictionaryException{ //1st param is the node that this function is traversing from
                                                                   //2nd param is the bird record that is to be inserted
        
        DataKey d = b.getKey(); //storing the birdrecord's datakey in a variable
        
        int result = node.getData().getKey().compareTo(d);
            
        if(result == 0){  //if the node has the same birdrecord then nothing happens
            throw new DictionaryException("Record exists");
        } else if(result > 0) //if the node has a bird record greater than variable b
        {
            
            if(node.getRight() == null){            //if right is empty then node is created
                node.right = new Node(b, node);
            } else 
            {
                insertBelow(node.getRight(), b);
            }
            
        } else if(result < 0){  //if the node has a bird record lesser than variable b
            if(node.getLeft() == null){
                node.left = new Node(b, node);
            } else 
            {
                insertBelow(node.getLeft(), b);
            }
        }
    }

    @Override
    public void remove(DataKey k) throws DictionaryException {
        root = deleteRecursive(root, k);
    }
    
    private Node<BirdRecord> deleteRecursive(Node<BirdRecord> node, DataKey key) throws DictionaryException{
		//you are at the node you want to delete
		if(key.compareTo(node.getData().k) == 0) {
			//if its a leaf
			if((node.getLeft() == null) && (node.getRight() == null)) {
				return null; 
			}
			//it is a node with one child on the right
			else if((node.getLeft() == null) && (node.getRight() != null)) {
				return node.getRight();
			}
			//it is a node with one child on the left
			else if((node.getLeft() != null) && (node.getRight() == null)) {
				return node.getLeft();
			}
			//It is a node with 2 children. This is more tricky.
			else if((node.getLeft() != null) && (node.getRight() != null)) {
				//the replacement node is the least node which is still greater than the one deleted.
				Node<BirdRecord> replacementNode = findMin(node.getRight()); 
				Node<BirdRecord> backupLeft = node.getLeft(); //save the left nodes
				//the min-value node's right is now set as all the right nodes minus itself.
				replacementNode.setRight(deleteDoubleNode(node.getRight())); 
				replacementNode.setLeft(backupLeft); //replace the left nodes as the regular left nodes
				return replacementNode; //return the new least "greater" node.
			}
			else {
				return node;
			}
		}
		//if the key is still less than the node we are at...
		else if(key.compareTo(node.getData().k) < 0) {
			if(node.getLeft() != null) {
				//keep looking but to the left of this node
				node.left = deleteRecursive(node.getLeft(), key);
				return node;
			}
		}
		//if the key is still greater than the node we are at...
		else if(key.compareTo(node.getData().k) > 0){
			if(node.getRight() != null) {
				//keep looking but to the right of this node
				node.right = deleteRecursive(node.getRight(), key);
				return node;
			}
		} else 
                  throw new DictionaryException("Node not found");
                
		return node; //return the regular node if all else fails (failsafe, most likely won't be reached).
	}
    
    private Node<BirdRecord> deleteDoubleNode(Node<BirdRecord> node) {
		if(node.getLeft() == null) {
			//at the bottom of the nodes.
			return node.getRight();
		}
		else {
			//set the left node as the right of the one we found at the bottom.
			node.setLeft(deleteDoubleNode(node.getLeft()));
		}
		return node;
	}
    
    
    private Node<BirdRecord> findMin(Node<BirdRecord> node) { //same thing as the smallest() method but this method returns the node instead
		while(node.getLeft() != null) {
			node = node.getLeft();
		}
		return node;
	}


    @Override
    public BirdRecord successor(DataKey k) throws DictionaryException {
        
       if(root.getData().getKey().compareTo(k) == 0){
           
           if(root.getLeft() != null)
               return root.getLeft().getData();
           else
               throw new DictionaryException("There is no successor for the given record key");
           
       } else if(root.getLeft() != null)
           return nextNode(root.getLeft() , k);
       else if(root.getRight() != null)
           return nextNode(root.getRight(), k);
           
           throw new DictionaryException("There is no successor for the given record key");
        
        
    }
    
    private BirdRecord nextNode(Node<BirdRecord> node, DataKey key) throws DictionaryException{
        
        if(node.getData().getKey().compareTo(key) == 0){   //if the current node's data is equal to the datakey's own
            
            if(node.getLeft() != null)
                return node.getLeft().getData();
            else
                throw new DictionaryException("There is no successor for the given record key");
                
        } else if(node.getLeft() != null)
            return nextNode(node.getLeft(), key);
        else if(node.getRight() != null)
            return nextNode(node.getRight(), key);
        else return node.getParent().getData();
        
    }

    @Override
    public BirdRecord predecessor(DataKey k) throws DictionaryException {
         if(root.getData().getKey().compareTo(k) == 0){
           
           if(root.getLeft() != null)
               return root.getLeft().getData();
           else if(root.getRight() != null)
               return root.getRight().getData();
           else
               throw new DictionaryException("There is no successor for the given record key");
           
       } else if(root.getLeft() != null)
           return previousNode(root.getLeft() , k);
       else if(root.getRight() != null)
           return previousNode(root.getRight(), k);
       else
           throw new DictionaryException("There is no successor for the given record key");
        
        
        
    }
    
    private BirdRecord previousNode(Node<BirdRecord> node, DataKey key) throws DictionaryException{
        
            if(node.getData().k.compareTo(key) == 0){   //if the current node's data is equal to the datakey's own
            
            if(node.getLeft() != null)
                return node.getLeft().getData();
            else
                throw new DictionaryException("There is no successor for the given record key");
                
        } else if(node.getLeft() != null){
            int num = node.getLeft().getData().k.compareTo(key);
            if(num < 1)
            return previousNode(node.getLeft(), key);
        }
        else if(node.getRight() != null){
            int num = node.getRight().getData().k.compareTo(key);
            if(num < 1)
            return previousNode(node.getRight(), key);
        }
            
           return node.getParent().getData();
    }

    @Override
    public BirdRecord smallest() throws DictionaryException {
        Node<BirdRecord> currentNode = root; //a node variable is created and initialized as the root.
        
        while(currentNode.getRight() != null){ //loops if the current node's right child is initialiized
            currentNode = currentNode.getRight(); //if there is a left child, that right child is traversed to
        }

        return currentNode.getData();
    }

    @Override
    public BirdRecord largest() throws DictionaryException {
      
       Node<BirdRecord> currentNode = root; //a node variable is created and initialized as the root.
        
       while(currentNode.getLeft() != null){ //loops if the current node's left child is initialiized
           currentNode = currentNode.getLeft(); //if there is a left child, that left child is traversed to
       }
       
       return currentNode.getData();
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }
    
    public class Node<T extends BirdRecord> {
        
        private T data;
        private Node<T> left;
        private Node<T> right;
        private Node<T> parent;
        
        public Node(){
            
        }
        
        public Node(T data){
            this.data = data;
            left = right = parent = null;
        }
        
        public Node(T data, Node<T> parent){
            this.data = data;
            this.parent = parent;
            left = right = null;
        }
        
        public Node(T data, Node<T> left, Node<T> right, Node<T> parent){
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
        
        public T getData(){
            return data;
        }
        
        public Node<T> getLeft(){
            return left;
        }
        
        public void setLeft(Node<T> left){
            this.left = left;
        }
        
        public Node<T> getRight(){
            return right;
        }
        
        public void setRight(Node<T> right){
            this.right = right;
        }
        
        public void setParent(Node<T> n){
            parent = n;
        }
        
        public Node<T> getParent(){
            return parent;
        }
        
        public boolean hasChildren(){
            return(getLeft() != null || getRight() != null);
        }
        
    }
    
}
