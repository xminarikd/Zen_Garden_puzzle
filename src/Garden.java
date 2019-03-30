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
        board[1][2] = -1;
        board[5][6] = -1;
        this.sirka = sirka;
        this.vyska = vyska;
        this.pocetKamen = pocetKamen;
        this.pocetPiesok = (vyska*sirka) - pocetKamen;
        this.polObvod = vyska + sirka;
        this.maxGene = polObvod + pocetKamen;
    }


    public Suradnica getStartindex(int index){
        if(index < sirka){
            return new Suradnica(0,index,0,1, index,null);
        }
        else if(index < vyska + sirka){
            return new Suradnica(index - sirka,sirka-1,-1,0, index,null);
        }
        else if(index < (2 * sirka) + vyska){
            return new Suradnica(vyska - 1,index - (vyska + sirka),0,-1, index,null);
        }
        else{
            return new Suradnica(index - ((2 * sirka) + vyska),0,1,0, index,null);
        }
    }

    public Suradnica changeDirection(Suradnica suradnica, int[][] board){
        //hore/dole
        if(suradnica.pohybRiadok == 0){
            if((suradnica.stlpec + 1 < sirka)  &&  board[suradnica.riadok][suradnica.stlpec+1] == 0)
                return new Suradnica(suradnica.riadok, suradnica.stlpec + 1, 1, 0,suradnica.bIndex,suradnica);

            else if((suradnica.stlpec - 1 < sirka && suradnica.stlpec - 1 >= 0)  &&  board[suradnica.riadok][suradnica.stlpec-1] == 0)
                return new Suradnica(suradnica.riadok, suradnica.stlpec - 1, -1, 0,suradnica.bIndex,suradnica);

            else if(suradnica.stlpec + 1 == sirka || suradnica.stlpec - 1 < 0)
                return new Suradnica(-1,-1,-1,-1,suradnica.bIndex,suradnica);

            else return null;
        }
        //lavo/pravo
        else{
            if((suradnica.riadok + 1 < vyska)  &&  board[suradnica.riadok+1][suradnica.stlpec] == 0)
                return new Suradnica(suradnica.riadok + 1, suradnica.stlpec, 0, 1,suradnica.bIndex,suradnica);

            else if((suradnica.riadok - 1 < vyska && suradnica.riadok - 1 >= 0)  &&  board[suradnica.riadok-1][suradnica.stlpec] == 0)
                return new Suradnica(suradnica.riadok - 1, suradnica.stlpec, 0, -1,suradnica.bIndex,suradnica);

            else if(suradnica.riadok + 1 == vyska || suradnica.riadok - 1 < 0)
                return new Suradnica(-1,-1,-1,-1,suradnica.bIndex,suradnica);

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

    public ArrayList walkGarden(ArrayList chromozome){
        Suradnica suradnica = null;
        Suradnica pred = null;
        int[][] board = newBoard();
        boolean blocked = false;
        int fitness;
        int poradie = 1;
        for(int i = 0; i < chromozome.size(); i++){
            pred = suradnica;
            suradnica = getStartindex((int) chromozome.get(i));
            suradnica.setbIndex((int) chromozome.get(i));

            if(!canWalk(suradnica,board)) {
                suradnica = findNewBegin(suradnica, board);

                if (suradnica == null) {
                    System.out.println("Something wrong");
                    chromozome.subList(i,chromozome.size()).clear();
                    break;
                }
                chromozome.set(i, suradnica.bIndex);
            }
                while(in(suradnica)){

                    if(!canWalk(suradnica,board)){
                        suradnica.decSuradnica();
                        pred = suradnica;
                        suradnica = changeDirection(suradnica,board);

                        if(suradnica == null){
                            System.out.println(Arrays.deepToString(board).replaceAll("], ", "]" + System.lineSeparator()));
                            System.out.println();
                            while(in(pred) || pred.pred != null){
                                if((!in(pred) || board[pred.riadok][pred.stlpec] != poradie) && pred.pred != null)
                                    pred = pred.pred;
                                board[pred.riadok][pred.stlpec] = 0;
                                pred.decSuradnica();
                            }
                            chromozome.remove(i);
                            blocked = true;
                            break;
                        }
                        if(suradnica.riadok == -1)
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

        fitness = fitness(board);
        chromozome.add(fitness);
        return chromozome;
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

    public int fitness(int[][] board){
        int iter = 0;
        for(int i = 0; i < vyska; i++)
            for(int j = 0; j < sirka; j++){
                if(board[i][j] >= 1)
                    iter++;
            }
        System.out.println("toto je fitness " + iter);
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
