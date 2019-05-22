package magic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gruppo 6
 */
public abstract class AbstractCreature  implements Creature {
    
    protected DecoratedPlayer owner;
    protected boolean isTapped=false;
    protected int damageInflicted=0;
    protected DecoratedCreature topDecorator;
    
    @Override
    public void setTopDecorator(DecoratedCreature c) { topDecorator=c; }
    @Override
    public DecoratedCreature getTopDecorator() { return topDecorator; }
        
    protected AbstractCreature(DecoratedPlayer owner) { this.owner=owner; }
        
    @Override
        public void tap() { 
            if (isTapped) {
                System.out.println("creature " + topDecorator.name() + " already tapped");
                return;
            }
            
            System.out.println("tapping creature " + topDecorator.name());
            isTapped=true; 
        }
        
    @Override
        public void untap() { 
            if (!isTapped) {
                System.out.println("creature " + topDecorator.name() + " not tapped");
                return ;
            }
            
            System.out.println("untapping creature " + topDecorator.name());
            isTapped=false; 
        }
        
    @Override
        public boolean isTapped() { return isTapped; }
    @Override
        public boolean canAttack() { return !topDecorator.isTapped(); }
    @Override
        public void attack() { topDecorator.tap(); } 
    @Override
        public boolean canDefend() { return true; }
    @Override
        public void defend() {} 
    @Override
        public void inflictDamage(int dmg) { 
            damageInflicted += dmg;
            if (damageInflicted >= topDecorator.getToughness())
                topDecorator.destroy();       
        }
        
    @Override
        public int getDamage() { return damageInflicted; }
    @Override
        public void resetDamage() { damageInflicted = 0; }
    @Override
        public void destroy() { topDecorator.remove(); }
    
    @Override
        public void insert() { System.out.println("inserting " + name()); }
    @Override
        public void remove() { System.out.println("Removing " + name()); }
    @Override
        public boolean isRemoved() { return topDecorator.isRemoved(); }
    @Override
        public boolean isTarget() { return true; }
    
    @Override
        public String toString() {
            return name() + " (" + topDecorator.getPower() + "/" +
                    topDecorator.getToughness() +")";
        }
    
    public List<Effect> effects() { return new ArrayList<>(); }
    public List<Effect> avaliableEffects() { return new ArrayList<>(); }
    
    public void accept(GameEntityVisitor v) { v.visit(topDecorator); }
    
}
