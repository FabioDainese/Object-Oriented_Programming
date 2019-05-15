# Introduction
A funny group project with the aim of creating a *Java* application in order to familiarize and to put into practice some **design patterns** (*Factory, Singleton, Prototype, Adapter, Composite, Decorator, Flyweight, Command, Iterator, Observer, State, Strategy, Visitor, Memento*).

All the material was developed for the '*Object-Oriented Programming*' [Ca' Foscari University](https://www.unive.it) bachelor's degree course in '*Technologies and Information Science*' (2016/2017).

# Project Description
The idea behind this project was to developed a *simpler* version of the [**Magic: The Gathering**](https://en.wikipedia.org/wiki/Magic:_The_Gathering) card table game. 
With '*simpler*' version we mean that there's only few cards available, 25 uniqie cards to choose from, over the thousands that the actual game offers. Another simplification was made by removing some *gameplay* phases/settings.

Once you'll start the game there will be an inithial setup phase, where the *two* players can choose the cards that will compose their decks if they want it or just leave it up to the game to pick them. After that it will start the actual *gameplay* battle.

The *game* was designed to be played as a textual version using the console (*STDIN,STDOUT*).

**N.B.** This project was developed using the *NetBeans IDE*, so all the additional files that you'll find are useful to run it through that platform by simply importing all the project, but if you want to access/read the actual *Java* game files you can find them at the following path [`'Project/src/cardgame/'`](Project/src/cardgame/).

### Cards
The cards made available to the players are: 
* **Abduction**: (*Enchant Creature*) When abduction comes into play, untap enchanted creature. You control enchanted creature;
* **Aether Barrier**: (*Enchantment*) Whenever a player plays a creature spell, that player sacrifies a permanent unless he/she pays 1;
* **Aether Flash**: (*Enchantment*) Whenever a creature comes into play, AetherFlash deals 2 damage to it;
* **Afflict**: (*Instant*) Target creature gets -1/-1 until end of turn. Draw a card;
* **Aggressive Urge**: (*Instant*) Target creature gets +1/+1 until end of turn. Draw a card;
* **Ancestral Mask**: (*Enchantment*) Enchanted creature gets +2/+2 for each other enchantment on the battlefield;
* **Argothian Enchantress**: (*Creature - [0/1]*) Shroud (This permanent/creature can't be the target of spells or abilities). Whenever you cast an enchantment spell, draw a card;
* **Aura Blast**: (*Instant*) Destroy target enchantment. Draw a card;
* **Benevolent Ancestor**: (*Creature - [0/4]*) Defender (this creature can't attack). Prevent the next 1 damage that would be dealt to target or player this turn;
* **Boiling Earth**: (*Sorcery*) Boiling Earth deals 1 damage to each creature;
* **Bronze Sable**: (*Creature - [2/1]*) "The Champion stood alone between the horde of the Returned and the shrine to Karametra, cutting down scores among hundreds. She would have been overcome if not for the aid of the temple guardians whom Karametra awakened";
* **Calming Verse**: (*Sorcery*) Destroy all enchantments you don't control;
* **Cancel**: (*Instant*) Counter target spell;
* **Darkness**: (*Instant*) Prevents all combat damage that would be deal this turn;
* **Day Of Judgment**: (*Sorcery*) Destroy all creatures;
* **Deflection**: (*Instant*) Change the target of a target spell with a single target;
* **False Peace**: (*Sorcery*) Choose any one player. That player can't attack on his or her next turn (Target player skips his/her next combat phase);
* **Fatigue**: (*Sorcery*) Target player skips his or her next draw step;
* **Friendly Environment\***: (*Enchantment*) Whenever a creature enters the game Homeopathy welcomes it;
* **Homeopathy\***: (*Instant*) Homeopathy does nothing;
* **Norwood Ranger**: (*Creature - [1/2]*) "The song of the forest is in perfect harmony. If a single note is out of place, the elves will find its source";
* **Reflexologist\***: (*Creature - [0/1]*) Reflexology does nothing;
* **Savor The Moment**: (*Sorcery*) Take an extra turn after this one. Skip the untap step of that turn;
* **Volcanic Hammer**: (*Sorcery*) Volcanic Hammer deals 3 damage to target creature or player;
* **World At War**: (*Sorcery*) After the first postcombat main phase this turn, there's an additional combat phase followed by an additional main phase. At the beginning of that combat, untap all creatures that attacked this turn;


###### **\*** Meaning: these cards are *not* in the original game (they were invented).

# License
The material is available under the [Apache 2.0](https://github.com/FabioDainese/Object-Oriented_Programming/blob/master/LICENSE).
