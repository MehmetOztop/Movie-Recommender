
import java.util.ArrayList;
import java.util.LinkedHashSet;


public class Operations {
	
	
	private ArrayList<Double> similarities= new ArrayList<>();
	private ArrayList<BST<String,Integer>>  users=Reader.users;
	private ArrayList<BST<String,Integer>>  usersI=new ArrayList<>();
	public static BST<Integer, BST<String,Integer>>  data=Reader.readU();
	private ArrayList<Integer> ratingsOfSM;
	private ArrayList<Integer> realIndex= new ArrayList<>();
	
	public ArrayList<String> intersection(BST<Integer, BST<String, Integer>> userTree, int person1, int person2) {
	    BST<String, Integer> p1= userTree.get(person1);
		BST<String, Integer> p2= userTree.get(person2);
		ArrayList<String> mP1= p1.getMovies();	//gets that user's movies in an arraylist(look at BST class)
		ArrayList<String> mP2=p2.getMovies(); //gets that user's movies in an arraylist(look at BST class)	
		ArrayList<String> sameMovies=new ArrayList<>();
		ratingsOfSM=new ArrayList<>();

	
	for(int i=0;i<mP1.size();i++) {//puts the movies seen by the both users and their ratings for those movies into arrays
		for(int j=0;j<mP2.size();j++) {
			if(mP1.get(i).equals(mP2.get(j))){
				sameMovies.add(mP1.get(i));
				ratingsOfSM.add(p1.get(mP1.get(i)));//odds=first user's ratings
				ratingsOfSM.add(p2.get(mP2.get(j)));//evens=second user's ratings
				break;
			}
		}
	}
	return sameMovies;
	}
	
	public double distance(BST<Integer, BST<String, Integer>> userTree, int person1, int person2) {//takes 2 users and finds the distance by looking through all movies in the intersection
		double k=0;
		intersection(userTree,person1, person2);//fills ratingsOfSM array(ratings of same movies)
		for(int i=0;i<ratingsOfSM.size();i=i+2) {
			double a= (ratingsOfSM.get(i) - ratingsOfSM.get((i+1)));
			double b= a*a;
			k=k+b;
		}
		return Math.sqrt(k);
	}
	
