import java.io.*;
import java.util.*;

class Povezave {
    int start, cilj, utez, h;
    Povezave(int start, int cilj, int utez, int h) {
            this.start = start;
            this.cilj = cilj;
            this.utez = utez;
            this.h = h;
        }
}

public class Naloga10{
    public static boolean flag = false;
    public static ArrayList potT = new ArrayList();
    public static ArrayList potNova = new ArrayList();

    public static boolean bingo(ArrayList<ArrayList<Povezave>> sosedi, boolean flag, int zacetek, int kon, FileWriter iz, boolean[] visited) throws IOException{
        flag = true;
        int tmp = 0;
        int trenOcjena = 0;
		while (zacetek != kon) {
            int vsota = Integer.MAX_VALUE/2;
            int naj = Integer.MAX_VALUE/2;
            
            visited[zacetek] = true;
			for(Povezave v : sosedi.get(zacetek)){
                if(v.cilj == kon ) vsota = v.utez + 0;
                if(v.cilj != kon ) vsota = v.utez + sosedi.get(v.cilj).get(0).h;
                if(vsota < naj && visited[v.cilj] == false){
                    naj = vsota;
                    tmp = v.cilj;
                }
                trenOcjena = v.h;
            }

            if(tmp==zacetek){ 
                iz.write(zacetek + "\n");
                potT.add(zacetek);
                return false;
            }
            
            if(sosedi.get(tmp) == null){
                sosedi.get(tmp).add(new Povezave(tmp, -1, -1, Integer.MAX_VALUE/2));
                return false;
            }
            
            if(trenOcjena < naj){
                flag = false;
                for(Povezave v : sosedi.get(zacetek))
                    v.h = naj;
            }

            iz.write(zacetek + ",");
            potT.add(zacetek);
            zacetek = tmp;
        }
        iz.write(zacetek + "\n");
        potT.add(zacetek);
        return flag;
    }
    
	public static void main (String[] args) throws FileNotFoundException, IOException{
        BufferedReader sc = new BufferedReader(new FileReader(args[0]));
        FileWriter iz = new FileWriter(new File(args[1]));

        int st = Integer.parseInt(sc.readLine());
        
        ArrayList<ArrayList<Povezave>> sosedi = new ArrayList<ArrayList<Povezave>>();

        sosedi.add(new ArrayList<Povezave>());

        while(st > 0){
            String[] vhod = sc.readLine().split(",");
            sosedi.add(new ArrayList<Povezave>());
            sosedi.get(Integer.parseInt(vhod[0])).add(new Povezave(Integer.parseInt(vhod[0]), Integer.parseInt(vhod[1]), Integer.parseInt(vhod[2]), 0));
            st--;
        }
        
        String[] pot = sc.readLine().split(",");
        int zac = Integer.parseInt(pot[0]);
        int kon = Integer.parseInt(pot[1]);
        boolean[] visited = new boolean[kon+1];
        boolean tmp = false;
        while(!tmp){
            tmp = bingo(sosedi, flag, zac, kon,iz,visited);
            if(tmp && !potT.equals(potNova)){
                for(int i = 0; i < potT.size(); i++){
                    if(i == potT.size() -1){
                        iz.write(potT.get(i) + "\n");
                        break;
                    }
                    iz.write(potT.get(i) + ",");
                }
            }
            potNova = (ArrayList) potT.clone();
            potT.clear();
            visited = new boolean[kon+1];
        }
        sc.close();
        iz.close();
	}
}