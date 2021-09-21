import java.util.Random;
import java.util.Scanner;

//Inicijalizira se poljana. Igrac se stavlja na sredinu poljane.
//Zatim se inicijaliziraju gusari.
//Pocinjemo na nivou tezine 1 i ukoliko pobjedimo taj nivo prelazimo na sljedeci nivo.
//Svaki novi nivo je tezi od prethonog(vise ciljeva,gusara i otoka).
//Ako nas gusar uhvati u bilo kojem trenutku tu je kraj igrice i pocinjemo ponovo od nivoa 1.


public class Igra {
	private Poljana poljana;
	private Igrac igrac;
	private Gusari[] gusari;
	private int stanje;
	
	public final static int neodluceno = 0;
	public final static int pobjeda = 1;
	public final static int poraz = -1;
	
	//Igra u konzoli
	public static void main(String[] args) {
		System.out.println("Dobro dosli u igricu");
		
		int tezina = 1;
		
		boolean pomocna = Igra.meni();
		
		while(pomocna) {
			Igra igra = new Igra(tezina);
			System.out.print(igra);
			while(igra.dajStanje() == neodluceno) {
				System.out.print("Unesite smjer kretanja: ");
				Scanner input = new Scanner(System.in);
				String unos = input.next();
				igra.igraj(unos);
				System.out.print(igra);		
			}
			if(igra.dajStanje() == pobjeda) {
				tezina++;
				System.out.println("Presli ste na novi nivo");		
			}
			else {
				System.out.println("Izgubili ste");
				break;		
			}	
		}
	}
	
	public Igra(int tezina) {
		stanje = neodluceno;
		poljana = new Poljana(tezina);
		igrac = new Igrac(poljana.dajDimenziju() / 2,poljana.dajDimenziju() /2);
		
		//Broj gusara i inicijalizacija istih na poljani po istom principu kao i za otoke i ciljeve.
		int brojGusara = tezina;
		gusari = new Gusari[brojGusara];
		Random random = new Random();
			
		for(int i = 0; i < brojGusara; i++) {
			int x , y;
			do {		
				x = random.nextInt(poljana.dajDimenziju());
				y = random.nextInt(poljana.dajDimenziju());
			}while(poljana.dajPolje(x,y).daLiJeOtok() || poljana.dajPolje(x, y).daLiJeCilj() || poljana.dajPolje(x, y).daLiJeGusar() || poljana.udaljen(x, y, poljana.dajUdaljenost()));
			
			//Svakom sljedecem gusaru dodjeljujemo drugi tip.
			if(i % 2 == 0)
				this.gusari[i] = new Gusari(x,y, 0);
			else
				this.gusari[i] = new Gusari(x,y, 1);
			poljana.dajPolje(x, y).postaviGusara(true);
		}
	}
		
	public static boolean meni(){
		Scanner input = new Scanner(System.in);
		System.out.println("1. Igraj");
		System.out.println("2. Pravila");
		System.out.println("3. Izadji");
		System.out.print("Izaberite opciju: ");
		boolean pomocna;
		int opcija = input.nextInt();
		if(opcija == 1)
			pomocna = true;
		else if(opcija == 3)
			pomocna = false;
		else if(opcija == 2) {
			System.out.println("Cilj igrice je pokupiti sve ciljeve na poljani a pri tome");
			System.out.println("ne doci u susret sa opasnim gusarima. Krecete se na tipke a,s,d,w. ");
			System.out.println("Svaki novi nivo je tezi od prethodnog.");
			System.out.println("Oznake za gusare,ciljeve,otoke i igracu su slijedece: ");
			System.out.println("U konzoli: Igrac:#, Gusari:G, Otoci: O, Ciljevi:C");
			System.out.println("Gui: Igrac:Crna boja, Gusari:Plava boja, Ciljevi:Crvena boja, Otoci:Zuta boja");
			return meni();
		}
		else
			return meni();
		return pomocna;
		
	}
	
