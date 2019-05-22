package magic;

/**
 * @author Gruppo 6
 */
public class DefaultCombatPhase implements Phase {
    @Override
    public void execute() {
        DecoratedPlayer currentPlayer = Magic.instance.getCurrentPlayer();
        
        System.out.println(currentPlayer.name() + ": combat phase");
        
        Magic.instance.getTriggers().trigger(Triggers.COMBAT_FILTER);

    }
}
