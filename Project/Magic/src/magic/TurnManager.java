package magic;

/**
 * @author Gruppo 6
 */
public interface TurnManager {
    DecoratedPlayer getCurrentPlayer();
    
    DecoratedPlayer getCurrentAdversary();
    
    DecoratedPlayer nextPlayer();
}
