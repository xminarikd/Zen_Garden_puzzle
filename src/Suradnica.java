public class Suradnica {
    public int riadok;
    public int stlpec;
    public int pohybRiadok;
    public int pohyvStlpec;
    public int bIndex;
    public Suradnica pred;


    public Suradnica(int riadok, int stlpec, int pohybRiadok, int pohyvStlpec) {
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.pohybRiadok = pohybRiadok;
        this.pohyvStlpec = pohyvStlpec;
    }

    public Suradnica(int riadok, int stlpec, int pohybRiadok, int pohyvStlpec, int bIndex, Suradnica pred) {
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.pohybRiadok = pohybRiadok;
        this.pohyvStlpec = pohyvStlpec;
        this.bIndex = bIndex;
        this.pred = pred;
    }

    public void incrementSuradnica(){
        riadok += pohybRiadok;
        stlpec += pohyvStlpec;
    }

    public void decSuradnica(){
        riadok -= pohybRiadok;
        stlpec -= pohyvStlpec;
    }

    public void setbIndex(int bIndex) {
        this.bIndex = bIndex;
    }
}
