import java.util.Random;
//Sadrzi informaciju o koordinatama gusara,tip i o tome da li je gusar jos ziv
//Imamo 2 tipa gusara - jedan koji uvijek prati igraca(random po koloni ili redu)
//I drugi tip koji se random krece po poljani

public class Gusari {
	private int x;
	private int y;
	private int tip;
	private boolean daLiJeZiv;
	
	public final static int pametniGusar = 0;
	public final static int neBasPametniGusar = 1;
	
	public Gusari(int x, int y, int tip) {
		this.x = x;
		this.y = y;
		this.daLiJeZiv = true;
		if(tip == 0)
			tip = pametniGusar;
		else
			tip = neBasPametniGusar;
	}
	
	public void pomjeri(int a, int b) {
		if(tip == pametniGusar)
			pomjeriPametnogGusara(a,b);
		else if(tip == neBasPametniGusar)
			pomjeriNeBasPametnogGusara(a,b);
	}
	//Ako se nalazi u istom redu sa igracem onda se priblizava po koloni(i obrnuto).
	private void pomjeriPametnogGusara(int a, int b) {
		Random random = new Random();
		boolean pomocna = random.nextBoolean();
		if(a == x && b < y)
			this.y--;
		else if(a == x && b > y)
			this.y++;
		else if(a > x && b == y)
			this.x++;
		else if(a < x && b == y)
			this.x--;
		else {
			//Ukoliko se ne nalazi ni u istom redu ni u istoj koloni onda ide random
			// ili po koloni ili po redu.
			if(a > x && b > y && pomocna)
				this.x++;
			else if(a > x && b > y && !pomocna)
				this.y++;
			else if(a < x && b > y && pomocna)
				this.x--;
			else if(a < x && b > y && !pomocna)
				this.y++;
			else if(a > x && b < y && pomocna)
				this.x++;
			else if(a > x && b < y && !pomocna)
				this.y--;
			else if(a < x && b < y && pomocna)
				this.x--;
			else if(a < x && b < y && !pomocna)
				this.y--;
		
		}
	}
	//Krece se u jednom od 4 random smjera i pri tome se provjerava da ne izadje iz opsega poljane.
	//Za pametnog gusara to nismo morali provjeravati jer on nikad nece izaci izvan opsega.
	private void pomjeriNeBasPametnogGusara(int a, int b) {
		Random random = new Random();
		int pomocna = random.nextInt(3);
		if(pomocna == 0) {
			if(this.x == 10)
				this.x--;
			else
				this.x++;
		}
		else if(pomocna == 1) {
			if(this.x == 0)
				this.x++;
			else
				this.x--;
		}
		else if(pomocna == 2) {
			if(this.y == 10)
				this.y--;
			else
				this.y++;
		}
		else if(pomocna == 3) {
			if(this.y == 0)
				this.y++;
			else
				this.y--;
		}
	}
	public int dajX() {
		return x;
	}
	public int dajY() {
		return y;
	}
	public boolean daLiJeZiv() {
		return daLiJeZiv;
	}
	public void postaviZiv(boolean ziv) {
		this.daLiJeZiv = ziv;
	}
	
}
