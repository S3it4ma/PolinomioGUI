package polinomi;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class PolinomioMap extends PolinomioAstratto {
    private Map<Monomio, Monomio> mappa=new TreeMap<>();

    @Override
    public int size(){
        return mappa.size();
    }

    @Override
    public void add(Monomio m) {
        if(m.getCOEFF()==0) return;
        if(!mappa.containsKey(m)) mappa.put(m, m);
        else{
            Monomio in=mappa.get(m).add(m);
            if(in.getCOEFF()!=0) mappa.put(m, in);
            else mappa.remove(m);
        }
    }

    @Override
    public Polinomio factory() {
        return new PolinomioMap();
    }

    @Override
    public Iterator<Monomio> iterator() {
        return mappa.values().iterator();
    }
    
}