	public void igraj(String unos) {
		
		boolean provjera = false;
		
		
		if(unos.charAt(0) == 'A' || unos.charAt(0) == 'a') {
			provjera = provjera(igrac.dajX(),igrac.dajY(),4);
		}
		else if(unos.charAt(0) == 'D' || unos.charAt(0) == 'd') {
			provjera = provjera(igrac.dajX(),igrac.dajY(),6);
		}
		else if(unos.charAt(0) == 'S' || unos.charAt(0) == 's') {
			provjera = provjera(igrac.dajX(),igrac.dajY(),2);
		}
		else if(unos.charAt(0) == 'W' || unos.charAt(0) == 'w') {
			provjera = provjera(igrac.dajX(),igrac.dajY(),8);
		}
		//Ukoliko je ilegalan potez unesen trazi se novi unos.
		if(provjera) {
			igrac.pomjeri(unos.charAt(0));
			this.apdejtujIgru();
		}
	}
	//Provejravamo da li je potez legalan.
	public boolean provjera(int x, int y, int smjer) {
		boolean provjera = true;
		if(smjer == 4) {
			if(y == 0 || poljana.dajPolje(x, y-1).daLiJeOtok())
				provjera = false;
		}
		else if(smjer == 6) {
			if(y == 10 || poljana.dajPolje(x, y+1).daLiJeOtok())
				provjera = false;
		}
		if(smjer == 2) {
			if(x == 10 || poljana.dajPolje(x+1, y).daLiJeOtok())
				provjera = false;
		}
		if(smjer == 8) {
			if(x == 0 || poljana.dajPolje(x-1, y).daLiJeOtok())
				provjera = false;
		}
		return provjera;
	}
	public void apdejtujIgru() {
		this.poljana.smanjiCilj(igrac.dajX(), igrac.dajY());
		if(this.poljana.brojCiljeva() == 0)
			stanje = pobjeda;
		else
			apdejtujGusare();
	}
	public void apdejtujGusare() {
		int x = igrac.dajX();
		int y = igrac.dajY();
		int brojGusara = this.gusari.length;
		//Ako je gusar mrtav zanemarujemo ga.
		for(int i = 0; i < brojGusara; i++) {
			if(!gusari[i].daLiJeZiv())
				continue;
	
			poljana.dajPolje(gusari[i].dajX(), gusari[i].dajY()).postaviGusara(false);
			gusari[i].pomjeri(x, y);
			//Ako je neki gusar uhvatio igraca.
			if(gusari[i].dajX() == igrac.dajX() && gusari[i].dajY() == igrac.dajY())
				this.stanje = poraz;	
		}
		for(int i = 0; i < brojGusara; i++) {
			if(!gusari[i].daLiJeZiv())
				continue;
			//Ako se gusar zabije u otok on nestaje.
			if(poljana.dajPolje(gusari[i].dajX(), gusari[i].dajY()).daLiJeOtok())
				gusari[i].postaviZiv(false);
			//Ako se dva gusara sudare oni nestaju i nastaje otok.
			for(int j = 0; j < brojGusara; j++) {
				if(i == j)
					continue;
				if(!gusari[j].daLiJeZiv())
					continue;
				if(gusari[i].dajX() == gusari[j].dajX() && gusari[i].dajY() == gusari[j].dajY()) {
					gusari[i].postaviZiv(false);
					gusari[j].postaviZiv(false);
					poljana.dajPolje(gusari[i].dajX(), gusari[i].dajY()).postaviOtok(true);
					poljana.smanjiCilj(gusari[i].dajX(), gusari[i].dajY());
				}
			}
			//Ako je gusar ostao ziv apdejtujemo ga.
			if(gusari[i].daLiJeZiv())
				poljana.dajPolje(gusari[i].dajX(), gusari[i].dajY()).postaviGusara(true);
		}
		
	}
	public int dajStanje() {
		return stanje;
	}
	public Poljana dajPoljanu() {
		return poljana;
	}
	public Igrac dajIgraca() {
		return igrac;
	}
	//Ispis igrice u konzoli.
	public String toString() {
		String s = new String();
		for(int i = 0; i < poljana.dajDimenziju(); i++) {
			for(int j = 0; j < poljana.dajDimenziju(); j++) {
				if(i == igrac.dajX() && j == igrac.dajY())
					s = s + "#";
				else if(poljana.dajPolje(i, j).daLiJeGusar())
					s = s + "G";
				else if(poljana.dajPolje(i, j).daLiJeCilj())
					s = s + "C";
				else if(poljana.dajPolje(i, j).daLiJeOtok())
					s = s + "O";
				else 
					s = s + ".";
			}
			s = s + '\n';
		}
		return s;
	}
	
	
}
