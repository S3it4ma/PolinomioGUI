package polinomi;

import java.util.Iterator;

public abstract class PolinomioAstratto implements Polinomio {
    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder(200);
        boolean flag=true;
        for(Monomio m:this){
            if(flag) flag=!flag;
            else{
                if(m.getCOEFF()>0) sb.append("+");
            }
            sb.append(m);
        }
        if(sb.toString().equals("")) return "0";
        return sb.toString();
    }
    @Override
    public int hashCode() {
        //sol di tipo generale
        final int M=83;
        int h=0;
        for(Monomio m:this){
            h=h*M+String.valueOf(m.getCOEFF()+m.getGRADO()).hashCode();
        }
        return h;
    }
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof PolinomioAstratto))  return false;
        if(this==obj) return true;
        Polinomio p=(Polinomio) obj;
        if(size()!=p.size()) return false;
        Iterator<Monomio> it1=iterator(), it2=p.iterator();
        while(it1.hasNext()){
            Monomio m1=it1.next();
            Monomio m2=it2.next();
            if(!m1.equals(m2) || m1.getCOEFF()!=m2.getCOEFF()) return false;
        }
        return true;
    }
}