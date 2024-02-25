package polinomi;

import java.util.*;

public class PolinomioSet extends PolinomioAstratto {
    private Set<Monomio> set=new TreeSet<>();

    @Override
    public void add(Monomio m) {
        if(m.getCOEFF()==0) return;
        if(set.contains(m)) {
           for(Monomio n:set){
                if(n.equals(m)) {
                    n=n.add(m);
                    set.remove(n);
                    if(n.getCOEFF()!=0) set.add(n);
                    break;                    
                }
           }
        }
        else set.add(m);
    }
    
    @Override
    public Polinomio factory() {
        return new PolinomioSet();
    }

    @Override
    public Iterator<Monomio> iterator() {
        return set.iterator();
    }
}
