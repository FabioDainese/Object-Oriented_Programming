package magic.cards;

import magic.*;

/**
 * This class represents the 'Fatigue' card
 * 
 * @author Gruppo 6
 */
public class Fatigue extends AbstractCard {
    static private final String cardName = "Fatigue";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new Fatigue(); }
                } );
    
    public String name() { return cardName; }
    public String type() { return "Sorcery"; }
    public String ruleText() { return "Target player skips his next draw phase"; }
    public String toString() { return name() + " [" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
    private static class FatigueEffect extends AbstractCardEffect implements TargetingEffect {
        DecoratedPlayer target;
        
        public FatigueEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void play() {
            pickTarget(owner);
            super.play();
        }
        
        public String toString() {
            if (target==null) return super.toString() + " (no target)";
            else return name() + " [" + target.name() + " skips his next draw phase]";
        }
        
        public void pickTarget(DecoratedPlayer p) {
            System.out.println( p.name() + ": choose target for " + name() );
            System.out.println("1) " + Magic.instance.getPlayer(0).name());
            System.out.println("2) " + Magic.instance.getPlayer(1).name());
            
            int idx = Magic.instance.getScanner().nextInt()-1;
            if (idx<0 || idx>1) target=null;
            else target=Magic.instance.getPlayer(idx);
        }
        
        public void resolve() {
            if (target!=null)
                new SkipPhaseUntilEnd(target, Phases.DRAW);
        }
    }

    public Effect getEffect(DecoratedPlayer owner) { 
        return new FatigueEffect(owner, this); 
    }
}
