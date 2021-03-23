package sk.okno;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Melodie
{
	static Clip skladba;
	static Clip jablko;
	File suborJablka = new File("boing.wav");
	
	/**
	 * bezparametricky konstruktor
	 */

	public Melodie()
	{

	}

	/**
	 * Zastavuje pesnicku po urcitej dobe
	 * @param doba doba po ktorej pesnicka prestane hrat
	 */
	public void zastavPoDobu(int doba)
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				zastavPesnicku();
				timer.cancel();
			}
		}, doba);

	}

	/**
	 * Zapina zvucku pri najazde na jablko a necha ju hrat urcitu dobu
	 * @param doba casova doba pri ktorej je zvucka aktivna
	 */
	public void zapniZvucku(int doba)
	{
		zapniZvuckuJablka();
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				zastavPesnickuJablka();
				timer.cancel();
			}
		}, doba);
	}
	/**
	 *  zastavuje zvuk jablka
	 */
	public void zastavPesnickuJablka()
	{
		jablko.stop();
		jablko.close();
	}
	/**
	 * zastavuje skladbu pozadia hry
	 */
	public void zastavPesnicku()
	{
		skladba.stop();
		skladba.close();
	}
	/**
	 * Pusta pesnicku s konkretnym nazvom z priecinka
	 * @param nazov nazov suboru
	 */
	public void pustiPesnicku(String nazov)
	{
		File subor = new File(nazov + ".wav");
		try
		{
			AudioInputStream audio = AudioSystem.getAudioInputStream(subor);
			skladba = AudioSystem.getClip();
			skladba.open(audio);
			skladba.start();
			skladba.loop(Integer.MAX_VALUE);
		} catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Audio zapina zvucku jablka 
	 */
	public void zapniZvuckuJablka()
	{
		try
		{
			AudioInputStream audio = AudioSystem.getAudioInputStream(suborJablka);
			jablko = AudioSystem.getClip();
			jablko.open(audio);
			jablko.start();

		} catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}
}
