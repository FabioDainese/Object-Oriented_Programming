package magic;

import java.util.Iterator;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;

/**
 * @author Gruppo 6
 */
public class ConcretePlayer implements Player {
    // basic properties: name, library, deck, and life

    public boolean isTarget() {return true;}
    
    private final Library library = new Library(this);

    public void setDeck(Iterator<Card> deck) { library.add(deck); }
    public Library getDeck() { return library; }
    
    private int life=10;
    public int getLife() {return life;}
    
    // need to attach strategy/decorator
    public void inflictDamage(int pts) {
        life -= pts;
        if (life <=0) lose("received fatal damage");
    }
    
    public void inflictDamage(Creature c, int dmg) { c.inflictDamage(dmg); }
    
    public void heal(int pts) { life += pts; }
                 
    // player looses. might need strategy/decorator
    public void lose(String s) { throw new EndOfGame(topDecorator.name() + " lost the game: "+ s); }     
    
    DecoratedPlayer topDecorator;
    public ConcretePlayer(DecoratedPlayer p) {
        topDecorator=p;
    }
    
    
    
     // hand management
    private final ArrayList<Card> hand = new ArrayList<>();
    private int maxHandSize=7;
    
    public List<Card> getHand() { return hand; }
    public int getMaxHandSize() { return maxHandSize; }
    public void setMaxHandSize(int size) { maxHandSize=size; }
    
    public void draw() {
        Card drawn = library.draw();
        System.out.println(topDecorator.name() +" draw card: " + drawn.name());
        hand.add(drawn);
    }
    
        
    
    // Creature management
    private final ArrayList<DecoratedCreature> creatures = new ArrayList<>();
    public List<DecoratedCreature> getCreatures() {return creatures;}
    // destroy a creature in play
    public void destroy(Creature c) {creatures.remove(c);} 
    
    
    
    // Enchantments management
    private final ArrayList<Enchantment> enchantments = new ArrayList<>();
    public List<Enchantment> getEnchantments() {return enchantments;}
    // destroy a creature in play
    public void destroy(Enchantment c) {enchantments.remove(c);} 
}
