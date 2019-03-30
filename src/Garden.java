import java.util.ArrayList;
import java.util.Arrays;

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
        this.pocetKamen = pocetKamen;
        this.pocetPiesok = (vyska*sirka) - pocetKamen;
        this.polObvod = vyska + sirka;
        this.maxGene = polObvod + pocetKamen;
    }


    public Suradnica getStartindex(int index){
        if(index < sirka){
            return new Suradnica(0,index,0,1, index);
        }
        else if(index < vyska + sirka){
            return new Suradnica(index - sirka,sirka-1,-1,0, index);
        }
        else if(index < (2 * sirka) + vyska){
            return new Suradnica(vyska - 1,index - (vyska + sirka),0,-1, index);
        }
        else{
            return new Suradnica(index - ((2 * sirka) + vyska),0,1,0, index);
        }
    }

    public Suradnica changeDirection(Suradnica suradnica, int[][] board){
        //hore/dole
        if(suradnica.pohybRiadok == 0){
            if((suradnica.stlpec + 1 < sirka)  &&  board[suradnica.riadok][suradnica.stlpec+1] == 0)
                return new Suradnica(suradnica.riadok, suradnica.stlpec + 1, 1, 0);

            else if((suradnica.stlpec - 1 < sirka && suradnica.stlpec - 1 >= 0)  &&  board[suradnica.riadok][suradnica.stlpec-1] == 0)
                return new Suradnica(suradnica.riadok, suradnica.stlpec - 1, -1, 0);

            else if(suradnica.stlpec + 1 == sirka || suradnica.stlpec - 1 < 0)
                return new Suradnica(-1,-1,-1,-1,-1);

            else return null;
        }
        //lavo/pravo
        else{
            if((suradnica.riadok + 1 < vyska)  &&  board[suradnica.riadok+1][suradnica.stlpec] == 0)
                return new Suradnica(suradnica.riadok + 1, suradnica.stlpec, 0, 1);

            else if((suradnica.riadok - 1 < vyska && suradnica.riadok - 1 >= 0)  &&  board[suradnica.riadok-1][suradnica.stlpec] == 0)
                return new Suradnica(suradnica.riadok - 1, suradnica.stlpec, 0, -1);

            else if(suradnica.riadok + 1 == vyska || suradnica.riadok - 1 < 0)
                return new Suradnica(-1,-1,-1,-1,-1);

            else return null;
        }
    }

    public Suradnica findNewBegin(Suradnica suradnica,int[][] board){
        int tmp = suradnica.bIndex;
        while(true){
            tmp++;
            if(tmp > (2*vyska + 2*sirka - 1)){
                tmp = 0;
            }
            if(tmp == suradnica.bIndex){
                return null;
            }
            if(canWalk(getStartindex(tmp),board)){
                return getStartindex(tmp);
            }
        }
    }

    public int walkGarden(ArrayList chromozome){
        Suradnica suradnica;
        int[][] board = newBoard();
        boolean blocked = false;
        int poradie = 1;
        for(int i = 0; i < maxGene; i++){
            suradnica = getStartindex((int) chromozome.get(i));
            suradnica.setbIndex((int) chromozome.get(i));

            if(!canWalk(suradnica,board)) {
                suradnica = findNewBegin(suradnica, board);

                if (suradnica == null) {
                    System.out.println("Something wrong");
                    break;
                }
                chromozome.set(i, suradnica.bIndex);
            }
                while(in(suradnica)){

                    if(!canWalk(suradnica,board)){
                        suradnica.decSuradnica();
                        suradnica = changeDirection(suradnica,board);

                        if(suradnica == null){
                            blocked = true;
                            break;
                        }
                        if(suradnica.bIndex == -1)
                            break;
                    }

                    board[suradnica.riadok][suradnica.stlpec] = poradie;
                    suradnica.incrementSuradnica();
                }
            poradie++;

                System.out.println(Arrays.deepToString(board).replaceAll("], ", "]" + System.lineSeparator()));
                System.out.println();
        }
      System.out.println(Arrays.deepToString(board).replaceAll("], ", "]" + System.lineSeparator()));
        return fitness(board);
    }


    public boolean in(Suradnica suradnica){
        return (suradnica.riadok < vyska && suradnica.riadok >= 0) && (suradnica.stlpec < sirka && suradnica.stlpec >= 0);
    }

    private boolean canWalk(Suradnica suradnica,int[][] board){
            return (board[suradnica.riadok][suradnica.stlpec] == 0);
    }

    public int[][] newBoard(){
        int[][] map;
        map = Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);
        return map;
    }

    public int fitness(int[][] Board){
        int iter = 0;
        for(int i = 0; i < vyska; i++)
            for(int j = 0; j < sirka; j++){
                if(board[i][j] >= 1)
                    iter++;
            }
        System.out.println("toto je fitness" + iter);
        return iter;
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
