public class Results {
    public int cisloriesenia;
    public int pocetgeneracii;
    public int avgFitt;
    public int selectionmetode;
    public int crossmetode;

    public Results(int pocetgeneracii, int avgFitt, int selectionmetode, int crossmetode) {
        this.pocetgeneracii = pocetgeneracii;
        this.avgFitt = avgFitt;
        this.selectionmetode = selectionmetode;
        this.crossmetode = crossmetode;
    }

    public int getCisloriesenia() {
        return cisloriesenia;
    }

    public void setCisloriesenia(int cisloriesenia) {
        this.cisloriesenia = cisloriesenia;
    }

    public int getPocetgeneracii() {
        return pocetgeneracii;
    }

    public void setPocetgeneracii(int pocetgeneracii) {
        this.pocetgeneracii = pocetgeneracii;
    }

    public int getAvgFitt() {
        return avgFitt;
    }

    public void setAvgFitt(int avgFitt) {
        this.avgFitt = avgFitt;
    }
}
