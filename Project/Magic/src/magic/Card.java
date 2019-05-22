package magic;

/**
 * @author Gruppo 6
 */
public interface Card extends GameEntity {
    // returns the effect to be placed on the stack
    Effect getEffect(DecoratedPlayer owner);
    String type(); //sorcery, instant, or creature
    String ruleText();
    boolean isInstant();
}