	public double sim_distance(BST<Integer, BST<String, Integer>> userTree, int person1, int person2) {//takes 2 users and finds the similarity according to given formula at the project page
		double sim=0.0;
		double dist= distance(userTree,person1, person2);
		sim= 1/ (1+dist);
		return sim;
				
	}
	public void topMatches(BST<Integer, BST<String, Integer>> userTree, int person1,int n) {//takes a user, looks at all users that watch at least 1 same movie with the user, adds their similarities into an array and shows n most similar users  
		
		StopWatch time = new StopWatch();
		
		BST<String, Integer> person= userTree.get(person1);
		for(int i=1;i<users.size();i++) {//looks all over the users
			if(users.get(i)!=person) {//looks the users except given user 
			if((intersection(userTree,i,person1))!=null) {//if given user and the ith user has at least 1 common movie finds similarity, add the similarity and id of that user into 2 arrays 
				realIndex.add(i);//id of users
				usersI.add(users.get(i));//user's BST 
				similarities.add(sim_distance(userTree,i, person1));//similarity
			}
			}
		}

	    ArrayList<Double> maxSims= new ArrayList<>();
	    ArrayList<Integer> maxIndexes= new ArrayList<>();
	    
	    StopWatch time0 = new StopWatch();
	    
	    //OPERATION 0
	    
	    for (int i = 0; i < n; i++) {//n times shows most similar user 
	    	double simT=0.0;
	    	int index=0;
	    	for (int j = 0; j < similarities.size(); j++) {//looks all over the similarities array and finds most similar user
				if(similarities.get(j)>=simT&&!maxIndexes.contains(realIndex.get(j))) {//adds the most similar user if the user is not inside at the maxindexes array
					simT=similarities.get(j);
					index=realIndex.get(j);	
				}
			}
	    	maxIndexes.add(index);
	    	maxSims.add(simT);	
	    	System.out.print(maxIndexes.get(i)+"   ");
			System.out.println(maxSims.get(i));	
		}
	    
	    System.out.println("topMatches operation 0 : "+time0.elapsedTime());
	    
	    System.out.println("topMatches operation total : "+time.elapsedTime());
	   
	    
	    
	    
	 
	}
	public void getRecommendations(BST<Integer, BST<String, Integer>> userTree, int person1,int n) {//takes an user and users BST, adds all movies that the user has not watched, eleminates movies, calculates scores of the movies and shows n best movies
		
		StopWatch time = new StopWatch();
		
		BST<String, Integer> person= userTree.get(person1);
		ArrayList<String> moviesofPerson= person.getMovies();
		ArrayList<String> recommendableMovies= new ArrayList<String>();
		StopWatch time0 = new StopWatch();
		
		//0 OPERATION
		
		for(int i=1;i<users.size();i++) {//looks all over the users and adds all that users has at least 1 common movie and their similarities
			if(users.get(i)!=person) {
			if((intersection(userTree, i,person1))!=null) {
				usersI.add(users.get(i));
				similarities.add(sim_distance(userTree, i,person1));
			}
			}
		}
		System.out.println("get Recommendations operation 0 : "+time0.elapsedTime());
		
		//OPERATION-1 
		
		StopWatch time1= new StopWatch();
		for(int k=0;k<usersI.size();k++) {// looks all users movies that eleminated at top and adds them into an array
			ArrayList<String>tl= usersI.get(k).getMovies();
			for(int g=0;g<tl.size();g++) {
			recommendableMovies.add(tl.get(g));
			}
		}
		System.out.println("get Recommendations operation 1 : "+time1.elapsedTime());
	
		
		StopWatch time2= new StopWatch();
		
		//OPERATION-2 

		
		recommendableMovies=new ArrayList<String>(new LinkedHashSet<>(recommendableMovies));// removes duplicated movies at the arrayList    
        
		System.out.println("get Recommendations operation 2 : "+time2.elapsedTime());
        

		StopWatch time3= new StopWatch();
		
		//OPERATION-3
		
		for(int a=0;a<moviesofPerson.size();a++) {//looks all movies of the user and delete them from recommendable movies arrayList
			if(recommendableMovies.contains(moviesofPerson.get(a))) {
				int x=recommendableMovies.indexOf(moviesofPerson.get(a));
				recommendableMovies.remove(x);
			}
		}
		System.out.println("get Recommendations operation 3 : "+time3.elapsedTime());
		
		////OPERATION-4
		
        StopWatch time4= new StopWatch();
        
		   
		

	     String a[]=new String[recommendableMovies.size()];
	     for(int i=0;i<recommendableMovies.size();i++) {
	    	 a[i]=recommendableMovies.get(i);
	     }
	     QuickSort.sort(a);//sorts the recommendable movies array
		 
	     System.out.println("get Recommendations operation 4 : "+time4.elapsedTime());
	     
	   //OPERATION-5
	     
	     StopWatch time5= new StopWatch();
	     
    	 ArrayList <ArrayList <Double>> scoresOfM= new ArrayList<>();//the array that stores movies' scores 
    	 ArrayList <ArrayList<Double>> scoresOfU= new ArrayList<>();//the array that stores users' similarities that have watched the movie,every inner arrayList represents a movie
    	 ArrayList <Double> FinalScores= new ArrayList<>();//final scores of movies
	     
	     
	     for(int i=0;i<recommendableMovies.size();i++) {//creates empty arrays according to recommendable movies that eleminated at the top
	    	 scoresOfM.add(new ArrayList<Double>());
	    	 scoresOfU.add(new ArrayList<Double>());
	     }
	     
	     for(int i=0;i<usersI.size();i++) {//look all over users in the intersection
	    	 ArrayList<String> tM= usersI.get(i).getMovies();//movies of i'th user
	    	 for(int k=0;k<recommendableMovies.size();k++) {//look all over the recommendable movies and if the user in the intersection user array has watched a movie fills array of movie in the scoresOfU and scoresOfM
	    		 String ts=recommendableMovies.get(k);
	    		 if(tM.contains(ts)) {
	    			 Double tScore= usersI.get(i).get(ts)*similarities.get(i);//user i's score that gives the movie * similarity
	    			 scoresOfM.get(k).add(tScore);
	    			 scoresOfU.get(k).add(similarities.get(i));
	    		 }
	    	 }
	     }
		   
		 
		 System.out.println("get Recommendations operation 5 : "+time5.elapsedTime()); 
		 
		//OPERATION-6
		 
		 StopWatch time6= new StopWatch();
		 
		 ArrayList<Double>sumSimilarities= new ArrayList<>();//the arrayList that stores the similarities of users' that voted on the movie, every index represents a movie
		 
		 for (int i = 0; i < recommendableMovies.size(); i++) {//looks all over recommendable movies, sums every movie's similarities at the scoresU
			Double sum=0.0;
			for (int j = 0; j < scoresOfU.get(i).size(); j++) {
				sum=scoresOfU.get(i).get(j)+sum;
			}
			sumSimilarities.add(sum);
		}
		 
		 for(int i=0;i<recommendableMovies.size();i++) {//looks all over recommendable movies, sums every movie's scores at the scoresM, calculates final score of the movie according to given formula and adds them into an arrayList
			 Double sum2=0.0;
			 for(int k=0;k<scoresOfM.get(i).size();k++) {
				 sum2=sum2+scoresOfM.get(i).get(k);	 
			 }
			 Double fScore=sum2/sumSimilarities.get(i);
			 if(fScore>5.0) {fScore=5.0;}
			 FinalScores.add(fScore);
		 }
		 
		 System.out.println("get Recommendations operation 6 : "+time6.elapsedTime()); 
		 
		 System.out.println("get Recommendations operation main : "+time.elapsedTime());

		 
		    ArrayList<Double> maxScores= new ArrayList<>();
		    ArrayList<String> maxMovie= new ArrayList<>();

		    
		    for (int i = 0; i < n; i++) {//shows n best movies
		    	double simT=0.0;
		    	String index="";
		    	for (int j = 0; j < recommendableMovies.size(); j++) {//looks all over the recommendableMovies array and finds best movie
					if(FinalScores.get(j)>=simT&&!maxMovie.contains(recommendableMovies.get(j))) {//adds best movie if the movie is not inside at the maxmovie array
						simT=FinalScores.get(j);
						index=recommendableMovies.get(j);	
					}
				}
		    	maxMovie.add(index);
		    	maxScores.add(simT);	
		    	MovScoreF.add(maxMovie.get(i));
		    	MovScoreF.add(Double.toString(maxScores.get(i)));
		    	System.out.print(maxMovie.get(i)+"   ");
				System.out.println(maxScores.get(i));	
			}
		    
		    

	     

	}
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *       TRANSFORMATION(PART II)
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	private ArrayList<Integer> ratingsOfSU;
	private ArrayList<BST<Integer,Integer>>  moviesI=new ArrayList<>();//represents array of moviesBST
	private static ArrayList<String> allMovieNames= new ArrayList<String>();
	private ArrayList<Integer> realIndexMovie= new ArrayList<>();
	private ArrayList<Double> similaritiesMov= new ArrayList<>();
	private ArrayList<BST<Integer, Integer>> everyMovie= new ArrayList<>();
	ArrayList<Double> maxSims;
    ArrayList<Integer> maxIndexes;
    public static ArrayList<String> MovScoreF= new ArrayList<>();
    
	
	public BST<String, BST<Integer,Integer>> transformPrefs  (BST<Integer, BST<String, Integer>> userTree) {//transforms user based BST into item based BST
		BST<String, BST<Integer,Integer>> temp=new BST<String, BST<Integer,Integer>>();//represents outer BST
		
		StopWatch time = new StopWatch();
		
		
		//OPERATION TRANS 1
		
		StopWatch time1 = new StopWatch();
		
		for(int i=1;i<users.size();i++) {//looks over all users arrayList and takes all movies
		
			ArrayList<String> movieNames= new ArrayList<String>();
			movieNames= userTree.get(i).getMovies();
			for (int j = 0; j <movieNames.size() ; j++) {
				if(!allMovieNames.contains(movieNames.get(j))) {
					allMovieNames.add(movieNames.get(j));
				}
			}
		}
		
		System.out.println("operation transformation 1 : "+time1.elapsedTime()); 
		
		//OPERATION TRANS 2
		
		StopWatch time2 = new StopWatch();
		
		for(int i=0;i<allMovieNames.size();i++) {//creates empty movie BST according to number of movies
			everyMovie.add(new BST<>());
			
		}
		
		System.out.println("operation transformation 2 : "+time2.elapsedTime()); 
		
		//OPERATION TRANS 3
		
		StopWatch time3 = new StopWatch();
		

		
		for (int i = 1; i < users.size(); i++) {// looks all over the users, looks at the movies of i'th user fills the movies' BST by doing it for every user
			ArrayList<String> movieNames= userTree.get(i).getMovies();
			for (int j = 0; j < movieNames.size(); j++) {//looks all movies that user i has watched
				int k= allMovieNames.indexOf(movieNames.get(j));//k represents id of movie
				everyMovie.get(k).put(i, userTree.get(i).get(movieNames.get(j)));//j represents name of the movie
			}
		}
		
		System.out.println("operation transformation 3 : "+time3.elapsedTime()); 
		
		//OPERATION TRANS 4
		
		StopWatch time4 = new StopWatch();
		
		for(int k=0;k<allMovieNames.size();k++) {//puts all movies' BST in the arrayList into outer(final) BST
			temp.put(allMovieNames.get(k), everyMovie.get(k));
		}
		
		System.out.println("operation transformation 4 : "+time4.elapsedTime()); 
		
		System.out.println("operation transformation main : "+time.elapsedTime()); 
		
		return temp;
	}
	
