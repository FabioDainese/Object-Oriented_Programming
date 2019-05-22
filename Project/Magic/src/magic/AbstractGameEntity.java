package magic;

/**
 * @author Gruppo 6
 */
public abstract class AbstractGameEntity implements GameEntity {
    protected boolean isRemoved=false;
    public boolean isRemoved() { return isRemoved; }
    public boolean isTarget() { return true; }
    public void remove() { isRemoved=true; }
    public void insert() { isRemoved=false; }
}
