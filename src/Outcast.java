import java.io.*;
import java.util.*;

public class Outcast {
    
    private WordNet words;
    
    //constructor takes WordNet object
    public Outcast(WordNet wordnet){
        words=wordnet;
        
    }
    
    //given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns){
        
        //calculate and store the sum of the distances for each noun
        int[] distances=new int[nouns.length];
        for(int i=0; i<nouns.length; i++){
            distances[i]=0;
            for(int j=0; j<nouns.length; j++)
                distances[i]+=words.distance(nouns[i],nouns[j]);
        }
        
        //determine index of noun with the maximum sum of distances
        int max=0;
        int maxIndex=0;
        for(int i=0; i<nouns.length; i++){
            if(distances[i]>max){
                maxIndex=i;
                max=distances[i];
            }
        }
        
        //return outcast, i.e. noun indexed by maxIndex
        return nouns[maxIndex];
    }
    
    //test client takes names of synset, hypernym, and outcast files, and prints an outcast in each file
    public static void main(String[] args) throws FileNotFoundException{
        WordNet wordnet=new WordNet("synsets.txt", "hypernyms.txt");
        Outcast outcast=new Outcast(wordnet);
        
        for(int t=0; t<3; t++){
            Scanner input=new Scanner(System.in);
            System.out.print("Enter the file name: ");
            String fileName=input.nextLine();
            System.out.print("\nEnter the file size: ");
            int fileSize=input.nextInt();
            
            File inFile=new File(fileName);
            Scanner input1=new Scanner(inFile);
            String[] nouns=new String[fileSize];
            
            int i=0;
            while(input1.hasNextLine()){
                nouns[i]=input1.nextLine();
                i++;
            }
            
            System.out.println(fileName+": "+outcast.outcast(nouns));
        }
    }
}
