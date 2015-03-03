import java.awt.*;
import java.util.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.applet.*;
import java.text.*;
public class dielectriques_ferromagnetisme extends java.applet.Applet implements Runnable{
    static final double pi=3.141592652;
    static final double eps0=1/(36*pi*10*Math.pow(10000.0,2));
    static final double mu0=4*pi/(10*Math.pow(1000.0,2));
    static int xpos=5,xneg=15,dxposneg=3,dyposneg=1,dipole=1, quadripole=2, dernier=3, choix_sur_disque=4, creer_ensemble=5, ecrire_sur_disque=6, creer=2, eliminer=3, montrer_champs=4;
    static final int top_stop = 60,left_stop = 20,bottom_stop = 100,right_stop = 100,top_stop_cree = 420,left_stop_cree = 20,bottom_stop_cree = 460,right_stop_cree = 200,top_cree = 320,left_cree = 20,bottom_cree = 360,right_cree = 200,top_demarre = 300,left_demarre = 50,bottom_demarre = 480,right_demarre = 850;
    point zer;
    public ensemble_de_dipoles ens_de_dipoles[]=new ensemble_de_dipoles[2];
    int ppmouseh;int ppmousev;boolean relachee,pressee,cliquee,draguee;
    boolean vas_y=false;
    Image image,image2;boolean ensemble_identiques=false;
    long temps_en_sec=0;int i_run;boolean creation_cylindres;
    long temps_initial_en_secondes,temps_minimum=3600,temps_initial_en_secondes_prec=0,temps_maximum=3600;
    String titre[]=new String [12];
    commentaire comm=null;Graphics gr;
    boolean dessiner_menu_principal_ou_fin=false;
    boolean comm_on=false,demo_faite=false,demo_a_faire=true;
    private SimpleDateFormat formatter;String d_ou_je_reviens;
    private MouseMotion motion;Color orange_pale;
    boolean terminer_demo=false;
    int n_ensembles;
    private MouseStatic mm;Thread Th1;
    boolean toutdebut,run_applet,peindre;
    Font times14=new Font("Times",Font.PLAIN,14);
    Font times_gras_14=new Font("Times",Font.BOLD,14);
    Font times_gras_24=new Font("Times",Font.BOLD,24);
    public  void init (){ 
	toutdebut=true;
	run_applet=true;peindre=true;
	System.out.println("init applet");
	mm=new MouseStatic(this);
	this.addMouseListener(mm);
	motion=new MouseMotion(this); setBackground(Color.white);
	this.addMouseMotionListener(motion);
	formatter = new SimpleDateFormat ("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
	Date maintenant=new Date();orange_pale=new Color(140,90,0);
	temps_initial_en_secondes=temps_en_secondes(maintenant);
	temps_initial_en_secondes_prec=temps_initial_en_secondes;
	System.out.println("maintenant "+maintenant+" s "+temps_initial_en_secondes);
	titre[0]="Dipoles electrique et magnetique, vus de pres";
	titre[1]="Quadripoles electrique et magnetique, vus de pres.";
	titre[2]="Dipoles et quadriploles vus de loin.";
	//titre[3]="Barreau cylindrique.";
	//titre[4]="Boule.";
	titre[3]="Barreau cylindrique.";
	titre[4]="Boule.";
	titre[5]="Ellipsoïde de révolution (ballon de rugby) .";
	titre[6]="Barreau infini.";	
	creation_cylindres=true;d_ou_je_reviens="";
	zer=new point(0.,0.);

	gr= getGraphics();
	String name="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/nfils_premiere_page.jpg";
	image=createImage(400,400);
	Graphics gTTampon=image.getGraphics();
	image=Toolkit.getDefaultToolkit().getImage(name);
	MediaTracker tracker=new MediaTracker(this);
	tracker.addImage(image,1); 
	String name2="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/dev/nfils_methode.jpg";
	image2=createImage(300,400);
	Graphics gTTampon2=image2.getGraphics();
	image2=Toolkit.getDefaultToolkit().getImage(name2);
	tracker.addImage(image2,0); 
	try {tracker.waitForAll(); }
	catch (InterruptedException e) {
	    System.out.println(" image2 pas arrivee?");
	}
    }
    public void start()
    {
	System.out.println(" start");
	Th1= new Thread(this);Th1.start();
    }
    public long temps_en_secondes(Date nun){
	formatter.applyPattern("s");
	int s = Integer.parseInt(formatter.format(nun));
	formatter.applyPattern("m");
	int m = Integer.parseInt(formatter.format(nun));
	formatter.applyPattern("h");
	int h = Integer.parseInt(formatter.format(nun));
	//System.out.println(" h "+h+" m "+m+" s "+s);
	return (h*3600+m*60+s);
    }    
    public void run(){
	
	int isleep=2;
	//menu_principal_et_fin();
	fin_de_programme:
	while (run_applet){
	    Date now=new Date();
	    temps_en_sec=temps_en_secondes(now);
	    //System.out.println("temps_en_sec "+temps_en_sec);
	    
	    if(temps_en_sec-temps_initial_en_secondes>temps_maximum){
		run_applet=true;break fin_de_programme; 
	    }
	    //if((dessiner_menu_principal_ou_fin&&(!creation_cylindress))||creation_cylindres)
	    if(toutdebut){
		dessiner_menu_principal_ou_fin=false;
		terminer_demo=false;
		if(demo_a_faire){
		    gr.drawImage(image2,20,450,this);
		    gr.drawImage(image,630,60,this);
		    n_ensembles=0;
		    for(int iii=1;iii>=0;iii--){
			creation_d_un_ensemble(iii,0,true);
			n_ensembles++;
		    }
		}
		vas_y=false;
		for(int ii=0;ii<n_ensembles;ii++)
		    vas_y|=ens_de_dipoles[ii].du_nouveau;
		if(vas_y)
		    for(int ii=0;ii<n_ensembles;ii++)
			if(!ens_de_dipoles[ii].en_train_de_peindre){
			    //ens_de_dipoles[ii].paint_et_dessine_lignes_de_champ();
			    if(cliquee)
				break;
			}
		if(cliquee){
		    cliquee=false;
		    terminer_demo=true;
		}
		demo_a_faire=false;
		if(terminer_demo){
		    cliquee=false;pressee=false;
		    eraserect( gr,0,0,1000,1200,Color.white);
		    d_ou_je_reviens="je reviens de num_fen";	
		    eliminer(0);
		    eliminer(1);
		    n_ensembles=0;
		    Date maintenant=new Date();
		    temps_initial_en_secondes_prec=temps_en_secondes(maintenant);
		    toutdebut=false;
		    System.out.println(" on a detruit les fenetres de demo ");
		    ppmouseh=-100;ppmousev=-100;
		    menu_principal_et_fin();
		    dessiner_menu_principal_ou_fin=false;					    
		}
	    }else{
		if(dessiner_menu_principal_ou_fin)
		    menu_principal_et_fin();
		
		if(d_ou_je_reviens!=""){
		    System.out.println("d_ou_je_reviens "+d_ou_je_reviens+" n_ensembles "+n_ensembles+" dessiner_menu_principal_ou_fin "+dessiner_menu_principal_ou_fin );
		    n_ensembles=0;
		    setVisible(true);
		    if(dessiner_menu_principal_ou_fin)
			menu_principal_et_fin();
		}
		if(creation_cylindres&&!toutdebut)
		    demarrer_application();
		if(d_ou_je_reviens!=""){
		    System.out.println("***d_ou_je_reviens "+d_ou_je_reviens+" n_ensembles "+n_ensembles+" creation_cylindres "+creation_cylindres+" toutdebut "+toutdebut );
		    d_ou_je_reviens="";
		}
		if(n_ensembles!=0){
		    vas_y=false;
		    for(int ii=0;ii<n_ensembles;ii++)
			vas_y|=ens_de_dipoles[ii].du_nouveau;
		    if(vas_y){
			dessiner_menu_principal_ou_fin=false;
			for(int ii=0;ii<n_ensembles;ii++){
			    if(!ens_de_dipoles[ii].en_train_de_peindre){
				//ens_de_dipoles[ii].paint_et_dessine_lignes_de_champ();
				if(ens_de_dipoles[ii].le_virer){
				    toutdebut=ens_de_dipoles[ii].command=="Revenir a la page initiale avec infos";
				    if(toutdebut){
					demo_a_faire=true;
					terminer_demo=false;
					dessiner_menu_principal_ou_fin=false;
					creation_cylindres=false;
					eraserect(gr,0,0,1000,1200,Color.white);
				    }else
					dessiner_menu_principal_ou_fin=true;
				    creation_cylindres=true;
				    //dessiner_menu_principal_ou_fin=true;
				    relachee=false;
				    pressee=false;
				    draguee=false;
				    cliquee=false;
				    d_ou_je_reviens=ens_de_dipoles[ii].command;
				    n_ensembles=0;				
				    eliminer(1-ii);
				    eliminer(ii);
				    break;				
				}
			    }
			    
			}
		    }
		}
	    }
	    i_run++;if(i_run==20)i_run=0;
	    //System.out.println("isleep");
	    try {Thread.sleep(isleep);}
	    catch (InterruptedException signal){System.out.println("catch ");}
	}
	for(int ii=0;ii<n_ensembles;ii++){
	    ens_de_dipoles[ii].dispose();
	}
	stop();this.destroy();
    }
    void demarrer_application(){ 
	//System.out.println("dem relachee "+relachee+" pressee "+pressee);
	if(cliquee){
	//if(relachee&&pressee){
	    cliquee=false;
	    System.out.println("11");
	    int xi=ppmouseh;int yi=ppmousev;
	    System.out.println(" top_demarre-50 "+(top_demarre-50)+" xi "+yi+" yi "+yi);
	    if ((xi > left_demarre)&&(xi < right_demarre)){
		for(int i=0;i<9;i++){
		    if ((yi > top_demarre-50+i*30)&&(yi < top_demarre-50+(i+1)*30)){
			n_ensembles=2;
			for(int iii=1;iii>=0;iii--)
			    creation_d_un_ensemble(iii,i,false);
			relachee=false;pressee=false;
			creation_cylindres=false;
		    }
		}
	    }
	}		
    }
    public void creation_d_un_ensemble(int iii,int i_demarre,boolean demo){
	System.out.println(" creation_d_un_ensemble iii "+iii+" i_demarre "+i_demarre);
	ens_de_dipoles[iii]=new ensemble_de_dipoles("Ensemble"+iii,this,i_demarre,iii,demo);
    }
    void eliminer(int num_ens){
	if(ens_de_dipoles[num_ens].command=="Sortir du programme")
	    run_applet=false;
	n_ensembles=0;
	cliquee=false;
	relachee=false;
	pressee=false;
	
	//ens_de_dipoles[[num_ens].dispose();
	//ens_de_dipoles[[num_ens]=null;
	if(ens_de_dipoles[num_ens].mltpl_brr!=null){
	    ens_de_dipoles[num_ens].mltpl_brr.elimine();
	}
	    //ens_de_dipoles[num_ens].elimine();
	
	ens_de_dipoles[num_ens].dispose();
	ens_de_dipoles[num_ens]=null;

	System.out.println("apres dispose() ");
    }
    public  void menu_principal_et_fin(){
	int ix,iy;
      if(run_applet){
	  gr.setColor(Color.red);gr.setFont(times14);
	  
	  System.out.println(" debut paint creation_cylindres "+creation_cylindres);
	  if(creation_cylindres){
	      gr.setFont(times_gras_24);
	      gr.setColor(Color.blue);
	      gr.drawString("Choisissez les dipoles ou la geometrie d'un systeme physique.",left_demarre, top_demarre-140);	 
	      gr.drawString("Vous aurez a gauche les dielectriques, a droite le ferromagnetisme.",left_demarre, top_demarre-120);	 
	      paintrect_couleur(gr,top_demarre-50,left_demarre,top_demarre+220,right_demarre,Color.red);
		  for (int kk=0;kk<7;kk++){
		      gr.drawString(titre[kk],left_demarre, top_demarre-30+kk*30);	 
		      drawline_couleur(gr,left_demarre, top_demarre-20+kk*30, right_demarre, top_demarre-20+kk*30, Color.red);

		  }
	  }
      }else{
	  eraserect(gr,0,0,1000,1200,Color.white);
	  gr.setFont(times_gras_24);gr.setColor(Color.black);
	  if(temps_en_sec-temps_initial_en_secondes>temps_maximum)
	      gr.drawString("TEMPS MAXIMUM EXPIRE",100,100);
	  else
	      gr.drawString("FIN DU PROGRAMME",100,100);
      }
    }
    void on_s_en_va(){
	if(comm!=null){
	    comm.dispose();
	    comm=null;
	}
	for(int iii=1;iii>=0;iii--){
	    if(ens_de_dipoles[iii]!=null){
		ens_de_dipoles[iii].dispose();
		ens_de_dipoles[iii]=null;
	    }
	}
	n_ensembles=0;
    }
    void drawline_couleur(Graphics g,int xin, int yin, int xfin, int yfin, Color couleur)
    {
	g.setColor(couleur);g.drawLine(xin,yin,xfin,yfin);
    }
    void paintrect_couleur(Graphics g,int top, int left, int bot, int right, Color couleur)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(couleur);g.drawRect(x,y,width,height);
    }    

