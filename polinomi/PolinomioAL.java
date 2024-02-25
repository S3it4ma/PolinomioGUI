package polinomi;

import java.util.*;

public class PolinomioAL extends PolinomioAstratto{
    private List<Monomio> al;

    public PolinomioAL(int capacity){
        if (capacity<=0) throw new IllegalArgumentException();
        al=new ArrayList<>(capacity);
    }

    @Override 
    public int size(){
        return al.size();
    }

    @Override
    public void add(Monomio m) {
        if(m.getCOEFF()==0) return;
        int j=Collections.binarySearch(al, m);
        if(j>=0){
            al.set(j, al.get(j).add(m));
            if(al.get(j).getCOEFF()==0) al.remove(j);
        }
        else{
            boolean flag=false;
            for(int i=0; i<al.size() && !flag; ++i)
            if(al.get(i).compareTo(m)>0) {
                al.add(i, m);
                flag=!flag;
            }
        
        if (!flag) al.add(m);
        }
    }

    @Override
    public Polinomio factory() {
        return new PolinomioAL(20);
    }

    @Override
    public Iterator<Monomio> iterator() {
        return al.iterator();
    }
}
