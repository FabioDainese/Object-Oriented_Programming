package magic;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Gruppo 6
 */
public class DefaultMainPhase implements Phase {
    
    @Override
    public void execute() {
        DecoratedPlayer currentPlayer = Magic.instance.getCurrentPlayer();
        int responsePlayerIdx = (Magic.instance.getPlayer(0) == currentPlayer)?1:0;
        
        System.out.println(currentPlayer.name() + ": main phase");
        
        Magic.instance.getTriggers().trigger(Triggers.MAIN_FILTER);
        
        
        // alternate in placing effect until bith players pass
        int numberPasses=0;
        
        if (!Utilities.playAvailableEffect(currentPlayer, true))
            ++numberPasses;
        
        while (numberPasses<2) {
            if (Utilities.playAvailableEffect(Magic.instance.getPlayer(responsePlayerIdx),false))
                numberPasses=0;
            else ++numberPasses;
            
            responsePlayerIdx = (responsePlayerIdx+1)%2;
        }
        
        Magic.instance.getStack().resolve();
    }
    
    
    // looks for all playable effects from cards in hand and creatures in play
    // and asks player for which one to play
    // includes creatures and sorceries only if isMain is true
    private boolean playAvailableEffect(DecoratedPlayer activePlayer, boolean isMain) {
        //collect and display available effects...
        ArrayList<Effect> availableEffects = new ArrayList<>();
        Scanner reader = Magic.instance.getScanner();

        //...cards first
        System.out.println(activePlayer.name() + " select card/effect to play, 0 to pass");
        int i=0;
        for( Card c:activePlayer.getHand() ) {
            if ( isMain || c.isInstant() ) {
                availableEffects.add( c.getEffect(activePlayer) );
                System.out.println(Integer.toString(i+1)+") " + c );
                ++i;
            }
        }
        
        //...creature effects last
        for ( Creature c:activePlayer.getCreatures()) {
            for (Effect e:c.avaliableEffects()) {
                availableEffects.add(e);
                System.out.println(Integer.toString(i+1)+") " + c.name() + 
                    " ["+ e + "]" );
                ++i;
            }
        }
        
        //get user choice and play it
        int idx= reader.nextInt()-1;
        if (idx<0 || idx>=availableEffects.size()) return false;

        availableEffects.get(idx).play();
        return true;
    }
    
}
