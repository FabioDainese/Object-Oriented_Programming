package magic;

/**
 * @author Gruppo 6
 */
public interface Permanent extends GameEntity {
    //void insert();
    //void remove();
    DecoratedPlayer getOwner();
    void setOwner(DecoratedPlayer p);
}
