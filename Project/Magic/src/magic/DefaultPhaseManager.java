package magic;

import java.util.Deque;
import java.util.EnumMap;

/**
 * @author Gruppo 6
 */
public class DefaultPhaseManager implements PhaseManager {
    private final EnumMap<Phases, Deque<Phase> > phases;
    private Phases currentPhaseIdx=Phases.NULL;
    
    public DefaultPhaseManager(EnumMap<Phases, Deque<Phase> > p) {phases=p;}
    
    
    @Override
    public Phases currentPhase() { return currentPhaseIdx; }
    
    @Override
    public Phases nextPhase() { 
        currentPhaseIdx = currentPhaseIdx.next();
        return currentPhase();
    }
}
