package magic.cards;

import magic.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the 'Bronze Sable' card
 * 
 * @author Gruppo 6
 */
public class BronzeSable extends AbstractCard {
    static private final String cardName = "Bronze Sable";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new BronzeSable(); }
                } );

    public String name() { return cardName; }
    public String type() { return "Creature"; }
    public String ruleText() { 
        return "Put in play a creature " + cardName + "(2/1)"; 
    }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }

    
    private static class BronzeSableEffect extends AbstractCreatureCardEffect {
        public BronzeSableEffect(DecoratedPlayer p, Card c) { super(p,c); }
        
        protected Creature createCreature() { return new BronzeSableCreature(owner); }
    }
    public Effect getEffect(DecoratedPlayer p) { return new BronzeSableEffect(p,this); }
    
    
    private static class BronzeSableCreature extends AbstractCreature {
        
       BronzeSableCreature(DecoratedPlayer owner) { 
            super(owner);
        }
        
        public String name() { return cardName; }
        
        public int getPower() { return 2; }
        public int getToughness() { return 1; }

    }
    
}

