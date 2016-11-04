import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class PG2TSB {
	
	public static void main(String[] args) throws IOException {
		String pageReference = "";
		BufferedReader buffer = new BufferedReader(new FileReader("pageRefString.txt"));
        String line = "";
        while ((line = buffer.readLine()) != null) {
        	pageReference += line;
        	System.out.println(line);
        }
        buffer.close();
        
        String[] pageReferenceArray = pageReference.split(" ");
        paging(3, pageReferenceArray);
        paging(4, pageReferenceArray);
	}
	 
	private static void paging(int numberOfFrames, String[] pages) throws IOException{
		String dataToDisplay = "";
		int numberOfPageFaults = 0;
		String[] frames = new String[numberOfFrames];
		int[] lastUseOfPage = new int[numberOfFrames];
		
		for(int i = 0; i < lastUseOfPage.length; i++){
			lastUseOfPage[i] = 0;
		}
		
		for(int i = 0; i < frames.length; i++){
			frames[i] = "-";
		}
		
		System.out.println("\n\nRunning LRU simulation with " + numberOfFrames + " Frames. \n");
		boolean pageExist;
		
		for(int i = 0; i < pages.length; i++){
			pageExist = false;
			for(int j = 0; j < frames.length; ++j){;
				if(frames[j].equals(pages[i])){
					pageExist = true;
				}
			}
			
			if(pageExist != true){
				int pageToReplace = getLongestTimeSinceUse(lastUseOfPage);
				frames[pageToReplace] = pages[i];
				lastUseOfPage[pageToReplace] = -1;
				numberOfPageFaults += 1;
			}
			
			dataToDisplay += framesToString(frames) + "\n";
			lastUseOfPage = incLastUseOfPage(lastUseOfPage);
		}
		dataToDisplay += "\nTotal Number Of Page Faults: " + numberOfPageFaults;
		System.out.println(dataToDisplay);
		writeDataToOutput(dataToDisplay, numberOfFrames);
	}
	
	private static void writeDataToOutput(String dataToDisplay, int numberOfFrames) throws IOException{
		String[] dataPieces = dataToDisplay.split("\n");
		String file = "LRU output With " + numberOfFrames + " Frame.txt";
		PrintWriter writer = new PrintWriter(file, "UTF-8");
				
		for(int i = 0; i < dataPieces.length; i++){
			writer.println(dataPieces[i]);
			
		}
		writer.close();
	}
	
	private static String framesToString(String[] frames) {
		String text = "";
		
		for(int i = 0; i < frames.length; i++){
			text += frames[i] + " ";
		}
		
		text += "\n";
		return text;
	}
	
	private static int[] incLastUseOfPage(int[] lastUseOfPage){
		for(int i = 0; i < lastUseOfPage.length; i++){
			lastUseOfPage[i] = lastUseOfPage[i] + 1;
		}
		
		return lastUseOfPage;
	}
	
	private static int getLongestTimeSinceUse(int[] lastUseOfPage){
		int currentPage = lastUseOfPage[0];
		int position = 0;
		
		for(int i = 1; i < lastUseOfPage.length; i++) {
			if(currentPage < lastUseOfPage[i]){
				position = i;
				currentPage = lastUseOfPage[i];
			}
		}
		return position;
	}
}
