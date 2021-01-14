import java.io.*;
import java.util.*;

public class Naloga7{
    public static FileWriter iz;
    public static LinkedList<Integer> koncni = new LinkedList<Integer>();
    public static ArrayList[] izpis;
	
	public static void main(String args[]){
        String vhod = args[0];
        String izhod = args[1];

        try{
            BufferedReader sc = new BufferedReader(new FileReader(vhod));
            String[] stevilo = sc.readLine().split(" ");

            int vozlisca = Integer.parseInt(stevilo[0]);
            int povezave = Integer.parseInt(stevilo[1]);

            String[] pot = sc.readLine().split(" ");
            
            int zacetek = Integer.parseInt(pot[0]);
            int konec = Integer.parseInt(pot[1]);
            
            if(zacetek > konec){
                int tmp = konec;
                konec = zacetek;
                zacetek = tmp;
            }
           
            iz = new FileWriter(izhod);

            ArrayList[] adj = new ArrayList[vozlisca];
            izpis = new ArrayList[vozlisca];
            for (int i = 0; i < vozlisca; i++) {
                adj[i] = new ArrayList();
                izpis[i] = new ArrayList();
            }

            while(povezave > 0){
                String[] pov = sc.readLine().split(" ");
                addEdge(adj, Integer.parseInt(pov[0]), Integer.parseInt(pov[1]));
                povezave--;
            }
		
            najkrajsa(adj, zacetek, konec, vozlisca);
            
            for(int i = koncni.size()-2; i >= 0; i--){
                isBridge(adj, zacetek, konec, koncni.get(i), koncni.get(i+1), vozlisca);
            }
        
            for(int i = 0; i < vozlisca; i++){
                izpis[i].sort(null);
                if(izpis[i].size() > 0){
                    int tmp = 0;
                    while(tmp <= izpis[i].size()-1){
                        iz.write(i + " " + (Integer)izpis[i].get(tmp) + "\r\n");
                        tmp++;
                    }
                }
            }
            sc.close();
            iz.close();
        }catch (FileNotFoundException ex) {
            System.out.printf("Datoteka %s ne obstaja!%n", vhod);
        }catch(IOException ex){
            System.out.printf("Prišlo je do napake.");
        }
    }

	private static void addEdge(ArrayList[] adj, int i, int j){
		adj[i].add(j);
		adj[j].add(i);
	}

	private static boolean najkrajsa(ArrayList[] adj, int s, int dest, int v){

		int pred[] = new int[v];

		if (BFS(adj, s, dest, v, pred) == false)
			return false;
	
		int iter = dest;
        koncni.add(iter);
		while (pred[iter] != -1){
			koncni.add(pred[iter]);
			iter = pred[iter];
        }
        return true;
    }

	private static boolean BFS(ArrayList[] adj, int src, int dest, int v, int pred[]){
		LinkedList zaPregled = new LinkedList();
		boolean jeObiskano[] = new boolean[v];

		for(int i = 0; i < v; i++)
			pred[i] = -1;
		

		jeObiskano[src] = true;
		zaPregled.add(src);
 
		while(!zaPregled.isEmpty()){
			int u = (int) zaPregled.remove();
			for(int i = 0; i < adj[u].size(); i++){
				if (jeObiskano[(int) adj[u].get(i)] == false) {
					jeObiskano[(int) adj[u].get(i)] = true;
					pred[(int) adj[u].get(i)] = u;
					zaPregled.add((int) adj[u].get(i));
					if ((int)adj[u].get(i) == dest){
                        return true;
                    }
				}
            }
        }
        
		return false;
    }

    public static void isBridge(ArrayList[] adj, int zac, int cilj, int tmpSource, int tmpDestination, int vozlisca){
        izbrisiPovezavo(adj, tmpSource, tmpDestination);
        boolean flag = najkrajsa(adj, zac, cilj, vozlisca);
        if(!flag){
            if(tmpSource < tmpDestination) izpis[tmpSource].add(tmpDestination); 
            else izpis[tmpDestination].add(tmpSource);
        } 
        addEdge(adj, tmpSource, tmpDestination);
    }
    
    public static void izbrisiPovezavo(ArrayList[] adj, int zac, int cilj){
        for(int i = 0; i < adj[zac].size(); i++){
            if((int)adj[zac].get(i)==cilj) {
                adj[zac].remove(i);
                break;
            }
        }

        for(int i = 0; i < adj[cilj].size();i++){
            if((int)adj[cilj].get(i)==zac){
                adj[cilj].remove(i);
                break;
            }
        }
    }
}

