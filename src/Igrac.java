
// Sadrzi koordinate igraca i funkciju pomjeranje igraca.
public class Igrac {
	private int x;
	private int y;
	
	public Igrac(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void pomjeri(char potez) {
		if(potez == 'A' || potez == 'a')
			this.pomjeriLijevo();
		else if(potez == 'D' || potez == 'd')
			this.pomjeriDesno();
		else if(potez == 'S' || potez == 's')
			this.pomjeriDole();
		else if(potez == 'W' || potez == 'w')
			this.pomjeriGore();
	}
	
	public void pomjeriLijevo() {
		this.y--;
	}
	public void pomjeriDesno() {
		this.y++;
	}
	public void pomjeriDole() {
		this.x++;
	}
	public void pomjeriGore() {
		this.x--;
	}
	public int dajX() {
		return this.x;
	}
	public int dajY() {
		return this.y;
	}
}
