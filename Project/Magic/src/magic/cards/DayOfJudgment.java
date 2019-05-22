package magic.cards;

import magic.*;

import java.util.ArrayList;

/**
 * This class represents the 'Day Of Judgment' card
 * 
 * @author Gruppo 6
 */
public class DayOfJudgment extends AbstractCard {
    static private final String cardName = "Day of Judgment";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new DayOfJudgment(); }
                } );
    
    private static class DayOfJudgmentEffect extends AbstractCardEffect {
        public DayOfJudgmentEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void resolve() {
            
            ArrayList<DecoratedCreature> creatures = new ArrayList<>();
            creatures.addAll(Magic.instance.getPlayer(0).getCreatures());
            creatures.addAll(Magic.instance.getPlayer(1).getCreatures()); 
            for (DecoratedCreature c:creatures) {
                if (!c.isRemoved()) c.destroy();
            }
        }
    }

    public Effect getEffect(DecoratedPlayer owner) { 
        return new DayOfJudgmentEffect(owner, this); 
    }
    
    public String name() { return cardName; }
    public String type() { return "Sorcery"; }
    public String ruleText() { return "Destroy all creatures"; }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
}