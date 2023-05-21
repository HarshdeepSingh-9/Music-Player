//Name: HARSHDEEP SINGH
//Id: 501174746


import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;



// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			try{
				String action = scanner.nextLine();

				if (action == null || action.equals("")) 
				{
					System.out.print("\n>");
					continue;
				}
				else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;
				
				else if (action.equalsIgnoreCase("STORE"))	// List all songs
				{
					store.listAll(); 
				}
				else if (action.equalsIgnoreCase("SONGS"))	// List all songs
				{
					mylibrary.listAllSongs(); 
				}
				else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
				{
					mylibrary.listAllAudioBooks(); 
				}
				else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
				{
					mylibrary.listAllArtists(); 
				}
				else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
				{
					mylibrary.listAllPlaylists(); 
				}
				// Download audiocontent (song/audiobook/podcast) from the store 
				// Specify the index of the content
				else if (action.equalsIgnoreCase("DOWNLOAD")) 
				{
					int from_Index = 0;
					int to_Index = 0;
					System.out.print("From Store Content #: ");
					if (scanner.hasNextInt())
					{
						from_Index = scanner.nextInt();
					}

					System.out.print("To Store Content #: ");
					if (scanner.hasNextInt())
					{
						to_Index = scanner.nextInt();
						scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
					}
					
					for(int i=from_Index;i<=to_Index;i++){
						AudioContent content = store.getContent(i);
						try{
								mylibrary.download(content);
						}catch(RuntimeException exception){
							System.out.println(exception.getMessage());
						}
					}
				}
				// Get the *library* index (index of a song based on the songs list)
				// of a song from the keyboard and play the song 
				else if (action.equalsIgnoreCase("PLAYSONG")) 
				{
					System.out.print("Song Number: ");
					int index = scanner.nextInt();
					mylibrary.playSong(index);
				}
				// Print the table of contents (TOC) of an audiobook that
				
				else if (action.equalsIgnoreCase("BOOKTOC")) 
				{
					System.out.print("Audio Book Number: ");
					int index = scanner.nextInt();
					mylibrary.printAudioBookTOC(index);
				}
				// Similar to playsong above except for audio book
				
				else if (action.equalsIgnoreCase("PLAYBOOK")) 
				{
					System.out.print("Audio Book Number: ");
					int index = scanner.nextInt(); 

					System.out.print("Chapter: ");
					int chapter = scanner.nextInt();
					mylibrary.playAudioBook(index,chapter);
				}
				// Specify a playlist title (string) 
				
				else if (action.equalsIgnoreCase("PLAYALLPL")) 
				{
					System.out.print("Playlist Title: ");
					String title = scanner.next();
					mylibrary.playPlaylist(title);
				}
				// Specify a playlist title (string) 
				// Read the index of a song/audiobook/podcast in the playist from the keyboard 
				
				else if (action.equalsIgnoreCase("PLAYPL")) 
				{
					System.out.print("Playlist Title: ");
					String title = scanner.next();
					
					System.out.print("Content Number: ");
					int index = scanner.nextInt();
					mylibrary.playPlaylist(title,index);
				}
				// Delete a song from the list of songs in mylibrary and any play lists it belongs to
				
				else if (action.equalsIgnoreCase("DELSONG")) 
				{
					System.out.print("Library Song #: ");
					int index = scanner.nextInt();
					mylibrary.deleteSong(index);
				}
				// Read a title string from the keyboard and make a playlist
				
				else if (action.equalsIgnoreCase("MAKEPL")) 
				{
					System.out.print("Playlist Title: ");
					String title = scanner.next();
					mylibrary.makePlaylist(title);
				}
				// Print the content information (songs, audiobooks, podcasts) in the playlist
			
				else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
				{
					System.out.print("Playlist Title: ");
					String title = scanner.next();
					mylibrary.printPlaylist(title);
				}
				// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
				
				else if (action.equalsIgnoreCase("ADDTOPL")) 
				{
					System.out.print("Playlist Title: ");
					String title = scanner.next();

					System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
					String type = scanner.next();

					System.out.print("Library Content #: ");
					int index = scanner.nextInt();
					mylibrary.addContentToPlaylist(type,index,title);
				}
				// Delete content from play list based on index from the playlist
			
				else if (action.equalsIgnoreCase("DELFROMPL")) 
				{
					System.out.print("Playlist Title: ");
					String title = scanner.next();

					System.out.print("Playlist Content #: ");
					int index = scanner.nextInt();
					mylibrary.delContentFromPlaylist(index,title);
				}
				
				else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
				{
					mylibrary.sortSongsByYear();
				}
				else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
				{
					mylibrary.sortSongsByName();
				}
				else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
				{
					mylibrary.sortSongsByLength();
				}
				else if(action.equalsIgnoreCase("SEARCH"))
				{
					System.out.print("Title: "); // by taking the input we will call the search method from AudioContentStore class
					String title = scanner.nextLine();
					store.search(title);
				}

				else if(action.equalsIgnoreCase("SEARCHA"))
				{
					System.out.print("Artist: ");
					String artist = scanner.nextLine(); // it will call searchA
					store.searchA(artist);
				}

				else if(action.equalsIgnoreCase("SEARCHG"))
				{
					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: "); // will take input send it to the method defiined in AudioContentStore
					String genre = scanner.nextLine();
					store.searchG(Song.Genre.valueOf(genre));
				}

				else if(action.equalsIgnoreCase("DOWNLOADA"))
				{
					System.out.print("Artist Name: ");
					String artist = scanner.nextLine();

					for(String key: store.getByartist().keySet()){ // first we will check into our artist map that does it have this key,
						if(key.equals(artist)){
							ArrayList<Integer> list = store.getByartist().get(key); // if it is , it will store that stuff into list
							
							for(int i : list){
								AudioContent content = store.getContent(i+1); //it will be a constructor , will make out the data into the audioContent class which is store into the store object
								try{
									mylibrary.downloadAG(content);
								}catch(RuntimeException exception){
									System.out.println(exception.getMessage());
								}
							}
						}
					}
				}

				else if(action.equalsIgnoreCase("DOWNLOADG"))
				{
					System.out.print("Genre: ");
					String genre = scanner.nextLine();

					for(Song.Genre key: store.getByGenre().keySet()){ // same above approach
						if(key == Song.Genre.valueOf(genre)){
							ArrayList<Integer> list = store.getByGenre().get(key);
							
							for(int i=0;i<list.size();i++){
								AudioContent content = store.getContent(list.get(i)+1);
								try{
									mylibrary.downloadAG(content);
								}catch(RuntimeException exception){
									System.out.println(exception.getMessage());
								}
							}
						}
					}
				}
			} 
			catch(RuntimeException exception){
				System.out.println(exception.getMessage());
			}
			System.out.print("\n>");
		}
	}
}
