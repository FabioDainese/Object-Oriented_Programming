
package magic.cards;

import magic.AbstractCard;
import magic.AbstractCardEffect;
import magic.AbstractCreatureDecorator;
import magic.Card;
import magic.CardConstructor;
import magic.Magic;
import magic.DecoratedCreature;
import magic.DecoratedPlayer;
import magic.Effect;
import magic.StaticInitializer;
import magic.TargetingEffect;
import magic.TriggerAction;
import magic.Triggers;
import java.util.ArrayList;

/**
 * This class represents the 'Barkshell Blessing' card
 * 
 * @author Gruppo 6
 */
public class BarkshellBlessing extends AbstractCard {

    static private final String cardName = "Barkshell Blessing";
    
    private boolean conspire = false;
    
    private BarkshellBlessing(){}
    private BarkshellBlessing(boolean conspire){ this.conspire = conspire;}

    static private StaticInitializer initializer
            = new StaticInitializer(cardName, new CardConstructor() {
                public Card create() {
                    return new BarkshellBlessing();
                }
            });

    private class BarkshellBlessingEffect extends AbstractCardEffect implements TargetingEffect {

        private DecoratedCreature target;

        public BarkshellBlessingEffect(DecoratedPlayer p, Card c) {
            super(p, c);
        }

        public void play() {
            pickTarget(owner);
            
            super.play();
        }

        public void resolve() {
            if (target == null) {
                return;
            }

            if (target.isRemoved()) {
                System.out.println("Attaching " + cardName + " to removed creature");
                return;
            }

            final BarkshellBlessingDecorator decorator = new BarkshellBlessingDecorator();
            TriggerAction action = new TriggerAction() {
                public void execute(Object arg) {
                    if (!target.isRemoved()) {
                        System.out.println("Triggered removal of " + cardName + " from " + target);
                        target.removeDecorator(decorator);
                    } else {
                        System.out.println("Triggered dangling removal of " + cardName + " from removed target. Odd should have been invalidated!");
                    }
                }
            };
            
            System.out.println("Attaching " + cardName + " to " + target.name() + " and registering end of turn trigger");
            Magic.instance.getTriggers().register(Triggers.END_FILTER, action);

            decorator.setRemoveAction(action);
            target.addDecorator(decorator);
            
            if(!conspire){
                conspire(owner);
            }
            
        }

        /**
         * Asks to the player to choose a creature on which apply the card's effect
         * 
         * @param p Represents the player that has played the card
         */
        public void pickTarget(DecoratedPlayer p) {
            System.out.println(p.name() + ": choose target for " + cardName);

            ArrayList<DecoratedCreature> creatures = new ArrayList<>();
            int i = 1;

            DecoratedPlayer player1 = Magic.instance.getPlayer(0);
            DecoratedPlayer player2 = Magic.instance.getPlayer(1);

            for (DecoratedCreature c : player1.getCreatures()) {
                if (c.isTarget()) {
                    System.out.println(i + ") " + player1.name() + ": " + c);
                    ++i;
                    creatures.add(c);
                }
            }
            for (DecoratedCreature c : player2.getCreatures()) {
                if (c.isTarget()) {
                    System.out.println(i + ") " + player2.name() + ": " + c);
                    ++i;
                    creatures.add(c);
                }
            }

            int idx = Magic.instance.getScanner().nextInt() - 1;
            if (idx < 0 || idx >= creatures.size()) {
                target = null;
            } else {
                target = creatures.get(idx);
            }
        }

        /**
         * Asks to the player if he wants to use the 'Conspire' ability of the card
         * 
         * @param p Represents the player that has played the card
         */
        public void conspire(DecoratedPlayer p) {
            System.out.println(p.name() + ": Do you want to tap two of your untapped creature to get an other " + cardName + " card to use? [y/n]");
            String buffer;
            boolean check = false;
            do {
                if (check) {
                    System.out.println("Invalid characters! Please insert [y/n]");
                }
                check = true;
                buffer = Magic.instance.getScanner().next();
            } while (!buffer.trim().matches("[yn]"));
            if (buffer.equals("y")) {
                System.out.println(p.name() + ": choose 2 targets to tap.");
                for (int j = 0; j < 2; j++) {
                    boolean checkNumberCard = chooseCreatureToTap(p,2-j);
                    if(!checkNumberCard){
                        return;
                    }
                }
                /* Creo una nuova carta e la gioco */
                BarkshellBlessing bb = new BarkshellBlessing(true);
                bb.getEffect(owner).play();
            }
            
        }

