package magic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author Gruppo 6
 */
public class DefaultCombatDefense implements Phase {
    @Override
    public void execute() {
        final DecoratedPlayer current_player = Magic.instance.getCurrentPlayer();
        final DecoratedPlayer adversary = Magic.instance.getCurrentAdversary();
        final int current_player_idx = (Magic.instance.getPlayer(0) == current_player)?0:1;
        Scanner reader = Magic.instance.getScanner();
        
        System.out.println("-> defender declaration subphase");
        
        ArrayList<CombatEntry> combatField = Magic.instance.getCombatField();
        int active_player;
        int number_passes;
         HashSet<DecoratedCreature> defenders = new HashSet<>();
        
        // declare defenders
        if (!adversary.getCreatures().isEmpty()) {
            for (CombatEntry e: combatField) {
                if (e.attacker.isRemoved()) continue;
                System.out.println(adversary.name() + " choose creatures defending from " + e.attacker );
                ArrayList<DecoratedCreature> potential_defenders=new ArrayList<>();
                int i=1;
                for (DecoratedCreature c:adversary.getCreatures()) {
                    if (c.canDefend() && !defenders.contains(c)) {
                        System.out.println(i + ") " + c);
                        potential_defenders.add(c);
                        ++i;
                    }
                }
                String input;
                while ( (input=reader.nextLine()).equals("") );
                Scanner creature_list = new Scanner(input);
                while (creature_list.hasNextInt()) {
                    int idx = creature_list.nextInt()-1;
                    if (idx>=0 && idx<potential_defenders.size()) {
                        DecoratedCreature defender=potential_defenders.get(idx);
                        if (!defenders.contains(defender)) {
                            defender.defend();
                            e.defenders.add(defender);
                            defenders.add(defender);
                        }
                    }
                }
            }
        }
        
        System.out.println("Defender declaration response subphase");
        //allow priority player to respond first
        active_player = current_player_idx;
        number_passes=0;
        while (number_passes<2) {
            if (Utilities.playAvailableEffect(Magic.instance.getPlayer(active_player),false))
                number_passes=0;
            else ++number_passes;
            
            active_player = (active_player+1)%2;
        }
        Magic.instance.getStack().resolve();
        
    }
}