    void drawline_pt_entier(Graphics g,point pt1, point pt2){
	g.drawLine((int)pt1.x,(int)pt1.y,(int)pt2.x,(int)pt2.y);
    }    
    void eraserect(Graphics g, int top, int left, int bot, int right,Color couleur)
    {
	int x,y,width,height;
	x=left;y=top;width=right-left;height=bot-top;
	g.setColor(couleur);g.fillRect(x,y,width,height);
    }
    void paintcircle_couleur (Graphics g,long x,long y, long r,Color couleur){
	g.setColor(couleur);g.fillOval((int)x,(int)y,(int)r,(int)r);
    }
    public void appelle_comm(String command,int i_demarre){
	if(!comm_on){
	    comm=new commentaire(command,i_demarre);
	    comm.ecrit_aide();comm_on=true;
	}
	else
	    comm.ecrit_aide();
    }
    public void	traite_click(){
	//System.out.println("entree traite_click ");
	Date maintenant=new Date();
	temps_initial_en_secondes=temps_en_secondes(maintenant);
	if(cliquee){
	    if(temps_initial_en_secondes<temps_initial_en_secondes_prec+2){
		cliquee=false;pressee=false;relachee=false;
	    }else
		temps_initial_en_secondes_prec=temps_initial_en_secondes;
	}
	if(cliquee&&!toutdebut&&n_ensembles!=0){
	    cliquee=false;pressee=false;relachee=false;
	    for(int ik=0;ik<n_ensembles;ik++){    
		ens_de_dipoles[ik].le_virer=true;
		ens_de_dipoles[ik].command="Revenir a la fenetre principale";
	    }
	}
    }
    class commentaire extends Frame {
	final int top=400;final int left=200;final int bottom = 660;final int right = 750;Graphics grp_c;
	String commentar[]=new String[11];int i_demarre;int nb_lignes=10; 
	public commentaire(String s,int i_demarre1){
	    super(s);i_demarre=i_demarre1;
	    System.out.println("cree graphe "+s+" i_demarre "+i_demarre );	    
	    setSize(right-left,bottom-top);
	    setLocation(left,top);
	    //setLocation(left,top);
	    setVisible(true);
	    grp_c= getGraphics(); comm_on=true;
	    grp_c.setColor(Color.black);
	    for (int i=0;i<10;i++)commentar[i]="";
		commentar[0]="Les charges a la surface des conducteurs sont proportionelles aux champs juste a l'exterieur ";
		commentar[1]="de ces conducteurs, qu'on a figure ici; elles sont positives pour un champ sortant du conducteur, ";
		commentar[2]="negatives pour un champ rentrant. En rapprochant les conducteurs, vous voyez que les";
		commentar[3]="charges se concentrent dans les parties le plus proches.";
		commentar[4]=" Le cas de deux fils de charges opposees est appele dipolaire, et de quatre fils de charge";
		commentar[5]=" totale nulle: quadripolaire.";
		commentar[6]="Les différences de potentiel sont celles relatives entre deux conducteurs. Les charges ";
		commentar[7]="indiquees dans les fenetres 'donnees' sont les charges en 'regard' des conducteurs.";
		commentar[8]="Les matrices devraient être parfaitement antisymetriques (elements symetriques opposes)";
		nb_lignes=9;
	}
	public void ecrit_aide(){
	    for (int i=0;i<nb_lignes;i++)
		grp_c.drawString(commentar[i],20,50+i*20);
	}
	public void elimine(){
	    setVisible(false);
	    System.out.println("elimination de comm");
	    comm_on=false;  dispose();
	}
    }
    class MouseMotion extends MouseMotionAdapter
    {
	dielectriques_ferromagnetisme subject;
	public MouseMotion (dielectriques_ferromagnetisme a)
	{
	    subject=a;
	}
	public void mouseMoved(MouseEvent e)
	{ppmouseh=e.getX();ppmousev=e.getY();draguee=false;}
	public void mouseDragged(MouseEvent e)
	{ppmouseh=e.getX();ppmousev=e.getY();draguee=true;
	//System.out.println("draguee dans Mousemove "+draguee);
	traite_click();
	}
    }
    class MouseStatic extends MouseAdapter
    {
	dielectriques_ferromagnetisme subject;
	public MouseStatic (dielectriques_ferromagnetisme a)
	{
	    subject=a;
	}
	public void mouseClicked(MouseEvent e)
	{
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;
	    System.out.println("cliquee "+cliquee);
	    traite_click();
	    //	System.out.println("ens_de_dipoles[icylindre].nb_el_ens "+ens_de_dipoles[icylindre].nb_el_ens);
	    //System.out.println("icylindre "+icylindre);
	    //for( int iq=0;iq<ens_de_dipoles[icylindre].nb_el_ens;iq++)
	}
	public void mousePressed(MouseEvent e)
	{
	    ppmouseh=e.getX();ppmousev=e.getY();pressee=true;
	    System.out.println("pressee "+pressee);
	    traite_click();
	}
	public void mouseReleased(MouseEvent e)
	{
	    ppmouseh=e.getX();ppmousev=e.getY();cliquee=true;relachee=true;
	    System.out.println("relachee "+relachee);
	}
    }
}
class ensemble_de_dipoles extends Frame implements ActionListener{
    static int top_ens = 20,left_ens = 0,bottom_ens = 630,right_ens = 600;
    static final double pi=3.141592652;    vecteur vct;
    multipole_ou_barreau mltpl_brr=null;
    double coco=0,cece=0,caca=0,cucu=0,cici=0;
     int numeros_kk[][][]=new int[5][5][63];
    String nom_conduc[]=new String[5];	MenuItem item_fil[]= new MenuItem[5];
    String issu_de_fil[]=new String[5];
    boolean zeichnen_punkt=false,imprime=false,deja_venu=false;
    double cos_angle[]=new double[64];double sin_angle[]=new double[64];
    double sigma_cos[]=new double[8];
    double sigma_sin[]=new double[8];
    double sigma[]=new double[64];
    point point_surface[]=new point[256];
    String str_increm; 
    double kk_plus_precis=0.;boolean booboo=false;
    boolean diff_signes=false;
    biparam parametres_droite_initiale,parametres_droite_finale;double distance_avant_fin=0.;
    multipole_ou_barreau p_ou_b;
    multipole_ou_barreau pole_ou_barreau[]=new multipole_ou_barreau[2];
    multipole multipol;
    boolean hors_cadre=false,a_reculon=false;double prd_vect=0.,prd_vect1=0.;
    boolean demo,ligne_coupee=false,ligne_coupee_d_emblee=false;
    point pt_essai,pt_d_intersec,dist_pt_init,dist_pt_fin,cchhpp,pt_ss;
    double angle_champs=0.,angle_positions=0.,diff_angles=0.,diff_angles0=0.,diff_angles_prec=0.,increment_angles=0.1;
    double somme_inc=0.;
    vecteur chp_dpt,chp_dpt_prec;
    int iq_arrivee=0,iq_arrivee_prec=0, numero_zone_arrivee=0;
    boolean calculs_faits=false,lignes_de_champ_faites=false,calculating=false;
    int iter,n_poles_ou_barreaux_tot,num_secteur=0;
    biboolean bibo;
    double distance_quadrillage=12.;
    double champ_de_ref=0.;
    boolean du_nouveau,en_train_de_peindre;
    boolean ret,trouve_deplacement;boolean attend_souris=false;
    double x_essai=0.,y_essai=0.,dtet,dttt;
    double dx_pt_souris, dy_pt_souris;
    vecteur chp_pts_a_montrer[]=new vecteur[10000];
    int fil_initial_de_la_ligne[]=new int[10000];
    int fil_initial_ligne_a_montrer=0;boolean montrer_un_champ_sur_2=false;
    boolean draw_point=false;
    boolean champ_du_point_a_dessiner[]=new boolean[10000];
    vecteur vovo,wowo,wawa;
    point difference_pt,difference_chp;
    int nombre_de_pts_a_montrer=0,nombre_de_pts_a_montrer_init=0;
    point toto,titi,tata,tutu,tete,zer;point dist_reelle;double diff_potentiel=0.;
    boolean totologic=false;
    point_y_z zer_y_z;
    int tata_int=0,tete_int=0,titi_int=0,toto_int=0,tutu_int=0;
    int ppmouseh,ppmousev,ppmouseh_prec,ppmousev_prec;
    double echelle=0.1;boolean relachee,pressee,cliquee,draguee;
    String command,comment,comment_init;
    point champ[]=new point [64];
    boolean le_virer=false;
    boolean dejaext[]=new boolean[10];	boolean fin_gere_menus_souris;
    int  numeroens[]=new int [10];int i_demarre,n_stoppages;
    int fact_zoom_suppl;double fct_zm_sspl=1.;
    point elec_centre_q_totales,elec_centre_de_plus;
    Graphics grp_c;
    dielectriques_ferromagnetisme subject;int iq_dep=-1;
    private MouseStatic mm;    private MouseMotion motion;
    boolean conseil,cree_wires,zoom;int i_ens;
    MenuBar mb1[]=new MenuBar[2];
    public ensemble_de_dipoles(String s,dielectriques_ferromagnetisme a,int i_demarre1,int i_ens1,boolean demo1){	
	super(s);
	subject=a;i_ens=i_ens1;i_demarre=i_demarre1;du_nouveau=true;
	cree_wires=false;n_stoppages=0;
	en_train_de_peindre=false;calculating=false;
	demo=demo1;
	zer=new point(0.,0.);
	zer_y_z=new point_y_z(0.,0.);
	if(demo){
	    bottom_ens = 630/2;right_ens = 600/2;
	}else{
	    bottom_ens = 630;right_ens = 600;
	}
	elec_centre_q_totales=new point(zer);
	elec_centre_de_plus=new point(zer);
	toto=new point(zer);
	titi=new point(zer);
	tata=new point(zer);
	tutu=new point(zer);
	tete=new point(zer);
	dist_reelle=new point(zer);
	vovo=new vecteur(zer,zer);
	wowo=new vecteur(zer,zer);
	wawa=new vecteur(zer,zer);
	vct=new vecteur(zer,zer);
	bibo =new biboolean(false,false);
	difference_pt=new point(zer);difference_chp=new point(zer);
	pt_essai=new point(zer);
	pt_ss=new point(zer);
	chp_dpt_prec=new vecteur(zer,zer);
	chp_dpt=new vecteur(zer,zer);
	parametres_droite_initiale =new biparam(0.,0.);
	parametres_droite_finale=new biparam(0.,0.);
	pt_d_intersec=new point(zer);
	dist_pt_init=new point(zer);
	dist_pt_fin=new point(zer);
        dttt=dtet/10.0;
	nom_conduc[0]="A";nom_conduc[1]="B";nom_conduc[2]="C";nom_conduc[3]="D";nom_conduc[4]="E";
	issu_de_fil[0]="montrer les champs issus du fil A";
	issu_de_fil[1]="montrer les champs issus du fil B";
	issu_de_fil[2]="montrer les champs issus du fil C";
	issu_de_fil[3]="montrer les champs issus du fil D";
	issu_de_fil[4]="montrer les champs issus du fil E";	
	cchhpp=new point(zer);
        addWindowListener(new java.awt.event.WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
		    le_virer=true;du_nouveau=true;
		    command="Revenir a la page principale";
		};
	    });

        fact_zoom_suppl=1;
	trouve_deplacement=false;comment="";
 	mm=new MouseStatic(this);
	this.addMouseListener(mm);
	motion=new MouseMotion(this); setBackground(Color.white);
	this.addMouseMotionListener(motion);
	setLayout(new FlowLayout());
	mb1[i_ens]=null;barre_des_menus();
	pack();
	setVisible(true);
	grp_c=getGraphics();
	grp_c.setColor(Color.cyan);
	setSize(right_ens-left_ens,bottom_ens-top_ens);
	if(demo){
	    setLocation(left_ens+i_ens*600/2,top_ens);
	}else{
	    setLocation(left_ens+i_ens*600,top_ens);
	}
	    comment_init="";
	    cree_dipoles_ou_barreau();
    }
    public void barre_des_menus(){
	System.out.println("i_demarre  "+i_demarre);
	mb1[i_ens]=new MenuBar();
	if(!demo){
	    Menu sortir= new Menu("Sortir");
	    MenuItem iteb1=new MenuItem("Revenir a la page principale");
	    sortir.add(iteb1);
	    iteb1.addActionListener(this);
	    MenuItem iteb11=new MenuItem("Revenir a la page initiale avec infos");
	    sortir.add(iteb11);
	    iteb11.addActionListener(this);
	    MenuItem iteb12=new MenuItem("Sortir du programme");
	    sortir.add(iteb12);iteb12.addActionListener(this);
	    mb1[i_ens].add(sortir);
	}
	Menu dilater= new Menu("Montrer les champs");
	MenuItem item1=new MenuItem("montrer les champs aux points cliques");
	dilater.add(item1);
	item1.addActionListener(this);
	for(int i=0;i<n_poles_ou_barreaux_tot;i++){
	    item_fil[i]=new MenuItem(issu_de_fil[i]);
	    dilater.add(item_fil[i]);
	    item_fil[i].addActionListener(this);
	}
	MenuItem item1_sur_2=new MenuItem("ne montrer qu'un champ sur 2");
	MenuItem item11=new MenuItem("multiplier l'echelle d'un facteur 2");
	MenuItem item2=new MenuItem("diviser l'echelle d'un facteur 2");
	MenuItem item3=new MenuItem("utiliser l'echelle de l'autre fenetre");
	dilater.add(item1_sur_2);
	item1_sur_2.addActionListener(this);
	dilater.add(item11);
	item11.addActionListener(this);
	dilater.add(item2);
	item2.addActionListener(this);
	dilater.add(item3);
	item3.addActionListener(this);
	mb1[i_ens].add(dilater);
	if(demo){
	    item1.setEnabled(false);
	    item11.setEnabled(false);
	    item2.setEnabled(false);
	    item3.setEnabled(false);
	}  
	Menu operations_sur_elements= new Menu("modifier l'ensemble");
	if(demo)
	    operations_sur_elements= new Menu("modifier");
	    MenuItem itepq3=new MenuItem("deplacer un cylindre par son centre");
	    MenuItem iterr=new MenuItem("augmenter de 10 nCb/m la charge d'un cylindre");
	    MenuItem itepp=new MenuItem("diminuer de 10 nCb/m la charge d'un cylindre");
	    MenuItem iterr1=new MenuItem("augmenter de 20% le rayon d'un cylindre");
	    MenuItem itepp1=new MenuItem("diminuer de 20% le rayon d'un cylindre");
	    operations_sur_elements.add(itepq3);
	    itepq3.addActionListener(this);
	    operations_sur_elements.add(iterr);
	    iterr.addActionListener(this);
	    operations_sur_elements.add(itepp);
	    itepp.addActionListener(this);
	    operations_sur_elements.add(iterr1);
	    iterr1.addActionListener(this);
	    operations_sur_elements.add(itepp1);
	    itepp1.addActionListener(this);
	    mb1[i_ens].add(operations_sur_elements);
	if(demo){
	    itepq3.setEnabled(false);
	    iterr.setEnabled(false);
	    itepp.setEnabled(false);
	    iterr1.setEnabled(false);
	    itepp1.setEnabled(false);
	}  

	if(i_demarre>=0){
	    Menu aide= new Menu("aide et commentaires");
	    if(demo)
		aide= new Menu("aide");
	    MenuItem itab1=new MenuItem("aide et commentaires");
	    aide.add(itab1);
	    itab1.addActionListener(this);
	    mb1[i_ens].add(aide);
	}
	setMenuBar(mb1[i_ens]);
    }
    public void actionPerformed(ActionEvent e)
    {
	command=e.getActionCommand();
	System.out.println("i_ens "+i_ens+" n_stoppages "+n_stoppages+" command "+command);
	//if(command!="")n_stoppages=0;
	if(command!=""){
	    Date maintenant=new Date();
	    subject.temps_initial_en_secondes=subject.temps_en_secondes(maintenant);
	    if (command=="Revenir a la page principale"||command=="Sortir du programme"||command=="Revenir a la page initiale avec infos"){
		le_virer=true;
		if(command=="Sortir du programme")
		    subject.run_applet=false;	    
	    }
	}
	if(command!="")
	    traite_commande ();
    }
    public void traite_commande (){
	boolean dg=false;
	if(command!=""){
	    fin_gere_menus_souris=false;
	    du_nouveau=true;
	    //System.out.println(" command "+command);
	}
	if(command=="montrer les champs aux points cliques"){
	    comment="Cliquez la ou vous voulez voir le champ";attend_souris=true;
	}
	if(command=="deplacer un cylindre par son centre"){
	    comment="Faites glisser le point rouge du centre du fil choisi";
	    attend_souris=true;
	}
	if(command=="ne montrer qu'un champ sur 2"){
	    montrer_un_champ_sur_2=!montrer_un_champ_sur_2;
	    System.out.println(" montrer_un_champ_sur_2 "+montrer_un_champ_sur_2);   
	}
	if((command=="augmenter de 10 nCb/m la charge d'un cylindre")||(command=="diminuer de 10 nCb/m la charge d'un cylindre")||(command=="diminuer de 20% le rayon d'un cylindre")){
	    comment="Cliquez sur le centre du cylindre que vous choisissez.";attend_souris=true;
	}
	if(attend_souris){
	    grp_c.setColor(Color.black);
	    grp_c.drawString(comment,100,200);
	}
	if (command=="aide et commentaires")
	    if(!subject.comm_on)
	    subject.appelle_comm(command,i_demarre);
	    else{
		subject.comm.elimine();subject.comm=null;
	    }
	if (command=="utiliser l'echelle de l'autre fenetre"){
	    if(subject.n_ensembles==2){
		System.out.println(" i_ens "+i_ens+" echelle "+echelle+" fact_zoom_suppl "+fact_zoom_suppl+" fct_zm_sspl "+fct_zm_sspl);
		echelle=subject.ens_de_dipoles[1-i_ens].echelle;
		fact_zoom_suppl=subject.ens_de_dipoles[1-i_ens].fact_zoom_suppl;
		fct_zm_sspl=subject.ens_de_dipoles[1-i_ens].fct_zm_sspl;
		System.out.println("echelle "+echelle+" fact_zoom_suppl "+fact_zoom_suppl+" fct_zm_sspl "+fct_zm_sspl);
	    }
	}
	System.out.println("montrer_un_champ_sur_2 dans traite_commande() "+montrer_un_champ_sur_2);
	for(int i=0;i<n_poles_ou_barreaux_tot;i++){
	    if(command==issu_de_fil[i]){
		fil_initial_ligne_a_montrer=i;
		System.out.println(" fil_initial_ligne_a_montrer "+fil_initial_ligne_a_montrer);
		break;
	    }
	}
	for (int j=0;j<nombre_de_pts_a_montrer;j++){
	    champ_du_point_a_dessiner[j]=(fil_initial_de_la_ligne[j]==fil_initial_ligne_a_montrer)&&!(montrer_un_champ_sur_2&&j/2*2==j);
	    if(champ_du_point_a_dessiner[j])
		toto_int++;
	}
	System.out.println(" toto_int     "+ toto_int);
	if (command=="multiplier l'echelle d'un facteur 2"){
	    fact_zoom_suppl++;dg= true;
	}
	if (command=="diviser l'echelle d'un facteur 2"){
	    fact_zoom_suppl--;dg= true;
	}
	if(dg){
	    command="";attend_souris=false;
	    fct_zm_sspl=1.0;
	    if (fact_zoom_suppl > 1)
		for(int i=2;i<=fact_zoom_suppl;i++)
		    fct_zm_sspl*= 2;
	    if (fact_zoom_suppl < 1)
		for(int i=fact_zoom_suppl;i<1;i++)
		    fct_zm_sspl/=2.0;
	    System.out.println("fct_zm_sspl "+ fct_zm_sspl);
	}
    }
    public boolean gere_menus_souris (){
	if(command!="")fin_gere_menus_souris=false;
	if(command=="deplacer un cylindre par son centre"){
	    ret=false;
	    if(draguee||trouve_deplacement)
		ret=glisser_vibr();
	    if(relachee&&trouve_deplacement)
		ret=glisser_vibr();
	    if(pressee)
	    	ret=glisser_vibr();
	}
	boolean clic_ok=false;int iq_choisi=-1;
	if(attend_souris){
	    if(cliquee){
		if(!((ppmouseh==ppmouseh_prec)&&(ppmousev==ppmousev_prec))){
		    ppmouseh_prec=ppmouseh;ppmousev_prec=ppmousev;
		    clic_ok=true;
		    for(int iq=0;iq<n_poles_ou_barreaux_tot;iq++){
			if((Math.abs(ppmouseh-pole_ou_barreau[iq].centre.x)<7)&&(Math.abs(ppmousev-pole_ou_barreau[iq].centre.y)<7))
			    iq_choisi=iq;
		    }
		}	    
	    }
	}
	if(clic_ok){
	    if(command=="montrer les champs aux points cliques"){
		point ptt=new point((double)ppmouseh,(double)ppmousev);
		//	point vec=champ(ptt);
		point vec=new point(zer);
		ptt.print("point clique i_ens "+i_ens+" ptt ");
		vec.print("vec ");
		//vec.y=-vec.y;
		chp_pts_a_montrer[nombre_de_pts_a_montrer]=new vecteur(vec,ptt);
		nombre_de_pts_a_montrer++;
		cliquee=false;
	    }else{
		boolean impossible=false;
		if(iq_choisi!=-1){
		    if(command=="augmenter de 10 nCb/m la charge d'un cylindre"){
			pole_ou_barreau[iq_choisi].q_ou_i+=10.;	
		    }
		    if(command=="diminuer de 10 nCb/m la charge d'un cylindre"){
			    pole_ou_barreau[iq_choisi].q_ou_i-=10.;	
		    }
		    if((command=="augmenter de 20% le rayon d'un cylindre")||(command=="diminuer de 20% le rayon d'un cylindre")){
			double aaa=0.;
			if(command=="augmenter de 20% le rayon d'un cylindre")
			    aaa=pole_ou_barreau[iq_choisi].rc*1.2;
			else
			    aaa=pole_ou_barreau[iq_choisi].rc*0.8;
			for(int iq=0;iq<n_poles_ou_barreaux_tot;iq++){
			    double dist=Math.sqrt(Math.pow(pole_ou_barreau[iq].centre.x-pole_ou_barreau[iq_choisi].centre.x,2)+Math.pow(pole_ou_barreau[iq].centre.x-pole_ou_barreau[iq_choisi].centre.x,2));
			    //boolean exterieur=(dist>pole_ou_barreau[iq].rc)&&(dist>pole_ou_barreau[iq_choisi].rc));
			    if(!((dist>pole_ou_barreau[iq].rc+aaa)||(dist<Math.abs(pole_ou_barreau[iq].rc-aaa)))){
				comment="Impossible, deux cercles se coupent";
				impossible=true;
			    }
			    System.out.println("impossible "+impossible+" dist "+dist+" pole_ou_barreau[iq].rc "+pole_ou_barreau[iq].rc+" aaa "+aaa);

			}
			if(!impossible)
			    pole_ou_barreau[iq_choisi].rc=aaa; 
		    }
		}
		if(!impossible){
		    //calculs();
		    //lignes_de_champ();
		}
	    }
	}
	return fin_gere_menus_souris;
    }
    public void paint_et_dessine_lignes_de_champ(){
	//if(attend_souris)
	//return;
	en_train_de_peindre=true;
	du_nouveau=false;
	cliquee=false;
	subject.eraserect(grp_c,0, 0, 1000, 1000,Color.white);
	System.out.println(" multipol "+multipol);
	//multipol.paint();
	if(!attend_souris){
	    if(calculs_faits){
		if(!trouve_deplacement)
		    for(int iq=0;iq<n_poles_ou_barreaux_tot;iq++){
			//System.out.println("iq "+iq +"i_ens "+i_ens +" echelle "+echelle);
			/////////pole_ou_barreau[iq].chp_clc.va_dessiner();
		    }
	    }
	    if(lignes_de_champ_faites){
		for (int i=0;i<nombre_de_pts_a_montrer;i++)
		    if(champ_du_point_a_dessiner[i])
			chp_pts_a_montrer[i].dessine(echelle,fct_zm_sspl,grp_c,Color.green);
		System.out.println("nombre_de_pts_a_montrer "+nombre_de_pts_a_montrer);
		grp_c.setColor(Color.black);
		grp_c.drawString(comment_init,220,bottom_ens-top_ens-28);
		grp_c.setColor(Color.black);
		grp_c.drawString(comment,220,bottom_ens-top_ens-28);
		System.out.println("apres data.ecrit"+nombre_de_pts_a_montrer);
	    }
	}else{
	    grp_c.setColor(Color.black);
	    grp_c.drawString(comment,100,300);
	}
	en_train_de_peindre=false;
    }
    public boolean glisser_vibr ()
    {
      boolean ret=false;
      int xi=ppmouseh;int yi=ppmousev;
      //System.out.println("xi "+xi+" yi"+yi+" pressee "+pressee+"trouve_deplacement"+trouve_deplacement);
      if (pressee){
	  if(!trouve_deplacement){
	      for(int iq=0;iq<n_poles_ou_barreaux_tot;iq++){
		  System.out.println("iq cherche "+iq);
		  double ddx=Math.abs(ppmouseh-pole_ou_barreau[iq].centre.x);
		  double ddy=Math.abs(ppmousev-pole_ou_barreau[iq].centre.y);
		  if ((ddx < 10 )&&(ddy <10)){
		      comment="iq trouve "+iq;
		      trouve_deplacement=true;
		      ret=true;
		      iq_dep=iq;
		      dx_pt_souris = ppmouseh-pole_ou_barreau[iq].centre.x;
		      dy_pt_souris = ppmousev-pole_ou_barreau[iq].centre.y;
		      System.out.println(" *****************deplacement initial, iq "+iq);
		  }
	      }
	  }
      }
      if(trouve_deplacement){
	  if(relachee){
	      //System.out.println("&&&&&&&&&&&&&&&&&&&&&& "+iq_dep);
	      System.out.println("trouve_deplacement "+trouve_deplacement+" relachee "+relachee+" iq_dep "+iq_dep);
	      deplacement(iq_dep);
	      trouve_deplacement=false;iq_dep=-1;
	      relachee=false;
	  }else{
	      if(draguee){
		  System.out.println("trouve_deplacement "+trouve_deplacement+" draguee "+draguee);
		  deplacement(iq_dep);
	      }
	  }
      }
      return ret;
    }
    public void deplacement( int iq)
    {
	if(relachee||draguee){
	    float ay =Math.round(ppmousev - dy_pt_souris);
	    float ax =Math.round(ppmouseh - dx_pt_souris);
	    pole_ou_barreau[iq].centre.assigne(ax,ay);
	}
    }
 

    public double calcule_echelle(){
	double ec=1.;double vec2_max=0.;
	for(int iq=0;iq<n_poles_ou_barreaux_tot;iq++){
	    /*///////
	    pole_ou_barreau[iq].chp_clc.remplis(false);
	    for(int k=0;k<subject.dim;k++)
		if(pole_ou_barreau[iq].chp_clc.vecteur_champ[k].chp.longueur_carre()>vec2_max)
		    vec2_max=pole_ou_barreau[iq].chp_clc.vecteur_champ[k].chp.longueur_carre();
	    *////////
	}
	double sqq=Math.sqrt(vec2_max);
	ec=20./Math.sqrt(vec2_max);
	System.out.println(" ec"+ec+" sqq "+sqq);
	//System.out.println("Calculs termines echelle"+echelle);
	return ec;
    }
    public void	traite_click(){
	//System.out.println("entree traite_click ");
	if(cliquee||draguee||relachee||pressee){
	    Date maintenant=new Date();
	    subject.temps_initial_en_secondes=subject.temps_en_secondes(maintenant);
	    if(command!=""){
		boolean succes_menus=gere_menus_souris ();
		du_nouveau=true;
	    }
	}
	if(cliquee){
	    if(!((ppmouseh==ppmouseh_prec)&&(ppmousev==ppmousev_prec))){
		ppmouseh_prec=ppmouseh;ppmousev_prec=ppmousev;	
	    }else
		cliquee=false;
	}

	if(draguee||relachee||pressee||cliquee)
	   du_nouveau=true;
	//System.out.println("fin_gere_menus_souris "+fin_gere_menus_souris);
	//System.out.println(" dans le traite click  3");
	}
    class MouseMotion extends MouseMotionAdapter
    {
	ensemble_de_dipoles subj;
	public MouseMotion (ensemble_de_dipoles a)
	{
	    subj=a;
	}
	public void mouseMoved(MouseEvent e)
	{ppmouseh=e.getX();ppmousev=e.getY();draguee=false;}
	public void mouseDragged(MouseEvent e)
	{ppmouseh=e.getX();ppmousev=e.getY();draguee=true;
	//System.out.println("draguee dans Mousemove "+draguee);
	traite_click();
	}
    }
    
    class MouseStatic extends MouseAdapter
    {
	ensemble_de_dipoles subj;
	public MouseStatic (ensemble_de_dipoles a){
	    subj=a;
	}
	public void mouseClicked(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;
	    System.out.println("cliquee "+cliquee);
	    traite_click();
	    //	System.out.println("ens_de_dipoles[icylindre].nb_el_ens "+ens_de_dipoles[icylindre].nb_el_ens);
	    //System.out.println("icylindre "+icylindre);
	    //for( int iq=0;iq<ens_de_dipoles[icylindre].nb_el_ens;iq++)
	}
	public void mousePressed(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();pressee=true;relachee=false;
	    System.out.println("pressee "+pressee);
	    traite_click();
	}
	public void mouseReleased(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;relachee=true;pressee=false;
	    System.out.println("relachee "+relachee);
	    traite_click();
	}
    }
    abstract class multipole_ou_barreau{
	Graphics gr_de_cote;
	palette paalette;
	double q_ou_i=0.;point lambda_ou_s;
	double rc=0.;float scale=0,aaa=0;
	double distance_minimum_au_bord=5.;//pixels
	boolean stop=false,imprimer_resultats=false,montrer_les_champ=false;
	boolean de_loin=false,composante_horiz_pas_vert=false;
	point champ_exterieur;
	solution_eq_lineaires sol_sigma_ou_I_32;
	solution_eq_lineaires sol_sigma_ou_I_64;
	point_xyz champ_exterieur_xyz,posit_xyz,vec_un_xyz,pt_xyz;
	point centre;int rayon,rayon0,longueur;
	double angle=0.,residu=0;
	double alpha[]=new double[64];
	double sin_angle[]=new double[64];
	double cos_angle[]=new double[64];
	point position,posit;point_y_z position_y_z;
	boolean tour_complet[]=new boolean [64];
	double facteur_fondamental=0,champ_exterieur_scal=0.;
	double mu0_ou_un_sur_eps0;
	point q_dist_plus=new point(zer);
	point q_dist_moins=new point(zer);
	double distance_minimum_a_l_arc=0;
	point_xyz toto_xyz,titi_xyz,tata_xyz,tutu_xyz,tete_xyz,feld_xyz;
	point_y_z tata_y_z,tete_y_z,titi_y_z,toto_y_z,tutu_y_z;
	point_y_z point_initial_arc=new point_y_z(0.,0.);
	point_y_z point_final_arc=new point_y_z(0.,0.);
	point_y_z point_central_arc=new point_y_z(0.,0.);
	point_xyz field[]=new point_xyz[64];
	point coord[]=new point[64];
	point_y_z courant=new point_y_z(0.,0.);
	double susceptibilite=2.,alphaa=0.;
	point_y_z distance_a_point_central_arc=new point_y_z(0.,0.);
	abstract boolean pas_trop_proche_des_bords(point dist);
	multipole_ou_barreau(){
	    lambda_ou_s=new point(0.,0.);
	    position=new point(zer);
	    posit=new point(zer);
	    position_y_z=new point_y_z(zer_y_z);
	    champ_exterieur=new point(0.,1.);
	    champ_exterieur_xyz=new point_xyz(champ_exterieur,0.);
	    posit_xyz=new point_xyz(0.,0.,0.);
	    feld_xyz=new point_xyz(0.,0.,0.);
	    pt_xyz=new point_xyz(0.,0.,0.);
	    vec_un_xyz=new point_xyz(0.,0.,0.);
	    for (int i=0;i<64;i++){
		field[i]=new point_xyz(0.,0.,0.);
		coord[i]=new point(0.,0.);
		sin_angle[i]=Math.sin(pi*i/64);
		cos_angle[i]=Math.cos(pi*i/64.);
		alpha[i]=0.;
	    }
	    toto_xyz=new point_xyz(0.,0.,0.);
	    titi_xyz=new point_xyz(0.,0.,0.);
	    tata_xyz=new point_xyz(0.,0.,0.);
	    tutu_xyz=new point_xyz(0.,0.,0.);
	    tete_xyz=new point_xyz(0.,0.,0.);
	    toto_y_z=new point_y_z(0.,0.);
	    titi_y_z=new point_y_z(0.,0.);
	    tata_y_z=new point_y_z(0.,0.);
	    tutu_y_z=new point_y_z(0.,0.);
	    tete_y_z=new point_y_z(0.,0.);
	    if(i_ens==0)
		mu0_ou_un_sur_eps0=1/subject.eps0;
	    else
		mu0_ou_un_sur_eps0=subject.mu0;
	    facteur_fondamental=mu0_ou_un_sur_eps0/(4*pi);            
	}
	double sigma_ou_I(point_xyz vect,point_xyz chpp_xyz,double sig_ou_I){
	    cici=chpp_xyz.scalaire(vect);
	    if(composante_horiz_pas_vert)
		champ_exterieur_scal=champ_exterieur_xyz.x*vect.x;
	    else
		champ_exterieur_scal=champ_exterieur_xyz.y*vect.y;
	    caca=susceptibilite/(1.+susceptibilite/2.)*(champ_exterieur_scal+cici)/mu0_ou_un_sur_eps0;
	    coco=-cici+mu0_ou_un_sur_eps0*caca/2;
	    alphaa=coco/(sig_ou_I*mu0_ou_un_sur_eps0);
	    return caca;
	}
	class champs_points_calcules{
	    vecteur vecteur_champ[]=new vecteur[2000] ;
	    int nb_champs_a_dessiner=0;
	    public champs_points_calcules(){
		for (int i=0;i<2000;i++)
		    vecteur_champ[i]=new vecteur(zer,zer);
	    }
	    public void remplis(boolean inclue_champ_exterieur){
		montrer_les_champ=true;
		nb_champs_a_dessiner=0;scale=0;nb_champs_a_dessiner=0;
		double d=0;
		double decalage_x=10;
		if(!demo)
		    decalage_x=20;
		position.assigne((float) left_ens,(float) top_ens+decalage_x);
		scale=0;
		point dist=new point(0.,0.);;
		for (int kk=0;kk<10000;kk++){
		    dist.assigne_soustrait(position,centre);
		    totologic=true;
		    if(i_demarre==1||i_demarre>=3)
			totologic=pas_trop_proche_des_bords(dist);
		    //if(!totologic)
		    //	dist.print(" totologic "+totologic+" dist ");
		    /*
		      if(i_demarre==1)
		      if(Math.abs(dist.longueur()-rayon)>5.&&Math.abs(position.x-centre.x)<10.)
		      totologic=false;
		      if(i_demarre>=3)
		      if(Math.abs(dist.longueur()-rayon)<10.)
		      totologic=false;
		    */
		    //if(i_demarre<=2||Math.abs((dist.longueur_carre()-rayon*rayon)/(2.0*rayon))>5.){
		    //if(totologic&&est_dedans(dist)){
		    if(totologic){
			vecteur_champ[kk].pnt.assigne(position);
			vecteur_champ[kk].chp.assigne(champ_en_un_point(position,0.));
			if(inclue_champ_exterieur)
			    vecteur_champ[kk].chp.additionne(champ_exterieur);
			titi.assigne(240.,280.);
			toto.assigne_soustrait(vecteur_champ[kk].pnt,titi);
			titi.assigne(240.,160.);
			tutu.assigne_soustrait(vecteur_champ[kk].pnt,titi);
			//if(tutu.longueur_carre()<100.||toto.longueur_carre()<100.)
			//    vecteur_champ[kk].print(" $$$$$vecteur_champ[kk] ");

			/*
			if(kk/20*20==kk)
			    vecteur_champ[kk].print(" kk "+kk+" vecteur_champ[kk] ");
			*/
			//if(est_dedans(dist))
			//vecteur_champ[kk].print(" hhhvecteur_champ[kk] ");
			if(vecteur_champ[kk].chp.longueur_carre()>scale)
			    scale=(float)vecteur_champ[kk].chp.longueur_carre();
			nb_champs_a_dessiner=kk;
			//if(kk/10*10==kk)
			//vecteur_champ[kk].print("kk "+kk+" vecteur_champ[kk] ");
		
		    }		    
		    position.x+=20;
		    if(position.x>right_ens){
			position.x=(float) left_ens+decalage_x;
			position.y+=20;
			if(position.y>bottom_ens)
			    break;
		    }
		}
		scale=(float)Math.sqrt(scale);
	    }
	}
	void dessiner_les_champs(champs_points_calcules c0,champs_points_calcules c1,double fct){
	    fct_zm_sspl=fct;
	    vecteur v=new vecteur(zer,zer);
	    if(i_demarre<=2){
		scale=0;
		for (int kk=0;kk<c0.nb_champs_a_dessiner;kk++){
		    v.assigne(c0.vecteur_champ[kk]);
		    if(n_poles_ou_barreaux_tot==2)
			v.chp.additionne(c1.vecteur_champ[kk].chp);
		    if(v.chp.longueur_carre()>scale)
			scale=(float)v.chp.longueur_carre(); 
		}
		scale=(float)Math.sqrt(scale);
	    }
	    System.out.println(" c0.nb_champs_a_dessiner "+c0.nb_champs_a_dessiner+" scale dans dessiner_les champs "+scale);
	    
	    for (int kk=0;kk<c0.nb_champs_a_dessiner;kk++){
		v.assigne(c0.vecteur_champ[kk]);
		titi.assigne(240.,280.);
		toto.assigne_soustrait(v.pnt,titi);
		titi.assigne(240.,160.);
		tutu.assigne_soustrait(v.pnt,titi);
		if(tutu.longueur_carre()<100.||toto.longueur_carre()<100.)
		    v.print(" scale "+(float)scale+" vvvvvvvvvvvv ");


		if(n_poles_ou_barreaux_tot==2)
		    v.chp.additionne(c1.vecteur_champ[kk].chp);
		v.dessine(scale,fct_zm_sspl,grp_c,Color.green);
	    }
	}
	class palette{
	    final static int top_palette=60,left_palette=10,right_palette=110,bot_palette=88;
	    int indice_lamb=0;
	    int min_palette=20,max_palette=80;
	    double I_ou_Q_min=0.,I_ou_Q_max=0.;
	    couleur couleur_de_la_lumiere;
	    final couleur farbe_bei_laenge[]=new couleur [100]; 
	    String toto_string="";
	    palette(){
		couleur_de_la_lumiere=new couleur(255,255,255);
		int v=0,w=0;
		for(int i=0;i<100;i++){
		    if(i<27)  //ultra bleu
			farbe_bei_laenge[i]= new couleur(0,0,(int)(255*i/27.));
		    else if(i<50){ //entre bleu et vert
			v=i-27;
			w=50-i;
			if(v>w){
			    w=(int)(255.*w)/v;
			    v=255;
			}else{
			    v=(int)(255.*v)/w;
			    w=255;		    
			}
			farbe_bei_laenge[i]= new couleur(0,v,w);
		    }
		    else if(i<83){ //entre vert et rouge
			v=i-50;
			w=83-i;
			if(v>w){
			    w=(int)(255.*w)/v;
			    v=255;
			}else{
			    v=(int)(255.*v)/w;
			    w=255;		    
			}
			farbe_bei_laenge[i]= new couleur(v,w,0);		
			//farbe_bei_laenge[i]= new couleur((int)(255*(i-50)/33.),(int)(255*(83-i)/33.),0);		
		    }else if(i>=83)//infra rouge
			farbe_bei_laenge[i]=new couleur((int)(255*(100-i)/17.),0,0);
		}			
	    }
	    void dessine(String string_min,String string_max){
		gr_de_cote.setColor(Color.black);
		gr_de_cote.drawString(string_min,left_palette,bot_palette-32);
		gr_de_cote.drawString(string_max,left_palette+60,bot_palette-32);
		for(int i=min_palette;i<max_palette;i++)
		    subject.drawline_couleur(gr_de_cote,left_palette+i,bot_palette,left_palette+i,top_palette,farbe_bei_laenge[i].col);
		//ppv=left_palette+(int)Math.round((right_palette-left_palette)*(lambda-min_palette)/(max_palette-min_palette));
		//subject.drawline_couleur(gr_de_cote,ppv,bot_palette,ppv,top_palette,Color.black );
	    }
	    boolean determine_la_couleur(double courant_ou_charge){
		System.out.println(" courant_ou_charge "+(float)courant_ou_charge);
		if((courant_ou_charge>I_ou_Q_min&&courant_ou_charge<=I_ou_Q_max)){
		    coco=(courant_ou_charge-I_ou_Q_min)/(I_ou_Q_max-I_ou_Q_min);
		    indice_lamb=min_palette+(int)(coco*(max_palette-min_palette));
		    if(indice_lamb<min_palette)
			indice_lamb=min_palette;
		    if(indice_lamb>=max_palette)
			indice_lamb=max_palette;
		    System.out.println(" indice_lamb "+indice_lamb+" coco "+(float)coco);
		    couleur_de_la_lumiere.assigne(farbe_bei_laenge[indice_lamb]);
		    return true;
		}
		return false;
	    }
	}
	abstract void paint();
	abstract void elimine();
	abstract point champ_en_un_point(point position,double posit_z);
	abstract boolean est_dedans(point dist);
    }


    abstract class bille_ou_barreau_cylindrique extends multipole_ou_barreau{
	int dim=16;double norme=0.;
	point champ_en_un_point,champ_au_centre;
	boolean ecrire_champ=false;
	double alpha_estime=0.65;
	point_xyz vecteur_unite_xyz[]=new point_xyz[64];
	bille_ou_barreau_cylindrique(){
	    super();
	    champ_au_centre=new point(zer);
	    champ_en_un_point=new point(zer);
	    for (int i=0;i<64;i++)
		vecteur_unite_xyz[i]=new point_xyz(0.,0.,0.);
	}
	boolean pas_trop_proche_des_bords(point dist){
	    return far_from_boarders(dist);
	}
	abstract boolean far_from_boarders(point dist);
    }
    class ellipsoide extends bille_ou_barreau_cylindrique{
	point champ_en_un_point;
	point chp_sppl_dvs_sscpt[]=new point[16];
	point post[]=new point[16];
	point_y_z projection_du_rayon_sur_y_z[]=new point_y_z[64];
	point_y_z projection_perp_au_rayon_sur_y_z[]=new point_y_z[64];
	double rapport_des_axes=1.,aa,bb;
	double sintet[]=new double[64];
	double costet[]=new double[64];
	double ytet[]=new double[64];
	double xtet[]=new double[64];
	double dxtet[]=new double[64];
	double dytet[]=new double[64];
	double dl_tg_tet[]=new double[64];
	double r_tet[]=new double[64];
	double sigma_H[]=new double[64];;
	double sigma_V[]=new double[64];;
	double r_phi_prime[]=new double[64];
	double cosphi_prime[]=new double[64];
	double sinphi_prime[]=new double[64];
	double dz_phi_prime []=new double[64];
	double dtet_ellipse[]=new double[64];
	int iteration=0;
	double densite_de_charge[][]=new double[64][2];
	double dens_courant_pour_paint[][]=new double[64][2];
	boolean spherique=false;
	boolean exclure_phi[]=new boolean[64];
	point_xyz pt_xyz,pt_aux_xyz;
	champs_points_calcules chp_clc_barreau;
	ellipsoide(point ctr1, double rayon1, double longueur1,double largeur1){
	    super();
	    centre=new point(ctr1);
	    rayon=(int)rayon1;
	    longueur=(int)longueur1;
	    champ_en_un_point=new point(zer);
	    pt_xyz=new point_xyz(0.,0.,0.);
	    pt_aux_xyz=new point_xyz(0.,0.,0.);
	    //if(i_ens==0)
	    //	projection_du_rayon_sur_y_z[1000]=null;
	    if(i_ens==1){
		susceptibilite=80.;
		alpha_estime=0.33;
	    }else{
		susceptibilite=5.;
		alpha_estime=0.33;
	    }
	    rapport_des_axes=2.;
	    if(i_demarre==4)
		rapport_des_axes=1.;
	    bb=rayon1;
	    aa=bb*rapport_des_axes;
	    /*
	      aa=rayon1;
	      bb=aa*rapport_des_axes;
	    */
	    double phi=-pi/64.;double cos_phi=0,sin_phi=0;
	    for (int ifi=0;ifi<64;ifi++){	
		phi+=2*pi/64.;
		cos_phi=Math.cos(phi);
		sin_phi=Math.sin(phi);		
		projection_du_rayon_sur_y_z[ifi]=new point_y_z(cos_phi,sin_phi);
		projection_perp_au_rayon_sur_y_z[ifi]=new point_y_z(-sin_phi,cos_phi);
	    }
	    double teta=-pi/128.; double tg_teta=0.,rrr=0.;
	    double s_dy=0.;
	    for (int itet=0;itet<64;itet++){	
		teta+=pi/64.;
		costet[itet]=Math.cos(teta);
		sintet[itet]=Math.sin(teta);
		if(spherique){
		    ytet[itet]=rayon*sintet[itet];
		    xtet[itet]=rayon*costet[itet];
		    dxtet[itet]=-ytet[itet]*pi/64.;
		    dytet[itet]=xtet[itet]*pi/64.;
		}else{
		    tg_teta=rapport_des_axes*sintet[itet]/costet[itet];
		    dtet_ellipse[itet]=pi/64.;
		    cosphi_prime[itet]=costet[itet];
		    sinphi_prime[itet]=sintet[itet];
		    r_tet[itet]=Math.sqrt(1./(sintet[itet]*sintet[itet]/(bb*bb)+costet[itet]*costet[itet]/(aa*aa)));
		    r_phi_prime[itet]=r_tet[itet];
		    ytet[itet]=r_tet[itet]*sintet[itet];
		    xtet[itet]=r_tet[itet]*costet[itet];
		    dxtet[itet]=-ytet[itet]*r_tet[itet]*r_tet[itet]*dtet_ellipse[itet]/(bb*bb);
		    dytet[itet]=xtet[itet]*r_tet[itet]*r_tet[itet]*dtet_ellipse[itet]/(aa*aa);
		    dz_phi_prime[itet]=dytet[itet];
		    if(i_ens==1)
			toto.assigne(dxtet[itet],dytet[itet]);
		    else
			toto.assigne(-dytet[itet],+dxtet[itet]);
		    vecteur_unite_xyz[itet].assigne(toto,0.);
		    vecteur_unite_xyz[itet].divise_cst(-toto.longueur());
		}
		dl_tg_tet[itet]=Math.sqrt(dxtet[itet]*dxtet[itet]+dytet[itet]*dytet[itet]);
		if(itet<=31)
		    s_dy+=dytet[itet];
		vec_un_xyz.assigne(vecteur_unite_xyz[itet]);
		if(Math.abs(champ_exterieur.x)>0.001){
		    champ_exterieur_scal=champ_exterieur_xyz.x*vec_un_xyz.x;
		    sigma_H[itet]=champ_exterieur_scal*susceptibilite/(1.+susceptibilite*alpha_estime)/mu0_ou_un_sur_eps0;
		}
		if(Math.abs(champ_exterieur.y)>0.001){
		    if(i_ens==0&&itet>31)
			vec_un_xyz.multiplie_cst(-1.);
		    champ_exterieur_scal=champ_exterieur_xyz.y*vec_un_xyz.y;
		    sigma_V[itet]=champ_exterieur_scal*susceptibilite/(1.+susceptibilite*alpha_estime)/mu0_ou_un_sur_eps0;
		}
		if(itet==0||itet==7||itet==15||itet==23||itet==24||itet==31||itet==32||itet==40||itet==56||itet==63){
		    coco=champ_exterieur_xyz.scalaire(vec_un_xyz);
		    cucu=(1.+susceptibilite*alpha_estime);
		    System.out.println("itet "+itet+" xtet[itet] "+(float)xtet[itet]+" ytet[itet] "+(float)ytet[itet]+"  dxtet[itet]  "+(float)dxtet[itet]+"  dytet[itet]  "+(float)dytet[itet]+" dtet_ "+(float)dtet_ellipse[itet]+" coco "+(float)coco+" cucu "+(float)cucu);
		    vec_un_xyz.print(" sigma_H[itet] "+(float)sigma_H[itet]+" mu0*sigma_H[itet] "+(float)(sigma_H[itet]*mu0_ou_un_sur_eps0)+" sigma_V[itet] "+(float)sigma_V[itet]+" vec_un_xyz ");
		}
	    }
	    System.out.println(" s_dy "+(float)s_dy);
	    //if(i_ens==1)
	    if(Math.abs(champ_exterieur.x)>0.){
		composante_horiz_pas_vert=true;
		calcule_les_sigma(sigma_H);
	    }
	    if(Math.abs(champ_exterieur.y)>0.){
		composante_horiz_pas_vert=false;
		calcule_les_sigma(sigma_V);
	    }

	    for (int itet=0;itet<64;itet++)	
		if(itet==0||itet==7||itet==15||itet==23||itet==24||itet==31||itet==32||itet==40||itet==56||itet==63)
		    System.out.println("itet "+itet+" sigma_H[itet] "+(float)sigma_H[itet]+" sigma_V[itet] "+(float)sigma_V[itet]);

	    chp_clc_barreau=new champs_points_calcules();
	    chp_clc_barreau.remplis(false);
	    /*
	      point pos[]=new point[16];
	      for (int j=0;j<dim;j++){
	      chp_sppl_dvs_sscpt[j]=new point(zer);
	      post[j]=new point(zer);
	      }
	      champs_suppl();
	    */
	    paint();
	}
	void calcule_les_sigma(double[] sigma){
	    double sigma_recalculee[]=new double [64];
	    for (int ib_h=0;ib_h<3;ib_h++){
		System.out.println();
		System.out.println(" ib_h "+ib_h);
		System.out.println();
		tata_xyz.print(" tata_xyz ");
		champ_au_centre.assigne(champ_en_un_point(centre,0.));
		centre.print_sl(" centre ");
		champ_au_centre.print_sl(" champ_au_centre. ");
		//if(i_ens==0)
		//xtet[1000]=0;
		int itott=16;
		toto.assigne(centre.x+xtet[itott],centre.y+ytet[itott]);
		exclure_phi[itott]=false;
		titi.assigne(champ_en_un_point(toto,0.));
		titi.print_sl(" titi ");
		exclure_phi[itott]=false;
		double dix=centre.x-longueur/2.-(double)longueur/64.;
		point_xyz chp_later=new point_xyz(0.,0.,0.);
		int i_haut_bas=1;
		int itet=0;double sisig=0.;
		for (int itett=0;itett<64;itett++){
		    /*
		      exclure_phi[itet]=true;
		      if(itet!=0)
		      exclure_phi[itet-1]=true;
		      if(itet!=63)
		      exclure_phi[itet+1]=true;
		    */
		    itet=itett;
		    if(composante_horiz_pas_vert){
			toto.assigne(centre.x+xtet[itet],centre.y+ytet[itet]);
			titi.assigne_soustrait(toto,centre);
			chp_later.assigne(champ_en_un_point_H(toto,0.,titi.longueur()));
		    }else{
			if(itett>31){
			    itet=63-itett;
			    i_haut_bas=-1;
			}
			pt_aux_xyz.assigne(centre.x+xtet[itet],centre.y+i_haut_bas*ytet[itet],0.);
			chp_later.assigne(champ_en_un_point_V(pt_aux_xyz,0.));
		    }
		    //ecrire_champ=itet==7||itet==24;
		    exclure_phi[itet]=false;
		    if(itet!=0)
			exclure_phi[itet-1]=false;
		    if(itet!=63)
			exclure_phi[itet+1]=false;
		    vec_un_xyz.assigne(vecteur_unite_xyz[itett]);
		    if(i_ens==0&&!composante_horiz_pas_vert&&itett>31)
			vec_un_xyz.multiplie_cst(-1.);
		    sigma_recalculee[itett]=sigma_ou_I(vec_un_xyz,chp_later,sigma[itett]);
		    if(itett==0||itett==7||itett==15||itett==24||itett==31||itett==32||itett==39||itett==48||itett==56||itett==63){
			//if(itet==0||itet==7||itet==15||itet==23||itet==31){
			System.out.println(" itett "+itett+" itet "+itet+" I_later "+(float)sigma[itett]+" recalcule "+(float)sigma_recalculee[itett]+" alphaa "+(float)alphaa);
			chp_later.print("coco "+(float)coco+" cici "+(float)cici+" mu0*sig/2 "+(float)(mu0_ou_un_sur_eps0*sigma[itett]/2.)+"chp_later ");
			//pt_aux_xyz.print(" xtet[itet] "+(float)xtet[itet]+ " pt_aux_xyz ");
			//vec_un_xyz.print(" xtet[itet] "+(float)xtet[itet]+" ytet[itet] "+(float)ytet[itet]+" vec_un_xyz ");
		    }
		}
		//exclure_phi[2000]=false;
		for (int itett=0;itett<64;itett++)
		    sigma[itett]=sigma_recalculee[itett];
		champ_au_centre.assigne(champ_en_un_point(centre,0.));
		champ_au_centre.print(" champ_au_centre. ");

	    }
	}


	public void elimine(){

	}
	boolean est_dedans(point dist){
	    if(spherique)
		return (dist.longueur_carre()<rayon*rayon);
	    else
		return (dist.x*dist.x/(aa*aa)+dist.y*dist.y/(bb*bb)<1);
	}
	boolean far_from_boarders(point dist){
	    if(spherique)
		return (Math.abs(dist.longueur()-rayon)>distance_minimum_au_bord);
	    else{
		if(dist.longueur_carre()>1){
		    coco=dist.y/dist.longueur();
		    cucu=dist.x/dist.longueur();
		    cece=Math.sqrt(1./(coco*coco/(bb*bb)+cucu*cucu/(aa*aa)));
		    return (Math.abs(dist.longueur()-cece)>distance_minimum_au_bord);
		}else{
		    return true;
		}
	    }
	}
	void paint(){
	    grp_c.setColor(Color.black);
	    grp_c.drawOval((int)(centre.x-aa),(int)(centre.y-bb),(int)(2*aa),(int)(2*bb));
	    double max_charge=0,min_charge=0;
	    for (int itet=1;itet<64;itet+=4)
		for (int ififi=0;ififi<2;ififi++){
		    if(i_ens==1){
			if((dens_courant_pour_paint[itet][ififi]+dens_courant_pour_paint[itet+1][ififi])/2>max_charge)
			    max_charge=(dens_courant_pour_paint[itet][ififi]+dens_courant_pour_paint[itet+1][ififi])/2;
			if((dens_courant_pour_paint[itet][ififi]+dens_courant_pour_paint[itet+1][ififi])/2<min_charge)
			    min_charge=(dens_courant_pour_paint[itet][ififi]+dens_courant_pour_paint[itet+1][ififi])/2;
		    }else{
			if((densite_de_charge[itet][ififi]+densite_de_charge[itet+1][ififi])/2>max_charge)
			    max_charge=(densite_de_charge[itet][ififi]+densite_de_charge[itet+1][ififi])/2;
			if((densite_de_charge[itet][ififi]+densite_de_charge[itet+1][ififi])/2<min_charge)
			    min_charge=(densite_de_charge[itet][ififi]+densite_de_charge[itet+1][ififi])/2;
		    }
		}
	    System.out.println(" min_charge "+min_charge+" max_charge "+max_charge);
	    vecteur v=new vecteur(zer,zer);double dens=0.;
	    for (int itet=1;itet<64;itet+=4){
		tata.assigne((xtet[itet]+xtet[itet+1])/2,(ytet[itet]+ytet[itet+1])/2);
		toto.assigne_additionne(centre,tata);
		for (int ififi=0;ififi<2;ififi++){
		    if(i_ens==1)
			dens=(dens_courant_pour_paint[itet][ififi]+dens_courant_pour_paint[itet+1][ififi])/2;
		    else
			dens=(densite_de_charge[itet][ififi]+densite_de_charge[itet+1][ififi])/2;
		    //System.out.println(" itet "+itet+" ififi "+ififi+"dens "+(float)dens);
		    if(ififi==0)
			tata.assigne(xtet[itet],ytet[itet]);
		    else
			tata.assigne(xtet[itet],-ytet[itet]);
		    toto.assigne_additionne(centre,tata);

		    if(dens>0){
			grp_c.setColor(Color.red);
			toto_int=(int)Math.round(10*Math.abs(dens/max_charge));
		    }else{
			grp_c.setColor(Color.blue);
			toto_int=(int)Math.round(10*Math.abs(dens/min_charge));
		    }
		    grp_c.drawOval((int)toto.x-toto_int,(int)toto.y-toto_int,2*toto_int,2*toto_int);
		}
	    }
	    dessiner_les_champs(chp_clc_barreau,null,0.5);
	    //if(i_ens==1)
	    //	xtet[1000]=0;
	}
	point champ_en_un_point(point posit,double z_bidon){
	    champ_en_un_point.assigne(zer);
	    point dist_au_centre=new point(posit);
	    dist_au_centre.soustrait(centre);
	    double d_au_c=dist_au_centre.longueur();
	    double cos_psi=0,sin_psi=0,tg_psi=0;
	    if(Math.abs(champ_exterieur.x)>0.0001)
		champ_en_un_point.assigne(champ_en_un_point_H(posit,0.,d_au_c).extrait_point());
	    //if(tutu.longueur_carre()<20)
	    //champ_en_un_point.print(" toto mid ";
	    if(Math.abs(champ_exterieur.y)>0.0001){
		pt_aux_xyz.assigne(posit.x,posit.y,z_bidon);
		toto.assigne(champ_en_un_point_V(pt_aux_xyz,d_au_c).extrait_point());
		champ_en_un_point.additionne(toto);
	    }
	    //if(tutu.longueur_carre()<20)
	    //champ_en_un_point.print(" toto fin ");

	    return champ_en_un_point;
	}
	point_xyz champ_en_un_point_H(point posit,double z_bidon,double d_c){
	    tutu_xyz.assigne(zer,0.);
	    posit_xyz.assigne(posit,z_bidon);
	    double cos_psi=0,sin_psi=0,tg_psi=0;
	    for (int itet=0;itet<64;itet++){
		tg_psi=-(rapport_des_axes*rapport_des_axes*sintet[itet]/costet[itet]);
		cos_psi=1./Math.sqrt(1+tg_psi*tg_psi);
		if(cos_psi*costet[itet]<0)
		    cos_psi=-cos_psi;
		sin_psi=Math.abs(cos_psi*tg_psi);
		for (int ifi=0;ifi<64;ifi++){
		    //if(!(exclure_phi[itet]&&(ifi==0||ifi==63))){
		    toto_xyz.assigne(centre,0.);
		    toto_xyz.x+=xtet[itet];
		    toto_y_z.assigne_facteur(projection_du_rayon_sur_y_z[ifi],ytet[itet]);
		    toto_xyz.additionne(0.,toto_y_z);
		    tata_xyz.assigne_soustrait(toto_xyz,posit_xyz);
		    caca=tata_xyz.longueur();
		    //			if(caca>rayon*2.*pi/64.){
		    caca=caca*caca*caca;
		    if(i_ens==0){
			coco=sigma_H[itet]*dl_tg_tet[itet]*ytet[itet]*2.*pi/64.;
			tutu_xyz.soustrait_facteur(tata_xyz,coco/caca);
			//if(d_c<10.&&itet==32&&(ifi==0||ifi==15)){
			if(ecrire_champ&&(ifi==15||ifi==48)&&(itet==15||itet==23||itet==48)){
			    tata_xyz.print("itet "+itet+" ifi "+ifi+" tata_xyz "); 
			    tutu_xyz.print("coco/caca "+(float)(coco/caca)+" tutu_xyz "); 
			}
		    }else{
			//if(tata_xyz.longueur_carre()>1){
			tete_y_z.assigne_facteur(projection_perp_au_rayon_sur_y_z[ifi],sigma_H[itet]*dxtet[itet]*ytet[itet]*2*pi/64.);
			tete_xyz.assigne(0.,tete_y_z);
			if((ifi==0||ifi==32)&&d_c<10.){
			    dens_courant_pour_paint[itet][ifi/63]=sigma_H[itet]*dxtet[itet];
			    if(ifi==32)
				dens_courant_pour_paint[itet][ifi/63]=-dens_courant_pour_paint[itet][ifi/63];
			}
			pt_xyz.assigne_diviseur(tata_xyz.vectoriel(tete_xyz),caca);
			tutu_xyz.soustrait(pt_xyz);
		    }
		    //			}
		    //}
		}
	    }
	    tutu_xyz.multiplie_cst(facteur_fondamental);
	    if(ecrire_champ)
		tutu_xyz.print(" tutu_xyz_xyz ");
	    return tutu_xyz;
	}
	point_xyz champ_en_un_point_V(point_xyz posit_xyz,double d_c){
	    tutu_xyz.assigne(0.,0.,0.);
	    //posit_xyz.print(" posit_xyz ");
	    double y=0;
	    double rrr=0.,sisig=0.;
	    double S=0.,dS=0.,R=0.,dY=0.,sfi2=0.,cfi2=0.;
	    for (int i_haut_bas=-1;i_haut_bas<2;i_haut_bas+=2){
		for (int itet=0;itet<32;itet++){
		    y=i_haut_bas*ytet[itet];
		    cece=Math.sqrt(1.-y*y/(bb*bb));
		    sisig=sigma_V[itet];
		    if(i_ens==0&&i_haut_bas==1)
			sisig=sigma_V[63-itet];
		    for (int ifi_prim=0;ifi_prim<64;ifi_prim++){
			rrr=r_phi_prime[ifi_prim]*cece;
			cfi2=projection_du_rayon_sur_y_z[ifi_prim].y*projection_du_rayon_sur_y_z[ifi_prim].y;
			sfi2=projection_du_rayon_sur_y_z[ifi_prim].z*projection_du_rayon_sur_y_z[ifi_prim].z;
			for (int i_avant_arriere=-1;i_avant_arriere<2;i_avant_arriere+=2){
			    toto_xyz.x =centre.x+i_avant_arriere*rrr*cosphi_prime[ifi_prim];
			    toto_xyz.y=centre.y+y;
			    toto_xyz.z=i_avant_arriere*rrr*sinphi_prime[ifi_prim];
			    tata_xyz.assigne_soustrait(toto_xyz,posit_xyz);
			    caca=tata_xyz.longueur();
			    //if(caca>rayon*2.*pi/64.){
			    caca=caca*caca*caca;
			    if(i_ens==0){				  
				dS=Math.sqrt(cece*cece*(bb*bb*cfi2+aa*aa*sfi2)+Math.pow(y*r_phi_prime[ifi_prim]/(bb*bb)*(bb*cfi2+aa*sfi2),2))*Math.abs(dytet[itet])*pi/64.;
				if(i_avant_arriere==-1&&i_haut_bas==-1){
				    S+=dS;
				    cici=Math.abs(dytet[itet]/projection_du_rayon_sur_y_z[itet/2].y);
				    R+=cici;
				    if(ifi_prim==0)
					dY+=dytet[itet];
				    /*
				      if(ifi_prim==15&&(itet==0||itet==7||itet==15||itet==23||itet==31)){
				      System.out.println("itet "+itet+" ifi_prim "+ifi_prim+" S "+(float)S+" cl*cece "+(float)(dl_tg_tet[ifi_prim]*cece)+" dS "+(float)dS);
				      System.out.println("itet "+itet+" ifi_prim "+ifi_prim+" dytet[itet] "+(float)dytet[itet]+" proj "+(float)projection_du_rayon_sur_y_z[itet/2].y+" cici "+(float)cici+" R "+(float)R);
				      }
				    */
				}
				coco=sisig*dS;
				tete_xyz.assigne_facteur(tata_xyz,coco/caca);				    }else{
				titi_xyz.assigne(dxtet[ifi_prim],0.,dz_phi_prime[ifi_prim]);
				titi_xyz.multiplie_cst(i_avant_arriere*cece);
				tete_xyz.assigne(titi_xyz.vectoriel(tata_xyz));
				tete_xyz.multiplie_cst(-dytet[itet]*sisig/caca);    
				if((ifi_prim==0||ifi_prim==32)&&d_c<10.){
				    dens_courant_pour_paint[itet][ifi_prim/31]=tete_xyz.y;
				    dens_courant_pour_paint[63-itet][ifi_prim/31]=-dens_courant_pour_paint[itet][ifi_prim/63];
				}
				/*
				  if((itet==0||itet==7||itet==15||itet==23||itet==31||itet==32||itet==40||itet==48||itet==56||itet==63)&&ifi_prim==16){
				  System.out.println();
				  tata_xyz.print(" itet "+itet+" i_haut_bas "+i_haut_bas+" ifi_p "+ifi_prim+" tata_xyz ");
				  tete_xyz.print(" y "+(float)y+" tete_xyz ");
				  toto_xyz.print(" toto_xyz ");
				  titi_xyz.print(" titi_xyz ");
				  }
				*/
			    }
			    tutu_xyz.additionne(tete_xyz);
			}
		    }
		}
	    }
	    /*
	      if(i_ens==0){
	      double e=Math.sqrt(aa*aa-bb*bb)/aa;
	      double surf1=(pi*(2.*aa*aa+bb*bb/e*Math.log((1.+e)/(1.-e))))/4.;
	      double surf2=(2.*pi*bb*(bb+aa/e*Math.asin(e)))/4.;
	      System.out.println("S "+(float)S+" pi*r2 "+(float)(pi*bb*bb)+" dY "+(float)dY+" e "+(float)e+" surf1 "+(float)surf1+" surf2 "+(float)surf2);
	      dxtet[1000]=0;
	      }
	    */
	    tutu_xyz.multiplie_cst(facteur_fondamental);
	    return tutu_xyz;
	}
	
	//point champ_en_un_point_vertical(point posit,double z_bidon){
	
	void champs_suppl(){
	    for (int j=0;j<dim;j++)
		post[j].assigne(centre.x,centre.y+(float)rayon/(float)dim*(float)j);
	    point champ_de_fugue=new point(zer);
	    //stop=true;
	    
	    //chp_sppl_dvs_sscpt[0].assigne_diviseur(champ_exterieur,2.);
	    chp_sppl_dvs_sscpt[0].assigne_diviseur(champ_exterieur,3./2.);
	    chp_sppl_dvs_sscpt[0].print("i=0 "+" chp_sppl_dvs_sscpt[0] ");
	    for (int i=1;i<dim;i++){
		//System.out.println(" i "+i);
		chp_sppl_dvs_sscpt[i].assigne_diviseur(champ_exterieur,3.);
		chp_sppl_dvs_sscpt[i].additionne_diviseur(chp_sppl_dvs_sscpt[i-1],3.);
		//System.out.println("chp_sppl_dvs_sscpt[i-1].x "+chp_sppl_dvs_sscpt[i-1].x+" chp_sppl_dvs_sscpt[i-1].x/cc "+chp_sppl_dvs_sscpt[i-1].x/cc);
		champ_de_fugue.assigne(zer);
		for (int j=1;j<=i-1;j++){
		    titi.assigne(champ_de_fuite_normalise(rayon*(float)(j)/(float)dim,post[i]));
		    champ_de_fugue.additionne_facteur(titi,(chp_sppl_dvs_sscpt[j-1].x-chp_sppl_dvs_sscpt[j].x));
		    //System.out.println(" j "+j+" champ_de_fugue.x "+(float)champ_de_fugue.x+" titi.x "+titi.x);
		}
		chp_sppl_dvs_sscpt[i].soustrait(champ_de_fugue);
		//chp_sppl_dvs_sscpt[i].print(" chp_sppl_dvs_sscpt[i] ");
	    }
	}
	point champ_de_fuite_normalise(double ray,point pos){
	    rayon0=rayon;
	    rayon=(int)Math.round(ray);
	    toto.assigne_soustrait(pos,centre);
	    //toto.print(" rayon "+rayon+" toto ");
	    //pos.print(" pos ");
	    toto.assigne_soustrait(champ_en_un_point(pos,0.),champ_exterieur);
	    //toto.print("toto pout pos ");
	    //centre.print(" centre ");

	    //toto.assigne_soustrait(champ_en_un_point(centre,0.),champ_exterieur);
	    //toto.print("toto pout centre ");
	    //chp_sppl_dvs_sscp[1000]=null;
	    

	    rayon=rayon0;
	    //toto.divise_cst(2./3.*susceptibilite*champ_exterieur.x);
	    toto.divise_cst(susceptibilite*champ_exterieur.x);
	    return toto;
	} 
    }

    class barreau_cylindrique_infini extends bille_ou_barreau_cylindrique{
	face vue_de_face;
        point_y_z projections_du_rayon[]=new point_y_z[64];
        point_y_z projections_perp_au_rayon[]=new point_y_z[64];
	int iteration=0;
	double surf_extrem_elem=0;
	double surf_laterale_elem=0;
	champs_points_calcules chp_clc_barreau;
	double dI_axial_lateral_sur_rdphi[]=new double [64];
	double dI_ortho_radial_sur_dx;
	double rdphi;
	barreau_cylindrique_infini(point ctr1, double rayon1, double longueur1,double largeur1){
	    super();
	    susceptibilite=80;
	    centre=new point(ctr1);rayon=(int)rayon1;longueur=(int)longueur1;
	    facteur_fondamental=mu0_ou_un_sur_eps0/(4*pi);            ;
	    surf_extrem_elem=pi*rayon*rayon/(16*16);
	    rdphi=2*pi*rayon/64.;
	    double phi=-2*pi/64.,cosphi=0,sinphi=0;
	    for (int ifi=0;ifi<64;ifi++){	
		phi+=2*pi/64.;
		cosphi=Math.cos(phi);
		sinphi=Math.sin(phi);		
		projections_du_rayon[ifi]=new point_y_z(cosphi,sinphi);
		projections_perp_au_rayon[ifi]=new point_y_z(-sinphi,cosphi);
		//projections_du_rayon[ifi].print(" ifi "+ifi+" projections_du_rayon[ifi] ");
		dI_axial_lateral_sur_rdphi[ifi]=champ_exterieur.y*susceptibilite*sinphi/mu0_ou_un_sur_eps0;
		//dI_axial_lateral_sur_rdphi[ifi]=0.;
	    }
	    dI_ortho_radial_sur_dx=champ_exterieur.x*susceptibilite/mu0_ou_un_sur_eps0;
	    surf_laterale_elem=rayon*longueur/64.*pi/16;
	    vue_de_face=new face(" Vue de face ");	    	    
	    chp_clc_barreau=new champs_points_calcules();
	    chp_clc_barreau.remplis(false);
	    paint();
	    scale=(float)champ_exterieur.longueur();//pour la vue de face
	    vue_de_face.centre_deface.print(" centre_deface ");
	}
	public void elimine(){
	    vue_de_face.dispose();
	    vue_de_face=null;	    
	}
	boolean far_from_boarders(point dist){
	    return Math.abs(Math.abs(dist.y)-rayon)>distance_minimum_au_bord;
	}
	boolean est_dedans(point dist){
	    return true;	    
	}
	public void paint(){
	    
	    System.out.println("mmmmmmmmmmmmmm entree paint iter "+iter+" i_ens "+i_ens);
	    //if(deja_venu)
	    //	projections_du_rayon[1000]=null;
	    //deja_venu=true;
	    grp_c.setColor(Color.black);
	    grp_c.drawLine((int)(centre.x-500 ),(int)(centre.y-rayon),(int)(centre.x+500 ),(int)(centre.y-rayon));
	    grp_c.drawLine((int)(centre.x-500 ),(int)(centre.y+rayon),(int)(centre.x+500 ),(int)(centre.y+rayon));
       	    vecteur v=new vecteur(zer,zer);int x=0;
	    vue_de_face.dessine_de_face();
	    dessiner_les_champs(chp_clc_barreau,null,0.7);
	}
	point champ_en_un_point(point posit,double posit_z){
	    tutu.assigne_soustrait(posit,centre);
	    pt_xyz.assigne(posit,posit_z);
	    toto.assigne(champ_en_un_point_xyz(pt_xyz).extrait_point());
	    if(tutu.longueur_carre()<20)
		toto.print(" toto truc ");
	    return toto;
	}
	point_xyz champ_en_un_point_xyz(point_xyz posit_xyz){
	    tutu_xyz.assigne(0.,0.,0.);
	    titi_y_z.assigne(posit_xyz.y,posit_xyz.z);
	    for (int ifi=0;ifi<64;ifi++){
		toto_y_z.assigne(centre.y,0.);	
		toto_y_z.additionne_facteur(projections_du_rayon[ifi],(double)rayon);
		tata_y_z.assigne_soustrait(titi_y_z,toto_y_z);
		if(tata_y_z.longueur_carre()>1){
		    coco=tata_y_z.longueur_carre();
		    tutu_xyz.x-=(dI_ortho_radial_sur_dx*tata_y_z.scalaire(projections_du_rayon[ifi])/coco);//on devrait multiplier par , mais le fact.fondam. est mu0/2pi
		    tata_y_z.multiplie_cst(dI_axial_lateral_sur_rdphi[ifi]*rdphi/coco);
		    tata_y_z.rotation(0.,1.); 
		    tutu_xyz.additionne(0.,tata_y_z.y,tata_y_z.z);
		    //tata_y_z.print(" coco "+coco+" tata_y_z ");
		    //System.out.println(" ifi "+ifi+" dI_axial_lateral_sur_rdphi[ifi] "+dI_axial_lateral_sur_rdphi[ifi]+" dI_ortho_radial_sur_dx "+dI_ortho_radial_sur_dx);
		}
	    }
	    tutu_xyz.multiplie_cst(facteur_fondamental);
	    tutu_xyz.additionne(champ_exterieur_xyz);
	    return tutu_xyz;
	}
	class face extends Frame{
	    Graphics gr_deface;
	     point_y_z centre_deface;
	    int right_face=300+left_ens+i_ens*600,left_face=0+left_ens+i_ens*600,bottom_face=600+top_ens,top_face=300+top_ens;
	    public face(String s){
		super(s);	    
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
			    //while(occupied){
			    //}
			    if(vue_de_face!=null){
				vue_de_face.dispose();
				vue_de_face=null;
			    }
			    dispose();
			};
		    });		
		setVisible(true);
		setSize(right_face-left_face,bottom_face-top_face);
		setLocation(left_face,top_face);
		gr_deface=getGraphics();
		centre_deface=new point_y_z((double)(bottom_face-top_face)/2,(double)(right_face-left_face)/2);
		
	    }
	    void dessine_de_face(){
		gr_deface.setColor(Color.black);
		gr_deface.drawOval((int)centre_deface.z-rayon,(int)centre_deface.y-rayon,2*rayon,2*rayon);
		vecteur_y_z v_y_z=new vecteur_y_z(zer_y_z,zer_y_z);
		for (int ifi=0;ifi<64;ifi+=4){
		    toto_y_z.assigne_facteur(projections_du_rayon[ifi],(double)rayon);
		    toto_y_z.additionne(centre_deface);
		    toto_int=(int)Math.round(10*Math.abs(dI_axial_lateral_sur_rdphi[ifi]/dI_axial_lateral_sur_rdphi[16]));
		    if(dI_axial_lateral_sur_rdphi[ifi]>0)
			gr_deface.setColor(Color.red);
		    else
			gr_deface.setColor(Color.blue);
		    gr_deface.drawOval((int)toto_y_z.z-toto_int,(int)toto_y_z.y-toto_int,2*toto_int,2*toto_int);
		    titi_y_z.assigne_facteur(projections_perp_au_rayon[ifi],-1.);
		    v_y_z.assigne(titi_y_z,toto_y_z);
		    v_y_z.dessine(1.,1.,gr_deface,Color.orange);
		    v_y_z.print(" ifi "+ifi+" v_y_z ");
		}
		vecteur_y_z vecteur_chp_y_z=new vecteur_y_z(zer_y_z,zer_y_z);
		//position_y_z.assigne((double)top_face,(double)(left_face));
		position_y_z.assigne(0.,0.);
		point_xyz vvv=new point_xyz(0.,0.,0.); 
		for (int kk=0;kk<2000;kk++){
		    tata_y_z.assigne_soustrait(position_y_z,centre_deface);
		    if(Math.abs(tata_y_z.longueur()-rayon)>distance_minimum_au_bord){
			vecteur_chp_y_z.pnt.assigne(position_y_z);
			toto_y_z.assigne(position_y_z.y-centre_deface.y+centre.y,position_y_z.z-centre_deface.z);
			//toto.assigne(0.,toto_y_z.y);
			pt_xyz.assigne(0.,toto_y_z);
			//vvv.assigne(champ_en_un_point_xyz(toto,toto_y_z.z));
			vvv.assigne(champ_en_un_point_xyz(pt_xyz));
			vecteur_chp_y_z.chp=vvv.extrait_point_y_z();
			vecteur_chp_y_z.dessine(scale,fct_zm_sspl,gr_deface,Color.green);
			if(kk/10*10==kk)
			    vecteur_chp_y_z.print(" kkùùùù "+kk+" vecteur_chp_y_z ");
			if(kk/100*100==kk)
			    System.out.println(" centre_deface.y "+centre_deface.y+" centre.y "+centre.y+" centre_deface.z "+centre_deface.z); 
			toto_y_z.assigne_soustrait(centre_deface,position_y_z);
			if(toto_y_z.longueur_carre()<40*40)	
			    System.out.println("***************** position_y_z.y-centre_deface.y+centre.y "+(position_y_z.y-centre_deface.y+centre.y));	
			}
		    position_y_z.z+=20;
		    if(position_y_z.z>right_face-left_face){
			position_y_z.z=0.;
			position_y_z.y+=20;
			if(position_y_z.y>bottom_face-top_face)
			    break;
		    }
		}
		/*
		if(i_ens==1){
		  System.out.println(" bottom_face "+bottom_face+" top_face "+top_face+" left_face "+left_face+" right_face "+right_face);
		  projections_du_rayon[1000]=null;
		}
		*/	    
	    }
	}	
    }
    class barreau_cylindrique extends bille_ou_barreau_cylindrique{
	side vue_de_cote;
	point_xyz chp_xyz;
	point_xyz chp_excit;
        point_y_z projections_du_rayon[]=new point_y_z[64];
        point_y_z projections_perp_au_rayon[]=new point_y_z[64];
	int iteration=0;
	solution_eq_lineaires solution_champs,solution_sigma_ou_I_H,solution_sigma_ou_I_V;
	double I_H_extremite_plus[]=new double [64];
	double I_H_laterale[]=new double [64];
	double I_H_laterale_ref[]=new double [64];
	double I_V_laterale_ref[]=new double [64];
	double I_H_extremite_plus_ref[]=new double [64];
	double sig_recalculee[]=new double [64];
	double sig_recalculee_ref[]=new double [64];
	double sig_recal[]=new double [64];
	double sig_recal_ref[]=new double [64];
	double sig_lissee_32[]=new double [64];
	double I_V_laterale[]=new double [64];
	double I_lateral_ref[]=new double [64];
	double sigma_V_laterale[][]=new double [64][64];
	double sigma_V_extremite_plus[][]=new double [64][64];
	double sigma_V_laterale_recalculee[][]=new double [64][64];
	double sigma_V_extremite_plus_recalculee[][]=new double [64][64];
	champs_points_calcules chp_clc_barreau;
	double demie_longueur_carree=0,dfi=2*pi/64;int iLLrr=0;
	double rdphi,eprime0=0,eprime0_prec=0,aprime_prec=0,sigma_ou_I_P=0,I_H_V=0,rapport_sigma_ou_I_Hs_en_P=0,E_charges_en_P=0,E_total_en_P=0;
	double xy=0,xx=0,x4=0,yy=0,y4=0;
	double derivee_par_rapport_aux_laterales[]=new double[64];
	double surf_extremite[]=new double[64];
	double coefficient_lateral=0.,longueur_segments_z=0.;
	double polynome_x[]=new double[4];
	double polynome_y[]=new double[4];
	double coef[]=new double[4];
	double surf_lat_elem;
	boolean exclure_phi[]=new boolean[64];
	double coefficients_sigma_ou_I_H[]=new double[4];
	double coefficients_sigma_ou_I_V[]=new double[4];
	int iterat=0;double chi_2=0.,chp_y_moyen_prop_a_r=0.;
	int espacement_r=24,limite_au_bord=2;
	boolean pas_laterales=false,eliminer_extremite_plus=false,eliminer_extremites=false,exclure_phi_nul_ou_63=false;	
	barreau_cylindrique(point ctr1, double rayon1, double longueur1,double largeur1){
	    super();
	    centre=new point(ctr1);rayon=(int)rayon1;longueur=(int)longueur1;
	    demie_longueur_carree=longueur/2.*longueur/2.;
	    champ_en_un_point=new point(zer);
	    if(i_ens==0)
		coef[1000]=0;
	    facteur_fondamental=mu0_ou_un_sur_eps0/(4*pi); 
	    //solution_champs=new solution_eq_lineaires(4);
	    //solution_sigma_ou_I_H=new solution_eq_lineaires(3);
	    //solution_sigma_ou_I_V=new solution_eq_lineaires(3);
	    rdphi=2*pi*rayon/64.;
	    if(i_ens==1){
		susceptibilite=80.;
		alpha_estime=0.33;
	    }else{
		susceptibilite=5.;
		alpha_estime=0.33;
	    }
	    double phi=-pi/64.,cosphi=0,sinphi=0;
	    for (int ifi=0;ifi<64;ifi++){	
		phi+=2*pi/64.;
		cosphi=Math.cos(phi);
		sinphi=Math.sin(phi);		
		projections_du_rayon[ifi]=new point_y_z(cosphi,sinphi);
		projections_perp_au_rayon[ifi]=new point_y_z(-sinphi,cosphi);
		exclure_phi[ifi]=false;
		if(i_ens==1)
		    vecteur_unite_xyz[ifi].assigne(0.,projections_perp_au_rayon[ifi]);
	    }
	    chp_xyz=new point_xyz(0.,0.,0.);
	    chp_excit=new point_xyz(0.,0.,0.);
	    surf_lat_elem=longueur*2.*pi*rayon /64.;
	    double dr=rayon/64.,r=0.,r_p=0.;
	    for (int ir=0;ir<64;ir++){
		r+=dr;
		surf_extremite[ir]=pi*(r*r-r_p*r_p);
		r_p=r;
	    }
	    chp_xyz=new point_xyz(0.,0.,0.);
	    for (int i=0;i<64;i++){
		I_H_extremite_plus[i]=0.;
		I_H_laterale[i]=0.;
		I_H_laterale_ref[i]=0.;
		I_V_laterale_ref[i]=0.;
		I_V_laterale[i]=0.;
		I_H_extremite_plus_ref[i]=0.;
		for (int j=0;j<64;j++){
		    sigma_V_laterale[i][j]=0.;
		    sigma_V_extremite_plus[i][j]=0.;
		}
	    }
	    if(champ_exterieur.x>0.0001){
		composante_horiz_pas_vert=true;
		if(i_ens==0){
		    //calcule_charges_circulaires(I_H_extremite_plus,I_H_laterale);
		    coco=susceptibilite/(1.+susceptibilite*alpha_estime)*champ_exterieur.x/mu0_ou_un_sur_eps0;
		    for (int i=0;i<64;i++)
			I_H_extremite_plus[i]=coco;
		    calcule_charges_H();
		}else{
		    susceptibilite=10;
		    alpha_estime=0.05;
		    coco=susceptibilite/(1.+susceptibilite*alpha_estime)*champ_exterieur.x/mu0_ou_un_sur_eps0;
		    for (int i=0;i<64;i++){
			I_H_laterale[i]=coco;
			vecteur_unite_xyz[i].assigne(1.,0.,0.);
			if(i==0||i==7||i==15||i==23||i==31||i==56)
		    System.out.println(" i "+i+" I_lateral "+(float)I_H_laterale[i]+" I_H_extremite_plus "+(float)I_H_extremite_plus[i]);
		    }

		    montee_en_douceur(false);
		}
	    }
	    if(champ_exterieur.y>0.0001){
		composante_horiz_pas_vert=false;
		if(i_ens==0){
		    for (int ifi=0;ifi<64;ifi++){
			vecteur_unite_xyz[ifi].assigne(0.,projections_du_rayon[ifi]);
			coco=subject.eps0*champ_exterieur_xyz.scalaire(vecteur_unite_xyz[ifi])*susceptibilite/(1.+susceptibilite*alpha_estime);
			if(ifi==0||ifi==7||ifi==15||ifi==23||ifi==31||ifi==56)
			    vecteur_unite_xyz[ifi].print(" ifi "+ifi+" coco "+coco+" coco*mu0 "+(float)(coco*mu0_ou_un_sur_eps0)+" vecteur_unite_xyz[ifi] ");
			for (int iL=0;iL<64;iL++){
			    sigma_V_laterale[ifi][iL]=coco;
			}
		    }		    
		    calcule_charges_V();
		}else{
		    limite_au_bord=0;
		    susceptibilite=10;
		    for (int ifi=0;ifi<64;ifi++){
			vecteur_unite_xyz[ifi].assigne(0.,projections_perp_au_rayon[ifi]);
			vecteur_unite_xyz[ifi].multiplie_cst(-1.);//^^
			I_V_laterale[ifi]=susceptibilite/(1.+susceptibilite*alpha_estime)*champ_exterieur_xyz.scalaire(vecteur_unite_xyz[ifi])/mu0_ou_un_sur_eps0;
			if(ifi==0||ifi==7||ifi==15||ifi==23||ifi==31||ifi==56){
			    System.out.println(" ifi "+ifi+" sig_I_V_laterale "+(float)I_V_laterale[ifi]+" mu0*sig_V_laterale "+(float)(I_V_laterale[ifi]*mu0_ou_un_sur_eps0));
			}
		    }
		    montee_en_douceur(true);
		}
	    }
	    pt_xyz.assigne(centre,0.);
	    champ_au_centre.assigne(champ_en_un_point_xyz(pt_xyz).extrait_point());
	    eprime0=champ_au_centre.x;
	    //verifie_coefficients();
	    vue_de_cote=new side(" Vue de cote ");	    	    
	    chp_clc_barreau=new champs_points_calcules();
	    chp_clc_barreau.remplis(false);
	    paint();
	    //if(i_ens==1)
	    //projections_du_rayon[1000]=null;
	    scale=(float)champ_exterieur.longueur();//pour la vue de cote
	    vue_de_cote.centre_de_cote.print(" centre_de_ cote ");
	}
	void montee_en_douceur(boolean vertic){
	    boolean OK=false;double d_suscept=10.;
	    for (int i=0;i<10;i++){
		if(i!=0)
		    susceptibilite+=d_suscept;
		if(vertic)
		    OK=calcule_courants_rectangulaires();
		else
		    OK=calcule_courants_circulaires(i);
		System.out.println(" i "+i+" susceptibilite "+susceptibilite+" d_suscept "+d_suscept+" OK "+OK);
		if(OK){
		    double somme=0.;
		    for (int k=0;k<64;k++)
			if(!vertic)
			    I_H_laterale_ref[k]=I_H_laterale[k];
			else
			    I_V_laterale_ref[k]=I_V_laterale[k];
		    System.out.println(" somme laterale "+(float)somme);
		    if(susceptibilite>79.9){
			if(vertic)
			    OK=calcule_courants_rectangulaires();
			else
			    OK=calcule_courants_circulaires(i);
			break;
		    }
		}else{
		    susceptibilite-=d_suscept;
		    d_suscept/=2.;
		    if(!vertic){
			for (int ir=limite_au_bord;ir<64;ir++)
			    I_H_extremite_plus[ir]=I_H_extremite_plus_ref[ir];
			for (int iL=limite_au_bord;iL<64-limite_au_bord;iL++)
			    I_H_laterale[iL]=I_H_laterale_ref[iL];
		    }else{
			for (int iL=limite_au_bord;iL<64-limite_au_bord;iL++)
			    I_V_laterale[iL]=I_V_laterale_ref[iL];
		    }
		}
	    }
	}
	void  calcule_charges_H(){
	    for (int itt=0;itt<3;itt++){
		System.out.println();
		System.out.println(" itt "+itt);
		System.out.println();
		pt_xyz.assigne(centre,0.);
		champ_au_centre.assigne(champ_en_un_point_xyz(pt_xyz).extrait_point());
		champ_au_centre.print_sl(" champ_au_centre. ");
		centre.print_sl(" centre ");
		double dix=centre.x-longueur*(0.5+1./128.-(float)limite_au_bord/64.);
		ecrire_champ=false;
		eliminer_extremite_plus=true;
		for (int iL=limite_au_bord;iL<64-limite_au_bord;iL++){
		    //ecrire_champ=iL==31;
		    dix+=longueur/64.;
		    coord[iL].assigne(dix,centre.y+rayon);
		    pt_xyz.assigne(coord[iL],0.);
		    field[iL].assigne(champ_en_un_point_xyz(pt_xyz));
		    sig_recalculee[iL]=susceptibilite/(1.+susceptibilite*0.5)*field[iL].y/mu0_ou_un_sur_eps0;
		}
		for (int iL=0;iL<64;iL++){
		    //if(iL<=limite_au_bord||iL==7||iL==15||iL==23||iL==31||iL==32||iL==56||iL>=63-limite_au_bord){
		    if(iL<=7||iL==7||iL==15||iL==23||iL==31||iL==32||iL==56||iL>=56){
			System.out.println(" iL "+iL+" I_H_laterale "+(float)I_H_laterale[iL]+" recalcule "+(float)sig_recalculee[iL]);
			field[iL].print(" field ");
		    }
		}
		double somme_ext=met_a_jour_evalue_bords_lat(I_H_laterale,sig_recalculee);
		eliminer_extremite_plus=false;
		exclure_phi_nul_ou_63=false;
		double r=-rayon/128.,dr=rayon/64.;	    
		for (int ir=0;ir<64-limite_au_bord;ir++){
		    r+=dr;
		    eliminer_extremite_plus=true;
		    pt_xyz.assigne(centre.x+longueur/2,centre.y+r,0.);
		    field[ir].assigne(champ_en_un_point_xyz(pt_xyz));
		    eliminer_extremite_plus=false;
		    vec_un_xyz.assigne(1.,0.,0.);
		    sig_recalculee[ir]=sigma_ou_I(vec_un_xyz,field[ir],I_H_extremite_plus[ir]);
		    if(ir<limite_au_bord||ir==7||ir==15||ir==23||ir==31||ir==56||ir>=63-limite_au_bord){
			System.out.println(" ir "+ir+" sig_laterale "+(float)I_H_extremite_plus[ir]+" recalcule "+(float)sig_recalculee[ir]+" alphaa "+(float)alphaa);
			field[ir].print(" coco "+(float)coco+" cici "+(float)cici+" sig/(2*eps0) "+(float)(mu0_ou_un_sur_eps0*I_H_extremite_plus[ir]/2.)+" field ");
		    }
		    alpha[ir]=alphaa;
		}
		/*
		  for (int ir=0;ir<64;ir++)
		  if(ir<limite_au_bord||ir==7||ir==15||ir==23||ir==31||ir==56||ir>=63-limite_au_bord){
		  System.out.println(" ir "+ir+" sig_laterale "+(float)I_H_extremite_plus[ir]+" recalcule "+(float)sig_recalculee[ir]+" alpha[ir] "+(float)alpha[ir]);
		  field[ir].print(" field[ir] ");
		  }
		*/
		double somme=met_a_jour_et_evalue_bords_ext(I_H_extremite_plus,sig_recalculee);
		//exclure_phi_nul_ou_63=true;
		pt_xyz.assigne(centre,0.);
		champ_au_centre.assigne(champ_en_un_point_xyz(pt_xyz).extrait_point());
		champ_au_centre.print_sl(" somme "+(float)somme+" champ_au_centre. ");
	    }
	}
	boolean calcule_courants_rectangulaires(){
	    double somme_rec=0.;
	    for (int ib=0;ib<3;ib++){
		System.out.println();
		System.out.println(" ib "+ib+" susceptibilite "+(float)susceptibilite);
		System.out.println();
		pt_xyz.assigne(centre,0.);
		champ_au_centre.assigne(champ_en_un_point_xyz(pt_xyz).extrait_point());
		champ_au_centre.print_sl(" champ_au_centre. ");
		centre.print_sl(" centre ");
		point_xyz chp_later=new point_xyz(0.,0.,0.);
		for (int ifi=0;ifi<64;ifi++){
		    exclure_phi[ifi]=true;
		    pt_xyz.assigne(centre.x,centre.y+rayon*projections_du_rayon[ifi].y,rayon*projections_du_rayon[ifi].z);
		    //if(ifi==15||ifi==48)
		    //ecrire_champ=true;
		    chp_later.assigne(champ_en_un_point_xyz(pt_xyz));
		    //ecrire_champ=false;
		    exclure_phi[ifi]=true;
		    sig_recalculee[ifi]=sigma_ou_I(vecteur_unite_xyz[ifi],chp_later,I_V_laterale[ifi]);
		    exclure_phi[ifi]=false;
		    if(ifi==0||ifi==7||ifi==15||ifi==23||ifi==31||ifi==56){
			//if(ifi<=15){
			System.out.println(" ifi "+ifi+" I_V_lateral "+(float)I_V_laterale[ifi]+" recalcule "+(float)sig_recalculee[ifi]+" alphaa "+(float)alphaa);
			chp_later.print("coco "+(float)coco+" cici "+(float)cici+" mu0*sig/2 "+(float)(mu0_ou_un_sur_eps0*I_V_laterale[ifi]/2.)+"chp_later ");
			vecteur_unite_xyz[ifi].print(" vecteur_unite_xyz[ifi] ");
		    }
		}
		//exclure_phi[2000]=false;
		double somme=0.;
		for (int ifi=0;ifi<64;ifi++){
		    I_V_laterale[ifi]=(sig_recalculee[ifi]+I_V_laterale[ifi])/2.;
		    somme+=Math.abs(I_V_laterale[ifi]);
		}
		cece=Math.abs((somme_rec-somme)/(somme_rec+somme));
		somme_rec=somme;
		pt_xyz.assigne(centre,0.);		
		champ_au_centre.assigne(champ_en_un_point_xyz(pt_xyz).extrait_point());
		champ_au_centre.print_sl(" somme "+(float)somme+" champ_au_centre. ");
		if(ib!=0&&cece<0.05)
		    return true;
	    }
	    return (cece<0.05);
	}
	boolean calcule_courants_circulaires(int passage){
	    System.out.println("calcule_courants_circulaires");
	    int ibmax=5;
	    double somme_courants_lateraux=0.,somme_courants_lateraux_rec=0.;
	    double somme_courants_ext=0.,somme_courants_ext_rec=0.;
	    for (int ib=0;ib<ibmax;ib++){
		System.out.println();
		System.out.println(" $$$ib "+ib+" susceptibilite "+susceptibilite);
		System.out.println();
		somme_courants_lateraux=0.;
		//ecrire_champ=true;
		somme_courants_ext=0.;
		//ecrire_champ=true;
		double dr=rayon/64.,r=-dr/2.;
		for (int ir=0;ir<64-limite_au_bord;ir++){
		    r+=dr;
		    posit.assigne(centre.x+longueur/2,centre.y+r);
		    if(i_ens==0)
			eliminer_extremite_plus=true;
		    pt_xyz.assigne(posit,0.);
		    chp_xyz.assigne(champ_en_un_point_xyz(pt_xyz));
		    eliminer_extremite_plus=false;
		    vec_un_xyz.assigne(0.,1.,0.);
		    sig_recal[ir]=susceptibilite/(1.+susceptibilite*0.5)*chp_xyz.scalaire(vec_un_xyz)/mu0_ou_un_sur_eps0;
		    somme_courants_ext+=sig_recal[ir];
		    if(ir==0||ir==7||ir==15||ir==23||ir==31||ir==56){
			System.out.println(" ir "+ir+" sig_extrem "+(float)I_H_extremite_plus[ir]+" recalcule "+(float)sig_recal[ir]);
		    }
		}
		ecrire_champ=false;
		//double dix=centre.x-longueur/2.-(double)longueur/128.+longueur/64.*limite_au_bord;
		double dix=centre.x-longueur*(0.5+1./128.-(float)limite_au_bord/64.);
		for (int iL=limite_au_bord;iL<64-limite_au_bord;iL++){
		    dix+=longueur/64.;
		    iLLrr=iL;
		    //ecrire_champ=(iLLrr==7||iLLrr==56);
		    //ecrire_champ=ib==1&&(iL==7||iL==56);
		    pt_xyz.assigne(dix,centre.y+rayon,0.);	
		    chp_xyz.assigne(champ_en_un_point_xyz(pt_xyz));
		    ecrire_champ=false;			
		    vec_un_xyz.assigne(1.,0.,0.);
		    sig_recalculee[iL]=sigma_ou_I(vec_un_xyz,chp_xyz,I_H_laterale[iL]);
		    somme_courants_lateraux+=sig_recalculee[iL];
		    if(iL<limite_au_bord||iL==7||iL==15||iL==23||iL==31||iL==56||iL==63){
			System.out.println(" iL "+iL+" I_H_lat "+(float)I_H_laterale[iL]+" recal "+(float)sig_recalculee[iL]+" cici "+(float)cici+" alph "+(float)alphaa);
			//System.out.println(" coco "+(float)coco+" cici "+(float)cici+" mu0*sig/2 "+(float)(mu0_ou_un_sur_eps0*I_H_laterale[iL]/2));
			//chp_xyz.print(" chp_xyz ");
		    }
		}
		exclure_phi_nul_ou_63=false;
		somme_courants_ext=met_a_jour_et_evalue_bords_ext(I_H_extremite_plus,sig_recal);
		somme_courants_lateraux=met_a_jour_evalue_bords_lat(I_H_laterale,sig_recalculee);
		coco=(somme_courants_lateraux_rec-somme_courants_lateraux)/(somme_courants_lateraux_rec+somme_courants_lateraux);
		for (int iL=0;iL<=limite_au_bord;iL++)
		    System.out.println(" iL "+iL+" I_H_laterale "+(float)I_H_laterale[iL]);		System.out.println(" somme_courants_lateraux "+(float)somme_courants_lateraux+" somme_courants_lateraux_rec "+(float)somme_courants_lateraux_rec+" rapport "+(float)coco);		
		coco=(somme_courants_ext_rec-somme_courants_ext)/(somme_courants_ext_rec+somme_courants_ext);
		for (int iL=63-limite_au_bord;iL<64;iL++)
		    System.out.println(" iL "+iL+" sig_I_H_laterale "+(float)I_H_laterale[iL]);
		System.out.println(" somme_courants_ext "+(float)somme_courants_ext+" somme_courants_ext_rec "+(float)somme_courants_ext_rec+" rapport "+(float)coco);		
		    


		pt_xyz.assigne(centre,0.);	
     		champ_au_centre.assigne(champ_en_un_point_xyz(pt_xyz).extrait_point());
		champ_au_centre.print("ib "+ib+" champ_au_centre ");
		cece=Math.abs((somme_courants_lateraux_rec-somme_courants_lateraux)/(somme_courants_lateraux_rec+somme_courants_lateraux));
		//cici=Math.abs((somme_courants_ext_rec-somme_courants_ext)/(somme_courants_ext_rec+somme_courants_ext));
		//if(ib!=0&&cece<0.1&&cici<0.2)
		if(ib>1&&cece<0.03)
		    return true;
		if(ib!=ibmax-1){
		   somme_courants_lateraux_rec=somme_courants_lateraux;
		   somme_courants_ext_rec=somme_courants_ext;
		}
		//coefficients_sigma_ou_I_V=solution_sigma_ou_I_V.resout();
		//System.out.println(" coefficients_sigma_ou_I_V 0 "+(float)coefficients_sigma_ou_I_V[0]+" 1 "+(float)coefficients_sigma_ou_I_V[1]+" 2 "+(float)coefficients_sigma_ou_I_V[2]);
	    }
	    cece=Math.abs((somme_courants_lateraux_rec-somme_courants_lateraux)/(somme_courants_lateraux_rec+somme_courants_lateraux));
	    //cici=Math.abs((somme_courants_ext_rec-somme_courants_ext)/(somme_courants_ext_rec+somme_courants_ext));

	    //return (cece<0.1&&cici<0.2);
	    return (cece<0.05);

	}
	void calcule_charges_V(){
	    point position=new point(zer);
	    System.out.println("calcule_courants_V");
	    for (int ib_q=0;ib_q<3;ib_q++){
		System.out.println();
		System.out.println(" $$$ib_q "+ib_q);
		System.out.println();
		//ecrire_champ=true;
		for (int ifi=0;ifi<64;ifi++){
		    double dr=rayon/64.,r=-dr/2.;
		    for (int ir=0;ir<64-limite_au_bord;ir++){
			r+=dr;
			pt_xyz.assigne(centre.x+longueur/2,centre.y+r*projections_du_rayon[ifi].y,r*projections_du_rayon[ifi].z);
			eliminer_extremite_plus=true;
			chp_xyz.assigne(champ_en_un_point_xyz(pt_xyz));
			eliminer_extremite_plus=false;
			eliminer_extremites=false;
			vec_un_xyz.assigne(1.,0.,0.);
			sigma_V_extremite_plus_recalculee[ifi][ir]=susceptibilite/(1.+susceptibilite*0.5)*chp_xyz.scalaire(vec_un_xyz)/mu0_ou_un_sur_eps0;
			if(ir==15&&((ifi==0||ifi==7||ifi==15||ifi==24||ifi==31||ifi==56)))
			    System.out.println(" ir "+ir+" ifi "+ifi+" sigma_V_extremite_plus "+(float)sigma_V_extremite_plus[ifi][ir]+" recalcule "+(float)sigma_V_extremite_plus_recalculee[ifi][ir]);
		    }
		}
		double somme=0.;
		for (int ifi=0;ifi<64;ifi++){
		    somme=met_a_jour_et_evalue_bords_ext(sigma_V_extremite_plus[ifi],sigma_V_extremite_plus_recalculee[ifi]);
		    if(ifi==0||ifi==7||ifi==31||ifi==56)
			System.out.println(" ifi "+ifi+" somme_extremite_plus "+(float)somme);
		}

		pt_xyz.assigne(centre,0.);	
		champ_au_centre.assigne(champ_en_un_point_xyz(pt_xyz).extrait_point());
		champ_au_centre.print(" champ_au_centre. ");
		ecrire_champ=false;
		for (int ifi=0;ifi<64;ifi++){
		    double dix=centre.x-longueur/2.-(double)longueur/128.+longueur/64.*limite_au_bord;
		    for (int iL=limite_au_bord;iL<64-limite_au_bord;iL++){
			dix+=longueur/64.;
			iLLrr=iL;
			//ecrire_champ=(iLLrr==7||iLLrr==56)&&ifi=7;
			pt_xyz.assigne(dix,centre.y+rayon*projections_du_rayon[ifi].y,rayon*projections_du_rayon[ifi].z);
			//ecrire_champ=ib_q==1&&(iL==7||iL==56);
			chp_xyz.assigne(champ_en_un_point_xyz(pt_xyz));
			ecrire_champ=false;
			sigma_V_laterale_recalculee[ifi][iL]=sigma_ou_I(vecteur_unite_xyz[ifi],chp_xyz,sigma_V_laterale[ifi][iL]);
			if((iL==7||iL==56)&&(ifi==0||ifi==7||ifi==15||ifi==24||ifi==31||ifi==56)){
			//if((iL<limite_au_bord||iL==7||iL==15||iL==23||iL==31||iL==56||iL==63)&&((ifi<limite_au_bord||ifi==7||ifi==15||ifi==23||ifi==31||ifi==56))){
			    System.out.println(" ifi "+ifi+" iL "+iL+" sigma_V_laterale "+(float)sigma_V_laterale[ifi][iL]+" recalcule "+(float)sigma_V_laterale_recalculee[ifi][iL]);
			    System.out.println(" coco "+(float)coco+" cici "+(float)cici+" mu0*sig/2. "+(float)(mu0_ou_un_sur_eps0*sigma_V_laterale[ifi][iL]/2));
			    chp_xyz.print(" alphaa "+(float)alphaa+" chp_xyz ");
			}
		    }
		}
		for (int ifi=0;ifi<64;ifi++){
		    somme=met_a_jour_evalue_bords_lat(sigma_V_laterale[ifi],sigma_V_laterale_recalculee[ifi]);
		    if(ifi==0||ifi==7||ifi==31||ifi==56)
			System.out.println(" ifi "+ifi+" somme_laterale "+(float)somme);
		}
		exclure_phi_nul_ou_63=false;
		pt_xyz.assigne(centre,0.);			
		champ_au_centre.assigne(champ_en_un_point_xyz(pt_xyz).extrait_point());
		champ_au_centre.print("ib_q "+ib_q+" champ_au_centre "); 
		
		//coefficients_sigma_ou_I_V=solution_sigma_ou_I_V.resout();
		System.out.println(" coefficients_sigma_ou_I_V 0 "+(float)coefficients_sigma_ou_I_V[0]+" 1 "+(float)coefficients_sigma_ou_I_V[1]+" 2 "+(float)coefficients_sigma_ou_I_V[2]);
	    }
	}
	/*
	void lissage(double[] entrees, int imin, int imax,boolean trente_2,boolean fit_par_sinus){
	    double secm[]=new double[2];double coeff[]=new double[2];double somme1=0.;
;
	    double moyenne=0.;
	    for (int i=imin;i<=imax;i++)
		moyenne+=entrees[i];
	    System.out.println(" somme "+(float)moyenne);
	    moyenne/=(imax-imin+1);
	    if(!fit_par_sinus){
		if(trente_2&&sol_sigma_ou_I_32==null||!trente_2&&sol_sigma_ou_I_64==null){
		    double matric[][]=new double[2][2];
		    for (int j=0;j<2;j++)
			for (int k=0;k<2;k++)
			    matric[j][k]=0.;
		    for (int i=imin;i<=imax;i++){
			if(trente_2){
			    matric[0][0]+=sin_32[i]*sin_32[i];
			    matric[0][1]+=sin_32[i]*cos_32[i];
			    matric[1][1]+=cos_32[i]*cos_32[i];
			}else{
			    matric[0][0]+=1;
			    matric[0][1]+=sin_64[i];
			    matric[1][1]+=sin_64[i]*sin_64[i];
			}
		    }
		    matric[1][0]+=matric[0][1]; 
		    for (int j=0;j<2;j++)
			System.out.println(" j "+j+" matric "+matric[j][0]+" "+matric[j][1]); 
		    if(trente_2)
			sol_sigma_ou_I_32=new solution_eq_lineaires(2,matric,secm);
		    else
			sol_sigma_ou_I_64=new solution_eq_lineaires(2,matric,secm);
		}
		for (int j=0;j<2;j++)
		    secm[j]=0.;
		if(trente_2){
		    for (int i=imin;i<=imax;i++){
			secm[0]+=entrees[i]*sin_32[i];
			secm[1]+=entrees[i]*cos_32[i];
		    }
		    sol_sigma_ou_I_32.assigne_secmem(secm);
		    coeff=sol_sigma_ou_I_32.resout();
		    for (int i=imin;i<=imax;i++){
			sig_lissee_32[i]=coeff[0]*sin_32[i]+coeff[1]*cos_32[i];
			somme1+=sig_recalculee[i];
		    }
		}else{
		    for (int i=imin;i<=imax;i++){
			secm[0]+=entrees[i];
			secm[1]+=entrees[i]*sin_64[i];
		    }
		    sol_sigma_ou_I_64.assigne_secmem(secm);
		    coeff=sol_sigma_ou_I_64.resout();
		    for (int i=imin;i<=imax;i++){
			sig_lissee_64[i]=coeff[0]+coeff[1]*sin_64[i];
			somme1+=sig_recalculee[i];
		    }
		}
		System.out.println(" coeff[0] "+(float)coeff[0]+" coeff[1] "+(float)coeff[1]+" somme1 "+somme1);
	    }else{
		secm[0]=0.;double aa=0.;
		for (int i=imin;i<=imax;i++){
			secm[0]+=entrees[i]*sin_32_pi[i];
			aa+=sin_32_pi[i]*sin_32_pi[i];
		}
		coeff[0]=secm[0]/aa;
		for (int i=imin;i<=imax;i++){
			sig_lissee_32[i]=coeff[0]*sin_32_pi[i];
			somme1+=sig_lissee_32[i];
		}
		System.out.println(" coeff[0] "+(float)coeff[0]+" somme1 "+somme1+" sig_lissee_32[7] "+(float)sig_lissee_32[7]);
	    }
	}
	*/
	double met_a_jour_et_evalue_bords_ext(double[] sig_ext,double[] sig_ext_rec){
	    cici=0.;
	    for (int ir=0;ir<64-limite_au_bord;ir++){
		sig_ext[ir]=(sig_ext[ir]+sig_ext_rec[ir])/2.;
		cici+=sig_ext[ir];
	    }

	    for (int i=64-limite_au_bord;i<64;i++){
		sig_ext[i]=2*sig_ext[i-1]-sig_ext[i-2];
		//System.out.println("i "+i+" sig_ext[i] "+(float)sig_ext[i]+" sig[i-1] "+(float)sig_ext[i-1]+" sig[i-2] "+sig_ext[i-2]); 
	    }
	    return cici;
	}
	double  met_a_jour_evalue_bords_lat(double[] sig_lat,double[] sig_lat_rec){
	    cici=0;
	    for (int iL=limite_au_bord;iL<64-limite_au_bord;iL++){
		//sig_lat[iL]=(sig_lat[iL]+sig_lat_rec[iL])/2.;
		sig_lat[iL]=sig_lat_rec[iL];
		cici+=sig_lat[iL];
	    }
	    for (int iL=64-limite_au_bord;iL<64;iL++){
		sig_lat[iL]=2*sig_lat[iL-1]-sig_lat[iL-2];
		//System.out.println("iL "+iL+" sig_lat[iL] "+(float)sig_lat[iL]+" sig_lat[i-1] "+(float)sig_lat[iL-1]+" sig_lat[iL-2] "+(float)sig_lat[iL-2]); 
	    }
	    for (int iL=limite_au_bord-1;iL>=0;iL--){
		sig_lat[iL]=2*sig_lat[iL+1]-sig_lat[iL+2];
		//System.out.println("iL "+iL+" sig_lat[iL] "+(float)sig_lat[iL]+" sig[iL+1] "+(float)sig_lat[iL+1]+" sig[iL+2] "+(float)sig_lat[iL+2]); 
	    }
	    return cici;
	}
	void verifie_coefficients(){
	    point decala=new point(1.,1.);
	    decala.assigne(1.,0.);
	    compare_champs(decala);
	    decala.assigne(2.,0.);
	    compare_champs(decala);
	    decala.assigne(3.,0.);
	    compare_champs(decala);
	    decala.assigne(4.,0.);
	    compare_champs(decala);
	    decala.assigne(2.,2.);
	    compare_champs(decala);
	    decala.assigne(0.,1.);
	    compare_champs(decala);
	    decala.assigne(0.,2.);
	    compare_champs(decala);
	    decala.assigne(5.,0.);
	    compare_champs(decala);
	    decala.assigne(0.,5.);
	    compare_champs(decala);
	    decala.assigne(5.,5.);
	    compare_champs(decala);
	    decala.assigne(10.,0.);
	    compare_champs(decala);
	    decala.assigne(0.,10.);
	    compare_champs(decala);
	    decala.assigne(10.,10.);
	    compare_champs(decala);
	    decala.assigne(20.,0.);
	    compare_champs(decala);
	    decala.assigne(0.,20.);
	    compare_champs(decala);
	    decala.assigne(20.,20.);
	    compare_champs(decala);
	    decala.assigne(50.,0.);
	    compare_champs(decala);
	    decala.assigne(0.,50.);
	    compare_champs(decala);
	    decala.assigne(50.,50.);
	    compare_champs(decala);
	    decala.assigne(90.,0.);
	    compare_champs(decala);
	    decala.assigne(0.,70.);
	    compare_champs(decala);
	    decala.assigne(90.,70.);
	    compare_champs(decala);

	}
	void calcule_puissances(point decl){
	    xy=decl.x*decl.y;
	    xx=decl.x*decl.x;
	    x4=xx*xx;
	    yy=decl.y*decl.y;
	    y4=yy*yy;
	    polynome_x[0]=(xx-yy/2.);
	    polynome_x[1]=(x4+y4/2.-3*xx*yy);
	    polynome_x[2]=xx*x4-yy*y4/2.-15/2.*xy*xy*(xx-yy);
	    polynome_x[3]=x4*x4-14*xy*xy*(x4+y4)+35*x4*y4+y4*y4/2;
	    polynome_y[0]=-xy;
	    polynome_y[1]=-2.*xy*(xx-yy);
	    polynome_y[2]=xy*(-3*(x4+y4)+10*xx*yy);
	    polynome_y[3]=4*xy*(-x4*xx+yy*y4+7*xy*xy*(xx-yy));
	}
	void compare_champs(point dec){
	    toto.assigne_additionne(centre,dec);
	    pt_xyz.assigne(toto,0.);
	    tutu.assigne(champ_en_un_point_xyz(pt_xyz).extrait_point());
	    System.out.println("µµµµµµµ dec.x "+dec.x+"dec.y "+dec.y);
	    System.out.println("calcule .x "+tutu.x+" .y "+tutu.y+" dif_x "+(float)(tutu.x-eprime0));
	    calcule_puissances(dec);
	    toto.assigne(eprime0,0.);
	    for (int ir=0;ir<4;ir++){
		toto.x+=coef[ir]*polynome_x[ir];
		toto.y+=coef[ir]*polynome_y[ir];
	    }
	    System.out.println("estime  .x "+toto.x+" .y "+toto.y+" dif_x "+(float)(toto.x-eprime0));
	}
	public void elimine(){
	    vue_de_cote.dispose();
	    vue_de_cote=null;	    
	}
	boolean est_dedans(point dist){
	    totologic=false;
	    if(Math.abs(dist.y)<rayon)
		totologic=Math.abs(dist.x)<longueur/2;
	    return totologic;
	    
	}
	boolean far_from_boarders(point dist){
	    totologic=true;
	    if(Math.abs(Math.abs(dist.y)-rayon)<distance_minimum_au_bord)
		totologic=Math.abs(dist.x)>longueur/2+distance_minimum_au_bord;
	    if(Math.abs(Math.abs(dist.x)-longueur/2)<distance_minimum_au_bord)
		totologic=Math.abs(dist.y)>rayon+distance_minimum_au_bord;
	    return totologic;
	}
	public void paint(){
	    
	    System.out.println("mmmmmmmmmmmmmm entree paint iter "+iter+" i_ens "+i_ens);
	    //if(deja_venu)
	    //	projections_du_rayon[1000]=null;
	    //deja_venu=true;
	    grp_c.setColor(Color.black);
	    grp_c.drawRect((int)(centre.x-longueur/2.),(int)(centre.y-rayon),longueur,2*rayon);
       	    vecteur v=new vecteur(zer,zer);int x=0;
	    vue_de_cote.dessine_de_cote();
	    dessiner_les_champs(chp_clc_barreau,null,0.7);
	    System.out.println("eprime0 "+(float)eprime0+" coef[0] "+(float)coef[0]+" E_total_en_P "+(float)E_total_en_P+" nb_champs_a_dessiner "+chp_clc_barreau.nb_champs_a_dessiner);
	}
	point champ_en_un_point(point posit,double posit_z){
	    champ_en_un_point.assigne(zer);
	    pt_xyz.assigne(posit,posit_z);
	    if(Math.abs(champ_exterieur.x)>0.0001){
		composante_horiz_pas_vert=true;
		champ_en_un_point.additionne(champ_en_un_point_xyz(pt_xyz).extrait_point());
	    }
	    if(Math.abs(champ_exterieur.y)>0.0001){
		composante_horiz_pas_vert=false;
		champ_en_un_point.additionne(champ_en_un_point_xyz(pt_xyz).extrait_point());
	    }
	    return champ_en_un_point;
	}
	/*
	point_xyz correction_de_champ_extremites_a_grand_rayon(point posit,int ir){
	    tutu_xyz.assigne(0.,0.,0.);
	    posit_xyz.assigne(posit,0.);
	    double dix=centre.x+longueur/2.+(double)longueur/128.;	
	    for (int iL=0;iL>64;iL--){
		dix-=longueur/64.;
		for (int ifi=0;ifi<64;ifi+=63){
		    if(!(exclure_phi_nul_ou_63&&(ifi==0||ifi==63))&&!exclure_phi[ifi]){
			toto_y_z.assigne_facteur(projections_du_rayon[ifi],(double)rayon);
			toto_y_z.y+=centre.y;
			toto_xyz.assigne(dix,toto_y_z);
			tata_xyz.assigne_soustrait(toto_xyz,posit_xyz);
			if(tata_xyz.longueur_carre()>longueur/64.*longueur/64.||tata_xyz.longueur_carre()>rayon*dfi*rayon*dfi){
			    coco=tata_xyz.longueur();
			    tete_xyz.assigne(0.,0.,0.);
			    if(i_ens==0){
				if(composante_horiz_pas_vert)
				    cece=-I_H_laterale[iL];
				else
				    cece=-I_V_laterale[iL]*projections_du_rayon[ifi].y;
				tete_xyz.additionne_facteur(tata_xyz,rdphi*longueur/64.*cece/(coco*coco*coco));
			    }else{
				if(composante_horiz_pas_vert){
				    toto_xyz.assigne(0.,projections_perp_au_rayon[ifi]);
				    toto_xyz.multiplie_cst(I_H_laterale[iL]*rdphi*longueur/64./(coco*coco*coco));
				}else{
				    cece=I_H_laterale[iL]*rdphi*longueur/64./(coco*coco*coco);
				    toto_xyz.assigne(cece,0.,0.);
				}
				tete_xyz.assigne(tata_xyz.vectoriel(toto_xyz));
			    }
			    tutu_xyz.additionne(tete_xyz);
			}			
		    }
		}
	    }
	    tutu_xyz.multiplie_cst(-1.);
	    toto_int=10;
	    if(ir==63)
		toto_int=100;
	    double d_long=longueur/(64.*toto_int);
	    dix=centre.x+longueur/2.+d_long/2.;double dfi_10=0.;	
	    for (int iL=63;iL>63-limite_au_bord;iL--){
		for (int iL_sur_10=toto_int;iL_sur_10>0;iL_sur_10--){
		    dix-=d_long;
		    for (int ifi=0;ifi<64;ifi+=63){
			dfi_10=-0.05;
			for (int ifi_10=0;ifi_10<10;ifi_10++){
			    dfi_10+=0.1;  
			    toto_y_z.assigne(rayon*projections_du_rayon[ifi].y,rayon*projections_du_rayon[ifi].z*dfi_10);
			    //toto_y_z.assigne_facteur(projections_du_rayon[ifi],(double)rayon);
			    toto_y_z.y+=centre.y;
			    toto_xyz.assigne(dix,toto_y_z);
			    tata_xyz.assigne_soustrait(toto_xyz,posit_xyz);
			    //if(tata_xyz.longueur_carre()>d_long*d_long||tata_xyz.longueur_carre()>rayon*dfi*rayon*dfi/100.){
				coco=tata_xyz.longueur();
				tete_xyz.assigne(0.,0.,0.);
				if(i_ens==0){
				    if(composante_horiz_pas_vert)
					cece=-I_H_laterale[iL];
				    else
					cece=-I_V_laterale[iL]*projections_du_rayon[ifi].y;
				    tete_xyz.additionne_facteur(tata_xyz,rdphi*longueur/64.*cece/(coco*coco*coco*toto_int*10.));
				}else{
				    if(composante_horiz_pas_vert){
					toto_xyz.assigne(0.,projections_perp_au_rayon[ifi]);
					toto_xyz.multiplie_cst(I_H_laterale[iL]*rdphi*longueur/64./(coco*coco*coco*toto_int*10.));
				    }else{
					cece=I_H_laterale[iL]*rdphi*longueur/64./(coco*coco*coco*toto_int*10.);
					toto_xyz.assigne(cece,0.,0.);
				    }
				    tete_xyz.assigne(tata_xyz.vectoriel(toto_xyz));
				}
				tutu_xyz.additionne(tete_xyz);
				//}			
			}
		    }
		}
	    }
	    tutu_xyz.multiplie_cst(facteur_fondamental);
	    return tutu_xyz;	    
	}
	point_xyz correction_de_champ_laterale_pres_d_extremite(point posit,int iL){
	    tutu_xyz.assigne(0.,0.,0.);
	    posit_xyz.assigne(posit,0.);
	    int icote=-1;
	    if(iL>32)
		icote=1;
	    double dr=rayon/64.,r=rayon+rayon/128.;
	    for (int ir=63;ir>63-limite_au_bord;ir--){
		r-=dr;
		for (int ifi=0;ifi<64;ifi+=63){
		    toto_xyz.assigne(centre.x+icote*longueur/2.,centre.y+r*projections_du_rayon[ifi].y,r*projections_du_rayon[ifi].z);	
		    tata_xyz.assigne_soustrait(toto_xyz,posit_xyz);
		    if(tata_xyz.longueur_carre()>dr*dr||tata_xyz.longueur_carre()>r*dfi*r*dfi){
			coco=tata_xyz.longueur();
			if(i_ens==0){
			    if(composante_horiz_pas_vert)
				cece=-icote*I_H_extremite_plus[ir];
			    else
				cece=-I_V_extremite_plus[ir];
			    tete_xyz.assigne_facteur(tata_xyz,r*dr*dfi*cece/(coco*coco*coco*toto_int));
			}else if(composante_horiz_pas_vert){
			    toto_xyz.assigne(0.,projections_perp_au_rayon[ifi]);
			    toto_xyz.multiplie_cst(I_H_extremite_plus[ir]*r*dr*dfi/(coco*coco*coco));
			    tete_xyz.assigne(tata_xyz.vectoriel(toto_xyz));
			}
		    }
		    tutu_xyz.additionne(tete_xyz);	
		}
	    }
	    tutu_xyz.multiplie_cst(-1.);
	    toto_int=10;
	    if(iL==0||iL==63)
		toto_int=100;
	    dr=rayon/(64.*toto_int);r=rayon+rayon/(64.*toto_int);double dfi_10=0.;
	    for (int ir=63;ir>63-limite_au_bord;ir--){
		for (int ir_sur_10=toto_int;ir_sur_10>0;ir_sur_10--){
		    r-=dr;
		    for (int ifi=0;ifi<64;ifi+=63){
			dfi_10=-0.05;
			for (int ifi_10=0;ifi_10<10;ifi_10++){
			    dfi_10+=0.1;  
			    toto_xyz.assigne(centre.x+icote*longueur/2.,centre.y+r*projections_du_rayon[ifi].y,r*projections_du_rayon[ifi].z*dfi_10);
			    tata_xyz.assigne_soustrait(toto_xyz,posit_xyz);
			    //if(tata_xyz.longueur_carre()>dr*dr||tata_xyz.longueur_carre()>r*dfi*r*dfi){
			    coco=tata_xyz.longueur();
			    if(i_ens==0){
				if(composante_horiz_pas_vert)
				    cece=-icote*I_H_extremite_plus[ir];
				else
				    cece=-I_V_extremite_plus[ir];
				tete_xyz.assigne_facteur(tata_xyz,r*dr*dfi*cece/(coco*coco*coco*toto_int*10));
			    }else if(composante_horiz_pas_vert){
				toto_xyz.assigne(0.,projections_perp_au_rayon[ifi]);
				toto_xyz.multiplie_cst(I_H_extremite_plus[ir]*r*dr*dfi/(coco*coco*coco*toto_int*10));
				tete_xyz.assigne(tata_xyz.vectoriel(toto_xyz));
			    }
			    //}
			    tutu_xyz.additionne(tete_xyz);
			    }	
		    }
		}
	    }

	    tutu_xyz.multiplie_cst(facteur_fondamental);
	    return tutu_xyz;
	}
	*/
	point_xyz champ_en_un_point_xyz(point_xyz posit_xyz){
	    //System.out.println(" BONJOUR champ_en_un_point_xyz ");
	    point res=new point(zer);
	    posit.assigne(posit_xyz.extrait_point());
	    feld_xyz.assigne(0.,0.,0.);
	    imprimer_resultats=false;
	    if(montrer_les_champ)
		for (int i=-1;i<2;i+=2){
		    for (int j=-1;j<2;j+=2){
			titi.assigne_soustrait(posit,centre);
			toto.assigne((float)(longueur/2-20)*i,(float)(rayon-20)*j);
			//toto.assigne(180.*i,20.*j);
			tutu.assigne_additionne(titi,toto);
			if(tutu.longueur()<10||titi.longueur()<10){
			    imprimer_resultats=true;
			    posit_xyz.print(" ***posit_xyz ");
			    break;
			}
		    }
		    if(imprimer_resultats)
			break;
		}
	    point_xyz tete_partiel=new point_xyz(0.,0.,0.);
	    long nn=0;
	    if(i_ens==1&&!composante_horiz_pas_vert){
		double diz=0.,rr=0.,drr=0.;
		for (int icote=-1;icote<2;icote+=2){
		    for (int ifi=0;ifi<64;ifi++){
			if(!exclure_phi[ifi]){
			    drr=-rayon*projections_du_rayon[ifi].z*dfi;
			    longueur_segments_z=Math.abs(2*rayon*projections_du_rayon[ifi].z);
			    diz=-longueur_segments_z/128.;	
			    for (int il=0;il<64;il++){
				diz+=longueur_segments_z/64.;
				toto_xyz.assigne(centre.x+icote*longueur/2.,centre.y+rayon*projections_du_rayon[ifi].y,-longueur_segments_z/2.+diz);	
				tata_xyz.assigne_soustrait(toto_xyz,posit_xyz);
				if(tata_xyz.longueur_carre()>drr*drr||tata_xyz.longueur_carre()>longueur_segments_z/64.*longueur_segments_z/64.){
				    coco=tata_xyz.longueur();
				    cece=icote*I_V_laterale[ifi]*longueur_segments_z/64.*drr/(coco*coco*coco);
				    toto_xyz.assigne(0.,0.,cece);
				    tete_xyz.assigne(tata_xyz.vectoriel(toto_xyz));
				    /*
				    if(ecrire_champ&&(il==15&&(ifi==0||ifi==7||ifi==15||ifi==23||ifi==31||ifi==32||ifi==40||ifi==48||ifi==56||ifi==63))){
					tete_xyz.extrait_point().print(" icote "+icote+" ifi "+ifi+" il "+il+" cece "+(float)cece+" tete_xyz. ");
					tata_xyz.print(" tata_xyz ");
				    }
				    */
				    feld_xyz.additionne(tete_xyz);
				}
			    }
			}
		    }
		}
		res.assigne(feld_xyz.extrait_point());
		//if(imprimer_resultats||ecrire_champ)
		// feld_xyz.print(" $$feld_xyz. ");
		for (int ifi=0;ifi<64;ifi++){
		    if(!exclure_phi[ifi]){
			double dix=centre.x-longueur/2.-(double)longueur/128.;	
			for (int iL=0;iL<64;iL++){
			    dix+=longueur/64.;
			    toto_y_z.assigne_facteur(projections_du_rayon[ifi],(double)rayon);
			    toto_y_z.y+=centre.y;
			    toto_xyz.assigne(dix,toto_y_z);
			    tata_xyz.assigne_soustrait(toto_xyz,posit_xyz);
			    if(tata_xyz.longueur_carre()>longueur/64.*longueur/64.&&tata_xyz.longueur_carre()>rayon*dfi*rayon*dfi){
				coco=tata_xyz.longueur();
				cece=I_V_laterale[ifi]*rdphi*longueur/64./(coco*coco*coco);
				toto_xyz.assigne(cece,0.,0.);
				tete_xyz.assigne(tata_xyz.vectoriel(toto_xyz));
				/*
				if(ecrire_champ&&iL==32&&(ifi==0||ifi==7||ifi==15||ifi==23||ifi==31||ifi==32||ifi==40||ifi==48||ifi==56||ifi==63)){
				    tete_xyz.print(" iL "+iL+" ifi "+ifi+" cece "+(float)cece+" tete_xyz ");
				    tata_xyz.print(" iL "+iL+" ifi "+ifi+" I_V_laterale[ifi] "+I_V_laterale[ifi]+" coco "+(float)coco+" tata_xyz ");
				}
				*/
				feld_xyz.additionne(tete_xyz);
			    }
			}
		    }
		}
		if(imprimer_resultats||ecrire_champ){
		    res.assigne_soustrait(feld_xyz.extrait_point(),res);
		    res.print_sl("res ");
		    feld_xyz.print(" $$feld_xyz. ");
		}		
		feld_xyz.multiplie_cst(facteur_fondamental);

		if(imprimer_resultats||ecrire_champ)
		//if(i_ens==0)
		    feld_xyz.print(" $$feld_xyz. ");
		return feld_xyz;
	    }else{
		if(!eliminer_extremites){
		    toto_int=2;
		    if(eliminer_extremite_plus)
			toto_int=0;
		    for (int icote=-1;icote<toto_int;icote+=2){
			//if(imprimer_resultats)
			//feld_xyz.print(" nn "+nn+" feld_xyz chgt de cote ");
			double dr=rayon/64.,r=-rayon/128.;
			for (int ir=0;ir<64;ir++){
			    r+=dr;
			    for (int ifi=0;ifi<64;ifi++){
				if(!(exclure_phi_nul_ou_63&&(ifi==0||ifi==63))){
				    toto_xyz.assigne(centre.x+icote*longueur/2.,centre.y+r*projections_du_rayon[ifi].y,r*projections_du_rayon[ifi].z);	
				    tata_xyz.assigne_soustrait(toto_xyz,posit_xyz);
				    tete_xyz.assigne(0.,0.,0.);
				    //if(tata_xyz.longueur_carre()>dr*dr||tata_xyz.longueur_carre()>r*dfi*r*dfi){
				    if(tata_xyz.longueur_carre()>2*dr*dr&&tata_xyz.longueur_carre()>2*r*dfi*r*dfi){
					coco=tata_xyz.longueur();
					if(i_ens==0){
					    if(composante_horiz_pas_vert)
						cece=-icote*I_H_extremite_plus[ir];
					    else
						//cece=-sigma_V_extremite_plus[ifi][ir]*projections_du_rayon[ifi].y;
						cece=-sigma_V_extremite_plus[ifi][ir];
					    tete_xyz.assigne_facteur(tata_xyz,r*dr*dfi*cece/(coco*coco*coco));
					    /*
					    if(imprimer_resultats)
						if((ifi==0||ifi==7||ifi==24||ifi==31||ifi==39||ifi==56||ifi==63)&&(ir==31||ir==0)){
						    tete_xyz.print(" icote "+icote+" ifi "+ifi+" ir "+ir+" cece "+(float)cece+" exclure_phi_nul_ou_63 "+exclure_phi_nul_ou_63+" tete_xyz ");
						    tata_xyz.print(" tata_xyz ");
						    tete_partiel.additionne(tete_xyz);
						}
					    */
					}else if(composante_horiz_pas_vert){
					    toto_xyz.assigne(0.,projections_perp_au_rayon[ifi]);
					    toto_xyz.multiplie_cst(I_H_extremite_plus[ir]*r*dr*dfi/(coco*coco*coco));
					    /*
					      else
					      toto_xyz.multiplie_cst(I_V_extremite_plus[ir]*rdphi*longueur/64./(coco*coco*coco));
					    */
					    tete_xyz.assigne(tata_xyz.vectoriel(toto_xyz));					    
					    if(ecrire_champ)
						if((ifi==7||ifi==24||ifi==39||ifi==56)&&(ir==31)){
						    cece=I_H_extremite_plus[ir]*r*dr*dfi/(coco*coco*coco);
						    tete_xyz.print(" icote "+icote+" ifi "+ifi+" coco "+(float)coco+" cece "+(float)cece+" tete_xyz ");
						    tata_xyz.print(" coco "+(float)coco+" tata_xyz ");
						}
					}
				    }
				    feld_xyz.additionne(tete_xyz);
				    //if(imprimer_resultats&&nn/1000*1000==nn)
				    //	feld_xyz.print(" nn "+nn+" feld_xyz ");
				    nn++;
				}
			    }
			}
		    }
		}
		res.assigne(feld_xyz.extrait_point());
		//if(tutu.longueur()<10||titi.longueur()<10||ecrire_champ)
		//if(i_ens==0)
		/*
		if(imprimer_resultats||ecrire_champ){
		    tete_partiel.print(" nn"+nn+" tete_partiel ");
		    feld_xyz.print(" feld_xyz à mi_parcours ");
		}
		*/
		tete_partiel.assigne(0.,0.,0.);
		double dix=centre.x-longueur/2.-(double)longueur/128.;	
		if(!pas_laterales)
		    for (int iL=0;iL<64;iL++){
			dix+=longueur/64.;
			for (int ifi=0;ifi<64;ifi++){
			    if(!(exclure_phi_nul_ou_63&&(ifi==0||ifi==63))&&!exclure_phi[ifi]){
				toto_y_z.assigne_facteur(projections_du_rayon[ifi],(double)rayon);
				toto_y_z.y+=centre.y;
				toto_xyz.assigne(dix,toto_y_z);
				tata_xyz.assigne_soustrait(toto_xyz,posit_xyz);
				if(tata_xyz.longueur_carre()>longueur/64.*longueur/64.||tata_xyz.longueur_carre()>rayon*dfi*rayon*dfi){
				    coco=tata_xyz.longueur();
				    tete_xyz.assigne(0.,0.,0.);
				    if(i_ens==0){
					if(composante_horiz_pas_vert)
					    cece=-I_H_laterale[iL];
					else
					    //cece=-sigma_V_laterale[ifi][iL]*projections_du_rayon[ifi].y;
					    cece=-sigma_V_laterale[ifi][iL];
					tete_xyz.additionne_facteur(tata_xyz,rdphi*longueur/64.*cece/(coco*coco*coco));
					/*
					  if(imprimer_resultats||ecrire_champ)
					    if((ifi==7||ifi==24||ifi==39||ifi==56)&&iL==7){
						    
						//if(ecrire_champ&&(iL==15||iL==48)&&(ifi==0||ifi==7||ifi==15||ifi==23||ifi==31||ifi==32||ifi==40||ifi==48||ifi==56||ifi==63)){
						tete_xyz.extrait_point().print_sl(" iL "+iL +" ifi "+ifi+" cece "+(float)cece+" tete_xyz ");
						tata_xyz.print(" coco "+(float)coco+" sigma "+(float)I_V_laterale[ifi]+"tata_xyz ");
						tete_partiel.additionne(tete_xyz);
						toto_xyz.print(" proj_du_rayon[ifi].y "+(float)projections_du_rayon[ifi].y+"toto_xyz ");
					    }
					*/
				    }else{
					if(composante_horiz_pas_vert){
					    toto_xyz.assigne(0.,projections_perp_au_rayon[ifi]);
					    toto_xyz.multiplie_cst(I_H_laterale[iL]*rdphi*longueur/64./(coco*coco*coco));
					}else{
					    cece=I_H_laterale[iL]*rdphi*longueur/64./(coco*coco*coco);
					    toto_xyz.assigne(cece,0.,0.);
					}
					tete_xyz.assigne(tata_xyz.vectoriel(toto_xyz));
					if(ecrire_champ)
					    if((ifi==7||ifi==24||ifi==39||ifi==56)&&iL==7){
						    
						//if(ecrire_champ&&(iL==15||iL==48)&&(ifi==0||ifi==7||ifi==15||ifi==23||ifi==31||ifi==32||ifi==40||ifi==48||ifi==56||ifi==63)){
						tete_xyz.extrait_point().print_sl(" $$iL "+iL +" ifi "+ifi+" tete_xyz ");
						tata_xyz.print(" $$coco "+(float)coco+" sigma "+(float)I_H_laterale[ifi]+"tata_xyz ");
						toto_xyz.print(" $$proj_du_rayon[ifi].y "+(float)projections_du_rayon[ifi].y+"toto_xyz ");
					    }
				    }
				    nn++;
				    feld_xyz.additionne(tete_xyz);
				    //if(imprimer_resultats&&nn/1000*1000==nn)
				    //feld_xyz.print(" nn "+nn+" feld_xyz ");
				    /*
				      if(i_ens==1&&(iLLrr==7||iLLrr==56)&&(iL==7||iL==56)&&(ifi==7||ifi==56)){
				      tete_xyz.print("iLLrr "+iLLrr+"iL "+iL+" ifi "+ifi+"tete_xyz ");
				      tata_xyz.print("tata_xyz ");
				      posit_xyz.print("posit_xyz ");
				      toto_xyz.print("sigma_laterale "+(float)I_H_laterale[iL]+"toto_xyz ");
				      }
				    */
				}			
			    }
			}
		    }
	    }

	    res.assigne_soustrait(feld_xyz.extrait_point(),res);
	    tete_xyz.assigne(feld_xyz);
	    feld_xyz.multiplie_cst(facteur_fondamental);
	    if(imprimer_resultats||ecrire_champ){
		//res.print_sl(" nn "+nn+" res ");
		//tete_partiel.print(" tete_partiel ");
		feld_xyz.print(" feld_xyz. ");
		//if(i_ens==1&&iLLrr==56)
		//I_H_laterale[1000]=0;
	    }
	    return feld_xyz;
	}

	class side extends Frame{
	    point_y_z centre_de_cote;
	    int right_cote=320+left_ens+i_ens*600,left_cote=0+left_ens+i_ens*600,bottom_cote=600+top_ens,top_cote=300+top_ens;
	    double fct_gauche[]=new double[6];
	    double fct_droite[]=new double[6];
	    point gauche_ext_plus; 
	    point droite_ext_plus; 
	    couleur couleur_lum;
	    double angle_de_cote,cos_angle_de_cote,sin_angle_de_cote;
	    String string_min="",string_max="";
	    public side(String s){
		super(s);	    
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
			    //while(occupied){
			    //}
			    if(vue_de_cote!=null){
				vue_de_cote.dispose();
				vue_de_cote=null;
			    }
			    dispose();
			}
		    });		
		setVisible(true);
		setSize(right_cote-left_cote,bottom_cote-top_cote);
		setLocation(left_cote,top_cote);
		gr_de_cote=getGraphics();
		centre_de_cote=new point_y_z((double)(bottom_cote-top_cote)/2+30,(double)(right_cote-left_cote)/2);
		angle_de_cote=60.*pi/180.;
		cos_angle_de_cote=Math.cos(angle_de_cote);
		sin_angle_de_cote=Math.sin(angle_de_cote);
		paalette=new palette();
		fct_gauche[0]=0.45;
		fct_droite[0]=0.8;
		fct_gauche[1]=0.52;
		fct_droite[1]=0.61;
		fct_gauche[2]=0.55;
		fct_droite[2]=0.56;
		fct_gauche[3]=0.59;
		fct_droite[3]=0.52;
		fct_gauche[4]=0.66;
		fct_droite[4]=0.47;
		fct_gauche[5]=0.80;
		fct_droite[5]=0.40;
		couleur_lum=new couleur(0,0,0);
		gauche_ext_plus=new point(zer);
		droite_ext_plus=new point(zer);
		if(i_ens==0){
		    string_min="1e-13  Cb/pixel2";string_max="1e-11 Cb/pixel2";
		    paalette.I_ou_Q_min=1e-13;
		    paalette.I_ou_Q_max=1e-11;
		}else{
		    string_min="1e5";string_max="1e7 A/pixel";
		    paalette.I_ou_Q_min=1e4;
		    paalette.I_ou_Q_max=1e7;
		}
	    }
	    void dessine_de_cote(){
		double r=0.,courant=0.;int ir=0,iL=0;
		vecteur v=new vecteur(zer,zer);
		for (int i=-4;i<=4;i++){
		    gr_de_cote.setColor(Color.black);
		    if(i==4){
			gr_de_cote.drawOval((int)(centre_de_cote.z+i*longueur/8.-rayon*cos_angle_de_cote),(int)centre_de_cote.y-rayon,(int)(2*rayon*cos_angle_de_cote),2*rayon);
			gr_de_cote.drawOval((int)(centre_de_cote.z+i*longueur/8.-rayon*cos_angle_de_cote)-1,(int)centre_de_cote.y-rayon-1,(int)(2*rayon*cos_angle_de_cote)+1,2*rayon+1);
			if(champ_exterieur.x!=0.){
			    r=-rayon/8.;
			    for (int j=0;j<4;j++){
				r+=rayon/4.;
				ir=(int)(Math.round(r/rayon*64.));
				courant=I_H_extremite_plus[ir];
				System.out.println(" ir$$$$ "+ir+" courant "+(float)courant);
				if(paalette.determine_la_couleur(courant))
				    gr_de_cote.setColor(paalette.couleur_de_la_lumiere.col);
			    }
			    gr_de_cote.drawOval((int)(centre_de_cote.z+i*longueur/8.-r*cos_angle_de_cote),(int)(centre_de_cote.y-r),(int)(2*r*cos_angle_de_cote),(int)(2*r));
			}
			if(champ_exterieur.y!=0.){
			    int ififi=0,ifi=0;
			    for (int ifk=6;ifk<28;ifk+=4){
				ifi=ifk;
				if(ifk>31)
				   ifi=ifk-1;
				longueur_segments_z=Math.abs(2*rayon*projections_du_rayon[ifi].z);
				courant=I_V_laterale[ifi];
				System.out.println(" ifi$$$$ "+ifi+" courant "+(float)courant);
				if(paalette.determine_la_couleur(courant))
				    couleur_lum.assigne(paalette.couleur_de_la_lumiere); 
				gr_de_cote.setColor(couleur_lum.col);
				coco=Math.round(centre_de_cote.z+longueur/2);
				caca=Math.round(longueur_segments_z*sin_angle_de_cote/2);
				cici=Math.round(longueur_segments_z*cos_angle_de_cote/2);
				cece=Math.round(centre_de_cote.y+rayon*projections_du_rayon[ifi].y);
				gauche_ext_plus.assigne(coco-caca*fct_gauche[ififi],cece+cici*fct_gauche[ififi]);
				droite_ext_plus.assigne(coco+caca*fct_droite[ififi],cece-cici*fct_droite[ififi]);
				subject.drawline_pt_entier(gr_de_cote,gauche_ext_plus,droite_ext_plus);
				v.chp.assigne_soustrait(gauche_ext_plus,droite_ext_plus);
				v.chp.divise_cst(100.);
				v.pnt.assigne(Math.round(centre_de_cote.z+longueur/2),cece);
				v.dessine(40.,1.,gr_de_cote,couleur_lum.col);

				toto.assigne(gauche_ext_plus.x-longueur,gauche_ext_plus.y);
				subject.drawline_pt_entier(gr_de_cote,gauche_ext_plus,toto);
				v.chp.assigne_soustrait(toto,gauche_ext_plus);
				v.chp.divise_cst(100.);
				v.pnt.assigne(gauche_ext_plus.x-longueur/2-5,gauche_ext_plus.y);
				v.dessine(40.,1.,gr_de_cote,couleur_lum.col);
				
				ififi++;
			    }
			}
		    }else{
			gr_de_cote.setColor(Color.black);
			if(i!=-4&&champ_exterieur.x!=0.){
			    iL=(i+4)*8;
			    if(champ_exterieur.x!=0.){
				courant=I_H_laterale[iL];
				System.out.println(" iL$$$$ "+iL+" courant "+(float)courant);
				if(paalette.determine_la_couleur(courant))
				    gr_de_cote.setColor(paalette.couleur_de_la_lumiere.col);
			    }
			}
			if(i==-4||champ_exterieur.x!=0.)
			    gr_de_cote.drawArc((int)(centre_de_cote.z+i*longueur/8.-rayon*cos_angle_de_cote),(int)centre_de_cote.y-rayon,(int)(2*rayon*cos_angle_de_cote),2*rayon,90,180);
			if(i==-4)
			    gr_de_cote.drawArc((int)(centre_de_cote.z+i*longueur/8.-rayon*cos_angle_de_cote)-1,(int)centre_de_cote.y-rayon-1,(int)(2*rayon*cos_angle_de_cote)+1,2*rayon+1,90,180);
			
		    }
		}
		gr_de_cote.setColor(Color.black);
		for (int i=-1;i<=2;i+=2){
		    gr_de_cote.drawLine((int)(centre_de_cote.z-longueur/2),(int)centre_de_cote.y+i*rayon+i,(int)(centre_de_cote.z+longueur/2),(int)centre_de_cote.y+i*rayon+i);
		    gr_de_cote.drawLine((int)(centre_de_cote.z-longueur/2),(int)centre_de_cote.y+i*rayon+2*i,(int)(centre_de_cote.z+longueur/2),(int)centre_de_cote.y+i*rayon+2*i);
		}
		paalette.dessine(string_min,string_max);
	    }
	}	
    }
    class barreau_parallelipedique extends multipole_ou_barreau{
	face vue_de_cote;
	champs_points_calcules chp_clc_barreau;
	double dI_normal_a_x;
	double dI_normal_a_y;
	int hauteur,largeur;
	barreau_parallelipedique(point ctr1, double hauteur1, double longueur1,double largeur1){
	    super();
	    centre=new point(ctr1);
	    hauteur=(int)hauteur1;
	    rayon=hauteur/2;//pour certains affichages
	    longueur=(int)longueur1;
	    largeur=(int)largeur1;
	    susceptibilite=80.;
	    dI_normal_a_x=susceptibilite*champ_exterieur.x/mu0_ou_un_sur_eps0;
	    dI_normal_a_y=susceptibilite*champ_exterieur.y/mu0_ou_un_sur_eps0;
	    //vue_de_face=new face(" Vue de face ");	    	    
	    chp_clc_barreau=new champs_points_calcules();
	    chp_clc_barreau.remplis(false);
	    paint();
	    scale=(float)champ_exterieur.longueur();//pour la vue de face
	    //vue_de_face.centre_deface.print(" centre_deface ");
	}

	public void elimine(){
	    if(vue_de_cote!=null){
		vue_de_cote.dispose();
		vue_de_cote=null;
	    }	    
	}
	boolean est_dedans(point dist){
	    return true;	    
	}
	boolean pas_trop_proche_des_bords(point dist){
	    totologic=true;
	    if(Math.abs(Math.abs(dist.y)-hauteur/2)<distance_minimum_au_bord)
		totologic=Math.abs(dist.x)>longueur/2+distance_minimum_au_bord;
	    if(Math.abs(Math.abs(dist.x)-longueur/2)<distance_minimum_au_bord)
		totologic=Math.abs(dist.y)>hauteur/2+distance_minimum_au_bord;
	    return totologic;
	}

	public void paint(){
	    
	    System.out.println("mmmmmmmmmmmmmm entree paint iter "+iter+" i_ens "+i_ens);
	    //if(deja_venu)
	    //	projections_du_rayon[1000]=null;
	    //deja_venu=true;
	    subject.paintrect_couleur(grp_c,(int)centre.y-hauteur/2,(int)centre.x-longueur/2,(int)centre.y+hauteur/2,(int)centre.x+longueur/2,Color.black);
      	    vecteur v=new vecteur(zer,zer);int x=0;
	    //vue_de_cote.dessine_de_face();
	    dessiner_les_champs(chp_clc_barreau,null,0.7);
	}
	
	point champ_en_un_point(point posit,double posit_z){
	    tutu.assigne_soustrait(posit,centre);
	    pt_xyz.assigne(posit,posit_z);
	    toto.assigne(champ_en_un_point_xyz(pt_xyz).extrait_point());
	    if(tutu.longueur_carre()<20)
		toto.print(" mmmmmmmmmmmmmtoto fin ");
	    return toto;
	}
	point_xyz champ_en_un_point_xyz(point_xyz posit_xyz){
	    feld_xyz.assigne(0.,0.,0.);
	    double diy=-(double)hauteur/128.;
	    double dix=0.,diz=0.;
	    for (int ih=0;ih<64;ih++){
		diy+=hauteur/64.;
		for (int icote=-1;icote<2;icote+=2){
		    dix=-(double)longueur/128;	
		    for (int iL=0;iL<64;iL++){
			dix+=longueur/64.;
			toto_xyz.assigne(centre.x-longueur/2.+dix,centre.y-hauteur/2.+diy,icote*largeur/2.);	
			tata_xyz.assigne_soustrait(posit_xyz,toto_xyz);
			if(tata_xyz.longueur_carre()>1){
			    coco=tata_xyz.longueur();
			    toto_xyz.assigne(-icote*dI_normal_a_y*hauteur/64.*longueur/64./(coco*coco*coco),0.,0.);
			    tete_xyz.assigne(tata_xyz.vectoriel(toto_xyz));
			    feld_xyz.additionne(tete_xyz);
			}
		    }
		    diz=-largeur/128.;	
		    for (int il=0;il<64;il++){
			diz+=largeur/64.;
			toto_xyz.assigne(centre.x+icote*longueur/2.,centre.y-hauteur/2.+diy,-largeur/2.+diz);	
			tata_xyz.assigne_soustrait(posit_xyz,toto_xyz);
			if(tata_xyz.longueur_carre()>1){
			    coco=tata_xyz.longueur();
			    toto_xyz.assigne(0.,0.,+icote*dI_normal_a_y*hauteur/64.*largeur/64./(coco*coco*coco));
			    tete_xyz.assigne(tata_xyz.vectoriel(toto_xyz));
			    feld_xyz.additionne(tete_xyz);
			}
		    }
		}
	    }
	    feld_xyz.multiplie_cst(facteur_fondamental);
	    //feld_xyz.additionne(champ_exterieur_xyz);
	    return feld_xyz;
	}
	class face extends Frame{
	    Graphics gr_deface;
	    point_y_z centre_deface;
	    int right_face=300+left_ens+i_ens*600,left_face=0+left_ens+i_ens*600,bottom_face=600+top_ens,top_face=300+top_ens;
	    public face(String s){
		super(s);	    
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
			    //while(occupied){
			    //}
			    if(vue_de_cote!=null){
				vue_de_cote.dispose();
				vue_de_cote=null;
			    }
			    dispose();
			};
		    });		
		setVisible(true);
		setSize(right_face-left_face,bottom_face-top_face);
		setLocation(left_face,top_face);
		gr_deface=getGraphics();
		centre_deface=new point_y_z((double)(bottom_face-top_face)/2,(double)(right_face-left_face)/2);
		
	    }
	    void dessine_de_face(){
		gr_deface.setColor(Color.black);
		gr_deface.drawOval((int)centre_deface.z-rayon,(int)centre_deface.y-rayon,2*rayon,2*rayon);
		/*
		vecteur_y_z v_y_z=new vecteur_y_z(zer_y_z,zer_y_z);
		for (int ifi=0;ifi<64;ifi+=4){
		    toto_y_z.assigne_facteur(projections_du_rayon[ifi],(double)rayon);
		    toto_y_z.additionne(centre_deface);
		    toto_int=(int)Math.round(10*Math.abs(dI_axial_lateral_sur_rdphi[ifi]/dI_axial_lateral_sur_rdphi[16]));
		    if(dI_axial_lateral_sur_rdphi[ifi]>0)
			gr_deface.setColor(Color.red);
		    else
			gr_deface.setColor(Color.blue);
		    gr_deface.drawOval((int)toto_y_z.z-toto_int,(int)toto_y_z.y-toto_int,2*toto_int,2*toto_int);
		    titi_y_z.assigne_facteur(projections_perp_au_rayon[ifi],-1.);
		    v_y_z.assigne(titi_y_z,toto_y_z);
		    v_y_z.dessine(1.,1.,gr_deface,Color.orange);
		    v_y_z.print(" ifi "+ifi+" v_y_z ");
		}
		vecteur_y_z vecteur_chp_y_z=new vecteur_y_z(zer_y_z,zer_y_z);
		//position_y_z.assigne((double)top_face,(double)(left_face));
		position_y_z.assigne(0.,0.);
		point_xyz vvv=new point_xyz(0.,0.,0.); 
		for (int kk=0;kk<2000;kk++){
		    vecteur_chp_y_z.pnt.assigne(position_y_z);
		    toto_y_z.assigne(position_y_z.y-centre_deface.y+centre.y,position_y_z.z-centre_deface.z);
		    toto.assigne(0.,toto_y_z.y);
		    vvv.assigne(champ_en_un_point_xyz(toto,toto_y_z.z));
		    vecteur_chp_y_z.chp=vvv.extrait_point_y_z();
		    vecteur_chp_y_z.dessine(scale,fct_zm_sspl,gr_deface,Color.green);
		    if(kk/10*10==kk)
			vecteur_chp_y_z.print(" kkùùùù "+kk+" vecteur_chp_y_z ");
		    if(kk/100*100==kk)
			System.out.println(" centre_deface.y "+centre_deface.y+" centre.y "+centre.y+" centre_deface.z "+centre_deface.z); 
		    toto_y_z.assigne_soustrait(centre_deface,position_y_z);
		    if(toto_y_z.longueur_carre()<40*40)	
			System.out.println("***************** position_y_z.y-centre_deface.y+centre.y "+(position_y_z.y-centre_deface.y+centre.y));	
		    position_y_z.z+=20;
		    if(position_y_z.z>right_face-left_face){
			position_y_z.z=0.;
			position_y_z.y+=20;
			if(position_y_z.y>bottom_face-top_face)
			    break;
		    }
		}
		*/
		/*
		if(i_ens==1){
		  System.out.println(" bottom_face "+bottom_face+" top_face "+top_face+" left_face "+left_face+" right_face "+right_face);
		  projections_du_rayon[1000]=null;
		}
		*/	    
	    }
	}	
    }
    class barreau_cylindrique_infini_prime extends multipole_ou_barreau{
        point projection_du_rayon[]=new point[64];
	int iteration=0;
	champs_points_calcules chp_clc_barreau;
	double dI_axial_lateral_sur_rdphi[]=new double [64];
	double rdphi;
	barreau_cylindrique_infini_prime(point ctr1, double rayon1, double longueur1,double largeur1){
	    super();
	    centre=new point(ctr1);
	    rayon=(int)rayon1;
	    longueur=(int)longueur1;
	    facteur_fondamental=mu0_ou_un_sur_eps0/(2*pi);
	    rdphi=2*pi*rayon/64.;
	    susceptibilite=80.;
	    double phi=-2*pi/128.;double cosphi=0,sinphi=0;
	    for (int ifi=0;ifi<64;ifi++){	
		phi+=2*pi/64.;
		cosphi=Math.cos(phi);
		sinphi=Math.sin(phi);		
		projection_du_rayon[ifi]=new point(sinphi,cosphi);//attention
		dI_axial_lateral_sur_rdphi[ifi]=-susceptibilite*champ_exterieur.longueur()*projection_du_rayon[ifi].x/mu0_ou_un_sur_eps0;   
	    } 
	    chp_clc_barreau=new champs_points_calcules();
	    chp_clc_barreau.remplis(false);
	    paint();
	}
	public void elimine(){
	}
	boolean pas_trop_proche_des_bords(point dist){
	    return true;
	}
	boolean est_dedans(point dist){
	    return true;	    
	}
	void paint(){
	    grp_c.setColor(Color.black);
	    grp_c.drawOval((int)centre.x-rayon,(int)centre.y-rayon,2*rayon,2*rayon);
	    vecteur v=new vecteur(zer,zer);
	    for (int ifi=0;ifi<64;ifi+=4){
		toto.assigne_facteur(projection_du_rayon[ifi],(double)rayon);
		toto.additionne(centre);
		if(dI_axial_lateral_sur_rdphi[ifi]>0)
		    grp_c.setColor(Color.red);
		else
		    grp_c.setColor(Color.blue);
		toto_int=(int)Math.round(10*Math.abs(dI_axial_lateral_sur_rdphi[ifi]/dI_axial_lateral_sur_rdphi[16]));
		grp_c.drawOval((int)toto.x-toto_int,(int)toto.y-toto_int,2*toto_int,2*toto_int);
		toto.print(" ifi "+ifi+" toto_int "+toto_int+" toto "); 
	    }
	    dessiner_les_champs(chp_clc_barreau,null,0.5);
	}
	point champ_en_un_point(point posit,double z_bidon){
	    tutu.assigne(zer);
	    titi.assigne(posit);
	    for (int ifi=0;ifi<64;ifi++){
		toto.assigne(centre);
		toto.additionne_facteur(projection_du_rayon[ifi],(double)rayon);
		tata.assigne_soustrait(posit,toto);
		if(tata.longueur_carre()>1){
		    tata.multiplie_cst(dI_axial_lateral_sur_rdphi[ifi]/tata.longueur_carre());
		    tata.rotation(0.,1.);
		    tutu.additionne(tata);
		}
		//if(tutu.longueur_carre()<20)
		//    toto.print(" ifi "+ifi+" toto fin ");
	    }
	    tutu.multiplie_cst(facteur_fondamental*rdphi);
	    tutu.additionne(champ_exterieur);
	    return tutu;
	}
    }
    abstract class dipole extends multipole_ou_barreau{
	vecteur dip;
	champs_points_calcules chp_clc_dipole;point pos_plus,pos_moins;
	dipole(point centre1,double q_ou_i1,point lambda_ou_s1){
	    super();
	    q_ou_i=(double)q_ou_i1; 
	    centre=new point(centre1);
	    lambda_ou_s=new point(lambda_ou_s1);
	    if(i_ens==0){
		pos_plus=new point(centre);
		pos_plus.additionne_facteur(lambda_ou_s,0.5);
		pos_moins=new point(pos_plus);
		pos_moins.soustrait(lambda_ou_s);
	    }else{
		rayon=(int)Math.round(Math.sqrt(Math.abs(lambda_ou_s.x)*4/pi));
		System.out.println(" i_ens "+i_ens+" rayon "+rayon+" lambda_ou_s.x "+lambda_ou_s.x);
	    }
	    dip=new vecteur(centre,lambda_ou_s);
	    chp_clc_dipole=new champs_points_calcules();
	    chp_clc_dipole.remplis(false);
	}
	dipole(point centre1,point dipol1){
	    centre=new point(centre1);
	    dip.assigne(centre,dipol1);
	    chp_clc_dipole=new champs_points_calcules();
	    chp_clc_dipole.remplis(false);
	}
	boolean pas_trop_proche_des_bords(point dist){
	    return true;
	}
	boolean est_dedans(point dist){
	    return true;	    
	}
	point champ_en_un_point(point posit,double posit_z ){
	    double rrr=0;
	    if(i_ens==0){
		q_dist_plus.assigne_soustrait(posit,pos_plus);
		aaa=(float)q_dist_plus.longueur();
		q_dist_plus.divise_cst(aaa*aaa*aaa);
		q_dist_moins.assigne_soustrait(posit,pos_moins);
		aaa=(float)q_dist_moins.longueur();
		q_dist_moins.multiplie_cst((float)-1.);
		q_dist_moins.divise_cst(aaa*aaa*aaa);
		toto.assigne_additionne(q_dist_plus,q_dist_moins);
		return toto;
	    }else{
		titi.assigne(zer);
		for (int j=0;j<8;j++){
		    distance_minimum_a_l_arc=0;
		    point_initial_arc.assigne(centre.y+rayon*cos_angle[j*8],rayon*sin_angle[j*8]);
		    if(j!=7)
			point_final_arc.assigne(centre.y+rayon*cos_angle[(j+1)*8],rayon*sin_angle[(j+1)*8]);
		    else
			point_final_arc.assigne(centre.y+rayon,1.);
		    point_central_arc.assigne(centre.y+rayon*cos_angle[j*8+3],rayon*sin_angle[j*8+3]);
		    courant.assigne(-sin_angle[j*8+3],cos_angle[j*8+3]);
		    distance_a_point_central_arc.assigne(posit.y-point_central_arc.y,-point_central_arc.z);
		    coco=centre.x-posit.x;
		    coco=Math.sqrt(coco*coco+distance_a_point_central_arc.longueur_carre());
		    toto.assigne(distance_a_point_central_arc.vectoriel(courant),-(posit.x-centre.x)*courant.z);
		    toto.divise_cst(coco*coco*coco);
		    if(i_ens==0||i_ens==1&&lambda_ou_s.x>0)
			titi.additionne(toto);
		    else if(i_ens==1&&lambda_ou_s.x<0)
			titi.soustrait(toto);
		}
		return titi;
	    }
	}
    }
    class multipole extends multipole_ou_barreau{
	dipole bipole[]=new dipole[2];
	multipole(){
	    super();
	    point ctr=new point(300,300);
	    if(i_demarre<=2){
		if(demo)
		    ctr.multiplie_cst(0.5);
		float q_lam=180;
		if(i_ens==1)
		    q_lam=3000;
		float distance_dipoles=100;
		if(i_demarre==1&&i_ens==0){
		    distance_dipoles=200;
		    q_lam=90;
		}
		subject.eraserect(grp_c,0, 0, 1000, 1000,Color.white);
		point toto=new point(q_lam,0.);
		if(i_demarre==0){
		    n_poles_ou_barreaux_tot=1;
		    bipole[0]=new dipole_de_pres(ctr,q_lam,toto);
		}
		if(i_demarre==1){
		    n_poles_ou_barreaux_tot=2;
		    bipole[0]=new dipole_de_pres(ctr,q_lam,toto);
		    ctr.x+=distance_dipoles;
		    toto.multiplie_cst((float)-1.);
		    bipole[1]=new dipole_de_pres(ctr,q_lam,toto);
		}
		if(i_demarre==2){
		    n_poles_ou_barreaux_tot=1;
		    bipole[0]=new dipole_de_loin(ctr,toto);
		    if(i_ens==1){
			n_poles_ou_barreaux_tot=2;
			ctr.x+=distance_dipoles;
			toto.multiplie_cst((float)-1.);
			bipole[1]=new dipole_de_loin(ctr,toto);
		    }
		}
		paint();
	    }
	}
	boolean pas_trop_proche_des_bords(point dist){
	    return true;
	}
	boolean est_dedans(point dist){
	    return true;	    
	}
	public void elimine(){
	}
	point champ_en_un_point(point posit,double posit_z ){
	    return zer;
	}
	public void paint(){
	    System.out.println("paint trouve_deplacement "+trouve_deplacement);
	    for(int iq=0;iq<n_poles_ou_barreaux_tot;iq++){
		p_ou_b=bipole[iq];
		if(!de_loin){
		    p_ou_b.centre.print(" q_ou_i" +q_ou_i+ " centre ");
		    subject.paintcircle_couleur(grp_c,Math.round(p_ou_b.centre.x),Math.round(p_ou_b.centre.y),4,Color.green);
		    if(i_ens==0){
			subject.paintcircle_couleur(grp_c,Math.round(p_ou_b.centre.x+p_ou_b.lambda_ou_s.x/2),Math.round(p_ou_b.centre.y),4,Color.red);
			subject.paintcircle_couleur(grp_c,Math.round(p_ou_b.centre.x-p_ou_b.lambda_ou_s.x/2),Math.round(p_ou_b.centre.y),4,Color.blue);
		    }else{
			grp_c.setColor(Color.blue);
			grp_c.fillOval((int)Math.round(p_ou_b.centre.x-(float)(p_ou_b.rayon+1)/2),(int)p_ou_b.centre.y-(p_ou_b.rayon+1),p_ou_b.rayon+1,2*(p_ou_b.rayon+1));
			grp_c.setColor(Color.white);
			grp_c.fillOval((int)Math.round(p_ou_b.centre.x-(float)(p_ou_b.rayon-1)/2),(int)p_ou_b.centre.y-(p_ou_b.rayon-1),p_ou_b.rayon-1,2*(p_ou_b.rayon-1));
		    }
		}else{
		}
	    }
	    if(n_poles_ou_barreaux_tot==2)
		dessiner_les_champs(bipole[0].chp_clc_dipole,bipole[1].chp_clc_dipole,2.);
	    else if(n_poles_ou_barreaux_tot==1)
		dessiner_les_champs(bipole[0].chp_clc_dipole,null,2.);
	    fais_lignes_de_champ();
	}	    
	void fais_lignes_de_champ(){
	    int ecart=20;
	    int nb_lignes=16;
	    if(i_ens==1){
		ecart=6;
		nb_lignes=8;
	    }
	    point centre_multipole=new point(bipole[0].centre);
	    int iq=0;
	    if(n_poles_ou_barreaux_tot==2){
		centre_multipole.additionne(bipole[1].centre);
		centre_multipole.multiplie_cst(0.5);
	    }
	    int npassages=1;
	    if(n_poles_ou_barreaux_tot==2&&i_ens==0)
		npassages=2;
	    for (int ipass=0;ipass<=npassages;ipass++){
		for (int i=-nb_lignes;i<=nb_lignes;i++){
		    point point_sur_la_ligne=new point(centre_multipole.x,centre_multipole.y+ecart*i);
		    if(n_poles_ou_barreaux_tot==2)
			point_sur_la_ligne.assigne(bipole[0].centre.x,bipole[0].centre.y+ecart*i);
		    point chpt=new point(zer);
		    int x=0,y=0;point unitaire_suivant_champ=new point(zer);
		    double pt_x_prec=0,pt_y_prec=0,diff_x=0,diff_x_prec=0,diff_y=0,diff_y_prec=0;
		    point pt_prec=new point(zer);
		    point diff=new point(zer);point diff_prec=new point(zer);
		    grp_c.setColor(Color.red);
		    for (int kk=0;kk<400;kk++){
			x=(int)Math.round(point_sur_la_ligne.x);
			y=(int)Math.round(point_sur_la_ligne.y);
			grp_c.drawLine(x,y,x,y);
			coco=centre_multipole.x-(point_sur_la_ligne.x-centre_multipole.x);
			x=(int)Math.round(coco);
			grp_c.drawLine(x,y,x,y);
			chpt.assigne(bipole[0].champ_en_un_point(point_sur_la_ligne,0.));
			if(n_poles_ou_barreaux_tot==2)
			    chpt.additionne(bipole[1].champ_en_un_point(point_sur_la_ligne,0.));
			
			unitaire_suivant_champ.assigne_diviseur(chpt,chpt.longueur());
			if(n_poles_ou_barreaux_tot==2&&i_ens==1)
			    unitaire_suivant_champ.multiplie_cst(0.25);  
			if(ipass==1)
			    unitaire_suivant_champ.multiplie_cst(-1.);
			point_sur_la_ligne.additionne(unitaire_suivant_champ);
			diff.assigne_soustrait(point_sur_la_ligne,pt_prec);
			if(i_ens==1){
			    //System.out.println("kk "+kk+" diff_x "+(float)diff_x+" diff_x_prec "+(float)diff_x_prec);
			    if(i_demarre==0)
				if((diff.x*diff_prec.x>=0&&diff.y*diff_prec.y<=0||point_sur_la_ligne.y>bottom_ens||point_sur_la_ligne.y<top_ens)&&kk>2)
				    break;
			}else{
			    if(i_demarre==0)
				if(point_sur_la_ligne.x>right_ens||point_sur_la_ligne.x<left_ens)
				    break;
			}
			diff_prec.assigne(diff);
			pt_prec.assigne(point_sur_la_ligne);
		    }		
		}
	    }	 
	}
   	
	class dipole_de_pres extends dipole {
	    public dipole_de_pres(point centre1,double q_ou_i1,point lambda_ou_s1){
		super(centre1,q_ou_i1,lambda_ou_s1);
		de_loin=false;
	    }
	    public void paint(){
	    }
	    boolean est_dedans(point dist){
		return true;	    
	    }
	    public void elimine(){
	    }
	}
	class dipole_de_loin extends dipole {
	    dipole_de_loin(point centre1,point dipole1){
		super(centre1,dipole1);
		de_loin=true;
	    }
	    public void paint(){
	    }

	    public void elimine(){
	    }
	    boolean est_dedans(point dist){
		return true;	    
	    }

	}
    }
    class biparam{
	double a,b;    
	biparam(double ai, double bi){
	    a=ai;b=bi;
	}
	void assigne(biparam bp){
	    a=bp.a;b=bp.b;
	}
    }
    class biboolean{
	boolean pt_fn_ligne,approche_fin_ligne;    
	biboolean(boolean ai,boolean bi){
	    pt_fn_ligne=ai;approche_fin_ligne=bi;
	}
	void assigne(biboolean bp){
	    pt_fn_ligne=bp.pt_fn_ligne;approche_fin_ligne=bp.approche_fin_ligne;
	}
	void assigne(boolean ai,boolean bi){
	    pt_fn_ligne=ai;approche_fin_ligne=bi;
	}
    }
    public void cree_dipoles_ou_barreau (){

	//float q_lam=1.e-19*1.e-10;
	point ctr=new point(300,300);
	if(demo)
	    ctr.multiplie_cst(0.5);
	if(i_demarre<=2){
	    multipol=new multipole();
	    mltpl_brr=multipol;
	}else{
	    /*
	      if(i_demarre>=3||i_demarre<=5){
	      ctr.assigne(318,215);
	      n_poles_ou_barreaux_tot=1;
	      if(i_demarre==3)
	      pole_ou_barreau[0]=new barreau_dielectrique(ctr,40,200,0);
	      else if (i_demarre==4){
	      ctr.y+=100;
	      pole_ou_barreau[0]=new barreau_dielectrique(ctr,80,0,0);//en fait bille 
	      }else
	    */
	    if (i_demarre==3){
		ctr.assigne(320,220);
		//pole_ou_barreau[0]=new barreau_cylindrique(ctr,20,400,0);
		pole_ou_barreau[0]=new barreau_cylindrique(ctr,80,200,0);
		//pole_ou_barreau[0]=new barreau_cylindrique(ctr,160,80,0);
		//pole_ou_barreau[0]=new barreau_cylindrique(ctr,40,400,0);
		//pole_ou_barreau[0]=new barreau_cylindrique(ctr,20,400,0);
		//pole_ou_barreau[0]=new barreau_cylindrique(ctr,1,200,0);
		//ctr.assigne(420,420);
		//pole_ou_barreau[0]=new barreau_cylindrique(ctr,360,120,0);
	    }else if (i_demarre==4){
		ctr.assigne(320,320);
		pole_ou_barreau[0]=new ellipsoide(ctr,80,80,0);
		//pole_ou_barreau[0]=new bille(ctr,80,0,0);
	    }else if (i_demarre==5){
		ctr.assigne(320,320);
		//pole_ou_barreau[0]=new ellipsoide(ctr,80,80,0);
		pole_ou_barreau[0]=new ellipsoide(ctr,80,80,0);
	    }
	    else if(i_demarre==6){
		ctr.y+=100;
		pole_ou_barreau[0]=new barreau_cylindrique_infini(ctr,80,200,0);
	    }
	    mltpl_brr=pole_ou_barreau[0];
	}	
    }
}
class solution_eq_lineaires{
    double matrix[][]=new double[4][4];
    double inverse_matrix[][]=new double[4][4];
    double secmem[]=new double[4];
    double coef[]=new double[4];
    int dim;
    public solution_eq_lineaires(int dim1,double[][] matrix1,double[] secmem1){
	dim=dim1;
	for (int i=0;i<dim;i++){
	    secmem[i]=secmem1[i];
	    for (int j=0;j<dim;j++)
		matrix[i][j]=matrix1[i][j]; 
	}

    }
   void assigne_secmem(double[] secm){
	    for (int j=0;j<dim;j++)
		    secmem[j]=secm[j];
    }
    public double[] resout(){
	double pivot, souspiv;
	double essai[][]=new double[4][4];
	for (int i=0;i<dim;i++)
	    for (int j=0;j<dim;j++){
		essai[i][j]=matrix[i][j]; 
		if (i==j)
		    inverse_matrix[i][j]=1.0;
		else
		    inverse_matrix[i][j]=0.0;
	    }
	for (int i=0;i<dim;i++){
	    pivot=essai[i][i];
	    for (int j=i;j<dim;j++)
		essai[i][j]=essai[i][j]/pivot;
	    for (int j=0;j<=i;j++)
		inverse_matrix[i][j]=inverse_matrix[i][j]/pivot;
	    //System.out.println("i "+i+" pivot "+pivot+" essai[i][i] "+essai[i][i]);
	    for (int ii=0;ii<dim;ii++){
		if (ii!=i) {
		    souspiv=essai[ii][i];
		    for (int j=i;j<dim;j++)
			essai[ii][j]-=essai[i][j]*souspiv;
		    for (int j=0;j<=i;j++)
			inverse_matrix[ii][j]-=inverse_matrix[i][j]*souspiv;
		}
	    }
	}
	/*
	for (int i=0;i<dim;i++)
	    System.out.println(" essai i "+i+" "+(float)essai[i][0]+" "+(float)essai[i][1]+" "+(float)essai[i][2]+" "+(float)essai[i][3]);
	*/
	for (int i=0;i<dim;i++){
	    for (int j=0;j<dim;j++){
		essai[i][j]=0.;	
		for (int k=0;k<4;k++)
		    essai[i][j]+=matrix[i][k]*inverse_matrix[k][j];
	    }
	    // System.out.println("$$ essai i "+i+" "+(float)essai[i][0]+" "+(float)essai[i][1]+" "+(float)essai[i][2]+" "+(float)essai[i][3]);
	}
	/*
	for (int i=0;i<dim;i++)
	    System.out.println(" matrix  i "+i+" "+(float)matrix[i][0]+" "+(float)matrix[i][1]+" "+(float)matrix[i][2]+" "+(float)matrix[i][3]);
	for (int i=0;i<dim;i++){
	    System.out.println(" inv_mat_in_x  i "+i+" "+(float)inverse_matrix[i][0]+" "+(float)inverse_matrix[i][1]+" "+(float)inverse_matrix[i][2]+" "+(float)inverse_matrix[i][3]);
	}
	System.out.println(" secmem "+(float)secmem[0]+" "+(float)secmem[1]+" "+(float)secmem[2]+" "+(float)secmem[3]);
	*/
	for (int i=0;i<dim;i++){
	    coef[i]=0.;
	    for (int j=0;j<dim;j++)
		coef[i]+=inverse_matrix[i][j]*secmem[j];
	}
	return coef;
    }

}
class vecteur{
    static final double pi=3.141592652;
    point chp;point pnt;
    public vecteur (vecteur v){
	chp=new point(v.chp);
	pnt=new point(v.pnt);
    }
    public vecteur (point v, point p){
	chp=new point(v);
	pnt=new point(p);
    }
    public void assigne ( vecteur v){
	chp.assigne(v.chp);
	pnt.assigne(v.pnt);
    }
    public void assigne(point v, point p){
	chp.assigne(v);
	pnt.assigne(p);
    }
    public void assigne_soustrait ( vecteur v,vecteur www){
	chp.assigne_soustrait(v.chp,www.chp);
	pnt.assigne_soustrait(v.pnt,www.pnt);
    }
    public void dessine( double fzoom,double fct_zm_sspl,Graphics g,Color col){
	
	int x_ini=(int)Math.round(pnt.x); int y_ini=(int)Math.round(pnt.y);
	int x_fin=x_ini+(int)(chp.x*40/fzoom*fct_zm_sspl);
	int y_fin=y_ini+(int)(chp.y*40/fzoom*fct_zm_sspl);
	g.setColor(col);
	//System.out.println(" x_ini "+x_ini+" y_ini "+y_ini+" x_fin "+x_fin+" y_fin "+y_fin);	    
	g.drawLine(x_ini, y_ini, x_fin, y_fin);
	double direct=chp.direction()*pi/180.;
	double dir=direct+3*pi/4.;
	int xf1=x_fin+(int)(7.0*Math.cos(dir));int yf1=y_fin+(int)(7.0*Math.sin(dir));
	g.drawLine(x_fin, y_fin, xf1, yf1);
	dir=direct-3*pi/4.;
	xf1=x_fin+(int)(7.0*Math.cos(dir));yf1=y_fin+(int)(7.0*Math.sin(dir));
	g.drawLine(x_fin, y_fin, xf1, yf1);
    }
    public void print(String st){
	System.out.println(st+ " chp.x "+(float)chp.x+" chp.y "+(float)chp.y+" pnt.x "+(float)pnt.x+" pnt.y "+(float)pnt.y);
    }
}
class vecteur_y_z{
    static final double pi=3.141592652;
    point_y_z chp;point_y_z pnt;
    public vecteur_y_z (vecteur_y_z v){
	chp=new point_y_z(v.chp);
	pnt=new point_y_z(v.pnt);
    }
    public vecteur_y_z (point_y_z v, point_y_z p){
	chp=new point_y_z(v);
	pnt=new point_y_z(p);
    }
    public void assigne ( vecteur_y_z v){
	chp.assigne(v.chp);
	pnt.assigne(v.pnt);
    }
    public void assigne(point_y_z v, point_y_z p){
	chp.assigne(v);
	pnt.assigne(p);
    }
    public void assigne_soustrait ( vecteur_y_z v,vecteur_y_z www){
	chp.assigne_soustrait(v.chp,www.chp);
	pnt.assigne_soustrait(v.pnt,www.pnt);
    }
    public void print(String st){
	System.out.println(st+ " chp.y "+(float)chp.y+" chp.z "+(float)chp.z+" pnt.y "+(float)pnt.y+" pnt.z "+(float)pnt.z);
    }

