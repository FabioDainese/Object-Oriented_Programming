package magic;

/**
 * @author Gruppo 6
 */
public class DefaultTurnManager implements TurnManager {
    private final DecoratedPlayer[] Players;
    int currentPlayerIdx=1;
    
    public DefaultTurnManager(DecoratedPlayer[] p) { Players=p; }
    
    @Override
    public DecoratedPlayer getCurrentPlayer() { return Players[currentPlayerIdx]; }
    
    @Override
    public DecoratedPlayer getCurrentAdversary() { return Players[(currentPlayerIdx+1)%2]; }
    
    @Override
    public DecoratedPlayer nextPlayer() { 
        currentPlayerIdx = (currentPlayerIdx+1)%2;
        return getCurrentPlayer();
    }
}
