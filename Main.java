// Unit tests the DirectedCycle data type.
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        File inFile = new File("tinyDG.txt");
        Scanner input = new Scanner(inFile);
        Digraph G = new Digraph(input);

        DirectedCycle finder = new DirectedCycle(G);
        if (finder.hasCycle()) {
            System.out.print("Directed cycle: ");
            for (int v : finder.cycle()) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
        else 
            System.out.println("No directed cycle");
        System.out.println();
    }
}