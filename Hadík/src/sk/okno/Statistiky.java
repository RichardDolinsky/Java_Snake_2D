package sk.okno;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Statistiky {
	Set<Hrac> hraci = new TreeSet<>();

	/**
	 *  vytvary mapu a vklada hracov
	 * @param hraci pridavanz hraci
	 */
	public Statistiky(Map<String, Hrac> hraci) {
		hraci.forEach((k, v) -> this.hraci.add(v));
	}

	/**
	 * Aktualizuje skore hraca
	 * @param hrac meno konkretnetho hraca ktoremu bude aktualizovane skore
	 */
	public void aktualizujSkore(Hrac hrac) {
		hrac.setPocetHier(hrac.getPocetHier() + 1);
		hrac.setCelkoveSkore(hrac.getCelkoveSkore() + PanelHry.pocetJablk);
		if (hrac.getMaxSkore() < PanelHry.pocetJablk) {
			hrac.setMaxSkore(PanelHry.pocetJablk);
		}

	}
/**
 * ziskava mnozinu hracov bez duplicit
 * @return hraci hraci v kolekcii bez diplicit
 */
	public Set<Hrac> getHraci() {
		return hraci;
	}

}
