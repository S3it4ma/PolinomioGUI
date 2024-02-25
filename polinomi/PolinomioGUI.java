package polinomi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.FontUIResource;

import java.io.*;
import java.util.HashMap;


class PoliFrame extends JFrame {
    private Icon icon=new ImageIcon(getClass().getResource("notSelectedR2.png"));
    private Icon icon2=new ImageIcon(getClass().getResource("Selected.png"));
    private Font FONT=new FontUIResource("Helvetica", Font.PLAIN, 18);
    private Font FONTNUM=new FontUIResource("Cambria", Font.PLAIN, 18);
    private Color COLORE=new ColorUIResource(94, 56, 213);
    private AEL listener;
    private File fileDiSalvataggio=null;
    private JPanel pannelloPol; //pannello su cui aggiungere checkbox di polinomi
    private JPanel pannelloAggiungi; //pannello per aggiungere polinomi con input e bottone aggiungi
    private JTextField aggiungiPol;
    private JButton button=new JButton("Aggiungi");
    private JMenuItem apri, salva, esci, rimuovi, calcDer, calcSomma, calcProd, svuota, valore, colore, about, help;
    private String titolo="Polinomio GUI";
    private HashMap<String, PolinomioSet> p=new HashMap<>();
    private enum STATE{AVVIATO, INIZIALE};
    private STATE stato;

