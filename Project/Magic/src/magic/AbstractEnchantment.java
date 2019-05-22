package magic;

/**
 * @author Gruppo 6
 */
public abstract class AbstractEnchantment extends AbstractGameEntity implements Enchantment {
    protected DecoratedPlayer owner;        
    protected AbstractEnchantment(DecoratedPlayer owner) { this.owner=owner; }
    @Override
    public DecoratedPlayer getOwner() { return owner; }
    @Override
    public void setOwner(DecoratedPlayer p) { owner=p; }
        
    @Override
        public void insert() {
            Magic.instance.getTriggers().trigger(Triggers.ENTER_ENCHANTMENT_FILTER,this);
        }
    
    @Override
        public void remove() {
            owner.getEnchantments().remove(this);
            super.remove();
            Magic.instance.getTriggers().trigger(Triggers.EXIT_ENCHANTMENT_FILTER,this);
        }
        
    @Override
        public String toString() {
            return name() + " (Enchantment)";
        }
     

    @Override
        public void accept(GameEntityVisitor v) { v.visit(this); }
    
}
