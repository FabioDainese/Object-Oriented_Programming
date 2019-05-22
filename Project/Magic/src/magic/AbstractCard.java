package magic;

/**
 * @author Gruppo 6
 */
public abstract class AbstractCard extends AbstractGameEntity implements Card {
    public void accept(GameEntityVisitor v) { v.visit(this); }
}
