package sk.okno;

public class Hrac implements Comparable<Hrac> {

	private String meno;
	private int pocetHier;
	private int maxSkore;
	private int celkoveSkore;

	/**
	 * Konstruktor hraca
	 * 
	 * @param meno       meno hraca
	 * @param pocetHier  pocet odohranych hier
	 * @param maxSkore   maximalne dosiahnute skore
	 * @param pocetBodov celkovy pocet bodov
	 */
	public Hrac(String meno, int pocetHier, int maxSkore, int pocetBodov) {
		this.meno = meno;
		this.pocetHier = pocetHier;
		this.maxSkore = maxSkore;
		this.celkoveSkore = pocetBodov;
	}

	/**
	 * pocet odohranych hier
	 * 
	 * @return pocetHier pocet odohranych hier
	 */
	public int getPocetHier() {
		return pocetHier;
	}

	/**
	 * nastavuje meno hraca
	 * 
	 * @param meno meno noveho hraca
	 */
	public void setMeno(String meno) {
		this.meno = meno;
	}

	/**
	 * aktualizuje pocet odohranych hier
	 * 
	 * @param pocetHier pocet odohranych hier
	 */

	public void setPocetHier(int pocetHier) {
		this.pocetHier = pocetHier;
	}

	/**
	 * nastavuje maximalneho dosiahnute skore daneho hraca
	 * 
	 * @param maxSkore maximalne dosiahnute skore
	 */
	public void setMaxSkore(int maxSkore) {
		this.maxSkore = maxSkore;
	}

	/**
	 * nastavuje celkove skore, skore je pripocitavane kazdou hrou
	 * 
	 * @param celkoveSkore celkove skore nahranych bodov
	 */
	public void setCelkoveSkore(int celkoveSkore) {
		this.celkoveSkore = celkoveSkore;
	}

	/**
	 * vracia cislo maximalneho skore
	 * 
	 * @return maxSkore maximalne skore
	 */
	public int getMaxSkore() {
		return maxSkore;
	}

	/**
	 * @return meno meno hraca
	 */
	public String getMeno() {
		return meno;
	}

	/**
	 * ziskavame cislo celkoveho skore
	 * 
	 * @return celkoveSkore celkovy pocet bodov odohranych za vsetky hry daneho
	 *         hraca
	 */
	public int getCelkoveSkore() {
		return celkoveSkore;
	}
	/**
	 *  upravena metoda na vygenerovanie textu s premennymi meno, pocet hier, max skore, celkove skore
	 */
	@Override
	public String toString() {
		return "" + meno + ": pocet hier: " + pocetHier + " max. skore: " + maxSkore + " celk. skore: " + celkoveSkore;
	}
/**
 * implementovana metoda z -java.lang.Comparable.compareTo()
 */
	@Override
	public int compareTo(Hrac hrac) {
		int rozdiel;
		if (hrac.getMaxSkore() == this.getMaxSkore()) {
			rozdiel = 1;
		} else {
			rozdiel = (hrac.getMaxSkore() - this.getMaxSkore()) * 2;
		}
		return rozdiel;
	}
	

}
