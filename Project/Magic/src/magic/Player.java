package magic;

import java.util.Iterator;
import java.util.List;

/**
 * @author Gruppo 6
 */
public interface Player {
   
    boolean isTarget();
    
    void setDeck(Iterator<Card> deck);
    Library getDeck();
    
    List<Card> getHand();
    int getMaxHandSize();
    void setMaxHandSize(int size);
    void draw();
    
    
    int getLife();
    void inflictDamage(int dmg);
    void inflictDamage(Creature c, int dmg);
    void heal(int pts);
    void lose(String s);
    
    
    List<DecoratedCreature> getCreatures();
    void destroy(Creature c);
    List<Enchantment> getEnchantments();
    void destroy(Enchantment e);
}
