package polinomi;

public class Monomio implements Comparable<Monomio> {
    private final int COEFF, GRADO;

    public Monomio(final int C, final int G){
        if(G<0) throw new IllegalArgumentException();
        COEFF=C; GRADO=G;
    }
    public Monomio(Monomio m){
        COEFF=m.COEFF;
        GRADO=m.GRADO;
    }

    public int getCOEFF() {
        return COEFF;
    }
    public int getGRADO() {
        return GRADO;
    }

    public Monomio add(Monomio m){
        if(!this.equals(m)) throw new RuntimeException("monomi non simili");
        return new Monomio(COEFF+m.COEFF, GRADO);
    }

    public Monomio mul(int s){
        return new Monomio(COEFF*s, GRADO);
    }

    public Monomio mul(Monomio m){
        return new Monomio(COEFF*m.COEFF, GRADO+m.GRADO);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder(20);
        if(COEFF==0) sb.append(0);
        else{
            if(COEFF<0) sb.append('-');
            if(Math.abs(COEFF)!=1 || GRADO==0) sb.append(Math.abs(COEFF));
            if(GRADO>0) sb.append('x');
            if(GRADO>1) sb.append("^"+GRADO);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Monomio)) return false;   //controlla caso null
        if(obj==this)return true;
        Monomio x=(Monomio) obj;
        return GRADO==x.GRADO;
    }   // equals basato sul grado e non il coefficiente

    @Override
    public int hashCode() {
        return GRADO;
    }

    @Override
    public int compareTo(Monomio m) {
        if(GRADO>m.GRADO) return -1;
        if(GRADO<m.GRADO) return 1;
        return 0;
    } 
}
