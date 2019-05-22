package magic;

/**
 * @author Gruppo 6
 */
public abstract class AbstractEffect extends AbstractGameEntity implements Effect {
    
    @Override
    public void play() {  Magic.instance.getStack().add(this); }
    @Override
    public void resolve() { super.remove(); }
    
    @Override
    public void accept(GameEntityVisitor v) { v.visit(this); }
    
    public String toString() { return name() + " (Effect)"; }
 
}
