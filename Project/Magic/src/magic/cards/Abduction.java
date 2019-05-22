package magic.cards;

import magic.AbstractCard;
import magic.AbstractCardEffect;
import magic.AbstractCreatureCardEffect;
import magic.AbstractCreatureDecorator;
import magic.AbstractEnchantment;
import magic.AbstractEnchantmentCardEffect;
import magic.Card;
import magic.CardConstructor;
import magic.Magic;
import magic.Creature;
import magic.DecoratedCreature;
import magic.DecoratedPlayer;
import magic.Effect;
import magic.Enchantment;
import magic.StaticInitializer;
import magic.TargetingEffect;
import java.util.ArrayList;

/**
 * This class represents the 'Abduction' card
 * 
 * @author Gruppo 6
 */
public class Abduction extends AbstractCard {
    static private final String cardName = "Abduction";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new Abduction(); }
                } );

    public String name() { return cardName; }
    public String type() { return "Enchantment"; }
    public String ruleText() { 
        return "when " + cardName + " comes into play, untap target creature. You control enchanted creature. When enchanted creature exit play, return it to its owner instead"; 
    }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
    private static class AbductionEffect extends AbstractCardEffect implements TargetingEffect {
        public AbductionEffect(DecoratedPlayer p, Card c) { super(p,c); }
        
        
        private DecoratedCreature target=null;
        private DecoratedPlayer previousOwner=null;
        
        public void play() {
            pickTarget(owner);
            super.play();
        }
        
        public void pickTarget(DecoratedPlayer p) {
            System.out.println( p.name() + ": choose target for " + name() );
            
            ArrayList<DecoratedCreature> creatures=new ArrayList<>();
            int i=1;
            
            DecoratedPlayer player1 = Magic.instance.getPlayer(0);
            DecoratedPlayer player2 = Magic.instance.getPlayer(1);
            
            for (DecoratedCreature c: player1.getCreatures()) {
                if (c.isTarget()) {
                    System.out.println( i + ") " + player1.name() + ": " + c);
                    ++i;
                    creatures.add(c);
                }
            }
            for (DecoratedCreature c: player2.getCreatures()) {
                if (c.isTarget()) {
                    System.out.println( i + ") " + player2.name() + ": " + c);
                    ++i;
                    creatures.add(c);
                }
            }
            
            int idx= Magic.instance.getScanner().nextInt()-1;
            if (idx<0 || idx>=creatures.size()) {
                target=null;
            } else {
                target=creatures.get(idx);
            }
        }
        
        public void resolve() {
            Enchantment e;
            if (target==null || target.isRemoved()) {
                e=new AbductionEnchantment(owner, null, null);
            } else {
                DecoratedPlayer previousOwner=target.getOwner();
                e=new AbductionEnchantment(owner, target, previousOwner);
            }
            owner.getEnchantments().add(e);
            e.insert();
            super.resolve();
        }
    }
    public Effect getEffect(DecoratedPlayer p) { return new AbductionEffect(p, this); }
    
    private static class AbductionEnchantment extends AbstractEnchantment {
        private final DecoratedCreature target;
        private final DecoratedPlayer previousOwner;
        private final AbductionDecorator decorator;
        public AbductionEnchantment(DecoratedPlayer p, DecoratedCreature c, 
                    DecoratedPlayer po) { 
            super(p); 
            target=c;
            previousOwner=po;
            decorator=new AbductionDecorator(previousOwner);
        }
        
        public void insert() {
            if (target!=null) {
                previousOwner.getCreatures().remove(target);
                owner.getCreatures().add(target);
                target.setOwner(owner);
                target.addDecorator(decorator);
            }
            super.insert();
        }
        
        public void remove() {
            super.remove();
            if (target==null || target.isRemoved() || target.getOwner()!=owner) return;
            owner.getCreatures().remove(target);
            previousOwner.getCreatures().add(target);
            target.setOwner(previousOwner);
            target.removeDecorator(decorator);
        }
        
        public String name() { return "Abduction"; }
        public String toString() { return name() + " (Enchantment)"; }
    }

    private static class AbductionDecorator extends AbstractCreatureDecorator {
        private final DecoratedPlayer previousOwner;

        public AbductionDecorator(DecoratedPlayer po) { previousOwner=po; }
        public void remove() {
            DecoratedCreature dc=new DecoratedCreature(previousOwner, decorated);
            dc.resetDamage();
            decorated=null;
            previousOwner.getCreatures().add(dc);
            dc.insert();
            //test for survival
            if (dc.getToughness() <= dc.getDamage()) { dc.destroy(); }
        }
    }
}
