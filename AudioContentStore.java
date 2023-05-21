//Name: HARSHDEEP SINGH
//Id: 501174746


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{
	private ArrayList<AudioContent> contents; 
	private Map<String, Integer> byTitle;
	private Map<String, ArrayList<Integer> > byartist;
	private Map<Song.Genre, ArrayList<Integer>> byGenre; 

	public AudioContentStore()
	{
		byTitle = new HashMap<String, Integer>(); // making various maps to store data
		byartist = new HashMap<String, ArrayList<Integer> >();
		byGenre = new HashMap<Song.Genre, ArrayList<Integer> >();

		try{
			contents = readContent();
		}catch(IOException exception){
			System.out.println(exception.getMessage()); // it will call the readContent() to store data into Arraylist Contents , if IO happends it will break out
			System.exit(1);
		}

		//Title
		for(int i=0;i<contents.size();i++){
			byTitle.put(contents.get(i).getTitle(), i); // it will put the stuff into the map named byTitle unless contents are empty
		}

		//Artist
		for(int i=0;i<contents.size();i++){ // this is to add artists , looping into the contents
			ArrayList<Integer> index = new ArrayList<Integer>();
			if(contents.get(i).getType().equalsIgnoreCase("SONG")){ // check if the type is song then get that content
				Song song = (Song)contents.get(i);
				if(byartist.containsKey(song.getArtist())){ //this will check that artist is into the key
					index = byartist.get(song.getArtist()); // this will get the index of that key from the artist map
					index.add(i);
				}else{
					index.add(i); // if no artist found it will create a new key with that
					byartist.put(song.getArtist(), index);
				}
			}

			if(contents.get(i).getType().equalsIgnoreCase("AUDIOBOOK")){
				AudioBook a = (AudioBook)contents.get(i); // same approach but this time for Audiobook
				if(byartist.containsKey(a.getAuthor())){
					index = byartist.get(a.getAuthor());
					index.add(i);
				}else{
					index.add(i);
					byartist.put(a.getAuthor(), index);
				}
			}
		}

		//Genre
		for(int i=0;i<contents.size();i++){ // same looping into the contents , all details are into the object so we will first reach to the object 
			ArrayList<Integer> num = new ArrayList<Integer>(); // then we will use the accessors to get the data and put that onto our maps
			if(contents.get(i).getType().equalsIgnoreCase("SONG")){
				Song song = (Song)contents.get(i);
			for(Song.Genre g: Song.Genre.values()){ // this will look for the type which is into the value of map
				if(song.getGenre() == g){  // if key exists it will add the index onto our new list 
					if(byGenre.containsKey(g)){
						num = byGenre.get(g);
						num.add(i);
					}else{
						num.add(i); // if no then it will put new key onto the map
						byGenre.put(g, num);
					}
				}
			}
		}
	}	
	}

	private ArrayList<AudioContent> readContent() throws IOException
	{
		ArrayList<AudioContent> content = new ArrayList<AudioContent>();
		Scanner in = new Scanner(new File("store.txt")); // reading new file
		//strings id, title, year, length, artist, composer, genre. 
		while(in.hasNextLine()){ // if next line exists , it will run
			String type = in.nextLine();
			if(type.equalsIgnoreCase("SONG")){ // this will be used to seperate all different songs
				String id = in.nextLine();
				String title = in.nextLine();
				int year = in.nextInt();
				int length = in.nextInt();
				in.nextLine();
				String artist = in.nextLine();
				String composer = in.nextLine();
				String g = in.nextLine();
				int lines = in.nextInt();
				in.nextLine();
				String lyrics = "";
				for(int i=0;i<lines;i++){
					String line = in.nextLine();
					lyrics += line + "\n";
				}
				switch(g){ 
					case "POP" : 
					Song song = new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.POP, lyrics);
					content.add(song); break;
					case "ROCK" : 
					song = new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.ROCK, lyrics);
					content.add(song); break;
					case "JAZZ" : 
					song = new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.JAZZ, lyrics);
					content.add(song); break;
					case "HIPHOP" : 
					song = new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.HIPHOP, lyrics);
					content.add(song); break;
					case "RAP" : 
					song = new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.RAP, lyrics);
					content.add(song); break;
					case "CLASSICAL" : 
					song = new Song(title, year, id, type, lyrics, length, artist, composer, Song.Genre.CLASSICAL, lyrics);
					content.add(song); break;
					default : break;
				}	
			}
			else if(type.equalsIgnoreCase("AUDIOBOOK")){
				String id = in.nextLine();
				String title = in.nextLine();
				int year = in.nextInt();
				int length = in.nextInt();
				in.nextLine();
				String author = in.nextLine();
				String narrator = in.nextLine();
				int numOfChapters = in.nextInt();
				in.nextLine();
				ArrayList<String> chapterTitles = new ArrayList<>();
				ArrayList<String> chapters = new ArrayList<>();
				for(int i=0;i<numOfChapters;i++){
					String chapter = in.nextLine();
					chapterTitles.add(chapter);
				}
				for(int i=0;i<numOfChapters;i++){
					int numOfLines = in.nextInt();
					in.nextLine();
					String lines = ""; 
					for(int j=0;j<numOfLines;j++){
						lines += in.nextLine() + "\n";
					}
					chapters.add(lines);
				}
				AudioBook audioBook = new AudioBook(title, year, id, type, "", length, author, narrator, chapterTitles, chapters);
				content.add(audioBook);
			}
		}
		return content;
	}
	public AudioContent getContent(int index)
	{
		if (index < 1 || index > contents.size())
		{
			return null;
		}
		return contents.get(index-1);
	}

	public Map<String, Integer> getByTitle() {
		return byTitle;
	}

	public Map<String, ArrayList<Integer>> getByartist() {
		return byartist;
	}

	public Map<Song.Genre, ArrayList<Integer>> getByGenre() {
		return byGenre;
	}
		
	public void listAll()
	{
		for (int i = 0; i < contents.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}

	public void search(String title){ // this will loop into the byTitle map and look for the key
		if(byTitle.containsKey(title)){
			for(String key : byTitle.keySet()){
				if(key.equals(title)){
					int index = byTitle.get(key);
					System.out.print(index+1 + ". "); // it will get that index where that data is store and print it out 
					contents.get(index).printInfo(); 
				}
			}
		}else{
			throw new NoMatches("No matches for "+ title); // if nothing then simply no matches 
		}
	}

	public void searchA(String artist){
		if(byartist.containsKey(artist)){ // same as previous will check for input artist to be into the key of byArtist map
			for(String key : byartist.keySet()){ 
				if(key.equals(artist)){
					ArrayList<Integer> artists = byartist.get(key);
					for(int i=0;i<artists.size();i++){
						System.out.print(artists.get(i)+1 + ". ");
						contents.get(artists.get(i)).printInfo();
						System.out.println("\n");
					}
				}
			}
		}else{
			throw new NoMatches("No matches for "+ artist);
		}
	}

	public void searchG(Song.Genre genre){
		if(byGenre.containsKey(genre)){
			for(Song.Genre key : byGenre.keySet()){
				if(key == genre){
					ArrayList<Integer> sogns_genre = byGenre.get(key);
					for(int i=0;i<sogns_genre.size();i++){
						System.out.print(sogns_genre.get(i)+1 + ". ");
						contents.get(sogns_genre.get(i)).printInfo();
						System.out.println("\n");
					}
				}
			}
		}else{
			throw new NoMatches("No matches for "+ genre);
		}

	}

	public class NoMatches extends RuntimeException{
		public NoMatches() {}

		public NoMatches(String message){
			super(message);
		}
	}
}