    public void dessine( double fzoom,double fct_zm_sspl,Graphics g,Color col){
	
	int x_ini=(int)Math.round(pnt.z); int y_ini=(int)Math.round(pnt.y);
	int x_fin=x_ini+(int)(chp.z*40/fzoom*fct_zm_sspl);
	int y_fin=y_ini+(int)(chp.y*40/fzoom*fct_zm_sspl);
	g.setColor(col);
	g.drawLine(x_ini, y_ini, x_fin, y_fin);
	double direct=chp.direction()*pi/180.;
	double dir=direct+3*pi/4.;
	int xf1=x_fin+(int)(7.0*Math.cos(dir));int yf1=y_fin+(int)(7.0*Math.sin(dir));
	g.drawLine(x_fin, y_fin, xf1, yf1);
	dir=direct-3*pi/4.;
	xf1=x_fin+(int)(7.0*Math.cos(dir));yf1=y_fin+(int)(7.0*Math.sin(dir));
	g.drawLine(x_fin, y_fin, xf1, yf1);
    }
}
class point_y_z{
    double y=0.,z=0.;
    static final double pi=3.141592652;
    point_y_z(double y1,double z1){
	y=y1;z=z1;
    }
    point_y_z(point_y_z p){
	y=p.y;z=p.z;
    }
    void assigne(double y1,double z1){
	y=y1;z=z1;
    }
    void assigne(point_y_z pt){
	y=pt.y;z=pt.z;
    }
    public void assigne_facteur(point_y_z a,double b){
	y=a.y*b;z=a.z*b;
    }

