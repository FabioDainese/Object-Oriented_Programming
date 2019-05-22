package magic;

import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * @author Gruppo 6
 */
public class CardStack implements Iterable<Effect> {
    private final ArrayDeque<Effect> stack = new ArrayDeque<>();
    
    public Iterator<Effect> iterator() { return stack.iterator(); }
    
    public void add(Effect e) { 
        stack.push(e); 
    }
    
    public void remove(Effect e) { 
        stack.remove(e); 
        e.remove();
    }
    
    public void resolve() {
        while(!stack.isEmpty()) { 
            Effect e = stack.pop();
            
            System.out.println("Stack: resolving " + e);
            
            e.resolve(); 
        }
    }
}
