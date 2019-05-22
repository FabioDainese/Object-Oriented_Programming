package magic;

/**
 * @author Gruppo 6
 */
public class EndOfGame extends RuntimeException {
    public EndOfGame() {}

    public EndOfGame(String msg) {
        super(msg);
    }
}
