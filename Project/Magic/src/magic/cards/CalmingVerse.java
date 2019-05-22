package magic.cards;

import magic.AbstractCard;
import magic.AbstractCardEffect;
import magic.Card;
import magic.CardConstructor;
import magic.Magic;
import magic.Enchantment;
import magic.DecoratedPlayer;
import magic.Effect;
import magic.StaticInitializer;
import java.util.ArrayList;

/**
 * This class represents the 'Calming Verse' card
 * 
 * @author Gruppo 6
 */
public class CalmingVerse extends AbstractCard {
    static private final String cardName = "Calming Verse";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new CalmingVerse(); }
                } );
    
    private static class DayOfJudgmentEffect extends AbstractCardEffect {
        public DayOfJudgmentEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void resolve() {
            
            ArrayList<Enchantment> enchantments = new ArrayList<>();
            enchantments.addAll(Magic.instance.getPlayer(0).getEnchantments());
            enchantments.addAll(Magic.instance.getPlayer(1).getEnchantments()); 
            for (Enchantment e:enchantments) {
                if (!e.isRemoved()) e.remove();
            }
        }
    }

    public Effect getEffect(DecoratedPlayer owner) { 
        return new DayOfJudgmentEffect(owner, this); 
    }
    
    public String name() { return cardName; }
    public String type() { return "Sorcery"; }
    public String ruleText() { return "Destroy all enchantments"; }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
}