    public PoliFrame(){
        setTitle(titolo);
        setSize(1200, 700);
        setLocationRelativeTo(null);
    
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                if(consensoUscita()) System.exit(0);
            }
        });

        listener=new AEL();

        JMenuBar jmb=new JMenuBar();
        jmb.setPreferredSize(new DimensionUIResource(1200, 30));
        this.setJMenuBar(jmb);

        DimensionUIResource prefSizeMenuItem=new DimensionUIResource(60, 27);

        JMenu file=new JMenu("File");
        apri=new JMenuItem("Apri"); apri.addActionListener(listener); file.add(apri);
        salva=new JMenuItem("Salva"); salva.addActionListener(listener); file.add(salva);
        esci=new JMenuItem("Chiudi File"); esci.addActionListener(listener); file.add(esci);

        JMenu azioni=new JMenu("Azioni");
        rimuovi=new JMenuItem("Rimuovi"); rimuovi.addActionListener(listener); azioni.add(rimuovi);
        calcDer=new JMenuItem("Calcola derivata"); calcDer.addActionListener(listener); azioni.add(calcDer);
        calcSomma=new JMenuItem("Calcola somma"); calcSomma.addActionListener(listener); azioni.add(calcSomma);
        calcProd=new JMenuItem("Calcola prodotto"); calcProd.addActionListener(listener); azioni.add(calcProd);
        valore=new JMenuItem("Calcola valore"); valore.addActionListener(listener); azioni.add(valore);
        svuota=new JMenuItem("Elimina tutti i polinomi"); svuota.addActionListener(listener); azioni.add(svuota);

        JMenu sfondo=new JMenu("Sfondo"); 
        colore=new JMenuItem("Colore"); colore.addActionListener(listener); sfondo.add(colore);

        JMenu question=new JMenu("  ?  "); 
        about=new JMenuItem("About"); about.addActionListener(listener); question.add(about);
        help=new JMenuItem("Help"); help.addActionListener(listener); question.add(help);

        jmb.add(file); jmb.add(azioni); jmb.add(sfondo); jmb.add(question); 

        Container pane=this.getContentPane();

        aggiungiPol=new JTextField("Inserisci polinomio da aggiungere", 28);
        aggiungiPol.setPreferredSize(prefSizeMenuItem); aggiungiPol.setFont(FONTNUM);
        pannelloAggiungi=new JPanel(new FlowLayout());
        button.addActionListener(listener);
        pannelloAggiungi.add(aggiungiPol); pannelloAggiungi.add(button);
        pane.add(pannelloAggiungi, BorderLayout.PAGE_START);

        pannelloPol=new JPanel(); pannelloPol.setLayout(new BoxLayout(pannelloPol, BoxLayout.Y_AXIS));
        pannelloPol.setBackground(COLORE); pannelloPol.setBorder(new EmptyBorder(15, 30, 20, 20));
        
        pane.add(new JScrollPane(pannelloPol), BorderLayout.CENTER); 
        menuIniziale(); stato=STATE.INIZIALE;
    }

    private void menuIniziale() {
        esci.setEnabled(false);
        rimuovi.setEnabled(false);
        calcDer.setEnabled(false);
        calcProd.setEnabled(false);
        calcSomma.setEnabled(false);
        valore.setEnabled(false);
        salva.setEnabled(false);
        svuota.setEnabled(false);
    }

    private void menuAvviato() {
        rimuovi.setEnabled(true);
        calcDer.setEnabled(true);
        calcProd.setEnabled(true);
        calcSomma.setEnabled(true);
        valore.setEnabled(true);
        salva.setEnabled(true);
        svuota.setEnabled(true);
    }

    private boolean consensoUscita(){
        int op=JOptionPane.showConfirmDialog(null, "Continuare ?", "Uscendo perderai tutti i dati", JOptionPane.YES_NO_OPTION);
        return op==JOptionPane.YES_OPTION;
    }

    private class FrameColore extends JFrame implements ActionListener{
        private int WIDTH=450, HEIGHT=250;
        private JPanel pannelloRGB;
        private String[] listaColori={"Nero", "Rosa", "Grigio", "Blu", "Ciano", "Verde", "Rosso", "Arancione", "Beige", "Default"};
        private Color[] listaC= {ColorUIResource.BLACK, ColorUIResource.PINK, ColorUIResource.GRAY, ColorUIResource.BLUE, ColorUIResource.CYAN, new ColorUIResource(3, 192, 60), ColorUIResource.RED, ColorUIResource.ORANGE, new ColorUIResource(200, 173, 127), new ColorUIResource(94, 56, 213)};
        private JTextField rf, gf, bf;
        private JButton ok;

        public FrameColore(){
            setSize(WIDTH, HEIGHT);
            setLocationRelativeTo(null);

            JComboBox<String> cb=new JComboBox<>(listaColori);
            cb.setEditable(false);
            cb.addActionListener(this);

            JLabel r=new JLabel("R: "), g=new JLabel("   G: "), b=new JLabel("   B: ");
            r.setForeground(ColorUIResource.WHITE); g.setForeground(ColorUIResource.WHITE); b.setForeground(ColorUIResource.WHITE);
            rf=new JTextField("0", 4); gf=new JTextField("0", 4); bf=new JTextField("0", 4);
            pannelloRGB=new JPanel(); pannelloRGB.setBackground(COLORE);
            pannelloRGB.add(r); pannelloRGB.add(rf); pannelloRGB.add(g); pannelloRGB.add(gf); pannelloRGB.add(b); pannelloRGB.add(bf);
            pannelloRGB.setBorder(new EmptyBorder(22, 0, 0, 0));
            ok=new JButton("Seleziona"); ok.addActionListener(this);

            this.add(cb, BorderLayout.PAGE_START); this.add(pannelloRGB, BorderLayout.CENTER); this.add(ok, BorderLayout.PAGE_END);
        }

        @Override
        @SuppressWarnings("unchecked")
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() instanceof JComboBox){
                JComboBox<String> cb=(JComboBox<String>) e.getSource();
                String colore=(String) cb.getSelectedItem();

                for(int i=0; i<listaColori.length; ++i){
                    if(colore.equals(listaColori[i])) {
                        Color scelta=listaC[i];
                        rf.setText(""+scelta.getRed());
                        gf.setText(""+scelta.getGreen());
                        bf.setText(""+scelta.getBlue());
                    }
                }
            }
            else if(e.getSource()==ok) {
                try {
                    int r=Integer.parseInt(rf.getText());
                    int g=Integer.parseInt(gf.getText());
                    int b=Integer.parseInt(bf.getText());
                    COLORE=new ColorUIResource(r, g, b);
                    pannelloPol.setBackground(COLORE);
                    this.setVisible(false); this.dispose();
                } catch(Exception exc) {
                    JOptionPane.showMessageDialog(null, "Valori errati: R, G e B devono essere numeri tra 0 e 255", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class FrameAggiungi extends JFrame implements ActionListener{
        private JLabel label;
        private JTextField jtf;
        private JButton aggiungi, ok;
        private PolinomioSet val;
        private int WIDTH=450, HEIGHT=250;

        public FrameAggiungi(String sLabel, PolinomioSet val){
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.val=val;
            setSize(WIDTH, HEIGHT);
            setResizable(false);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            
            label=new JLabel(sLabel); label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setPreferredSize(new DimensionUIResource(WIDTH, HEIGHT/4)); label.setForeground(ColorUIResource.WHITE);
            label.setOpaque(true); label.setBackground(COLORE); 
            label.setFont(FONT);
            this.add(label, BorderLayout.PAGE_START);

            jtf= new JTextField(val.toString(), 28); jtf.setHorizontalAlignment(SwingConstants.CENTER);
            jtf.setFont(FONTNUM); jtf.setEditable(false); jtf.setMaximumSize(new DimensionUIResource(WIDTH, HEIGHT/5));
            this.add(jtf, BorderLayout.CENTER);

            aggiungi=new JButton("Aggiungi"); aggiungi.setPreferredSize(new DimensionUIResource(WIDTH/4, HEIGHT/6));
            ok=new JButton("Ok"); ok.setPreferredSize(new DimensionUIResource(WIDTH/4, HEIGHT/6));
            JPanel p=new JPanel(new FlowLayout());
            p.add(ok); p.add(aggiungi); this.add(p, BorderLayout.PAGE_END);

            p.setBackground(COLORE);

            aggiungi.addActionListener(this);
            ok.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==aggiungi){
                String pol=val.toString();
                p.put(pol, val);

                JCheckBox cb=new JCheckBox(pol);
                cb.setFont(FONTNUM); cb.setBorder(new EmptyBorder(4, 4,  4, 4));
                cb.setBackground(COLORE); cb.setForeground(ColorUIResource.WHITE);
                cb.setIcon(icon); cb.setSelectedIcon(icon2);
                pannelloPol.add(cb);

                PoliFrame.this.revalidate(); PoliFrame.this.repaint();
            }
            
            this.setVisible(false); this.dispose();
        }
    }

    private class AEL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==apri)
                apri();

            else if(e.getSource()==salva) 
                salva();

            else if(e.getSource()==esci) 
                esci();

            else if(e.getSource()==calcDer) 
                calcDer();
            
            else if(e.getSource()==calcProd) 
                calcProd();
            
            else if(e.getSource()==calcSomma) 
                calcSomma();
            
            else if(e.getSource()==rimuovi)
                rimuovi();
            
            else if(e.getSource()==valore) 
                valore();
        
            else if(e.getSource()==svuota)
                svuota();
            
            else if(e.getSource()==button)
                aggiungi();

            else if(e.getSource()==about) 
                JOptionPane.showMessageDialog(null, "Interfaccia Grafica per lo sviluppo di operazioni con polinomi\n - Paolo Costa, matricola 234558", "", JOptionPane.INFORMATION_MESSAGE);
            
            else if(e.getSource()==help) 
                JOptionPane.showMessageDialog(null, "Per svolgere le azioni bisogna selezionare gli elementi in base al tipo di operazione:\n\n"+
                                             " - Derivata e Valore: uno e un solo polinomio;\n\n - Prodotto: con un solo polinomio si effettuerà l'operazione del polinomio con se stesso\n"+
                                             "   (vale anche per la somma), altrimenti bisognerà selezionarne due;\n\n - Altre: almeno un polinomio selezionato.", "", JOptionPane.INFORMATION_MESSAGE);
            
            else if(e.getSource()==colore){
                FrameColore fc=new FrameColore();
                fc.setVisible(true);
            }

            revalidate();
            repaint();
        }
    }

    private void svuota(){
        p.clear();
        pannelloPol.removeAll();
        menuIniziale(); stato=STATE.INIZIALE;
    }

    private void aggiungi(){
        try {
            String polStringa=aggiungiPol.getText();
            PolinomioSet polSet=new PolinomioSet();
            Polinomio.parse(polStringa, polSet);
            polStringa=polSet.toString();
            if(!p.containsKey(polStringa)) {
                p.put(polStringa, polSet);

                JCheckBox cb=new JCheckBox(polStringa);
                cb.setFont(FONTNUM); cb.setBorder(new EmptyBorder(4, 4,  4, 4));
                cb.setOpaque(false); cb.setForeground(ColorUIResource.WHITE);
                cb.setIcon(icon); cb.setSelectedIcon(icon2);
                pannelloPol.add(cb);
            }
            aggiungiPol.setText("");
            if(stato==STATE.INIZIALE) {
                menuAvviato(); stato=STATE.AVVIATO;
            }
        } catch(IllegalArgumentException exc) {
            aggiungiPol.setText("Inserisci polinomio da aggiungere");
        }
    }

    private void apri() {
        JFileChooser fc=new JFileChooser();
        try {
            int risposta=fc.showOpenDialog(null);
            if(risposta==JFileChooser.APPROVE_OPTION){
                if(!fc.getSelectedFile().exists()){
                    JOptionPane.showMessageDialog(null,"File inesistente"); 
                }
                else{	
                    fileDiSalvataggio=fc.getSelectedFile();
                    try{
                        ripristina(fileDiSalvataggio.getAbsolutePath());
                        PoliFrame.this.setTitle(titolo+" - "+fileDiSalvataggio.getName());

                        esci.setEnabled(true);
                        if(stato==STATE.INIZIALE) {
                            menuAvviato(); stato=STATE.AVVIATO;
                        }
                    }catch(Exception exc){
                        JOptionPane.showMessageDialog(null,"File danneggiato o malformato");
                    }
                }
            }
            else if(risposta==JFileChooser.ERROR_OPTION)
                JOptionPane.showMessageDialog(null,"Apertura file fallita (1)");
        } catch(Exception exc) { JOptionPane.showMessageDialog(null, "Apertura file fallita (2)"); }
    }

    private void salva() {
        JFileChooser fc=new JFileChooser();
        if(!(fileDiSalvataggio==null)) {
            int op=JOptionPane.showConfirmDialog(null, "Vuoi salvare?", "Le modifiche andranno perse", JOptionPane.YES_NO_OPTION);
            if(op==JOptionPane.NO_OPTION) return;
        }
        else if(fc.showSaveDialog(null)==JFileChooser.APPROVE_OPTION) {
            fileDiSalvataggio=fc.getSelectedFile();

            esci.setEnabled(true);
            this.setTitle(titolo+" - "+fileDiSalvataggio.getName());
        }
        if(fileDiSalvataggio!=null) try {
            PrintWriter pw=new PrintWriter(new FileWriter(fileDiSalvataggio));
            for(String ps:p.keySet()) {
                pw.println(ps);
            }
            pw.close();
        } catch(IOException exc) {JOptionPane.showMessageDialog(null, "Problema nel salvataggio del file");}
    }

    private void esci() {
        salva();
        fileDiSalvataggio=null;
        this.setTitle(titolo);
        p.clear();
        pannelloPol.removeAll();
        menuIniziale();
        stato=STATE.INIZIALE;
    }

    private void calcDer(){
        PolinomioSet derivata=null;
        int i=0;
        while(i<pannelloPol.getComponentCount()){
            JCheckBox cb=(JCheckBox) pannelloPol.getComponent(i);
            if(cb.isSelected()) {
                if(derivata==null) {
                    derivata=(PolinomioSet) p.get(cb.getText()).derivata();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Troppi elementi selezionati");
                    return;
                }
            }
            ++i;
        }
        if(derivata==null) 
            JOptionPane.showMessageDialog(null, "Nessun elemento selezionato");
        else {
            FrameAggiungi p=new FrameAggiungi("La derivata del polinomio è", derivata);
            p.setVisible(true);
        }
    }

    private void calcProd(){
        PolinomioSet prod=null;
        boolean trovato=false;
        int i=0;
        while(i<pannelloPol.getComponentCount()){
            JCheckBox cb=(JCheckBox) pannelloPol.getComponent(i);
            if(cb.isSelected()) {
                if(trovato) {
                    JOptionPane.showMessageDialog(null, "Troppi elementi selezionati");
                    return;
                }
                else {
                    if(prod==null) prod=p.get(cb.getText());
                    else {
                        prod=(PolinomioSet) prod.mul(p.get(cb.getText()));
                        trovato=true;
                    }
                }
            }
            ++i;
        }
        if(prod==null) 
            JOptionPane.showMessageDialog(null, "Nessun elemento selezionato");
        else {
            if(!trovato) prod=(PolinomioSet) prod.mul(prod);

            FrameAggiungi p=new FrameAggiungi("Il prodotto risulta essere", prod);
            p.setVisible(true);
        }
    }

    private void calcSomma(){
        PolinomioSet somma=null;
        int i=0;
        while(i<pannelloPol.getComponentCount()){
            JCheckBox cb=(JCheckBox) pannelloPol.getComponent(i);
            if(cb.isSelected()) {
                if(somma==null) 
                    somma=p.get(cb.getText());
                else 
                    somma=(PolinomioSet) somma.add(p.get(cb.getText()));
            }
            ++i;
        }
        if(somma==null) 
            JOptionPane.showMessageDialog(null, "Nessun elemento selezionato");
        else {       
            FrameAggiungi p=new FrameAggiungi("la somma risulta essere", somma);
            p.setVisible(true);
        } 
    }

    private void valore(){
        PolinomioSet pol=null;
        boolean trovato=false;
        int i=0;
        while(i<pannelloPol.getComponentCount()){
            JCheckBox cb=(JCheckBox) pannelloPol.getComponent(i);
            if(cb.isSelected()) {
                if(!trovato) {
                    pol=p.get(cb.getText());
                    trovato=true;
                }
                else {
                    JOptionPane.showMessageDialog(null, "Troppi elementi selezionati");
                    return;
                }
            }
            ++i;
        }
        if(!trovato) 
            JOptionPane.showMessageDialog(null, "Nessun elemento selezionato");
        else {
            double x=Double.parseDouble(JOptionPane.showInputDialog("Inserisci il valore da sostituire nel polinomio"));
            double val=pol.valore(x);
            JOptionPane.showMessageDialog(null, "Il valore del polinomio calcolato in "+x+ " risulta essere: "+val);
        }
    }

    private void rimuovi(){
        boolean trovato=false;
        for(Component c: pannelloPol.getComponents()){
            JCheckBox cb=(JCheckBox) c;
            if(cb.isSelected()) {
                p.remove(cb.getText());
                pannelloPol.remove(c);
            }
        }
        if(!trovato) JOptionPane.showMessageDialog(null, "Nessun polinomio selezionato");
        else if(p.size()==0) {
            menuIniziale(); stato=STATE.INIZIALE;
        }
    }

    private void ripristina(String path) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(path));
        HashMap<String, PolinomioSet> hm=new HashMap<>();
        boolean stoLeggendo=true;
        try {
            for(;;) {
                String polinomio=br.readLine();
                if(polinomio==null) break;
                PolinomioSet pSet=new PolinomioSet();
                Polinomio.parse(polinomio, pSet);
                hm.put(polinomio, pSet);
            }

        } catch (Exception e){
            stoLeggendo=false;
        } finally { br.close(); }
        
        if(stoLeggendo==true){
            p.clear();
            for(PolinomioSet ps: hm.values()) {
                String strPol=ps.toString();
                p.put(strPol, ps);
                JCheckBox cb=new JCheckBox(strPol); 
                cb.setFont(FONTNUM); cb.setBorder(new EmptyBorder(4, 4, 4, 4));
                cb.setOpaque(false); cb.setForeground(ColorUIResource.WHITE);
                cb.setIcon(icon); cb.setSelectedIcon(icon2);
                pannelloPol.add(cb);
            }
        }
    }
}


public class PolinomioGUI {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                PoliFrame f=new PoliFrame();
                f.setVisible(true);
            }
        });
    }
}