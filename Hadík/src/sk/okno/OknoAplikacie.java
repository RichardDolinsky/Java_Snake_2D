package sk.okno;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class OknoAplikacie implements ActionListener
{
	private JFrame okno;
	private JFrame oknoHadik;
	private JTextField menoHraca;
	private JLabel popisHraca = new JLabel();
	private JTextArea topSkore = new JTextArea();
	private JMenuBar menu;
	private JMenu nastavenia;
	private Map<String, Hrac> hraci = new HashMap<>();
	private Hrac aktualnyHrac;
	private Statistiky statistika;
	private JRadioButtonMenuItem pomaly;
	private JRadioButtonMenuItem normalne;
	private JRadioButtonMenuItem rychlo;
	private JButton tlacitkoStart;
	private JButton tlacitkoTopSkore;
	private JButton tlacitkoPridajHraca;
	private JComboBox<String> zoznamHracov;
	private JLabel vyberHraca = new JLabel("Vyber hraca:");
	private JMenu nastavenieHada;
	private JRadioButtonMenuItem farbaZelena;
	private JRadioButtonMenuItem farbaModra;
	private JRadioButtonMenuItem farbaMix;
	

	public static void main(String[] args)
	{

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{	// otvara sa Frame OknoAplikacie
					OknoAplikacie window = new OknoAplikacie();
					window.okno.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Konstruktor inicializuje frame a nacitava zoznam hracov
	 */
	public OknoAplikacie()
	{
		nacitajHracov();
		inicializuj();
	}
/**
 * inicializuje celu hru
 * dokumentacne komentare v nutri metody
 */
	private void inicializuj()
	{	// okno hl. menu
		okno = new JFrame("Hadik");
		okno.setBounds(100, 100, 410, 300);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.getContentPane().setLayout(null);
		okno.setLocationRelativeTo(null);
		okno.setVisible(true);
		// okno samotnej hry
		oknoHadik = new JFrame("Hadik");

		tlacitkoStart = new JButton("Nova Hra");
		tlacitkoStart.addActionListener(this);
		tlacitkoStart.setBounds(49, 140, 110, 23);

		popisHraca.setSize(400, 20);
		popisHraca.setLocation(49, 170);

		vyberHraca.setSize(110, 20);
		vyberHraca.setLocation(49, 15);

		topSkore.setSize(150, 145);
		topSkore.setLocation(200, 20);

		okno.getContentPane().add(tlacitkoStart);
		okno.getContentPane().add(popisHraca);
		okno.getContentPane().add(vyberHraca);
		okno.getContentPane().add(topSkore);

		tlacitkoStart.setVisible(false);
		popisHraca.setVisible(false);
		topSkore.setVisible(false);
		topSkore.setEditable(false);

		menoHraca = new JTextField();
		menoHraca.setBounds(49, 100, 110, 23);
		menoHraca.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent e)
			{	// zamedzuje prezyvky dlhsej ako 7 znakov
				if (menoHraca.getText().length() > 7)
					e.consume();
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
			}
		});

		okno.getContentPane().add(menoHraca);
		
		// tlacisko TOP skore
		tlacitkoTopSkore = new JButton("TOP skore");
		tlacitkoTopSkore.addActionListener(this);
		tlacitkoTopSkore.setBounds(49, 200, 110, 23);
		okno.getContentPane().add(tlacitkoTopSkore);
		
		// tlacidlo Pridaj hraca
		tlacitkoPridajHraca = new JButton("Pridaj hraca");
		tlacitkoPridajHraca.addActionListener(this);
		tlacitkoPridajHraca.setBounds(49, 70, 110, 23);
		okno.getContentPane().add(tlacitkoPridajHraca);
		
		//vytvare Jmenu a moznostami nastavenia rychlosti hry
		menu = new JMenuBar();
		nastavenia = new JMenu("Rychlost");
		pomaly = new JRadioButtonMenuItem("pomaly");
		pomaly.addActionListener(this);

		normalne = new JRadioButtonMenuItem("normalne");
		normalne.addActionListener(this);
		normalne.setSelected(true);

		rychlo = new JRadioButtonMenuItem("rychlo");
		rychlo.addActionListener(this);

		nastavenia.add(pomaly);
		nastavenia.add(normalne);
		nastavenia.add(rychlo);
		menu.add(nastavenia);
		okno.setJMenuBar(menu);

		zoznamHracov = new JComboBox<>();
		zoznamHracov.setBounds(49, 40, 110, 23);
		zoznamHracov.addActionListener(this);
		
		// pridava nastavenia Jmenu farby hada
		nastavenieHada = new JMenu("Farba");
		menu.add(nastavenieHada);
		
		// vyber zelena farba
		farbaZelena = new JRadioButtonMenuItem("zelena");
		farbaZelena.setSelected(true);
		nastavenieHada.add(farbaZelena);
		farbaZelena.addActionListener(this);
		//vyber modra farba
		
		farbaModra = new JRadioButtonMenuItem("modra");
		nastavenieHada.add(farbaModra);
		farbaModra.addActionListener(this);
		
		// MIX farby
		farbaMix = new JRadioButtonMenuItem("mix");
		nastavenieHada.add(farbaMix);
		farbaMix.addActionListener(this);
		
		// prechadza zoznam hracov
		hraci.forEach((kluc, hrac) -> zoznamHracov.addItem(kluc));
		okno.getContentPane().add(zoznamHracov);

	}
	/**
	 * Udalosti akcie
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == tlacitkoStart)
		{
			okno.setVisible(false);
			topSkore.setVisible(false);
			oknoHadik.setVisible(true);
			PanelHry hra = new PanelHry();
			PanelHry.pocetJablk = 0;
			oknoHadik.getContentPane().add(hra);
			oknoHadik.pack();
			oknoHadik.setLocationRelativeTo(null);
			oknoHadik.setResizable(false);
			OknoPosluchac oknoPosluchac = new OknoPosluchac(okno, oknoHadik, statistika, aktualnyHrac, popisHraca, hraci,
					hra);
			oknoHadik.addWindowListener(oknoPosluchac);
		} else if (e.getSource() == tlacitkoTopSkore)
		{
			topSkore.setText("");
			statistika = new Statistiky(hraci);
			statistika.getHraci().forEach(hrac -> topSkore.append(hrac.getMeno() + ": " + hrac.getMaxSkore() + "\n"));
			topSkore.setVisible(true);
		} else if (e.getSource() == tlacitkoPridajHraca)
		{
			if (!menoHraca.getText().isEmpty())
			{
				if (!hraci.containsKey(menoHraca.getText()))
				{
					hraci.put(menoHraca.getText(), new Hrac(menoHraca.getText(), 0, 0, 0));
					zoznamHracov.addItem(menoHraca.getText());
					zoznamHracov.setSelectedItem(menoHraca.getText());
					JOptionPane.showMessageDialog(new JFrame(), "Novy hrac bol uspesne pridany.", "Informacia",
							JOptionPane.INFORMATION_MESSAGE);
				} else
				{
					JOptionPane.showMessageDialog(new JFrame(),
							"Hrac s prezyvkou \"" + menoHraca.getText() + "\" uz existuje.", "Upozornenie",
							JOptionPane.WARNING_MESSAGE);
				}
			}
			// Nastavenia hry typu rychlost a farba hada
		} else if (e.getSource() == pomaly)
		{
			PanelHry.oneskorenie = 275;
			normalne.setSelected(false);
			rychlo.setSelected(false);
		} else if (e.getSource() == normalne)
		{
			PanelHry.oneskorenie = 175;
			pomaly.setSelected(false);
			rychlo.setSelected(false);
		} else if (e.getSource() == rychlo)
		{
			PanelHry.oneskorenie = 105;
			pomaly.setSelected(false);
			normalne.setSelected(false);
		} else if ((e.getSource() == zoznamHracov))
		{
			aktualnyHrac = hraci.get(zoznamHracov.getSelectedItem());
			popisHraca.setText(aktualnyHrac.toString());
			tlacitkoStart.setVisible(true);
			popisHraca.setVisible(true);
			topSkore.setVisible(false);
			statistika = new Statistiky(hraci);
		} else if (e.getSource() == farbaModra)
		{

			farbaMix.setSelected(false);
			farbaZelena.setSelected(false);
			PanelHry.farbaHada = "modra";

		} else if (e.getSource() == farbaMix)
		{

			PanelHry.farbaHada = "mix";
			farbaModra.setSelected(false);
			farbaZelena.setSelected(false);

		}

		else if (e.getSource() == farbaZelena)
		{

			farbaMix.setSelected(false);
			farbaModra.setSelected(false);
			PanelHry.farbaHada = "zelena";

		}
	}
/**
 * Metoda nacitava hracov zo zoznamu v subore 
 * a ich premenne: meno,pocet hier, celkove skore, max. skore
 */
	private void nacitajHracov()
	{
		try
		{
			FileReader citacSuboru = new FileReader("hraci.txt");
			Scanner vstupneData = new Scanner(citacSuboru);
			while (vstupneData.hasNextLine())
			{
				String meno;
				int pocetHier, maxSkore, celkoveSkore;
				meno = vstupneData.next();
				pocetHier = vstupneData.nextInt();
				maxSkore = vstupneData.nextInt();
				celkoveSkore = vstupneData.nextInt();
				hraci.put(meno, new Hrac(meno, pocetHier, maxSkore, celkoveSkore));
			}
			vstupneData.close();

		} catch (FileNotFoundException e)
		{

		}
	}

}