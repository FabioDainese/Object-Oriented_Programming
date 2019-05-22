package magic;

/**
 * @author Gruppo 6
 */
public interface GameEntity {
    String name();    
    boolean isRemoved();
    void remove();
    void insert();
    boolean isTarget();
    
    void accept(GameEntityVisitor v);
}
