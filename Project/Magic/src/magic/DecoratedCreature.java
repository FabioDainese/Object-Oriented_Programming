package magic;

import java.util.List;

/**
 * @author Gruppo 6
 */
public class DecoratedCreature implements Creature, Permanent{
    private Creature decorated;
    private DecoratedPlayer owner;
    
    public DecoratedCreature(DecoratedPlayer o, Creature c) {
        owner=o;
        decorated=c;
        decorated.setTopDecorator(this);
    }
    @Override
    public DecoratedPlayer getOwner() { return owner; }
    @Override
    public void setOwner(DecoratedPlayer p) { owner=p; }
    
    public void addDecorator(CreatureDecorator d) {
        decorated=d.decorate(decorated);
        if (getDamage()>=getToughness()) destroy();
    }
    

    public void remove() { 
        Magic.instance.getTriggers().trigger(Triggers.EXIT_CREATURE_FILTER, this);
        owner.getCreatures().remove(this); 
        System.out.println("removing " + name() + " from field");
        decorated.remove(); 
        decorated=null;
    }
    public void insert() {
        decorated.insert();
        Magic.instance.getTriggers().trigger(Triggers.ENTER_CREATURE_FILTER,this);
    }

    public boolean isRemoved() { return decorated==null; }
    public void accept(GameEntityVisitor v) { v.visit(this); }
    
    public boolean isTarget() { return decorated.isTarget(); }
    
    public String name() { return decorated.name(); }
    public int getPower() { return Math.max(0,decorated.getPower()); }
    public int getToughness() { return Math.max(0,decorated.getToughness()); }
    
    public void tap() { decorated.tap(); }
    public void untap() { decorated.untap(); }
    public boolean isTapped() { return decorated.isTapped(); }
    
    public boolean canAttack() { return decorated.canAttack(); }
    public void attack() { decorated.attack(); }
    public boolean canDefend() { return decorated.canDefend(); }
    public void defend() { decorated.defend(); }
    public void inflictDamage(int dmg) { owner.inflictDamage(decorated, dmg); }
    public int getDamage() { return decorated.getDamage(); }
    public void resetDamage() { decorated.resetDamage(); }
    public void destroy() { decorated.destroy(); }
    
    public List<Effect> effects() { return decorated.effects(); }
    
    public List<Effect> avaliableEffects() { return decorated.avaliableEffects(); }
    

    public void removeDecorator(CreatureDecorator d) { 
        if (decorated instanceof CreatureDecorator) {
            decorated=((CreatureDecorator)decorated).removeDecorator(d);  
            if (getDamage() >= getToughness())
                destroy();
        }      
    }

    public void setTopDecorator(DecoratedCreature c) {}
    public DecoratedCreature getTopDecorator() { return this; }
    
    public String toString() { return decorated.toString(); }
}
