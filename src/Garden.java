import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

public class Garden {
    private int vyska;
    private int sirka;
    private int[][] board;
    private int pocetKamen;
    private int pocetPiesok;
    private int polObvod;
    private int maxGene;


    public Garden(int vyska, int sirka, int pocetKamen) {
        this.board = new int[vyska][sirka];
        for (int i = 0; i < vyska; i++)
            for (int j = 0; j < sirka; j++) {
                board[i][j] = 0;
            }
//        board[1][2] = -1;
//        board[5][6] = -1;
        this.sirka = sirka;
        this.vyska = vyska;
        this.pocetPiesok = (vyska*sirka) - pocetKamen;
        this.polObvod = vyska + sirka;
        this.maxGene = polObvod + pocetKamen;
    }


    public Suradnica getStartindex(int index){
        if(index < sirka){
            return new Suradnica(0,index,0,1);
        }
        else if(index < vyska + sirka){
            return new Suradnica(index - sirka,sirka-1,-1,0);
        }
        else if(index < (2 * sirka) + vyska){
            return new Suradnica(vyska - 1,index - (vyska + sirka),0,-1);
        }
        else{
            return new Suradnica(index - ((2 * sirka) + vyska),0,1,0);
        }
    }

    public void walkGarden(ArrayList chromozome){
        Suradnica suradnica;
        int poradie = 1;
        for(int i = 0; i < maxGene; i++){
            suradnica = getStartindex((int) chromozome.get(i));

            if(canWalk(suradnica)){

                while(canWalk(suradnica)){
                    board[suradnica.riadok][suradnica.stlpec] = poradie;
                    suradnica.incrementSuradnica();
                }
            poradie++;

                System.out.println(Arrays.deepToString(board).replaceAll("], ", "]" + System.lineSeparator()));
                System.out.println();
            }
        }

      System.out.println(Arrays.deepToString(board).replaceAll("], ", "]" + System.lineSeparator()));
    }




    private boolean canWalk(Suradnica suradnica){
        if((suradnica.riadok < vyska && suradnica.riadok >= 0) && (suradnica.stlpec < sirka && suradnica.stlpec >= 0))
            return board[suradnica.riadok][suradnica.stlpec] == 0;
        else return false;
    }


    public int[][] getBoard() {
        return board;
    }

    public int getPocetKamen() {
        return pocetKamen;
    }

    public int getPocetPiesok() {
        return pocetPiesok;
    }

    public int getPolObvod() {
        return polObvod;
    }

    public int getMaxGene() {
        return maxGene;
    }
}