	/*
	 * 
	 * 
	 *       AFTER HERE IT IS THE APPLICATION OF THE SAME METHODS AT THE TOP BY CHANGING ITEMS
	 * 
	 * 
	 */
	
	public ArrayList<Integer> transSection(BST<String, BST<Integer, Integer>> movieTree,String mov1,String mov2) {
	
		BST <Integer,Integer> movie1=movieTree.get(mov1);
	    BST <Integer,Integer> movie2=movieTree.get(mov2);
	    ArrayList<Integer> usersOfM1=movie1.getUsers();	
	    ArrayList<Integer> usersOfM2=movie2.getUsers();
	    ArrayList<Integer> sameUsers=new ArrayList<>();
	    ratingsOfSU=new ArrayList<>();

	
	for(int i=0;i<usersOfM1.size();i++) {
		for(int j=0;j<usersOfM2.size();j++) {
			if(usersOfM1.get(i).equals(usersOfM2.get(j))){
				sameUsers.add(usersOfM1.get(i));
				ratingsOfSU.add(movie1.get(usersOfM1.get(i)));
				ratingsOfSU.add(movie2.get(usersOfM2.get(j)));
				break;
			}
		}
	}
	return sameUsers;
	}
	
	
	public double transDistance(BST<String, BST<Integer, Integer>> movieTree,String mov1,String mov2) {

		double k=0;
		transSection(movieTree,mov1, mov2);
		for(int i=0;i<ratingsOfSU.size();i=i+2) {
			double a= (ratingsOfSU.get(i) - ratingsOfSU.get(i+1));
			double b= a*a;
			k=k+b;
		}
		return Math.sqrt(k);
	}
	
