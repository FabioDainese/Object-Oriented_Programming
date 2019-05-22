package magic;

import java.util.List;

/**
 * @author Gruppo 6
 */
public abstract class AbstractCreatureDecorator implements CreatureDecorator {
    protected Creature decorated;
    @Override
    public CreatureDecorator decorate(Creature c) { 
        decorated=c; 
        return this;
    }
    @Override
    public Creature removeDecorator(CreatureDecorator d) {
            Creature result = this;
            if (d==this) {
                result = decorated;
                decorated = null;
                remove();
            }
            else if (decorated instanceof CreatureDecorator)
                decorated=((CreatureDecorator)decorated).removeDecorator(d);
            return result;
        }
    
    public void tap() { decorated.tap(); }
    public void untap() { decorated.untap(); }
    public boolean isTapped() { return decorated.isTapped(); }
    
    public boolean canAttack() { return decorated.canAttack(); }
    public void attack() { decorated.attack(); }
    
    public boolean canDefend() { return decorated.canDefend(); }
    public void defend() { decorated.defend(); }
    
    public int getDamage() { return decorated.getDamage(); }
    public void resetDamage() { decorated.resetDamage(); }
    public void destroy() { decorated.destroy(); }
    
    public int getPower() { return decorated.getPower(); }
    public int getToughness() { return decorated.getToughness(); }
    
    // returns all the effects associated to this creature
    public List<Effect> effects() { return decorated.effects(); }
    
    // returns only the effects that can be played currently
    // depending on state, e.g., tapped/untapped
    public List<Effect> avaliableEffects() { return decorated.avaliableEffects(); }
    
    public void setTopDecorator(DecoratedCreature c) { decorated.setTopDecorator(c); }
    public DecoratedCreature getTopDecorator() { return decorated.getTopDecorator(); }
    
    public void inflictDamage(int dmg) { decorated.inflictDamage(dmg); }
    
    public String name() { return decorated.name(); }
    public boolean isRemoved() { return decorated==null; }
    public void insert() { 
        if (decorated != null) decorated.insert();
    }
    public void remove() { 
        if (decorated != null) decorated.remove();
        decorated = null;
    }
    public boolean isTarget() { return decorated.isTarget(); }
    
    public void accept(GameEntityVisitor v) { decorated.accept(v); }
    public String toString() { return decorated.toString(); }
}
