# Relationship-Graph

Algorithms for an undirected graph that represent relationships. (i.e Facebook )
The Friends.java consists of three methods.

1. ShortestChain finds the shortest path between user input, p1 and p2. This is done by running a successful breadth-first search starting from p1. 
2. Cliques finds all the cliques of students in a given school. The method returns an array list of array lists of cliques within the given school. 
3. Connectors runs a successful depth-first search on the graph and returns the connectors. A vertex is a connector if there are at least two other vertices x and w for which every path x and w goes through v.
