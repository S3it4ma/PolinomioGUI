package polinomi;

import java.util.Iterator;
import java.util.StringTokenizer;

public interface Polinomio extends Iterable<Monomio> {
	private static Monomio primoMonomio( StringTokenizer st ) {
		int segno=1, coeff=0, grado=0;
		String tk=st.nextToken();
		
		if( tk.charAt(0)=='-' ) {
			segno=-1;
			tk=st.nextToken();
		}
		
		if( tk.matches("\\d+") ) {
			coeff=Integer.parseInt(tk);
		}
		else if( tk.matches("x(\\^\\d+)?") ) {
			coeff=1;
			int i=tk.indexOf('^');
			if( i>=0 ) 
				grado=Integer.parseInt( tk.substring(i+1) );
			else
				grado=1;
		}
		else {
			int i=tk.indexOf('x');
			coeff=Integer.parseInt( tk.substring(0,i) );
			i=tk.indexOf('^',i+1);
			if( i>=0 )
				grado=Integer.parseInt( tk.substring(i+1) );
			else
				grado=1;
		}
		coeff=coeff*segno;
		return new Monomio(coeff,grado);
	}//primoMonomio
	
	private static Monomio prossimoMonomio( StringTokenizer st ) {
		int coeff=0, grado=0;
		int segno=st.nextToken().charAt(0)=='+'? 1 : -1;
		String tk=st.nextToken();
		if( tk.matches("\\d+") ) {
			coeff=Integer.parseInt(tk);
		}
		else if( tk.matches("x(\\^\\d+)?") ) {
			coeff=1;
			int i=tk.indexOf('^');
			if( i>=0 ) 
				grado=Integer.parseInt(tk.substring(i+1));
			else
				grado=1;
		}
		else {
			int i=tk.indexOf('x');
			coeff=Integer.parseInt( tk.substring(0,i) );
			i=tk.indexOf('^',i+1);
			if( i>=0 )
				grado=Integer.parseInt( tk.substring(i+1) );
			else
				grado=1;
		}
		coeff=coeff*segno;
		return new Monomio(coeff,grado);
	}//prossimoMonomio
	
    static void parse(String from, Polinomio to){
        String MONOMIO="(\\d+|x(\\^\\d+)?|\\d+x(\\^\\d+)?)";
        String SEGNO="[\\+\\-]";
        String POLINOMIO="\\-?"+MONOMIO+"("+SEGNO+MONOMIO+")*";
        if(!from.matches(POLINOMIO)) throw new IllegalArgumentException("Formato polinomio errato.");

        StringTokenizer st=new StringTokenizer(from, "+-", true);
        to.add(primoMonomio(st));
        while(st.hasMoreTokens()){
            to.add(prossimoMonomio(st));
        }
    }

    default int size(){
        int c=0;
        Iterator<Monomio> it=iterator();
        while(it.hasNext()) c++;
        return c;
    }
    default void clear(){
        Iterator<Monomio> it=iterator();
        while(it.hasNext()){
            it.next();
            it.remove();
        }
    }
    void add(Monomio m);

    /*meccanismo di igegneria del software che permette di scrivere gli altri metodi che altrimenti non potrei perchè
    non saprei a che data type appartiene (ArrayList, LL, Set...)*/
    Polinomio factory();
    default Polinomio add(Polinomio p){
        Polinomio somma=factory();
        for(Monomio m:this)
            somma.add(m);
        for(Monomio m:p)
            somma.add(m);
        return somma;
    }
    default Polinomio mul(Polinomio p){
        Polinomio ris=factory();
        for(Monomio m:this){
            Polinomio prodPar=p.mul(m);
            ris=ris.add(prodPar);
        }
        return ris;
    }
    default Polinomio mul(Monomio m){
        Polinomio ris=factory();
        for(Monomio x:this){
            ris.add(x.mul(m));
        }
        return ris;
    }
    default Polinomio derivata(){
        Polinomio ris=factory();
        for(Monomio m:this){
            int grado=m.getGRADO();
            ris.add(new Monomio(m.getCOEFF()*grado, grado-1));
        }
        return ris;
    }
    default double valore(double x){
        double v=0;
        for(Monomio m:this) v+=m.getCOEFF()*Math.pow(x, m.getGRADO());
        return v;
    }
}
