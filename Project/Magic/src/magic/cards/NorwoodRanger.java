
package magic.cards;

import magic.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the 'Norwood Ranger' card
 * 
 * @author Gruppo 6
 */
public class NorwoodRanger extends AbstractCard {
    static private final String cardName = "Norwood Ranger";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new NorwoodRanger(); }
                } );

    public String name() { return cardName; }
    public String type() { return "Creature"; }
    public String ruleText() { 
        return "Put in play a creature " + cardName + "(1/2)"; 
    }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }

    
    private static class NorwoodRangerEffect extends AbstractCreatureCardEffect {
        public NorwoodRangerEffect(DecoratedPlayer p, Card c) { super(p,c); }
        
        protected Creature createCreature() { return new NorwoodRangerCreature(owner); }
    }
    public Effect getEffect(DecoratedPlayer p) { return new NorwoodRangerEffect(p,this); }
    
    
    
    private static class NorwoodRangerCreature extends AbstractCreature {
        
        NorwoodRangerCreature(DecoratedPlayer owner) { 
            super(owner);
        }
        
        public String name() { return cardName; }
        
        public int getPower() { return 1; }
        public int getToughness() { return 2; }

    }
    
}
