package magic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * @author Gruppo 6
 */
public class Magic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            System.out.println("╔═════════════════════════════════════╗");
            System.out.println("║   Preliminary Checks and Loadings   ║");
            System.out.println("╚═════════════════════════════════════╝\n");
            System.out.println("\nKnown cards: " + CardFactory.size() + "\n");
            for (String s:CardFactory.known_cards()) {
                System.out.println(s);
            }
            System.out.println("\n╔═══════════════════╗");
            System.out.println("║   Initial Setup   ║");
            System.out.println("╚═══════════════════╝\n");
            
            instance.setup();

            System.out.println("\n╔══════════════════════════╗");
            System.out.println("║   The Battle Has Begun   ║");
            System.out.println("╚══════════════════════════╝\n");
            instance.run();
        } catch(RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    //Signleton and instance access
    public static final Magic instance = new Magic();
    
    public void setup() {
        System.out.println("Please write player 1's name:");
        Players[0].setName(reader.nextLine());
        
        ArrayList<Card> p1deck = new ArrayList<Card>();
        System.out.println( Players[0].name() + " give the filename of your deck (with extension):");
        try {
            Scanner p1deckFile = new Scanner(new File(reader.nextLine()));
            while(p1deckFile.hasNextLine()) {
                p1deck.add(CardFactory.construct(p1deckFile.nextLine()));
            }
        } catch (IOException ex) { throw new RuntimeException("cannot read player 1's deck file!" ); }
        Players[0].setDeck(p1deck.iterator());

        System.out.println("Please write player 2's name:");
        Players[1].setName(reader.nextLine());
        
        ArrayList<Card> p2deck = new ArrayList<Card>();
        System.out.println( Players[1].name() + " give the filename of your deck (with extension):");
        try {
            Scanner p2deckFile = new Scanner(new File(reader.nextLine()));
            while(p2deckFile.hasNextLine()) {
                p2deck.add(CardFactory.construct(p2deckFile.nextLine()));
            }
        } catch (IOException ex) { throw new RuntimeException("cannot read player 1's deck file!" ); }
        Players[1].setDeck(p2deck.iterator());    
    }
    
    
    //game setup 
    private Magic() { 
        turnManagerStack.push( new DefaultTurnManager(Players) );
        
        Players[0]=new DecoratedPlayer();
        Players[0].setName("Player 1");
        
        Players[1]=new DecoratedPlayer();
        Players[1].setName("Player 2");
    }
    
    //execute game
    public void run() {
        Players[0].getDeck().shuffle();
        Players[1].getDeck().shuffle();
                
        for (int i=0; i!=5; ++i) Players[0].draw();
        for (int i=0; i!=5; ++i) Players[1].draw();
        
        Players[0].setPhase(Phases.DRAW,new SkipPhaseCount(Phases.DRAW));
        try {
            while (true) { instance.nextPlayer().executeTurn(); }
        }catch(EndOfGame e){
            System.out.println(e.getMessage());
        }
    }
    
    
    // Player and turn management
    private final DecoratedPlayer[] Players = new DecoratedPlayer[2];
    private final Deque<TurnManager>  turnManagerStack = new ArrayDeque<>();
    public void setTurnManager(TurnManager m) { turnManagerStack.push(m); }
    public void removeTurnManager(TurnManager m) { turnManagerStack.remove(m); }
    
    public DecoratedPlayer getPlayer(int i) { return Players[i]; }    
    public DecoratedPlayer getCurrentPlayer() { return turnManagerStack.peek().getCurrentPlayer(); }
    public DecoratedPlayer getCurrentAdversary() { return turnManagerStack.peek().getCurrentAdversary(); }
    public DecoratedPlayer nextPlayer() { return turnManagerStack.peek().nextPlayer(); }
    
    
    // Stack access
    private final CardStack stack = new CardStack();
    public CardStack getStack() { return stack; }
    
    
    //Trigger access
    private final Triggers triggers=new Triggers();
    public Triggers getTriggers() { return triggers; }
    
    
    //IO resources  to be dropped in final version
    private final Scanner reader = new Scanner(System.in);
    public Scanner getScanner() { return reader; }
    
    private final ArrayList<CombatEntry> combatField = new ArrayList<>();
    public ArrayList<CombatEntry> getCombatField() { return combatField; }
}

