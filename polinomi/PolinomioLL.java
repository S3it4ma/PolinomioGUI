package polinomi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class PolinomioLL extends PolinomioAstratto {
    private LinkedList<Monomio> ll=new LinkedList<>();

    @Override
    public void add(Monomio m) {
        if(m.getCOEFF()==0) return;
        ListIterator<Monomio> i=ll.listIterator();
        boolean flag=false;  //fino a che non ho aggiunto
        while(i.hasNext() && !flag){
            Monomio n=i.next();
            if(m.equals(n)) {
                n=n.add(m);
                if(n.getCOEFF()==0) {
                    i.remove();
                }
                else i.set(n);
                flag=true;
            }
            else if(n.compareTo(m)>0){
                i.previous(); i.add(m); flag=true;
            }
        }
        if(!flag) i.add(m);
    }

    @Override
    public Polinomio factory() {
        return new PolinomioLL();
    }

    @Override
    public Iterator<Monomio> iterator() {
        return ll.iterator();
    }
    
}
