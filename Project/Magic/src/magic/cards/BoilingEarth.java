package magic.cards;

import magic.*;

import java.util.ArrayList;

/**
 * This class represents the 'Boiling Earth' card
 * 
 * @author Gruppo 6
 */
public class BoilingEarth extends AbstractCard {
    static private final String cardName = "Boiling Earth";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new BoilingEarth(); }
                } );
    
    private static class BoilingEarthEffect extends AbstractCardEffect {
        public BoilingEarthEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void resolve() {
            
            ArrayList<DecoratedCreature> creatures = new ArrayList<>();
            creatures.addAll(Magic.instance.getPlayer(0).getCreatures());
            creatures.addAll(Magic.instance.getPlayer(1).getCreatures()); 
            for (DecoratedCreature c:creatures) {
                if (!c.isRemoved()) c.inflictDamage(1);
            }
        }
    }

    public Effect getEffect(DecoratedPlayer owner) { 
        return new BoilingEarthEffect(owner, this); 
    }
    
    public String name() { return cardName; }
    public String type() { return "Sorcery"; }
    public String ruleText() { return cardName + " deals 1 damage to each creature"; }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
}