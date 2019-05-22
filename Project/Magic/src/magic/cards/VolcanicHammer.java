package magic.cards;

import magic.*;

import java.util.ArrayList;

/**
 * This class represents the 'Volcanic Hammer' card
 * 
 * @author Gruppo 6
 */
public class VolcanicHammer extends AbstractCard {
    static private final String cardName = "Volcanic Hammer";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new VolcanicHammer(); }
                } );
    
    public String name() { return cardName; }
    public String type() { return "Sorcery"; }
    public String ruleText() { return "Deal 3 damage to target creature or player"; }
    public String toString() { return name() + " [" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
    private static class VolcanicHammerEffect extends AbstractCardEffect implements TargetingEffect {
        Damageable target;
        
        public VolcanicHammerEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void play() {
            pickTarget(owner);
            super.play();
        }
        
        public String toString() {
            if (target==null) return super.toString() + " (no target)";
            else return name() + " [Deal 3 damage to " + target.name() + "]";
        }
        
        public void pickTarget(DecoratedPlayer p) {
            System.out.println( p.name() + ": choose target for " + name() );
            
            ArrayList<Damageable> targets = new ArrayList<>();
            int i=1;
            
            DecoratedPlayer curPlayer = Magic.instance.getPlayer(0);
            if (curPlayer.isTarget()) {
                System.out.println(i+ ") " + curPlayer.name());
                targets.add(curPlayer);
                ++i;
            }
            for (DecoratedCreature c:curPlayer.getCreatures()) {
                if (c.isTarget()) {
                    System.out.println(i+ ") " + curPlayer.name() + ": " + c.name());
                    targets.add(c);
                    ++i;
                }
            }
            
            curPlayer = Magic.instance.getPlayer(1);
            if (curPlayer.isTarget()) {
                System.out.println(i+ ") " + curPlayer.name());
                targets.add(curPlayer);
                ++i;
            }
            for (DecoratedCreature c:curPlayer.getCreatures()) {
                if (c.isTarget()) {
                    System.out.println(i+ ") " + curPlayer.name() + ": " + c.name());
                    targets.add(c);
                    ++i;
                }
            }
            
            int idx= Magic.instance.getScanner().nextInt()-1;
            if (idx<0 || idx>=targets.size()) target=null;
            else target=targets.get(idx);
        }
        
        public void resolve() {
            if (target==null) {
                System.out.println(cardName + " has no target");
            } else if (target.isRemoved() ) {
                System.out.println(cardName + " target not in play anymore");
            } else {
                target.inflictDamage(3);
            }
        }
    }

    public Effect getEffect(DecoratedPlayer owner) { 
        return new VolcanicHammerEffect(owner, this); 
    }
}
