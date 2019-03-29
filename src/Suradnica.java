public class Suradnica {
    public int riadok;
    public int stlpec;
    public int pohybRiadok;
    public int pohyvStlpec;


    public Suradnica(int riadok, int stlpec, int pohybRiadok, int pohyvStlpec) {
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.pohybRiadok = pohybRiadok;
        this.pohyvStlpec = pohyvStlpec;
    }

    public void incrementSuradnica(){
        riadok += pohyvStlpec;
        stlpec += pohybRiadok;
    }

}
