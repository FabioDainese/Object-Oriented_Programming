package magic;

/**
 * @author Gruppo 6
 */
public enum Phases {
    DRAW ("draw"), UNTAP ("untap"), COMBAT ("combat"),
    COMBAT_ATTACK ("combat: attack declaration"), 
    COMBAT_DEFENSE("combat: defense declaration"), COMBAT_DAMAGE("combat: damage distribution"),
    MAIN ("main"), END ("end"), NULL ("null");
    private static final Phases[] vals = values();
    private final String name;
    Phases(String n) { this.name=n; }
    public String getName() { return name; }
    public Phases next() { return vals[(this.ordinal()+1) % vals.length]; }
    public Phases prev() { return vals[(this.ordinal()+vals.length-1) % vals.length]; }
    public int getIdx() { return this.ordinal(); }
    public static Phases idx(int i) { return vals[i]; }
    
}
