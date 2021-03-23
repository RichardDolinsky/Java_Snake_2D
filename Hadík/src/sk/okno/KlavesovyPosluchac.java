package sk.okno;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KlavesovyPosluchac implements KeyListener
{
	static final char doLava = 'L';
	static final char doPrava = 'P';
	static final char hore = 'H';
	static final char dole = 'D';

	public static char SMER = doPrava;
	boolean nieJePauza;
/**
 * vytvara konstruktor a nastavuje hodnotu nieJePauza na true
 */
	public KlavesovyPosluchac()
	{
		nieJePauza = true;
	}
/**
 * Metoda pozoruje stlacenia klaves a nastavuje hodnoty premennych
 * zamedzuje kolizie sameho do seba, viac dokumentacne komentare v metode
 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		// cislo stlacene klavesy
		int key = e.getKeyCode();
		// nastavuje premennu SMER dolava ak je stlacene LEFT
		if (key == KeyEvent.VK_LEFT && SMER != doPrava && nieJePauza)
		{
			SMER = doLava;
		}
		// nastavuje premennu SMER doprava ak je stlacene RIGHT
		if (key == KeyEvent.VK_RIGHT && SMER != doLava && nieJePauza)
		{
			SMER = doPrava;
		}
		// nastavuje premennu SMER dole ak je stlacene DOWN

		if (key == KeyEvent.VK_UP && SMER != dole && nieJePauza)
		{
			SMER = hore;
		}
		// nastavuje premennu SMER hore ak je stlacene UP
		if (key == KeyEvent.VK_DOWN && SMER != hore && nieJePauza)
		{
			SMER = dole;
		}
		// nastavuje premennu nieJePauza na false ak sa stlaci SPACE
		if (key == KeyEvent.VK_SPACE)
		{	
			if (nieJePauza)
			{	
				PanelHry.casovac.stop();
				Melodie.skladba.stop();
			} else
			{
				PanelHry.casovac.start();
				Melodie.skladba.start();
			}
			nieJePauza = !nieJePauza;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{

	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
	
	}

}