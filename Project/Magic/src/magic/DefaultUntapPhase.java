package magic;

/**
 * @author Gruppo 6
 */
public class DefaultUntapPhase implements Phase {
    @Override
    public void execute() {
        DecoratedPlayer current_player = Magic.instance.getCurrentPlayer();
        
        System.out.println(current_player.name() + ": untap phase");
        
        Magic.instance.getTriggers().trigger(Triggers.UNTAP_FILTER);
        
        if (current_player.getCreatures().isEmpty())
            System.out.println("...no creatures to untap");
        
        for(Creature c:current_player.getCreatures()) {
            System.out.println("...untap " + c.name());
            c.untap();
        }
    }
}
