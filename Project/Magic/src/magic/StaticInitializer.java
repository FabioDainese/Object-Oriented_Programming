package magic;

/**
 * @author Gruppo 6
 */

// statically initialized object that registers 
// a card factory method with the card factory
public class StaticInitializer {
    
    public StaticInitializer(String s, CardConstructor c) {
        CardFactory.register(s,c);
    }
    
}
