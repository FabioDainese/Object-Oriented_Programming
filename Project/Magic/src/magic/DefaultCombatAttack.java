package magic;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Gruppo 6
 */
public class DefaultCombatAttack implements Phase {
    @Override
    public void execute() {
        final DecoratedPlayer current_player = Magic.instance.getCurrentPlayer();
        final DecoratedPlayer adversary = Magic.instance.getCurrentAdversary();
        final int current_player_idx = (Magic.instance.getPlayer(0) == current_player)?0:1;
        Scanner reader = Magic.instance.getScanner();
        
        System.out.println("-> attacker declaration subphase");
        
        ArrayList<CombatEntry> combatField = Magic.instance.getCombatField();
        combatField.clear();
        int active_player;
        int number_passes;
        
        if (!current_player.getCreatures().isEmpty()) {
            System.out.println(current_player.name() + " choose attackers");
            ArrayList<DecoratedCreature> potential_attackers=new ArrayList<>();
            int i=1;
            for (DecoratedCreature c:current_player.getCreatures()) {
                if (c.canAttack()) {
                    System.out.println(i + ") " + c);
                    potential_attackers.add(c);
                    ++i;
                }
            }
            String input;
            while ( (input=reader.nextLine()).equals("") );
            Scanner creature_list = new Scanner(input);
            while (creature_list.hasNextInt()) {
                int idx = creature_list.nextInt()-1;
                if (idx>=0 && idx<potential_attackers.size()) {
                    CombatEntry entry = new CombatEntry();
                    entry.attacker=potential_attackers.get(idx);
                    entry.attacker.attack();
                    combatField.add(entry);
                }
            }
        }

            
        System.out.println("Attacker declaration response subphase");
        //allow other player to respond first
        active_player = (current_player_idx+1)%2;
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
