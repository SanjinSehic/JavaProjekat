import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


//Crna boja je igrac.
//Zuta boja su otoci.
//Plava boja su gusari.
//Crvena boja su ciljevi.
//Bijela boja su prazna polja.
public class Gui {
	static JFrame jf;
	static JPanel jp;
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int tezina = 1;
		boolean pomocna = Igra.meni();
		Igra igra = new Igra(tezina);
		int dimenzija = igra.dajPoljanu().dajDimenziju();
		
		//Postavljamo Frame.
		jf = new JFrame("Gusari");
		jf.setSize(750, 750);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Postavljamo Panel sa grid layoutom.
		jp = new JPanel();
		jp.setLayout(new GridLayout(dimenzija,dimenzija));
		jp.setBackground(Color.WHITE);
		
		//Dodajemo dugmad.
		JButton[][] jb = new JButton[dimenzija][dimenzija];
		for(int i = 0; i < dimenzija; i++) {
			for(int j = 0; j < dimenzija; j++) {
				jb[i][j] = new JButton(i + "  " + j);
				jb[i][j].setSize(50, 50);
				jb[i][j].setEnabled(false);
				
				Color c;
				//Razlicita boja za igraca,gusare,otoke,ciljeve i prazna polja.
				if(i == igra.dajIgraca().dajX() && j == igra.dajIgraca().dajY())
					c = Color.BLACK;
				else if(igra.dajPoljanu().dajPolje(i, j).daLiJeCilj())
					c = Color.RED;
				else if(igra.dajPoljanu().dajPolje(i, j).daLiJeGusar())
					c = Color.BLUE;
				else if(igra.dajPoljanu().dajPolje(i, j).daLiJeOtok())
					c = Color.YELLOW;
				else
					c = Color.WHITE;
				
				jb[i][j].setBackground(c);
				jp.add(jb[i][j]);
				
			}
		}
		jf.add(jp);
		jf.setResizable(false);
		jf.setVisible(true);
		
		//Cekanje komande za pomjeranje.
		KeyListener k1 = new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == 'A' || e.getKeyCode() == 'a') {					
					String s = "A";
					igra.igraj(s);
				}
				else if(e.getKeyCode() == 'D' || e.getKeyCode() == 'd') {					
					String s = "D";
					igra.igraj(s);
				}
				else if(e.getKeyCode() == 'S' || e.getKeyCode() == 's') {					
					String s = "S";
					igra.igraj(s);
				}
				else if(e.getKeyCode() == 'W' || e.getKeyCode() == 'w') {					
					String s = "W";
					igra.igraj(s);
				}
			}
		

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
	jf.addKeyListener(k1);
		
	//Igrica ne radi kao u konzoli (igra se samo prvi nivo, nakon eventualne pobjede ne 
	//inicijalizira se nova igra na sljedecem nivou).
	while(true) {
		//igra = new Igra(tezina);
		while(igra.dajStanje() == igra.neodluceno) {
			//igra2.apdejtujIgru();
			for(int i = 0; i < dimenzija; i++) {
				for(int j = 0; j < dimenzija; j++) {
					Color c;
	
					if(i == igra.dajIgraca().dajX() && j == igra.dajIgraca().dajY())
						c = Color.BLACK;
					else if(igra.dajPoljanu().dajPolje(i, j).daLiJeCilj())
						c = Color.RED;
					else if(igra.dajPoljanu().dajPolje(i, j).daLiJeGusar())
						c = Color.BLUE;
					else if(igra.dajPoljanu().dajPolje(i, j).daLiJeOtok())
						c = Color.YELLOW;
					else
						c = Color.WHITE;
					
					jb[i][j].setBackground(c);
				}
			}	
		}
		if(igra.dajStanje() == igra.pobjeda) 
			tezina++;
		else 
			break;			
		}
	}
}
