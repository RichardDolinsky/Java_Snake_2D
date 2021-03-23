package sk.okno;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelHry extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	static final int SIRKA_PANELU = 600;
	static final int VYSKA_PANELU = 600;
	static final int VELKOST_KOCKY = 25;
	static final int POCET_KOCIEK = (SIRKA_PANELU * VYSKA_PANELU) / (VELKOST_KOCKY * VELKOST_KOCKY);
	static int oneskorenie = 175;
	final int x[] = new int[POCET_KOCIEK];
	final int y[] = new int[POCET_KOCIEK];
	int velkostHada;
	static int pocetJablk;
	int jablkoX;
	int jablkoY;
	boolean bezi;
	static Timer casovac;
	Random nahodneCislo;
	Melodie melody = new Melodie();
	KlavesovyPosluchac posluchac;
	public static String farbaHada = "zelena";
/**
 * konstruktor triedy s pociatocnymi parametrami: velkost okna, 
 * farba pozadia,
 * prednostne zaostrenie okna,
 * spusta pesnicku,
 * objekt triedy Random,
 * start hry
 */
	public PanelHry()
	{
		nahodneCislo = new Random();
		setPreferredSize(new Dimension(SIRKA_PANELU, VYSKA_PANELU));
		setBackground(Color.black);
		setFocusable(true);
		melody.pustiPesnicku("game");
		startHry();
	}

/**
 * Zaciatok hry, a urceni pociatocnej pozicie x, y v hracom okne a velkost hadika
 * vytvara jablko v hracej ploche, zapne casovac, poslucha na stlacene klavesy, a 
 * aktualizuje plochu pre pohyb hadika
 */
	public void startHry()
	{
		KlavesovyPosluchac.SMER = KlavesovyPosluchac.doPrava;
		velkostHada = 3;

		x[0] = 225;
		y[0] = 275;

		x[1] = 200;
		y[1] = 275;

		x[2] = 175;
		y[2] = 275;

		noveJablko();
		bezi = true;
		casovac = new Timer(oneskorenie, this);
		posluchac = new KlavesovyPosluchac();
		addKeyListener(posluchac);
		repaint();
		casovac.start();
	}

	/**
	 * zistuje ci hadik obsahuje kocku
	 * vracia true ak had naberie novu kocku
	 * @return true ak naberie kocku
	 * @return false v opacnom pripade
	 * 
	 */
	private boolean hadObsahujeKocku(int suradnicaX, int suradnicaY)
	{
		for (int i = 0; i < velkostHada; i++)
		{
			if (x[i] == suradnicaX && y[i] == suradnicaY)
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * vytvara nove jablko na ploche po najazde hada na suradnicu jablka sa vytvori nove jablko 
	 */
	private void noveJablko()
	{
		do
		{
			jablkoX = nahodneCislo.nextInt((int) (SIRKA_PANELU / VELKOST_KOCKY)) * VELKOST_KOCKY;
			jablkoY = nahodneCislo.nextInt((int) (VYSKA_PANELU / VELKOST_KOCKY)) * VELKOST_KOCKY;
		} while (hadObsahujeKocku(jablkoX, jablkoY));
	}
	/**
	 * vykresluje komponentu po hracej ploche
	 */

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		vykresli(g);
	}

	/**
	 *  vykresluje graficke komponenty pohybu hada v pripade ak sa had pohybuje
	 */

	private void vykresli(Graphics g)
	{
		if (bezi)
		{
			// vykresluje stvorceky v hracej ploche hadika
			for (int i = 0; i < VYSKA_PANELU / VELKOST_KOCKY; i++)
			{
				g.drawLine(i * VELKOST_KOCKY, 0, i * VELKOST_KOCKY, VYSKA_PANELU);
				g.drawLine(0, i * VELKOST_KOCKY, SIRKA_PANELU, i * VELKOST_KOCKY);

			}

			// nastavujem farbu jablka
			g.setColor(Color.red);
			g.fillOval(jablkoX, jablkoY, VELKOST_KOCKY, VELKOST_KOCKY);

			// nastavujem farbu hodika
			for (int i = 0; i < velkostHada; i++)
			{
				if (i == 0)
					// nastavuje farbu hlavy hadika
				{
					if (farbaHada == "modra")
					{
						g.setColor(Color.blue);
					} else if (farbaHada == "mix")
					{
						g.setColor(Color.pink);
					} else if (farbaHada == "zelena")
					{
						g.setColor(Color.green);
					}
					g.fillRect(x[i], y[i], VELKOST_KOCKY, VELKOST_KOCKY);

				} else
					// nastavuje farby tela hadika
				{	
					if (g.getColor() == Color.blue)
					{	// telo hada nastavuje na odtien modrej
						g.setColor(new Color(0, 0, 153));
					} else if (g.getColor() == Color.green)
					{	// telo hada nastavuje na odtien zelenej
						g.setColor(new Color(45, 180, 0));
					} else if (g.getColor() == Color.pink)
					{	// nastavuje nahodnu farbu tela hadika
						Random random = new Random();
						final float hue = random.nextFloat();
						final float saturation = 0.9f;
						final float luminance = 1.0f;
						Color color = Color.getHSBColor(hue, saturation, luminance);
						g.setColor(color);
					}// farby vsetky kocky tela hadika
					g.fillRect(x[i], y[i], VELKOST_KOCKY, VELKOST_KOCKY);
				}
			}
			// vypisanie skore na hraciu plochu aplikacie 
			g.setColor(Color.blue);
			g.setFont(new Font("Ink Free", Font.BOLD, 25));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Skore: " + pocetJablk, (SIRKA_PANELU - metrics.stringWidth("Skore: " + pocetJablk)) / 2,
					g.getFont().getSize());

		} else
		{
			koniecHry(g);
		}
	}
