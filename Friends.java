package apps;

import structures.Queue;
import structures.Stack;

import java.util.*;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2. Chain is returned as a
	 * sequence of names starting with p1, and ending with p2. Each pair (n1,n2)
	 * of consecutive names in the returned chain is an edge in the graph.
	 * 
	 * @param g
	 *            Graph for which shortest chain is to be found.
	 * @param p1
	 *            Person with whom the chain originates
	 * @param p2
	 *            Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no path from
	 *         p1 to p2
	 */

	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		 
		 
		ArrayList<String> result = new ArrayList<String>();
		Queue<Integer> q = new Queue<Integer>();


		boolean[] bo = new boolean[g.members.length];
		bo[g.map.get(p1)] = true;

		q.enqueue(g.map.get(p1));

		HashMap<Person, Person> previous = new HashMap<Person, Person>();
		ArrayList<String> shortestPath = new ArrayList<String>();

		while (!q.isEmpty()) {
			int per = q.dequeue();

			if (g.members[per].name.equals(p2)) {

				break;
			} else {
				Friend person = g.members[per].first;

				while (person != null) {

					if (!bo[person.fnum]) {
						q.enqueue(person.fnum);
						bo[person.fnum] = true;
						previous.put(g.members[person.fnum], g.members[per]);

					}

					person = person.next;
				}

			}

		}

		for (Person v = g.members[g.map.get(p2)]; v != null; v = previous.get(v)) {
			shortestPath.add(v.name);
		}

		Collections.reverse(shortestPath);
		Set<String> s = new LinkedHashSet<>(shortestPath);

		if (s.size() == 1) {
			s.clear();
		}

		result.addAll(s);
		System.out.println(result);
		return result;

	}

	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list
	 * contains the names of all students in a clique.
	 * 
	 * @param g
	 *            Graph for which cliques are to be found.
	 * @param school
	 *            Name of school
	 * @return Array list of clique array lists. Null if there is no student in
	 *         the given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {

		ArrayList<Person> kanyeclique = new ArrayList<Person>();

		for (int i = 0; i < g.members.length; i++) {

			if (g.members[i].school == null) {
				continue;
			} else if (g.members[i].school.equals(school)) {
				kanyeclique.add(g.members[i]);
			}

		}

		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

		while (kanyeclique.size() > 0) {
			result.add(recursive(g, kanyeclique, school));
		}

		return result;

	}

	private static ArrayList<String> recursive(Graph g, ArrayList<Person> kanyeclique, String school) {

		boolean[] visited = new boolean[g.members.length];
		ArrayList<String> temp1 = new ArrayList<String>();
		Queue<Integer> q = new Queue<Integer>();

		int temp = g.map.get(kanyeclique.remove(0).name);
		visited[temp] = true;
		q.enqueue(temp);

		while (!q.isEmpty()) {

			int dq = q.dequeue();
			temp1.add(g.members[dq].name); // adding first person
			Friend p = g.members[dq].first; // this is friend of first person

			while (p != null) {

				if (g.members[p.fnum].school != null) {
					if (!visited[p.fnum] && g.members[p.fnum].school.equals(school)) {

						if (kanyeclique.contains(g.members[p.fnum])) {
							kanyeclique.remove(g.members[p.fnum]);

						}
						q.enqueue(p.fnum);// enqueue the first friend
						visited[p.fnum] = true; // mark this friend as visited.
						temp1.add(g.members[p.fnum].name);

					}

				}
				p = p.next;

			}

		}

		Set<String> hs = new HashSet<>();
		hs.addAll(temp1);
		temp1.clear();
		temp1.addAll(hs);

		return temp1;
	}

	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g
	 *            Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {

		/** COMPLETE THIS METHOD **/

		ArrayList<String> result = new ArrayList<String>();
		boolean[] visited = new boolean[g.members.length];

		HashMap<Person, ArrayList<Integer>> previous = new HashMap<Person, ArrayList<Integer>>();

		int count = 0;

		Person start = g.members[0];

		System.out.println(Arrays.toString(visited));
		for (Map.Entry<Person, ArrayList<Integer>> entry : previous.entrySet()) {
			Person key = entry.getKey();
			ArrayList<Integer> value = entry.getValue();
			System.out.println(key.name + " " + value);
			// ...
		}

		for (int blah = 0; blah < g.members.length; blah++) {
			if (visited[blah] == false) {
				dfs(g.members, g.map.get(g.members[blah].name), visited, count, previous, result, start);
			}
		}
		Set<String> hs = new HashSet<>();
		hs.addAll(result);
		result.clear();
		result.addAll(hs);
		System.out.println(result);

		return result;

	}

	private static void dfs(Person[] adj, int v, boolean[] visited, int count,
			HashMap<Person, ArrayList<Integer>> previous, ArrayList<String> result, Person start) {
		visited[v] = true;
		ArrayList<Integer> list = new ArrayList<Integer>();
		count = count + 1;
		for (Map.Entry<Person, ArrayList<Integer>> entry : previous.entrySet()) {
			ArrayList<Integer> value = entry.getValue();
			if (value.get(0) == count) {
				count = count + 1;
			}
		}
		list.add(count);
		list.add(count);

		previous.put(adj[v], list);

		// When the DFS backs up from a neighbor, w, to v, if dfsnum(v) >
		// back(w),
		// then back(v) is set to min(back(v),back(w)
		// If a neighbor, w, is already visited then back(v) is set to
		// min(back(v),dfsnum(w))
		for (Friend f1 = adj[v].first; f1 != null; f1 = f1.next) {

			if (visited[f1.fnum]) {

				int dfsnum = 0;
				int back = 1;

				ArrayList<Integer> neighbor = previous.get(adj[v]);
				ArrayList<Integer> original = previous.get(adj[f1.fnum]); 
				neighbor.set(1, Math.min(neighbor.get(back), original.get(dfsnum)));
				
				if (adj[v].first.next == null) {
					result.add(adj[adj[v].first.fnum].name);
				}

			} else {

				dfs(adj, f1.fnum, visited, count, previous, result, start);
				ArrayList<Integer> neighbor = previous.get(adj[v]); 
																	
				ArrayList<Integer> original = previous.get(adj[f1.fnum]); 											
														
				if (neighbor.get(0) > original.get(1)) {
					neighbor.set(1, Math.min(neighbor.get(1), original.get(1)));
				}
				
				if (neighbor.get(0) <= original.get(1) && v != 0 && adj[v].first.next != null) {

					result.add(adj[v].name);
				}

			}

		}
	}

}