	public double transSim_distance(BST<String, BST<Integer, Integer>> movieTree,String mov1,String mov2) {
		double sim=0.0;
		double dist= transDistance(movieTree,mov1, mov2);
		sim= 1/ (1+dist);
		return sim;
				
	}
	
	public ArrayList<String> transTopMatches(BST<String, BST<Integer, Integer>> movieTree,String mov,int n) {
		
		ArrayList<String> result= new ArrayList<>();
		
		StopWatch time = new StopWatch();
		
		StopWatch time1 = new StopWatch();
		
		//OPERATION 1
		
		BST<Integer, Integer> movieI= movieTree.get(mov);
		for(int i=0;i<allMovieNames.size();i++) {
			if(!everyMovie.get(i).equals(movieI)) {
			if((transSection(movieTree,allMovieNames.get(i),mov))!=null) {
				realIndexMovie.add(i);
				moviesI.add(everyMovie.get(i));
				similaritiesMov.add(transSim_distance(movieTree,allMovieNames.get(i), mov));
			}
			}
		}
		
		System.out.println("transTopMatches operation 1 : "+time1.elapsedTime());

	    maxSims= new ArrayList<>();
	    maxIndexes= new ArrayList<>();
	    
	  //OPERATION 2
	    
	    StopWatch time2 = new StopWatch();
	    
	    for (int i = 0; i < n; i++) {
	    	double simT=0.0;
	    	int index=0;
	    	for (int j = 0; j < similaritiesMov.size(); j++) {
				if(similaritiesMov.get(j)>=simT&&!maxIndexes.contains(realIndexMovie.get(j))) {
					simT=similaritiesMov.get(j);
					index=realIndexMovie.get(j);	
				}
			}
	    	maxIndexes.add(index);
	    	maxSims.add(simT);	
	    	System.out.print(allMovieNames.get(maxIndexes.get(i))+"   ");
			System.out.println(maxSims.get(i));	
		}
	    
	    for (int i = 0; i < n; i++) {
	    	result.add(Double.toString(maxSims.get(i)));
	    	result.add(allMovieNames.get(maxIndexes.get(i)));	
		}
	    
	    System.out.println("transTopMatches operation 2 : "+time2.elapsedTime());
	    
	    System.out.println("transTopMatches operation main : "+time.elapsedTime());
	    
	    return result;
	}
	
