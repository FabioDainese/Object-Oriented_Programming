package magic.cards;

import magic.*;
import java.util.ArrayList;

/**
 * This class represents the 'World At War' card
 * 
 * @author Gruppo 6
 */
public class WorldAtWar extends AbstractCard {
    static private final String cardName = "World at War";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new WorldAtWar(); }
                } );
    
    private static class WorldAtWarEffect extends AbstractCardEffect {
        public WorldAtWarEffect(DecoratedPlayer p, Card c) { super(p,c); }
        public void resolve() {
            Magic.instance.getCurrentPlayer().setPhaseManager(new TurnChange());
        }
    }

    private static class TurnChange implements PhaseManager {

        private int phaseidx=0;
        
        public Phases currentPhase() { 
            Phases result;
            switch (phaseidx) {
                case 0: result=Phases.MAIN; break;
                case 1: result=Phases.COMBAT; break;
                case 2: result=Phases.COMBAT_ATTACK; break;
                case 3: result=Phases.COMBAT_DEFENSE; break;
                case 4: result=Phases.COMBAT_DAMAGE; break;
                case 5: result=Phases.MAIN; break;
                default: //should not happen!
                    System.out.println( cardName + " turn beyond range. Odd! Removing it");
                    Magic.instance.getCurrentPlayer().removePhaseManager(this);
                    result = Magic.instance.getCurrentPlayer().currentPhaseId();
            }
            return result;
        }

        public Phases nextPhase() {
            ++phaseidx;
            if (phaseidx>5) {
                System.out.println("Removing " + cardName + " turn changes");
                Magic.instance.getCurrentPlayer().removePhaseManager(this);
                return Magic.instance.getCurrentPlayer().currentPhaseId().next();
            }
            if (phaseidx==1) {
                System.out.println( cardName + " untapping creatures before new combat phase");
                ArrayList<CombatEntry> combatField=Magic.instance.getCombatField();
                for (CombatEntry entry: combatField) {
                    System.out.println( "untapping " + entry.attacker);
                    entry.attacker.untap();
                }
            }
            return currentPhase();
        }
        
    }
    
    public Effect getEffect(DecoratedPlayer owner) { 
        return new WorldAtWarEffect(owner, this); 
    }
    
    public String name() { return cardName; }
    public String type() { return "Sorcery"; }
    public String ruleText() { return "After the main phase this turn, there's an additional combat pahse followed by an additional main phase. At the beginning of that combat, untap all creatures that attacked in the previous combat phase."; }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }
    
}