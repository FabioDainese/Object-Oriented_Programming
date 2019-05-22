package magic.cards;

import magic.AbstractCard;
import magic.AbstractEnchantment;
import magic.AbstractEnchantmentCardEffect;
import magic.Card;
import magic.CardConstructor;
import magic.Magic;
import magic.DecoratedCreature;
import magic.DecoratedPlayer;
import magic.Effect;
import magic.Enchantment;
import magic.Permanent;
import magic.StaticInitializer;
import magic.TriggerAction;
import magic.Triggers;
import java.util.ArrayList;

/**
 * This class represents the 'AEther Flash' card
 * 
 * @author Gruppo 6
 */
public class AEtherFlash extends AbstractCard {
    static private final String cardName = "AEther Flash";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new AEtherFlash(); }
                } );

    public String name() { return cardName; }
    public String type() { return "Enchantment"; }
    public String ruleText() { 
        return "Whenever a creature comes into play, " + name() + " deals 2 damage to it"; 
    }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
    private static class AEtherFlashEffect extends AbstractEnchantmentCardEffect {
        AEtherFlashEffect(DecoratedPlayer p, Card c) { super(p,c); }
        @Override
        public Enchantment createEnchantment() {
            return new AEtherFlashEnchantment(owner);
        }
    }
    public Effect getEffect(DecoratedPlayer p) { return new AEtherFlashEffect(p,this); }
    
    private static class AEtherFlashEnchantment extends AbstractEnchantment {
        AEtherFlashTrigger action = new AEtherFlashTrigger();
        AEtherFlashEnchantment(DecoratedPlayer p) { super(p); }
        public String name() { return cardName; }
        public void insert() {
            super.insert();
            Magic.instance.getTriggers().register(Triggers.ENTER_CREATURE_FILTER, action);
        }
        public void remove() {
            Magic.instance.getTriggers().deregister(action);
            super.remove();
        }
        
        private static class AEtherFlashTrigger implements TriggerAction {
            @Override
            public void execute(Object arg) {
                if (arg==null || !(arg instanceof DecoratedCreature)) return;
                
                DecoratedCreature c=(DecoratedCreature)arg;
                c.inflictDamage(2);
            }
            
        }
    }
}