	public void calculateSimilarItems(BST<String, BST<Integer, Integer>> movieTree,int n) {//shows every movie's most similar 2 movies
		
		StopWatch time = new StopWatch();
		
		int k=0;
		for(int i=0;i<everyMovie.size();i=i+2) {
			
			ArrayList<String> result= transTopMatches(movieTree,allMovieNames.get(i), 2);
			System.out.println(allMovieNames.get(i)+"   :  (" + result.get(k)+", "+result.get(k+1)+"),    ("+result.get(k+2)+", "+result.get(k+3)+")");
			k=k+4;
		}
		
		System.out.println("calculateSimilarItems operation main : "+time.elapsedTime());
	}
	
	public void getRecommendedItems(BST<Integer, BST<String, Integer>> userTree,BST<String, BST<Integer, Integer>> movieTree,int user,int n) {
		
		
		//OPERATION 1
		
		StopWatch time1 = new StopWatch();
		
		ArrayList<String> allMovieName=allMovieNames;
		BST<String,Integer> userBST=data.get(user);
		ArrayList<String> moviesOfPerson=userBST.getMovies();
		
		for (int i = 0; i < moviesOfPerson.size(); i++) {
			
			allMovieName.remove(moviesOfPerson.get(i));
		}
		ArrayList<Double> scores=new ArrayList<>();
		
		for(int i=0;i<allMovieName.size();i++) {//looks all over the movies and calculates their scores according to formula
			double simT=0.0;
			double scorT=0.0;
			for (int j = 0; j < moviesOfPerson.size(); j++) {
				double simT2=0.0;
				simT2=transSim_distance(movieTree,allMovieName.get(i), moviesOfPerson.get(j));
				simT=simT+simT2;
				scorT=scorT+simT2*userBST.get(moviesOfPerson.get(j));
			}
			scores.add(scorT/simT);
		}
		
		
		System.out.println("getRecommendedItems operation 1 : "+time1.elapsedTime());
		
		//OPERATION 2
		
		
	    ArrayList<Double> maxScores= new ArrayList<>();
	    ArrayList<String> maxMovie= new ArrayList<>();

	    
	    for (int i = 0; i < n; i++) {//show best n movies
	    	double simT=0.0;
	    	String index="";
	    	for (int j = 0; j < scores.size(); j++) {
				if(scores.get(j)>=simT&&!maxMovie.contains(allMovieName.get(j))) {
					simT=scores.get(j);
					index=allMovieName.get(j);	
				}
			}
	    	maxMovie.add(index);
	    	maxScores.add(simT);
	    	MovScoreF.add(maxMovie.get(i));
	    	MovScoreF.add(Double.toString(maxScores.get(i)));
	    	
	    	System.out.print(maxMovie.get(i)+"   ");
			System.out.println(maxScores.get(i));	
		}
	    
	    
	    
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    Operations o= new Operations();
    BST<String, BST<Integer, Integer>> movieData= o.transformPrefs(data);
    o.getRecommendations(data, 9, 5);
    o.getRecommendedItems(data, movieData, 9, 5);
    
   
	}

}
