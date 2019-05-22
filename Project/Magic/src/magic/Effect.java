package magic;

/**
 * @author Gruppo 6
 */
public interface Effect extends GameEntity {
    // pays for effect and places it in the stack
    void play();
    
    // resolves the effect
    void resolve();
}
