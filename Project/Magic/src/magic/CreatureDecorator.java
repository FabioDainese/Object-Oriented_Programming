package magic;

/**
 * @author Gruppo 6
 */
public interface CreatureDecorator extends Creature {
    CreatureDecorator decorate(Creature c);  
    Creature removeDecorator(CreatureDecorator d);
}
