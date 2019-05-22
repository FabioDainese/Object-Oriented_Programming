package magic;

/**
 * @author Gruppo 6
 */
public interface PlayerDecorator extends Player {
    PlayerDecorator decorate(Player p);  
    Player removeDecorator(PlayerDecorator d);
}
