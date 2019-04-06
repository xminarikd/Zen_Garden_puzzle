import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.*;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Trieda, ktora sluzi na definovanie zahrady, jej vysky,sirky, poctu kamenov, resp. 2d pola, ktorý ju reprezentuje.
 */

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

        board[2][1] = -1;
        board[4][2] = -1;
        board[3][4] = -1;
        board[1][5] = -1;
        board[6][8] = -1;
        board[6][9] = -1;
        this.sirka = sirka;
        this.vyska = vyska;
        this.pocetKamen = pocetKamen;
        this.pocetPiesok = (vyska*sirka) - pocetKamen;
        this.polObvod = vyska + sirka;
        this.maxGene = polObvod + pocetKamen;
    }

    /**
     * Konstruktor na vytvorenie zahrady zo suboru.
     * @param file Súbor, ktory obsauhje testovaciu zahradu. Prvy riadok suboru obsahuje 3 cisla(vysku,sirku, pocet kamenov) dalsie obsahujú suradnice kamenov.
     * @throws IOException
     */

    public Garden(File file) throws IOException {

        String[] line;
        List<String> list = new ArrayList<>();
        Stream<String> stream = Files.lines(file.toPath());
        list = stream.collect(Collectors.toList());

        line = list.get(0).split(" ");
        this.vyska = Integer.parseInt(line[0]);
        this.sirka = Integer.parseInt(line[1]);
        this.pocetKamen = Integer.parseInt(line[2]);
        this.pocetPiesok = (vyska*sirka) - pocetKamen;
        this.polObvod = vyska + sirka;
        this.maxGene = polObvod + pocetKamen;

        this.board = new int[vyska][sirka];
        for (int i = 0; i < vyska; i++) {
            for (int j = 0; j < sirka; j++) {
                board[i][j] = 0;
            }
        }
        for(int i = 1; i <= pocetKamen; i++){
            line = list.get(i).split(" ");
            this.board[Integer.parseInt(line[0])][Integer.parseInt(line[1])] = -1;
        }
    }

    /**
     * Metoda na získanie zaciatocneho miesta hrabania, na zaklade pociatocneho cisla.
     * @param index Cislo, ktoré reprezentuje okraj zahrady, z ktoreho sa zacina hrabanie.
     * @return Suradnica, ktora zahrna pociatocne miesto pohybu a taktiez smer pohybu.
     */
    public Suradnica getStartindex(int index){
        int idx = Math.abs(index);
        if(idx < sirka){
            return new Suradnica(0,idx,1,0, index,null);
        }
        else if(idx < vyska + sirka){
            return new Suradnica(idx - sirka,sirka-1,0,-1, index,null);
        }
        else if(idx < (2 * sirka) + vyska){
            return new Suradnica(vyska - 1,idx - (vyska + sirka),-1,0, index,null);
        }
        else{
            return new Suradnica(idx - ((2 * sirka) + vyska),0,0,1, index,null);
        }
    }

    /**
     * Metoda na zmenenie smeru hrabania, ak nastala situaciu narazenia na prekazku.
     * V pripade, ak su oba smery volne vybera sa na zaklade kladnosti/zapornosti pociatocneho indexu suradnice.
     * V pripade ak ziadna zmena smeru nie je mozná vracia sa null.
     * @param suradnica Suradnica, pri ktorej nastalo narazenie na prekazku
     * @param board Mapa zahrady
     * @return Nova suradnica na pokracovanie hrabania, inak null
     */
    public Suradnica changeDirection(Suradnica suradnica, int[][] board){
        //hore/dole
        if(suradnica.pohyvStlpec == 0){
                if ((suradnica.stlpec + 1 < sirka && board[suradnica.riadok][suradnica.stlpec + 1] == 0) && (suradnica.stlpec - 1 < sirka && suradnica.stlpec - 1 >= 0) && board[suradnica.riadok][suradnica.stlpec - 1] == 0) {
                    if (suradnica.bIndex > 0)
                        return turnRight(suradnica);
                    else return turnLeft(suradnica);
                }

                else if(suradnica.stlpec + 1 < sirka && board[suradnica.riadok][suradnica.stlpec + 1] == 0)
                    return turnRight(suradnica);

                else if ((suradnica.stlpec - 1 < sirka && suradnica.stlpec - 1 >= 0) && board[suradnica.riadok][suradnica.stlpec - 1] == 0)
                    return turnLeft(suradnica);

                else if (suradnica.stlpec + 1 == sirka || suradnica.stlpec - 1 < 0)
                    return new Suradnica(-1, -1, -1, -1, suradnica.bIndex, suradnica);

                else return null;
            }
        //lavo/pravo
        else{
            if((suradnica.riadok + 1 < vyska && board[suradnica.riadok+1][suradnica.stlpec] == 0) && ((suradnica.riadok - 1 < vyska && suradnica.riadok - 1 >= 0)  &&  board[suradnica.riadok-1][suradnica.stlpec] == 0)) {
                if (suradnica.bIndex > 0)
                    return turnUp(suradnica);
                else return turnDown(suradnica);
            }

            else if(suradnica.riadok + 1 < vyska && board[suradnica.riadok+1][suradnica.stlpec] == 0)
                return turnDown(suradnica);

            else if((suradnica.riadok - 1 < vyska && suradnica.riadok - 1 >= 0)  &&  board[suradnica.riadok-1][suradnica.stlpec] == 0)
                return turnUp(suradnica);

            else if(suradnica.riadok + 1 == vyska || suradnica.riadok - 1 < 0)
                return new Suradnica(-1,-1,-1,-1,suradnica.bIndex,suradnica);

            else return null;
        }
    }

    public Suradnica turnRight(Suradnica suradnica){
        return new Suradnica(suradnica.riadok, suradnica.stlpec + 1, 0, 1, suradnica.bIndex, suradnica);
    }

    public Suradnica turnLeft(Suradnica suradnica){
        return new Suradnica(suradnica.riadok, suradnica.stlpec - 1, 0, -1, suradnica.bIndex, suradnica);
    }

    public Suradnica turnDown(Suradnica suradnica){
        return new Suradnica(suradnica.riadok + 1, suradnica.stlpec, 1, 0,suradnica.bIndex,suradnica);
    }

    public Suradnica turnUp(Suradnica suradnica){
       return new Suradnica(suradnica.riadok - 1, suradnica.stlpec, -1, 0,suradnica.bIndex,suradnica);
    }

    /**
     * Metoda na získanie novej súradnice hladania, v prípade, ak v predchadzajucej bol výsledok neuspesny
     * @param suradnica Suradnica, z ktorej sa nepodarilo hrabanie
     * @param board Mapa zahrady
     * @param hashSet Hashset, ktorý obsahuje zaciatky hrabania, z ktorých hrabanie bolo neuspesne.
     * @return Nová súradnica, nový zaciatok hrabania
     */
    public Suradnica findNewBegin(Suradnica suradnica,int[][] board, HashSet hashSet){
        int tmp = Math.abs(suradnica.bIndex);
        int tmp2;
        int iterator = 0;
        while(iterator++ < polObvod * 2){
            tmp++;
            if(tmp > (2*vyska + 2*sirka - 1)){
                tmp = 0;
            }
//            if(tmp == Math.abs(suradnica.bIndex)){
//                return null;
//            }
            if(hashSet.contains(tmp) && hashSet.contains(tmp * -1)){
                continue;
            }
            else if(hashSet.contains(tmp) && !hashSet.contains(tmp * -1)){
                tmp2 = tmp * -1;
            }
            else{
                tmp2 = tmp;
            }

            if(canWalk(getStartindex(tmp2),board)){
                return getStartindex(tmp2);
            }
        }
        return null;
    }

    /**
     * Metoda, ktora vykonava funkcionalitu hrabania zahrady.
     * @param individual Jednotlivec, ktory obsahuje chromozom, podla ktoreho prebieha hladanie.
     * @param result Boolean hodnota, ktora reprezentuje vypisovanie vysledneho hrabania.
     * @param findNewStart Boolean hodnota, ktorá urcuje ci sa budú hladať nove zaciatky hrabania.
     * @return Novy jedinec s novym chromozomom a fitness hodnotou
     */
    public Individual walkGarden(Individual individual, boolean result, boolean findNewStart){
        ArrayList chromozome = individual.getChromosome();
        Suradnica suradnica = null;
        Suradnica pred;
        HashSet hashSet = new HashSet();
        boolean blocked = false;
        int[][] board = newBoard();
        int fitness;
        int poradie = 1;
        for(int i = 0; i < chromozome.size(); i++){
            if(blocked){
                break;
            }
            pred = suradnica;
            suradnica = getStartindex((int) chromozome.get(i));
            suradnica.setbIndex((int) chromozome.get(i));
            if(!canWalk(suradnica,board)) {
                if(findNewStart) {
                    suradnica = findNewBegin(suradnica, board, hashSet);
                }
                else{
                    break;
                }
                if (suradnica == null) {
                    break;
                }
                chromozome.set(i, suradnica.bIndex);
            }
            while(in(suradnica)){

                if(!canWalk(suradnica,board)){
                    suradnica.decSuradnica();
                    pred = suradnica;
                    suradnica = changeDirection(suradnica,board);
                    //TODO koncenie v strede;
                    if(suradnica == null){
                        while(in(pred) || pred.pred != null){
                            if((!in(pred) || board[pred.riadok][pred.stlpec] != poradie) && pred.pred != null)
                                pred = pred.pred;
                            board[pred.riadok][pred.stlpec] = 0;
                            pred.decSuradnica();
                        }
                        Suradnica newBegin;
                        if(findNewStart){
                        hashSet.add(pred.bIndex);
                        newBegin = findNewBegin(pred,board,hashSet);
                        }
                        else{
                            blocked = true;
                            break;
                        }
                        if(newBegin == null){
                            break;
                        }
                        else{
                            chromozome.set(i,newBegin.bIndex);
                            i--;
                        }
                        poradie--;
                        break;
                    }
                    if(suradnica.riadok == -1)
                        break;
                }

                board[suradnica.riadok][suradnica.stlpec] = poradie;
                suradnica.incrementSuradnica();
            }
            poradie++;
        }
        if(result)
           System.out.println(Arrays.deepToString(board).replaceAll("], ", "]" + System.lineSeparator()));

        fitness = fitness(board);

        if(findNewStart){
            return walkGarden(new Individual(chromozome,fitness,false),false,false);
        }
        return new Individual(chromozome,fitness,false);
    }

    /**
     *
     * @return hodnota, ci sa suradnica nachadza vo vnutri zahrady.
     */
    public boolean in(Suradnica suradnica){
        return (suradnica.riadok < vyska && suradnica.riadok >= 0) && (suradnica.stlpec < sirka && suradnica.stlpec >= 0);
    }

    /**
     * @return hodnota, ci sa na danom mieste v zahrade nachadza nepohrabane miesto
     */
    private boolean canWalk(Suradnica suradnica,int[][] board){
            return (board[suradnica.riadok][suradnica.stlpec] == 0);
    }

    /**
     * @return Kopirovanie pôvodnej mapy
     */
    public int[][] newBoard(){
        int[][] map;
        map = Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);
        return map;
    }

    /**
     * Metoda, na vypocitanie fitnes, na zaklade poctu pohrabaných policok
     * @param board Mapa zahrady
     * @return Vracia hodnotu fitness, pre danu zahradu.
     */
    public int fitness(int[][] board){
        int iter = 0;
        for(int i = 0; i < vyska; i++)
            for(int j = 0; j < sirka; j++){
                if(board[i][j] >= 1)
                    iter++;
            }
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
