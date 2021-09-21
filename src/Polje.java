

// Sadrzi koordinate polja na poljani i informaciju o tome sta se nalazi u tom polju ako ono nije prazno.
public class Polje {
	private int x;
	private int y;
	private boolean otok;
	private boolean gusar;
	private boolean cilj;
	
	public Polje(int x, int y) {
		this.x = x;
		this.y = y;
		this.otok = false;
		this.gusar = false;
		this.cilj = false;
	}
	public boolean daLiJeOtok() {
		return this.otok;
	}
	public boolean daLiJeGusar() {
		return this.gusar;
	}
	public boolean daLiJeCilj() {
		return this.cilj;
	}
	public void postaviOtok(boolean otok) {
		this.otok = otok;
	}
	public void postaviGusara(boolean gusar) {
		this.gusar = gusar;
	}
	public void postaviCilj(boolean cilj) {
		this.cilj = cilj;
	}
}