/**
 * nastavi koniec hry a vypise dosniahnute skore na ciernom pozadi
 * @param g
 */
	private void koniecHry(Graphics g)
	{
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 55));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Skore: " + pocetJablk, (SIRKA_PANELU - metrics.stringWidth("Skore: " + pocetJablk)) / 2,
				225);
		g.drawString("Koniec hry", (SIRKA_PANELU - metrics.stringWidth("Koniec hry")) / 2, 325);
	}

	/**
	 * metoda zistuje pri kazdom pohybe ci nedoslo ku kolizii
	 * ci hadik zjedol jablko
	 * meni sa jeho pohyb
	 * v kazdom pripade sa prekresluje hracia plocha 
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (bezi)
		{
			pohybVpred();
			zistiKoliziu();
			zozerJablko();
		}
		repaint();
	}
/**
 * pri rovnakej pozicii hlavy hadika a jablka nastavaju zmeny
 * velkost hada
 * pocet jablk
 * vykreslenie noveho jablka na ploche hry
 * zapne sa zvucka ktora pretrvava nastaveny interval
 */
	private void zozerJablko()
	{
		if (x[0] == jablkoX && y[0] == jablkoY)
		{
			velkostHada++;
			pocetJablk++;
			noveJablko();
			melody.zapniZvucku(200);
		}
	}
/**
 * vykonava pohyb hada v pred
 * nova nadobudnuta pozicia hlavy hadika je rovna starej odpocitana o 1 kocku z chvostu
 * zmeny nastavaju len ak premenna "bezi" nadobuda "true"
 */
	private void pohybVpred()
	{
		for (int i = velkostHada; i > 0; i--)
		{
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		if (bezi)
		{	// vykonava smer hore po sltaceni sipky UP
			if (KlavesovyPosluchac.SMER == KlavesovyPosluchac.hore)
			{
				y[0] = y[0] - VELKOST_KOCKY;
				return;
			}
			// vykonava smer dole po sltaceni sipky DOWN
			else if (KlavesovyPosluchac.SMER == KlavesovyPosluchac.dole)
			{
				y[0] = y[0] + VELKOST_KOCKY;
				return;
			}
			// vykonava smer do lava po sltaceni sipky LEFT
			else if (KlavesovyPosluchac.SMER == KlavesovyPosluchac.doLava)
			{
				x[0] = x[0] - VELKOST_KOCKY;
				return;
				
				// vykonava smer doprava po sltaceni sipky RIGHT
			} else if (KlavesovyPosluchac.SMER == KlavesovyPosluchac.doPrava)
			{
				x[0] = x[0] + VELKOST_KOCKY;
			}
		}
	}
/**
 * metoda zistuje koliziu podla polohy hadika v hracej ploche
 * pri naburani do steny alebo do sameho seba nastava kolizia
 */
	public void zistiKoliziu()
	{
		// zistuje koliziu do svojho tela
		for (int i = velkostHada - 1; i > 0; i--)
		{
			if ((x[0] == x[i]) && (y[0] == y[i]))
			{
				bezi = false;
			}
		}

		// zistuje koliziu na lavu stenu
		if (x[0] < 0)
		{
			bezi = false;
		}

		// zistuje koliziu na pravu stenu
		if (x[0] > SIRKA_PANELU - VELKOST_KOCKY)
		{
			bezi = false;
		}

		// zistuje koliziu na hornu stenu
		if (y[0] < 0)
		{
			bezi = false;
		}

		// zistuje koliziu na dolnu stenu
		if (y[0] > VYSKA_PANELU - VELKOST_KOCKY)
		{
			bezi = false;
		}
		// ak "bezi" nadobuda "false": 
		// zastavuje melodiu a casovac 
		if (!bezi)
		{
			casovac.stop();
			melody.zastavPoDobu(300);
		}
	}
}