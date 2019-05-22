package magic;

/**
 * @author Gruppo 6
 */
public class SkipPhaseCount implements Phase {
    private final Phases phaseId;
    private int skipNum;
    
    public SkipPhaseCount(Phases id) {
        phaseId=id;
        skipNum=1;
    }
    public SkipPhaseCount(Phases id, int skip) {
        phaseId=id;
        skipNum=skip;
    }
    
    
    @Override
    public void execute() {
        DecoratedPlayer currentPlayer = Magic.instance.getCurrentPlayer();
        System.out.println(currentPlayer.name() + ": skip " + phaseId.name() +" phase");
        --skipNum;
        if (skipNum==0) currentPlayer.removePhase(phaseId,this);
    }
}
