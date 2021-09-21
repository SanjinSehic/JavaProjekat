import java.util.Random;

//Sluzi za inicijalizaciju poljane sa otocima i ciljevima.
public class Poljana {
	private Polje[][] polja;
	private int otoci;
	private int ciljevi;
	//Stavili smo dimenziju 11(jer smatram da je optimalna) ali ona se moze mijenjati po zelji.
	//Udaljenost sluzi za to da se gusari,otoci ili ciljevi ne mogu stvarati tik do igraca.
	public final static int dimenzija = 11;
	public final static int udaljenost = 1;
	
	public Poljana(int tezina) {
		// Generisemo prazna polja
		polja = new Polje[dimenzija][dimenzija];
		for(int i = 0; i < dimenzija; i++) {
			for(int j = 0; j < dimenzija; j++) {
				polja[i][j] = new Polje(i,j);
			}
		}
		//Generisemo otoke i ciljeve
		//Broj otoka i ciljeva se takodjer kao i dimenzija moze mijenjati po zelji.
		this.otoci = tezina + 4;
		this.generisiOtoke(this.otoci);
		this.ciljevi = tezina + 1;
		this.generisiCiljeve(this.ciljevi);
		
	}
	
	private void generisiOtoke(int otoci) {
		Random random = new Random();
		
		for(int i = 0; i < otoci; i++) {
			int x , y;
			do {
				//Stvaramo otok random na polju koje je prazno i udaljeno od igraca.
					x = random.nextInt(dimenzija);
					y = random.nextInt(dimenzija);
			}while(this.polja[x][y].daLiJeOtok() || udaljen(x,y,udaljenost));
			//Provjeravamo da li se na tom polju vec nalazi otok i udaljenost.
			
			this.polja[x][y].postaviOtok(true);
		}
	}
	private void generisiCiljeve(int ciljevi) {
		Random random = new Random();
		//Ista logika kao i za otoke.
		for(int i = 0; i < ciljevi; i++) {
			int x , y;
			do {
					x = random.nextInt(dimenzija);
					y = random.nextInt(dimenzija);
			}while(this.polja[x][y].daLiJeOtok() || this.polja[x][y].daLiJeCilj() || udaljen(x,y,udaljenost));			
			
			this.polja[x][y].postaviCilj(true);
		}
	}
	//Provjera da li je polje udaljeno od igraca za udaljenost.
	public boolean udaljen(int x, int y,int udaljenost) {
		boolean pomocna = false;
		for(int i = - udaljenost; i <= udaljenost; i++) {
			for(int j = - udaljenost; j <= udaljenost; j++) {
				if(dimenzija / 2 + i == x  && dimenzija / 2 + j == y)
					pomocna = true;
			}
		}
		return pomocna;
	}
	//Kada igrac obidje jedan cilj brisemo ga.
	public void smanjiCilj(int x, int y) {
		if(polja[x][y].daLiJeCilj()) {
			polja[x][y].postaviCilj(false);
			this.ciljevi--;
		}
	}
	public Polje dajPolje(int x, int y) {
		return polja[x][y];
	}
	
	public int brojCiljeva() {
		return this.ciljevi;
	}
	public int dajDimenziju() {
		return dimenzija;
	}
	public int dajUdaljenost() {
		return udaljenost;
	}
}
