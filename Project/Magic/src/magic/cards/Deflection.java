package magic.cards;

import java.util.ArrayList;

import magic.*;

/**
 * This class represents the 'Deflection' card
 * 
 * @author Gruppo 6
 */
public class Deflection extends AbstractCard {
    static private final String cardName = "Deflection";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new Deflection(); }
                } );
    
    public String name() { return cardName; }
    public String type() { return "Instant"; }
    public String ruleText() { return "Change the target of target spell with a single target"; }
    public String toString() { return name() + " [" + ruleText() +"]";}
    public boolean isInstant() { return true; }
    
    private static class DeflectionEffect extends AbstractCardEffect implements TargetingEffect {
        TargetingEffect target;
        
        public DeflectionEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void play() {
            pickTarget(owner);
            super.play();
        }
        
        public String toString() {
            if (target==null) return super.toString() + " (no target)";
            else return name() + " [Change the target of " + target.name() + " with a single target]";
        }
        
        public void pickTarget(DecoratedPlayer p) {
            System.out.println( p.name() + ": choose target for " + name() );
            CardStack stack = Magic.instance.getStack();
            ArrayList<TargetingEffect> effects=new ArrayList<>();
            int i=1;
            for (Effect e:stack) {
                if (e.isTarget() && e instanceof TargetingEffect)
                {
                    TargetingEffect te=(TargetingEffect)e;
                    effects.add(te);
                    System.out.println( i + ") " + te.name());
                    ++i;
                }
            }
            
            int idx= Magic.instance.getScanner().nextInt()-1;
            if (idx<0 || idx>=effects.size()) target=null;
            else target=effects.get(idx);
        }
        
        public void resolve() {
            if (target==null) {
                System.out.println(cardName + " has no target");
            } else if (target.isRemoved()) {
                System.out.println(cardName + " target is not on the stack anymore");
            } else {
                target.pickTarget(owner);
            }
        }
    }

    public Effect getEffect(DecoratedPlayer owner) { 
        return new DeflectionEffect(owner, this); 
    }
}

