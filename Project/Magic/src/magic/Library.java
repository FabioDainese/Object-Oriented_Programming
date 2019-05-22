package magic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Gruppo 6
 */
public class Library {
    private ArrayList<Card> cards= new ArrayList<Card>();
    final private Random rnd=new Random();
    final private ConcretePlayer owner;
    
    public void shuffle() {
    final int end=cards.size();
    for (int i=0; i!=end-1; ++i) {
        int idx = i+ rnd.nextInt(end-i);
        Card tmp = cards.get(idx);
        cards.set(idx,cards.get(i));
        cards.set(i, tmp);
        }
    }
    
     public Library(ConcretePlayer p) { owner=p; }
     
     public void add(Card c) { cards.add(c); }
     public void add(Iterator<Card> deck) {
        while (deck.hasNext())
            cards.add(deck.next());
    }
    
    Card draw() {
        if (cards.isEmpty()) 
            owner.lose("out of cards");
        
        return cards.remove(cards.size()-1);
    }
}
