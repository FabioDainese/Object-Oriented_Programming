package magic;

/**
 * @author Gruppo 6
 */
public interface GameEntityVisitor {
    void visit(DecoratedPlayer p);
    void visit(Card c);
    void visit(Effect e);
    void visit(DecoratedCreature c);
    void visit(Enchantment e);
}
