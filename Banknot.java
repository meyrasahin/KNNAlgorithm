public class Banknot {
    private double varyans;
    private double carpiklik;
    private double basiklik;
    private double entropi;
    private int tur;

    public Banknot(double varyans, double carpiklik, double basiklik, double entropi, int tur){   //yapıcı metot
        this.setVaryans(varyans);
        this.setCarpiklik(carpiklik);
        this.setBasiklik(basiklik);
        this.setEntropi(entropi);
        this.setTur(tur);
    }

    /**
     * @return the varyans
     */
    public double getVaryans() {
        return varyans;
    }

    /**
     * @param varyans the varyans to set
     */
    public void setVaryans(double varyans) {
        this.varyans = varyans;
    }

    /**
     * @return the carpiklik
     */
    public double getCarpiklik() {
        return carpiklik;
    }

    /**
     * @param carpiklik the carpiklik to set
     */
    public void setCarpiklik(double carpiklik) {
        this.carpiklik = carpiklik;
    }

    /**
     * @return the basiklik
     */
    public double getBasiklik() {
        return basiklik;
    }

    /**
     * @param basiklik the basiklik to set
     */
    public void setBasiklik(double basiklik) {
        this.basiklik = basiklik;
    }

    /**
     * @return the entropi
     */
    public double getEntropi() {
        return entropi;
    }

    /**
     * @param entropi the entropi to set
     */
    public void setEntropi(double entropi) {
        this.entropi = entropi;
    }

    /**
     * @return the tur
     */
    public int getTur() {
        return tur;
    }

    /**
     * @param tur the tur to set
     */
    public void setTur(int tur) {
        this.tur = tur;
    }


    public String toString(){
        return String.format("Varyans Değeri:%10f   Çarpıklık Değeri:%10f   Basıklık Değeri:%10f   Entropi Değeri:%10f   Tür:%4d",getVaryans(),getCarpiklik(),getBasiklik(),getEntropi(),getTur() );
    }

}
