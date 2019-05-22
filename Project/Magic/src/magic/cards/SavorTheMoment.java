package magic.cards;

import magic.*;

/**
 * This class represents the 'Savor The Moment' card
 * 
 * @author Gruppo 6
 */
public class SavorTheMoment extends AbstractCard {
    static private final String cardName = "Savor the Moment";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new SavorTheMoment(); }
                } );
    
    private static class SavorTheMomentEffect extends AbstractCardEffect {
        public SavorTheMomentEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void resolve() {
            new SkipPhaseUntilEnd(Magic.instance.getCurrentPlayer(), Phases.UNTAP);
            Magic.instance.setTurnManager(new ExtraTurn());
        }
    }

    public Effect getEffect(DecoratedPlayer owner) { 
        return new SavorTheMomentEffect(owner, this); 
    }
    
    
    private static class ExtraTurn implements TurnManager {
        private DecoratedPlayer current;
        private DecoratedPlayer adversary;
        
        public ExtraTurn() {
            current = Magic.instance.getCurrentPlayer();
            adversary = Magic.instance.getCurrentAdversary();
        }
       

        public DecoratedPlayer getCurrentPlayer() { return current; }
        public DecoratedPlayer getCurrentAdversary() { return adversary; }

        public DecoratedPlayer nextPlayer() {
            Magic.instance.removeTurnManager(this);
            return current;
        }
        
    }
    
    public String name() { return cardName; }
    public String type() { return "Sorcery"; }
    public String ruleText() { return "Take an extra turn after this one. Skip the untap step of that turn."; }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
}
