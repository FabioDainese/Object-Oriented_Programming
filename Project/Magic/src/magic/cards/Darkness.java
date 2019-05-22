package magic.cards;

import magic.*;

/**
 * This class represents the 'Darkness' card
 * 
 * @author Gruppo 6
 */
public class Darkness extends AbstractCard {
    static private final String cardName = "Darkness";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new Darkness(); }
                } );
    
    private static class DarknessEffect extends AbstractCardEffect {
        public DarknessEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void resolve() {
            DecoratedPlayer p = Magic.instance.getCurrentPlayer();
             new SkipPhaseUntilEnd(p,Phases.COMBAT_DAMAGE);
            
        }
    }
   
    
    public Effect getEffect(DecoratedPlayer owner) { 
        return new DarknessEffect(owner, this); 
    }
    
    public String name() { return cardName; }
    public String type() { return "Instant"; }
 
    public String ruleText() { return "Prevents all combat damage that would be delt this turn"; }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return true; }
    
}
