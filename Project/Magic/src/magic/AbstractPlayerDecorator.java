package magic;

import java.util.Iterator;
import java.util.List;

/**
 * @author Gruppo 6
 */
public class AbstractPlayerDecorator implements PlayerDecorator {
    protected Player decorated;
    @Override
    public PlayerDecorator decorate(Player c) { 
        decorated=c; 
        return this;
    }
    @Override
    public Player removeDecorator(PlayerDecorator d) {
            Player result = this;
            if (d==this) {
                result = decorated;
                decorated = null;
            }
            else if (decorated instanceof PlayerDecorator)
                decorated=((PlayerDecorator)decorated).removeDecorator(d);
            return result;
        }
    
    public boolean isTarget() { return decorated.isTarget(); }
    
    public void setDeck(Iterator<Card> deck) { decorated.setDeck(deck); }
    public Library getDeck() { return decorated.getDeck(); }
    
    public List<Card> getHand() { return decorated.getHand(); }
    public int getMaxHandSize() { return decorated.getMaxHandSize(); }
    public void setMaxHandSize(int size) { decorated.setMaxHandSize(size); }
    public void draw() { decorated.draw(); }
    
    
    public int getLife() { return decorated.getLife(); }
    public void inflictDamage(int dmg) { decorated.inflictDamage(dmg); }
    public void inflictDamage(Creature c, int dmg) { decorated.inflictDamage(c, dmg); }
    public void heal(int pts) { decorated.heal(pts); }
    public void lose(String s) { decorated.lose(s); }
    
    
    public List<DecoratedCreature> getCreatures() { return decorated.getCreatures(); }
    public void destroy(Creature c) { decorated.destroy(c); }
    public List<Enchantment> getEnchantments() { return decorated.getEnchantments(); }
    public void destroy(Enchantment e) { decorated.destroy(e); }
}
