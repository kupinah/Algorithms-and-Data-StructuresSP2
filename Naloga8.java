import java.io.*;
import java.util.*;

class Vozlisce{
    int stSinov;
    String oznaka;
    ArrayList sinovi;

    Vozlisce(String oznaka, ArrayList sinovi, int stSinov){
        this.oznaka = oznaka;
        this.sinovi = sinovi;
        this.stSinov = stSinov;
    }
}

public class Naloga8{
    
    public static boolean isci(ArrayList<Vozlisce> glavni, ArrayList<Vozlisce> vzorec, boolean flag, int iterG, int iterV){
        if(!glavni.get(iterG).oznaka.equals(vzorec.get(iterV).oznaka)) return true; 
        if(vzorec.get(iterV).stSinov > 0 && (glavni.get(iterG).stSinov != vzorec.get(iterV).stSinov)) return true;        

        int st = 0;
        while(st < vzorec.get(iterV).stSinov && st < glavni.get(iterG).stSinov){
            int prejsnjiG = iterG;
            int prejsnjiV = iterV;
            iterG = (int)glavni.get(iterG).sinovi.get(st)-1;
            iterV = (int)vzorec.get(iterV).sinovi.get(st)-1;
            flag = flag || isci(glavni, vzorec, flag, iterG, iterV); 
            iterG = prejsnjiG;
            iterV = prejsnjiV;
            st++;
        }
        return flag;
    }

    public static void main(String[] args){
        String vhod = args[0];
        String izhod = args[1];

        try{
            Scanner sc = new Scanner(new File(vhod));
            FileWriter iz = new FileWriter(new File(izhod));

            int stVzorec = Integer.parseInt(sc.nextLine());
            ArrayList<Vozlisce> vzorec = new ArrayList<Vozlisce>();
            for(int i = 0; i < stVzorec; i++){
                String[] podatki = sc.nextLine().split(",");
                ArrayList sinVzorec = new ArrayList();
                int tmp = 2;
                while(tmp < podatki.length){
                    sinVzorec.add(Integer.parseInt(podatki[tmp]));
                    tmp++;
                }
                vzorec.add(new Vozlisce(podatki[1], sinVzorec, tmp-2));
            }

            int stGlavni = Integer.parseInt(sc.nextLine());
            ArrayList<Vozlisce> glavni = new ArrayList<Vozlisce>();
            for(int i = 0; i < stGlavni; i++){
                String[] podatki = sc.nextLine().split(",");
                ArrayList sinGlavni = new ArrayList();
                int tmp = 2;
                while(tmp < podatki.length){
                    sinGlavni.add(Integer.parseInt(podatki[tmp]));
                    tmp++;
                }
                glavni.add(new Vozlisce(podatki[1], sinGlavni, tmp-2));
            }
            int velikost = glavni.size();
            int iter = 0;
            int counter = 0;
            while(iter < velikost){
                boolean flag = false;
                flag = isci(glavni, vzorec, flag, iter, 0);
                if(!flag) counter++;
                iter++;
            }
            iz.write(counter + "\r\n");
            iz.close();
        }catch(FileNotFoundException ex){
            System.out.println("Datoteka ne obstaja.");
        }catch(IOException e){
            System.out.println("Prišlo je do napake.");
        }
    }
}
