package sk.okno;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class OknoPosluchac implements WindowListener
{
	//Privatne premenne
	private JFrame okno;
	private JFrame okno2;
	private Statistiky data;
	private Hrac hrac;
	private JLabel popis;
	private Map<String, Hrac> hraci;
	private PanelHry hra;
	int i;
/**
 * Konstruktor vytvara:
 * @param okno menu okno aplikacie
 * @param okno2 okno hry
 * @param data statistiky hry
 * @param hrac vytvara hraca 
 * @param popis popis statistiky hraca
 * @param hraci mapu hracov
 * @param hra okno hry
 */
	public OknoPosluchac(JFrame okno, JFrame okno2, Statistiky data, Hrac hrac, JLabel popis, Map<String, Hrac> hraci,
			PanelHry hra)
	{
		this.okno = okno;
		this.okno2 = okno2;
		this.data = data;
		this.hrac = hrac;
		this.popis = popis;
		this.hraci = hraci;
		this.hra = hra;
	}

	@Override
	public void windowOpened(WindowEvent e)
	{

	}
	/**
	 * Akcie vykonavane pri zatvoreni hracieho okna hadika:
	 * vypnutie casovaca
	 * zatvorenie melodie
	 * zviditelnenie okna hlavneho menu
	 * aktualizovane skore
	 * nastavenie textu na ziskane skore
	 */

	@Override
	public void windowClosing(WindowEvent e)
	{
		
		PanelHry.casovac.stop();
		Melodie.skladba.close();
		okno.setVisible(true);
		data.aktualizujSkore(hrac);
		popis.setText(hrac.toString());

		try
		{
			PrintWriter zapisovac;
			zapisovac = new PrintWriter("hraci.txt");
			hraci.forEach((kluc, hrac) ->
			{
				i++;
				zapisovac.print(hrac.getMeno() + " ");
				zapisovac.print(hrac.getPocetHier() + " ");
				zapisovac.print(hrac.getMaxSkore() + " ");
				if (hraci.size() == i)
				{
					zapisovac.print(hrac.getCelkoveSkore());
				} else
				{
					zapisovac.println(hrac.getCelkoveSkore());
				}
			});
			zapisovac.close();
		} catch (FileNotFoundException event)
		{
			event.printStackTrace();
		}
		okno2.remove(hra);
		okno2.removeWindowListener(this);
	}

	@Override
	public void windowClosed(WindowEvent e)
	{

	}

	@Override
	public void windowIconified(WindowEvent e)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}

}
