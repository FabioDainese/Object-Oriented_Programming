package magic.cards;

import magic.*;
import java.util.ArrayList;

/**
 * This class represents the 'Cancel' card
 * 
 * @author Gruppo 6
 */
public class Cancel extends AbstractCard {
    static private final String cardName = "Cancel";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new Cancel(); }
                } );
    
    public String name() { return cardName; }
    public String type() { return "Instant"; }
    public String ruleText() { return "Counter target spell"; }
    public String toString() { return name() + " [" + ruleText() +"]";}
    public boolean isInstant() { return true; }
    
    private static class CancelEffect extends AbstractCardEffect implements TargetingEffect {
        Effect target;
        
        public CancelEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void play() {
            pickTarget(owner);
            super.play();
        }
        
        public String toString() {
            if (target==null) return super.toString() + " (no target)";
            else return name() + " [Counter " + target.name() + "]";
        }
        
        public void pickTarget(DecoratedPlayer p) {
            System.out.println( owner.name() + ": choose target for " + name() );
            CardStack stack = Magic.instance.getStack();
            ArrayList<Effect> effects = new ArrayList<>();
            int i=1;
            for (Effect e:Magic.instance.getStack()) {
                if (e.isTarget()) {
                    System.out.println( i + ") " + e.name());
                    ++i;
                    effects.add(e);
                }
            }
            
            int idx= Magic.instance.getScanner().nextInt()-1;
            if (idx<0 || idx>=effects.size()) target=null;
            else target=effects.get(idx);
        }
        
        public void resolve() {
            if (target==null) {
                System.out.println(cardName + " has no target");
            } else if ( target.isRemoved() ) {
                System.out.println(cardName + " target is not on the stack anymore");
            } else {
                System.out.println(cardName + " removing " + target.name() + " from stack");
                Magic.instance.getStack().remove(target);
            }
        }
    }

    public Effect getEffect(DecoratedPlayer owner) { 
        return new CancelEffect(owner, this); 
    }
}

