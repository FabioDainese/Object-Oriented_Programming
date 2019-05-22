package magic.cards;

import magic.*;

/**
 * This class represents the 'False Peace' card
 * 
 * @author Gruppo 6
 */
public class FalsePeace extends AbstractCard {
    static private final String cardName = "False Peace";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new FalsePeace(); }
                } );
    
    public String name() { return cardName; }
    public String type() { return "Sorcery"; }
    public String ruleText() { return "Target player skips his next combat phase"; }
    public String toString() { return name() + " [" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
    private static class FalsePeaceEffect extends AbstractCardEffect implements TargetingEffect {
        DecoratedPlayer target;
        
        public FalsePeaceEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void play() {
            pickTarget(owner);
            super.play();
        }
        
        public String toString() {
            if (target==null) return super.toString() + " (no target)";
            else return name() + " [" + target.name() + " skips his next combat phase]";
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
            if (target!=null) {
                target.setPhase(Phases.COMBAT,new SkipPhaseCount(Phases.COMBAT));
                target.setPhase(Phases.COMBAT_ATTACK,new SkipPhaseCount(Phases.COMBAT_ATTACK));
                target.setPhase(Phases.COMBAT_DEFENSE,new SkipPhaseCount(Phases.COMBAT_DEFENSE));
                target.setPhase(Phases.COMBAT_DAMAGE,new SkipPhaseCount(Phases.COMBAT_DAMAGE));
            }
        }
    }

    public Effect getEffect(DecoratedPlayer owner) { 
        return new FalsePeaceEffect(owner, this); 
    }
}