    void assigne_soustrait(point_y_z pt,point_y_z qt){
	y=pt.y-qt.y;z=pt.z-qt.z;
    }
    void additionne(point_y_z pt){
	y+=pt.y;z+=pt.z;
    }
    public void additionne_facteur(point_y_z a,double b){
	y+=b*a.y;
	z+=b*a.z;
    }
    public double longueur(){
	return(Math.sqrt(y*y+z*z));
    }
    public double longueur_carre(){
	return (y*y+z*z);
    }
    public void additionne_point_facteur(point_y_z a,double c){
	y+=c*a.y;
	z+=c*a.z;
    }
    public double scalaire(point_y_z a){
	return y*a.y+z*a.z;
    }
     public  double vectoriel(point_y_z b){
	 return y*b.z-z*b.y;
     }
    public void rotation(double c_ang,double s_ang){
	double y_p=y;double z_p=z;
	y=c_ang*y_p-s_ang*z_p;
	z=s_ang*y_p+c_ang*z_p;
    }

    public void multiplie_cst(double c){
	y*=c;
	z*=c;
    }
    public void divise_cst(double c){
	y/=c;
	z/=c;
    }
    public double direction(){
	double angle=0;
	if((double)Math.abs(z)>(double)Math.abs(y)){
	    angle=(double)180./pi*(double)Math.asin(y/longueur());
	    if(z<0.)
		if(y>0)
		    angle=(double)180.-angle;
		else
		    angle=-(double)180.-angle;
	}else{
	    angle=(double)180./pi*(double)Math.acos(z/longueur());
	    if(y<0.)angle=-angle;
	}
	return angle;
    }

