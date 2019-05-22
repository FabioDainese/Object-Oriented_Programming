package magic;

/**
 * @author Gruppo 6
 */
public abstract class AbstractCreatureCardEffect extends AbstractCardEffect {
    protected AbstractCreatureCardEffect( DecoratedPlayer p, Card c) { super(p,c); }
    
    // deferred method that creates the creature upon resolution
    protected abstract Creature createCreature();
    
    @Override
    public void resolve() {
        Creature c=createCreature();
        DecoratedCreature dc=new DecoratedCreature(owner, c);
        owner.getCreatures().add(dc);
        dc.insert();
        super.resolve();
    }
}
