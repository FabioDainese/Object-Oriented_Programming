package magic;

/**
 * @author Gruppo 6
 */
public abstract class AbstractCardEffect extends AbstractEffect {
    protected DecoratedPlayer owner;
    protected Card card;
    
    protected AbstractCardEffect(DecoratedPlayer p, Card c) { owner=p; card=c; }
    
    public void play() { 
        owner.getHand().remove(card);
        super.play();
    }
    
    public String name() { return card.name(); }
    public String toString() { return card.toString() + " (Effect)"; }
}
