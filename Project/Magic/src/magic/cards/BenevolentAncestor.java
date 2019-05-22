package magic.cards;

import magic.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the 'Benevolent Ancestor' card
 * 
 * @author Gruppo 6
 */
public class BenevolentAncestor extends AbstractCard {
    static private final String cardName = "Benevolent Ancestor";
    
    static private StaticInitializer initializer = 
            new StaticInitializer(cardName, new CardConstructor() { 
                public Card create() { return new BenevolentAncestor(); }
                } );
    
    private static class BenevolentAncestorEffect extends AbstractCreatureCardEffect {
        public BenevolentAncestorEffect(DecoratedPlayer p, Card c) { super(p,c); }
        
        protected Creature createCreature() { return new BenevolentAncestorCreature(owner); }
    }
    public Effect getEffect(DecoratedPlayer p) { return new BenevolentAncestorEffect(p,this); }
    
    
    private static class BenevolentAncestorCreature extends AbstractCreature {
        
        BenevolentAncestorCreature(DecoratedPlayer owner) {  super(owner); }
        
        public String name() { return cardName; }
        
        public boolean CanAttack() { return false; }
        public int getPower() { return 0; }
        public int getToughness() { return 4; }

        public List<Effect> effects() 
        { 
            ArrayList<Effect> effects= new ArrayList<>();
            effects.add(new ProtectionEffect(owner));
            return effects; 
        }
        public List<Effect> avaliableEffects() {
            ArrayList<Effect> effects= new ArrayList<>();
            if (!topDecorator.isTapped()) effects.add(new ProtectionEffect(owner));
            return effects;
        }
        
        private class ProtectionEffect extends AbstractEffect implements TargetingEffect {
            private DecoratedPlayer owner;
            private Damageable target;

            public ProtectionEffect(DecoratedPlayer p) { owner=p; }

            public String name() { return "prevent 1 damage"; }

            public String toString() {
                if (target==null) {
                    return "prevent the next 1 damage to target creature or player this turn";
                } else if (target.isRemoved() ) {
                    return "prevent the next 1 damage to (removed target)";
                } else return "prevent the next 1 damage to " + target.name(); 
            }

            public void play() {
                if (isTapped) return;
                topDecorator.tap();
                pickTarget(owner);
                super.play();
            }

            public void pickTarget(DecoratedPlayer p) {
                System.out.println( p.name() + ": choose target for " + name() );

                ArrayList<Damageable> targets = new ArrayList<>();
                int i=1;

                DecoratedPlayer curPlayer = Magic.instance.getPlayer(0);
                if (curPlayer.isTarget()) {
                    System.out.println(i+ ") " + curPlayer.name());
                    targets.add(curPlayer);
                    ++i;
                }
                for (DecoratedCreature c:curPlayer.getCreatures()) {
                    if (c.isTarget()) {
                        System.out.println(i+ ") " + curPlayer.name() + ": " + c.name());
                        targets.add(c);
                        ++i;
                    }
                }

                curPlayer = Magic.instance.getPlayer(1);
                if (curPlayer.isTarget()) {
                    System.out.println(i+ ") " + curPlayer.name());
                    targets.add(curPlayer);
                    ++i;
                }
                for (DecoratedCreature c:curPlayer.getCreatures()) {
                    if (c.isTarget()) {
                        System.out.println(i+ ") " + curPlayer.name() + ": " + c.name());
                        targets.add(c);
                        ++i;
                    }
                }

                int idx= Magic.instance.getScanner().nextInt()-1;
                if (idx<0 || idx>=targets.size()) target=null;
                else target=targets.get(idx);
            }
            public void resolve() { 
                if (target==null) {
                    System.out.println(cardName + ": damage prevention effect has no target");
                    return;    
                } else if (target.isRemoved()) {
                    System.out.println(cardName + ": target no longer in play");
                    return;
                }

                target.accept( new GameEntityVisitor() {
                   
                    public void visit(DecoratedPlayer p) {
                        final DecoratedPlayer ptarget=p;
                        final BenevolentAncestorPlayerDecorator pd = new BenevolentAncestorPlayerDecorator();

                        ptarget.addDecorator(pd);
                        Magic.instance.getTriggers().register(Triggers.END_FILTER, 
                                new TriggerAction() {
                                    public void execute(Object arg) {
                                        System.out.println("Removing " + cardName + ": damage prevention effect");
                                        ptarget.removeDecorator(pd);
                                        Magic.instance.getTriggers().deregister(this);
                                    }
                                }
                                );
                    }
                    
                    public void visit(Card c) {} //should not happen
                    
                    public void visit(Effect e) {}//should not happen
                    
                    public void visit(Enchantment e) {}//should not happen
                    
                    public void visit(DecoratedCreature c) {
                        final DecoratedCreature ctarget=c;
                        if (ctarget.isRemoved()) {
                            System.out.println(cardName + ": damage prevention effect targets a removed creature");
                            return;
                        }
                        final BenevolentAncestorCreatureDecorator cr = new BenevolentAncestorCreatureDecorator();

                        ctarget.addDecorator(cr);
                        Magic.instance.getTriggers().register(Triggers.END_FILTER, 
                                new TriggerAction() {
                                    public void execute(Object arg) {
                                        System.out.println("Removing " + cardName + ": damage prevention effect");
                                        ctarget.removeDecorator(cr);
                                        Magic.instance.getTriggers().deregister(this);
                                    }
                                }
                                );
                    }
                    
                }); //end accept

            }
        }
        
    }
    
    private static class BenevolentAncestorCreatureDecorator extends AbstractCreatureDecorator {
        int prevention=1;
        public void inflictDamage(int dmg) {
            System.out.println(cardName + " preventing 1 damage to player");
            if (dmg<=prevention) prevention-=dmg;
            else {
                decorated.inflictDamage(dmg-prevention);
                prevention=0;
            }
        }
    }
    
    private static class BenevolentAncestorPlayerDecorator extends AbstractPlayerDecorator {
        int prevention=1;
        public void inflictDamage(int dmg) {
            System.out.println(cardName + " preventing 1 damage to player");
            if (dmg<=prevention) prevention-=dmg;
            else {
                decorated.inflictDamage(dmg-prevention);
                prevention=0;
            }
        }
    }
    
    public String name() { return cardName; }
    public String type() { return "Creature"; }
    public String ruleText() { 
        return "Put in play a creature " + cardName + "(0/4) with tap: prevent the next 1 damage that would be delt to target creature or player this turn"; 
    }
    public String toString() { return name() + "[" + ruleText() +"]";}
    public boolean isInstant() { return false; }

}