    public void print(String st){
	System.out.println(st+ " y "+(float)y+" z "+(float)z);
    }
}
class point implements Cloneable{
    static final double pi=3.141592652;
    double x,y;    
    point(double xi, double yi){
	x=xi;y=yi;
    }
    point(point a){
	x=a.x;y=a.y;
    }
    point(point a,double b){
	x=a.x*b;y=a.y*b;
    }
    public Object clone(){
	try{
	    //point e=(point)super.clone();
	    //return e;
	    return super.clone();
	}
	catch (CloneNotSupportedException e){
	    return null;
	}
	
    }
    public void zero(){
	x=0.;y=0.;
    }
    public double direction(){
	double angle=0;
	if((double)Math.abs(x)>(double)Math.abs(y)){
	    angle=(double)180./pi*(double)Math.asin(y/longueur());
	    if(x<0.)
		if(y>0)
		    angle=(double)180.-angle;
		else
		    angle=-(double)180.-angle;
	}else{
	    angle=(double)180./pi*(double)Math.acos(x/longueur());
	    if(y<0.)angle=-angle;
	}
	return angle;
    }
    public void projections(double cosinus,double sinus)
    {
        double x_p=x;double y_p=y;
        x=-sinus*x_p+cosinus*y_p;
        y=cosinus*x_p+sinus*y_p;
    }
     public void assigne(double xi, double yi){
	x=xi;y=yi;
    }
    public void assigne(point a){
	x=a.x;y=a.y;
    }
    public void assigne_oppose(point a){
	x=-a.x;y=-a.y;
    }
    public void assigne_additionne(point a,point b){
	x=a.x+b.x;y=a.y+b.y;
    }
    public void assigne_soustrait(point a,point b){
	x=a.x-b.x;y=a.y-b.y;
    }
    public void assigne_facteur(point a,double b){
	x=a.x*b;y=a.y*b;
    }
    public void assigne_diviseur(point a,double b){
	x=a.x/b;y=a.y/b;
    }
    public double distance_carre(point pt){
	double d;
	d=(double)Math.pow(x-pt.x,2)+(double)Math.pow(y-pt.y,2);
	return d;
    }
    public void multiplie_cst(double a){
	x*=a;
	y*=a;
    }
    public void divise_cst(double a){
	x/=a;
	y/=a;
    }
    public void additionne(double xx,double yy){
	x+=xx;
	y+=yy;
    }
    public void additionne(point a){
	x+=a.x;
	y+=a.y;
    }
    public void soustrait(point a){
	x-=a.x;
	y-=a.y;
    }
    public void soustrait(double xx,double yy){
	x-=xx;
	y-=yy;
    }
    public void additionne_facteur(point a,double b){
	x+=b*a.x;
	y+=b*a.y;
    }
    public void additionne_diviseur(point a,double b){
	x+=a.x/b;
	y+=a.y/b;
    }
    public void soustrait_facteur(point a,double b){
	x-=b*a.x;
	y-=b*a.y;
    }
    public void soustrait_diviseur(point a,double b){
	x-=a.x/b;
	y-=a.y/b;
    }
    public double distance(point pt){
	double d;
	d=(double)Math.sqrt((double)Math.pow(x-pt.x,2)+(double)Math.pow(y-pt.y,2));
	return d;
    }
    public double longueur(){
	return ((double)Math.sqrt((double)Math.pow(x,2)+(double)Math.pow(y,2)));
    }
    public double longueur_carre(){
	return ((double)Math.pow(x,2)+(double)Math.pow(y,2));
    }
    public double scalaire(point a){
	return x*a.x+y*a.y;
    }
    public double produit_vectoriel(point a){
	return x*a.y-y*a.x;
    }
    public void rotation(double angle){
	double cos=(double)Math.cos(angle);double sin=(double)Math.sin(angle);
	double x_p=x;double y_p=y;
	x=cos*x_p-sin*y_p;
	y=sin*x_p+cos*y_p;
    }
    public void rotation(double c_ang,double s_ang){
	double x_p=x;double y_p=y;
	x=c_ang*x_p-s_ang*y_p;
	y=s_ang*x_p+c_ang*y_p;
    }
    public void print(String st){
	float xx=(float)x;float yy=(float)y;float l=(float)longueur();
	System.out.println(st+ " x "+xx+" y "+yy+" longueur() "+l);
    }
    public void print_sl(String st){
	float xx=(float)x;float yy=(float)y;
	System.out.println(st+ " x "+xx+" y "+yy);
    }
}
class point_xyz implements Cloneable{
    static final double pi=3.141592652;
    double x,y,z;    
    point_xyz(double xi, double yi, double zi){
	x=xi;y=yi;z=zi;
    }
    point_xyz(point_xyz a){
	x=a.x;y=a.y;z=a.z;
    }
    point_xyz(point_xyz a,double b){
	x=a.x*b;y=a.y*b;z=a.z*b;
    }
    point_xyz(double xx,point_y_z b){
	x=xx;y=b.y;z=b.z;
    }
    point_xyz(point a, double zz){
	x=a.x;y=a.y;z=zz;
    }
    public Object clone(){
	try{
	    //point e=(point)super.clone();
	    //return e;
	    return super.clone();
	}
	catch (CloneNotSupportedException e){
	    return null;
	}
	
    }
    public void zero(){
	x=0.;y=0.;z=0.;
    }
     public void assigne(double xi, double yi, double zi){
	x=xi;y=yi;z=zi;
    }
    public void assigne(double xx,point_y_z b){
	x=xx;y=b.y;z=b.z;
    }
    public void assigne(point a, double zz){
	x=a.x;y=a.y;z=zz;
    }

