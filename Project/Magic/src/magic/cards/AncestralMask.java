package magic.cards;

import magic.AbstractCard;
import magic.AbstractCreatureDecorator;
import magic.AbstractEnchantment;
import magic.AbstractEnchantmentCardEffect;
import magic.Card;
import magic.CardConstructor;
import magic.Magic;
import magic.DecoratedCreature;
import magic.DecoratedPlayer;
import magic.Effect;
import magic.Enchantment;
import magic.StaticInitializer;
import magic.TargetingEffect;
import magic.TriggerAction;
import magic.Triggers;
import java.util.ArrayList;

/**
 * This class represents the 'Ancestral Mask' card
 * 
 * @author Gruppo 6
 */
public class AncestralMask extends AbstractCard {
    static private final String cardName = "Ancestra lMask";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new AncestralMask(); }
                } );

    public String name() { return cardName; }
    public String type() { return "Enchantment"; }
    public String ruleText() { 
        return "Target creature gets +2/+2 for each other enchantment in play"; 
    }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
    private static class AncestralMaskEffect    extends AbstractEnchantmentCardEffect 
                                                implements TargetingEffect {
        private DecoratedCreature target;
        AncestralMaskEffect(DecoratedPlayer p, Card c) { super(p,c); }
        @Override
        public Enchantment createEnchantment() {
            return new AncestralMaskEnchantment(owner,target);
        }
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
            if (idx<0 || idx>=creatures.size()) target=null;
            else target=creatures.get(idx);
        }
    }
    public Effect getEffect(DecoratedPlayer p) { return new AncestralMaskEffect(p,this); }
    
    private static class AncestralMaskEnchantment extends AbstractEnchantment {
        private final DecoratedCreature target;
        AncestralMaskTrigger action = new AncestralMaskTrigger();
        AncestralMaskDecorator decorator = new AncestralMaskDecorator();
        AncestralMaskEnchantment(DecoratedPlayer p, DecoratedCreature c) { 
            super(p); 
            target=c;
        }
        public String name() { return cardName; }
        public void insert() {
            super.insert();
            target.addDecorator(decorator);
            Magic.instance.getTriggers().register(Triggers.ENTER_ENCHANTMENT_FILTER | Triggers.EXIT_ENCHANTMENT_FILTER, action);
            decorator.update(); 
        }
        public void remove() {
            Magic.instance.getTriggers().deregister(action);
            super.remove();
            target.removeDecorator(decorator);
        }
        
        private class AncestralMaskTrigger implements TriggerAction {
            @Override
            public void execute(Object arg) { 
                decorator.update(); 
            }
        }
        
        private class AncestralMaskDecorator extends AbstractCreatureDecorator {
            int addition=0;
            
            public int getPower() { return decorated.getPower()+addition; }
            public int getToughness() { return decorated.getToughness()+addition; }
            public void insert() {
                super.insert();
                update();
            }
            
            public void update() {
                
                addition = 2*( Magic.instance.getPlayer(0).getEnchantments().size() +
                        Magic.instance.getPlayer(1).getEnchantments().size() - 1 );
                
                
                if (target.getDamage() >= target.getToughness()) {
                    System.out.println("Removing " + target.name() + " due to change in " + cardName + " bonus");
                    target.destroy();
                }
            }
        }
    }
}
