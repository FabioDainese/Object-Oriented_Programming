package magic;

/**
 * @author Gruppo 6
 */
public class DefaultEndPhase implements Phase {
    public void execute() {
        DecoratedPlayer currentPlayer = Magic.instance.getCurrentPlayer();
        
        System.out.println(currentPlayer.name() + ": end phase");
        
        Magic.instance.getTriggers().trigger(Triggers.END_FILTER);
        
        for(Creature c:currentPlayer.getCreatures()) {
            System.out.println("...reset damage to " + c.name());
            c.resetDamage();
        }
        
        for(Creature c:Magic.instance.getCurrentAdversary().getCreatures()) {
            System.out.println("...reset damage to adversary creature " + c.name());
            c.resetDamage();
        }
    }
}
