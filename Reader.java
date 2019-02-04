import java.awt.List;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class Reader {
	public static ArrayList<BST<String,Integer>>  users;
	public static ArrayList<String> movieNames= new ArrayList<>();
	public static Integer[] index;
	
	public static ArrayList<String> readM() {//Reads movies from the file, puts them in a string arraylist
		File f= new File("u.item");
		String line="";
		try {
			FileInputStream FIS= new FileInputStream(f);
			DataInputStream DIS= new DataInputStream(FIS);
			BufferedReader BR= new BufferedReader(new InputStreamReader(DIS));
			movieNames.add("");
			
			while((line=BR.readLine())!=null){
				String[] dataL= line.split("\\|");// To split with "|", you need to put "//" before it
				movieNames.add(dataL[1]);//The second string in the array is the movie name for every line
			}
			
			BR.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			}
		
	return movieNames;//returns the string arrayList that contains every movie name in the file
		
		}
	

	public static ArrayList<BST<String,Integer>> readR(){//reads the file and fills the BST with the necessary data for every user
		File f=new File("u.data");
		String line="";
		readM();//fills the movieNames arrayList by calling the previous method
		ArrayList<BST<String,Integer>> users= userCreator();//creates an empty BST for every user through the "userCreator" function
		try {
			FileInputStream FIS= new FileInputStream(f);
			DataInputStream DIS= new DataInputStream(FIS);
			BufferedReader BR= new BufferedReader(new InputStreamReader(DIS));
			
			int userID;
			int movieID;
			int rating;
			String movieName;
			while((line=BR.readLine())!=null){
				String[] dataL=line.split("\t");//splits by tab
				userID=Integer.parseInt(dataL[0]);
				movieID=Integer.parseInt(dataL[1]);
				rating=Integer.parseInt(dataL[2]);
				movieName=movieNames.get(movieID);
				users.get(userID).put(movieName,rating);//fills every users BST according to the information from the file
					
			}
		BR.close();
			
	}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return users;
	}
	

		
		
    public static BST<Integer, BST<String, Integer>>readU() {//creates a BST using the BST arrayList of users
    	users= readR();//fills in the users arrayList through the "readR" method 
    	BST<Integer, BST<String, Integer>> result= new BST<Integer, BST<String, Integer>>();//creates the outer BST
    	for(int i=1; i<users.size();i++) {
    		result.put(i, users.get(i));//fills in the outer BST
    		
    	}
    	return result;	
    }

    
    public static ArrayList<BST<String,Integer>> userCreator(){//creates usersBST(inner) according to file
    	File f=new File("u.data");
    	String line="";
    	String[] data;
    	ArrayList <BST<String,Integer>> users = new ArrayList<>();
    	ArrayList<String> useless=new ArrayList<>();
    	
    	try {
    		FileInputStream FIS= new FileInputStream(f);
			DataInputStream DIS= new DataInputStream(FIS);
			BufferedReader BR= new BufferedReader(new InputStreamReader(DIS));
			users.add(new BST<String,Integer>());
			while((line=BR.readLine())!=null){
				data=line.split("\t");
				if(!useless.contains(data[0])) {//checks whether the same ID has been worked for or not
				users.add(new BST<String,Integer>());//puts an empty BST for every user
			    useless.add(data[0]);//collects the user IDs 
				}
			}
			BR.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return users;
    }

    

	
	public static void main(String[]args) {
    
	}
}