    public void assigne(point_xyz a){
	x=a.x;y=a.y;z=a.z;
    }
    public void assigne_oppose(point_xyz a){
	x=-a.x;y=-a.y;z=-a.z;
    }
    public void assigne_additionne(point_xyz a,point_xyz b){
	x=a.x+b.x;y=a.y+b.y;z=a.z+b.z;
    }
    public void assigne_soustrait(point_xyz a,point_xyz b){
	x=a.x-b.x;y=a.y-b.y;z=a.z-b.z;
    }
    public void assigne_facteur(point_xyz a,double b){
	x=a.x*b;y=a.y*b;z=a.z*b;
    }
    public void assigne_diviseur(point_xyz a,double b){
	x=a.x/b;y=a.y/b;z=a.z/b;
    }
    point extrait_point(){
	return new point(x,y);
    }
    point_y_z extrait_point_y_z(){
	return new point_y_z(y,z);
    }
    public double distance_carre(point_xyz pt){
	double d;
	d=(double)Math.pow(x-pt.x,2)+(double)Math.pow(y-pt.y,2)+(double)Math.pow(z-pt.z,2);
	return d;
    }
    public void multiplie_cst(double a){
	x*=a;y*=a;z*=a;
    }
    public void divise_cst(double a){
	x/=a;y/=a;z/=a;
    }
    public void additionne(double xx,double yy,double zz){
	x+=xx;y+=yy;z+=zz;
    }
    public void additionne(double xx,point_y_z b){
	x+=xx;y+=b.y;z+=b.z;
    }
    public void additionne(point_xyz a){
	x+=a.x;y+=a.y;z+=a.z;
    }
    public void soustrait(point_xyz a){
	x-=a.x;y-=a.y;z-=a.z;
    }
    public void soustrait(double xx,double yy,double zz){
	x-=xx;y-=yy;z-=zz;
    }
    public void additionne_facteur(point_xyz a,double b){
	x+=b*a.x;y+=b*a.y;z+=b*a.z;
    }
    public void soustrait_facteur(point_xyz a,double b){
	x-=b*a.x;y-=b*a.y;z-=b*a.z;
    }
    public double distance(point_xyz pt){
	double d;
	d=(double)Math.sqrt((double)Math.pow(x-pt.x,2)+(double)Math.pow(y-pt.y,2)+(double)Math.pow(z-pt.z,2));
	return d;
    }
    public double longueur(){
	return ((double)Math.sqrt(x*x+y*y+z*z));
    }
    public double longueur_carre(){
	return (x*x+y*y+z*z);
    }
    public double scalaire(point_xyz a){
	return x*a.x+y*a.y+z*a.z;
    }
    public point_xyz vectoriel(point_xyz a){
	return new point_xyz(y*a.z-z*a.y,z*a.x-x*a.z,x*a.y-y*a.x);
    }
     public void print(String st){
	float xx=(float)x;float yy=(float)y;float zz=(float)z;float l=(float)longueur();
	//System.out.println(st+ " x "+xx+" y "+yy+" z "+zz+" longueur() "+l);
	System.out.println(st+ " x "+(float)xx+" y "+(float)yy+" z "+(float)zz);
    }
}
abstract class triple_entier{
    int r,v,b;
    public triple_entier(int r1,int v1,int b1){
	r=r1;v=v1;b=b1;
    }
    public triple_entier(couleur c1){
	r=c1.r;v=c1.v;b=c1.b;
    }
    public void assigne(couleur c){
	r=c.r;v=c.v;b=c.b;
    }
    public void assigne_facteur(triple_entier c,double d){
	r=(int)(c.r*d);v=(int)(c.v*d);b=(int)(c.b*d);
    }
    public void remise_a_zero(){
	r=0;v=0;b=0;
    }
    public void multiplie(double f){
	r=(int)(r*f);v=(int)(v*f);b=(int)(b*f);
    }
    public void divise(double f){
	r=(int)(r/f);v=(int)(v/f);b=(int)(b/f);
    }
    public boolean egale(couleur a){
	return ((r==a.r)&&(v==a.v)&&(b==a.b));
    }
    abstract public void assigne(int r1,int v1,int b1);
    public void print(String st){
	System.out.println(st+ " r "+r+" v "+v+" b "+b);
    }
}
class triple_int extends triple_entier{
    public triple_int(int r1,int v1,int b1){
	super(r1,v1,b1);
    }
    public triple_int(couleur c1){
	super(c1);
    }
    public void assigne(couleur c){
	r=c.r;v=c.v;b=c.b;
    }
    public void assigne(int r1,int v1,int b1){
	r=r1;v=v1;b=b1;
    }
    public void assigne_facteur(triple_int c,double d){
	r=(int)(c.r*d);v=(int)(c.v*d);b=(int)(c.b*d);
    }
}
class couleur extends triple_entier{
    Color col;
    public couleur(int r1,int v1,int b1){
	super(r1,v1,b1);
	col=new Color(r,v,b);
	//	    if(col==rouge) marche pas!!!!!
    }
    public couleur(couleur c1){
	super(c1);
	col=new Color(c1.r,c1.v,c1.b);
    }
    public void assigne(couleur c){
	col=c.col;
	r=c.r;v=c.v;b=c.b;
    }
    public void assigne(triple_double c){
	r=(int)c.r;v=(int)c.v;b=(int)c.b;
	col=new Color(r,v,b);

    }
    public void multiplie(double f){
	r=(int)(r*f);v=(int)(v*f);b=(int)(b*f);
    }
    public void assigne(int r1,int v1,int b1){
	r=r1;v=v1;b=b1;
	col=new Color(r,v,b);
    }
}

