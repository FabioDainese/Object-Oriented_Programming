package magic;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * @author Gruppo 6
 */
public class DecoratedPlayer implements Player, Damageable {
    private Player decorated;
    
    public DecoratedPlayer() {
        decorated = new ConcretePlayer(this);
        
        phaseManagerStack.push(new DefaultPhaseManager(phases));
        
        phases.put(Phases.DRAW, new ArrayDeque<Phase>());
        setPhase(Phases.DRAW, new DefaultDrawPhase());
        
        phases.put(Phases.UNTAP, new ArrayDeque<Phase>());
        setPhase(Phases.UNTAP, new DefaultUntapPhase());
        
        phases.put(Phases.COMBAT, new ArrayDeque<Phase>());
        setPhase(Phases.COMBAT, new DefaultCombatPhase());
        
        phases.put(Phases.COMBAT_ATTACK, new ArrayDeque<Phase>());
        setPhase(Phases.COMBAT_ATTACK, new DefaultCombatAttack());
        
        phases.put(Phases.COMBAT_DEFENSE, new ArrayDeque<Phase>());
        setPhase(Phases.COMBAT_DEFENSE, new DefaultCombatDefense());
        
        phases.put(Phases.COMBAT_DAMAGE, new ArrayDeque<Phase>());
        setPhase(Phases.COMBAT_DAMAGE, new DefaultCombatDamage());
        
        phases.put(Phases.MAIN, new ArrayDeque<Phase>());
        setPhase(Phases.MAIN, new DefaultMainPhase());
        
        phases.put(Phases.END, new ArrayDeque<Phase>());
        setPhase(Phases.END, new DefaultEndPhase());
        
        phases.put(Phases.NULL, new ArrayDeque<Phase>());
    }
    
    public void addDecorator(PlayerDecorator d) {
        decorated=d.decorate(decorated);
    }
    
    public void removeDecorator(PlayerDecorator d) {
        if (decorated instanceof PlayerDecorator) {
            decorated=((PlayerDecorator)decorated).removeDecorator(d);  
        }  
    }
    
    private String name;
    @Override
    public String name() {return name;}
    @Override
    public boolean isRemoved() {return false;}
    @Override
    public void remove() {}
    @Override
    public void insert() {}

    @Override
    public void accept(GameEntityVisitor v) { v.visit(this); }
    @Override
    public String toString() {return name();}
    public void setName(String n) {name=n;}
    
    public void executeTurn() {
        System.out.println(name() + "'s turn");
        
        // print out the fields
        System.out.println("=== Field ===");
        for (int i=0; i!=2; ++i) {
            DecoratedPlayer fieldsPlayer=Magic.instance.getPlayer(i);
            List<DecoratedCreature> creatures = fieldsPlayer.getCreatures();
            if (creatures.isEmpty()) {
                System.out.println(fieldsPlayer.name() + " has no creature in play");
            } else {
                System.out.println(fieldsPlayer.name() + "'s creatures in play:");
                for (Creature c:creatures)
                    System.out.println("  "+c);
            }
            List<Enchantment> enchantments = fieldsPlayer.getEnchantments();
            if (enchantments.isEmpty()) {
                System.out.println(fieldsPlayer.name() + " has no enchantment in play");
            } else {
                System.out.println(fieldsPlayer.name() + "'s enchantments in play:");
                for (Enchantment e:enchantments)
                    System.out.println("  "+e);
            }
        }
        System.out.println("=============");        
        
        Phase curPhase;
        while ((curPhase=nextPhase())!=null) {
            curPhase.execute();
        }
    }
    
    public void selectDiscard() {
        Scanner reader = Magic.instance.getScanner();
        List<Card> hand=getHand();
        
        System.out.println(name() +" Choose a card to discard");
        for(int i=0; i!=hand.size(); ++i) {
            System.out.println(Integer.toString(i+1)+") " + hand.get(i) );
        }
        int idx= reader.nextInt()-1;
        if (idx>=0 && idx<hand.size())
            hand.remove(idx);         
    }
    
    
    // phase management
    
    // phases maps a phaseID to a stack of phase implemenations
    // top one is active
    private final EnumMap<Phases, Deque<Phase> > phases = new EnumMap<>(Phases.class);
    public Phase getPhase(Phases p) { return phases.get(p).peek(); }
    
    // set to final because it is called in constructor
    public final void setPhase(Phases id, Phase p) { phases.get(id).push(p); }
    public void removePhase(Phases id, Phase p) { phases.get(id).remove(p); }
    
    // 
    private final Deque<PhaseManager> phaseManagerStack = new ArrayDeque<>();
    public void setPhaseManager(PhaseManager m) { phaseManagerStack.push(m); }
    public void removePhaseManager(PhaseManager m) { phaseManagerStack.remove(m); }
    public Phases currentPhaseId() { return phaseManagerStack.peek().currentPhase(); }
    public Phase nextPhase() { return getPhase(phaseManagerStack.peek().nextPhase()); }
    
    
    
    
    @Override
    public boolean isTarget() { return decorated.isTarget(); }
    
    @Override
    public void setDeck(Iterator<Card> deck) { decorated.setDeck(deck); }
    @Override
    public Library getDeck() { return decorated.getDeck(); }
    
    @Override
    public List<Card> getHand() { return decorated.getHand(); }
    @Override
    public int getMaxHandSize() { return decorated.getMaxHandSize(); }
    @Override
    public void setMaxHandSize(int size) { decorated.setMaxHandSize(size); }
    @Override
    public void draw() { decorated.draw(); }
    
    @Override
    public int getLife() { return decorated.getLife(); }
    @Override
    public void heal(int pts) { decorated.heal(pts); }
    @Override
    public void lose(String s) { decorated.lose(s); }
    
    
    @Override
    public List<DecoratedCreature> getCreatures() { return decorated.getCreatures(); }
    @Override
    public void destroy(Creature c) { decorated.destroy(c); }
    @Override
    public List<Enchantment> getEnchantments() { return decorated.getEnchantments(); }
    @Override
    public void destroy(Enchantment e) { decorated.destroy(e); }
    
    @Override
    public void inflictDamage(int dmg) { decorated.inflictDamage(dmg); }
    @Override
    public void inflictDamage(Creature c, int dmg) { decorated.inflictDamage(c, dmg); }

}