        /**
         * Print the untapped creature of the player that has played the card and ask him to choose one (otherwise it'll print that there aren't enough cards)
         * 
         * @param p Represents the player that has played the card
         * @param minimumNumberCardAvailable It's the minimum number of the untapped cards that the player must have available in the battlefield
         */
        public boolean chooseCreatureToTap(DecoratedPlayer p, int minimumNumberCardAvailable) {
            int i = 1;
            ArrayList<DecoratedCreature> creatures = new ArrayList<>();
            for (DecoratedCreature c : p.getCreatures()) {
                if (!c.isTapped()) {
                    System.out.println(i + ") " + c.name());
                    ++i;
                    creatures.add(c);
                }
            }
            if (!creatures.isEmpty() && creatures.size() >= minimumNumberCardAvailable) {
                int buffer = -1;
                boolean check = false;
                System.out.println("Insert an index:");
                do{
                    if(check){
                        System.out.println("Wrong index! Try again:");
                    }
                    check = true;
                    try{
                        buffer = Magic.instance.getScanner().nextInt()-1;
                    }catch(Exception e){}
                }while(buffer < 0 || buffer >= creatures.size());
                creatures.get(buffer).tap();
                return true;
            } else {
                System.out.println("(No enough creature untapped available!)");
                return false;
            }
        }

        public String toString() {
            if (target == null) {
                return super.toString() + " (no target)";
            } else if (target.isRemoved()) {
                return super.toString() + " (removed creature)";
            } else {
                return cardName + " [" + target.name() + " gets +2/+2 until end of turn]";
            }
        }

    }

    /**
     * Allows you to get the effect of the 'BarkshellBlessing' card
     * 
     * @param owner Represents the owner of the 'BarkshellBlessing' card
     * @return Returns the effect of the 'BarkshellBlessing' card
     */
    public Effect getEffect(DecoratedPlayer owner) {
        return new BarkshellBlessingEffect(owner, this);
    }

    private static class BarkshellBlessingDecorator extends AbstractCreatureDecorator {

        TriggerAction action;

        public void setRemoveAction(TriggerAction a) {
            action = a;
        }

        public void remove() {
            System.out.println("Removing " + cardName + " and deregistering end of turn trigger");
            if (action != null) {
                Magic.instance.getTriggers().deregister(action);
            }
            super.remove();
        }

        public int getPower() {
            return decorated.getPower() + 2;
        }

        public int getToughness() {
            return decorated.getToughness() + 2;
        }
    }

    /**
     * Allows you to know the name of the card
     * 
     * @return Returns the name of the class (which is the same as the card)
     */
    public String name() {
        return cardName;
    }

    /**
     * Allows you to know the type of the card
     * 
     * @return Returns the type of the 'BarkshellBlessing' card
     */
    public String type() {
        return "Instant";
    }

    /**
     * Allows you to know the description of the card
     * 
     * @return Returns the description of the 'BarkshellBlessing' card
     */
    public String ruleText() {
        return "Target creature gets +2/+2 until end of turn. As you play this spell, you may tap two untapped creatures you control. When you do, copy it and you may choose a new target for the copy.";
    }

    /**
     * Rappresent the 'BarkshellBlessing' card as a String
     * 
     * @return Returns the string representation of the 'BarkshellBlessing' card
     */
    public String toString() {
        return cardName + "[" + ruleText() + "]";
    }

    /**
     * Allows you to know if the card is an 'Instant' or not
     * 
     * @return Returns a boolean value based on whether the card is an 'Instant' or not
     */
    public boolean isInstant() {
        return true;
    }

}
