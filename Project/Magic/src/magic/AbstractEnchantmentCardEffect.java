package magic;

/**
 * @author Gruppo 6
 */
public abstract class AbstractEnchantmentCardEffect extends AbstractCardEffect {
    protected AbstractEnchantmentCardEffect( DecoratedPlayer p, Card c) { super(p,c); }
    
    // deferred method that creates the creature upon resolution
    protected abstract Enchantment  createEnchantment();
    
    @Override
    public void resolve() {
        Enchantment e=createEnchantment();
        owner.getEnchantments().add(e);
        e.insert();
        super.resolve();
    }
}
