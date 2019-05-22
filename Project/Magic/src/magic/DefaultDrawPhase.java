package magic;

/**
 * @author Gruppo 6
 */
public class DefaultDrawPhase implements Phase {
    @Override
    public void execute() {
        DecoratedPlayer currentPlayer = Magic.instance.getCurrentPlayer();
        
        System.out.println(currentPlayer.name() + ": draw phase");
        
        Magic.instance.getTriggers().trigger(Triggers.DRAW_FILTER);
        currentPlayer.draw();
        
        while(currentPlayer.getHand().size() > currentPlayer.getMaxHandSize())
            currentPlayer.selectDiscard();
    }
}
