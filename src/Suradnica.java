public class Suradnica {
    public int riadok;
    public int stlpec;
    public int pohybRiadok;
    public int pohyvStlpec;
    public int bIndex;


    public Suradnica(int riadok, int stlpec, int pohybRiadok, int pohyvStlpec) {
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.pohybRiadok = pohybRiadok;
        this.pohyvStlpec = pohyvStlpec;
    }

    public Suradnica(int riadok, int stlpec, int pohybRiadok, int pohyvStlpec, int bIndex) {
        this.riadok = riadok;
        this.stlpec = stlpec;
        this.pohybRiadok = pohybRiadok;
        this.pohyvStlpec = pohyvStlpec;
        this.bIndex = bIndex;
    }

    public void incrementSuradnica(){
        riadok += pohyvStlpec;
        stlpec += pohybRiadok;
    }

    public void decSuradnica(){
        riadok -= pohyvStlpec;
        stlpec -= pohybRiadok;
    }

    public void setbIndex(int bIndex) {
        this.bIndex = bIndex;
    }
}
