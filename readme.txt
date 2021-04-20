/* *****************************************************************************
 *  Name:     
 *  NetID:      
 *
 *  Partner Name:     
 *  Partner NetID:     
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 4: WordNet


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in synsets.txt. Why did you make this choice?
 **************************************************************************** */
For synsets, I used a HashMap<Integer, String> structure as I thought it was the best
possible way to map the noun key to the noun itself (an int and a string, respectively).


/* *****************************************************************************
 *  Describe concisely the data structure(s) you used to store the 
 *  information in hypernyms.txt. Why did you make this choice?
 **************************************************************************** */
For hypernyms, I used a digraph because it is essentially a map structure that can have
multiple values per key, and like a HashMap it is a good way to map a noun key to the noun itself,
as well as to link two or more related nouns.


/* *****************************************************************************
 *  Describe concisely the algorithm you use in the constructor of
 *  ShortestCommonAncestor to check if the digraph is a rooted DAG.
 *  What is the order of growth of the worst-case running times of
 *  your algorithm? Express your answer as a function of the
 *  number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) 
 **************************************************************************** */

Description:



Order of growth of running time:


/* *****************************************************************************
 *  Describe concisely your algorithm to compute the shortest common ancestor
 *  in ShortestCommonAncestor. For each method, give the order of growth of
 *  the best- and worst-case running times. Express your answers as functions
 *  of the number of vertices V and the number of edges E in the digraph.
 *  (Do not use other parameters.) 
 *
 *  If you use hashing, assume the uniform hashing assumption so that put()
 *  and get() take constant time per operation.
 *
 *  Be careful! If you use a BreadthFirstDirectedPaths object, don't forget
 *  to count the time needed to initialize the marked[], edgeTo[], and
 *  distTo[] arrays.
 **************************************************************************** */

Description:


                                 running time
method                  best case            worst case
--------------------------------------------------------
length()

ancestor()

lengthSubset()

ancestorSubset()



/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings and lectures, but do
 *  include any help from people (including
 *  classmates and friends) and attribute them by name.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe any serious problems you encountered.                    
 **************************************************************************** */
Rachel's sickness is causing us to not be as productive as a unit as usual, but
there isn't a whole lot I can do about that other than hope she gets better soon.

/* *****************************************************************************
 *  If you worked with a partner, give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */
I worked on the part of the WordNet class and the Outcast class. Rachel's job is to work on
the ShortestCommonAncestor class.



/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on how much you learned from doing the assignment, and whether    
 *  you enjoyed doing it.                                             
 **************************************************************************** */



/* *****************************************************************************
 *  Include the screenshots of your output.
 **************************************************************************** */
