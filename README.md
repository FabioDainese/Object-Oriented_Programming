# Introduction
A funny group project with the aim of creating a *Java* application in order to familiarize and to put into practice some **design patterns** (*Factory, Singleton, Prototype, Adapter, Composite, Decorator, Flyweight, Command, Iterator, Observer, State, Strategy, Visitor, Memento*).

All the material was developed for the '*Object-Oriented Programming*' [Ca' Foscari University](https://www.unive.it) bachelor's degree course in '*Technologies and Information Science*' (2016/2017).

# Project Description
The idea behind this project was to developed a *simpler* version of the [**Magic: The Gathering**](https://en.wikipedia.org/wiki/Magic:_The_Gathering) card table game. 
With '*simpler*' version we mean that there's only few cards available, 23 uniqie cards to choose from, over the thousands that the actual game offers. Another simplification was made by removing some *gameplay* phases/settings.

Once you'll start the game there will be an inithial setup phase, where the *two* players can choose their *battle* names (ID) and their decks. To use a particular deck you'll need to provide to the system a file name, including its extension. You can find a *test* desk at [`'Project/Magic/test.deck'`](Project/Magic/test.deck) (so for example, when the game asks you to provide the file name you just need to enter `test.deck`), but feel free to create new ones!
Keep in mind to place them in the right folder (`'Project/Magic/'`) or you'll need to provide/enter the absolute file path once the game has started. 

After this initial phase it will start the actual *battle* phase.

The *game* was designed to be played as a textual version using the console (*STDIN,STDOUT*).

**N.B.** This project was developed using the *NetBeans IDE*, so all the additional files that you'll find are useful to run it through that platform by simply importing all the project, but if you want to run it through the terminal just move to the `'Project/Magic/dist/'` folder and type `java -jar Magic.jar` (in this case you'll need to provide the absolute file path for the players' decks).

Meanwhile if you want to read the actual *Java* game files (source code) you can find them at the following path:  [`'Project/Magic/src/magic/'`](Project/Magic/src/magic/).

### Cards
The cards made available to the players are: 
* **Abduction**: (*Enchant Creature*) When abduction comes into play, untap enchanted creature. You control enchanted creature;
* **AEther Barrier**: (*Enchantment*) Whenever a player plays a creature spell, that player sacrifies a permanent unless he/she pays 1;
* **AEther Flash**: (*Enchantment*) Whenever a creature comes into play, AetherFlash deals 2 damage to it;
* **Afflict**: (*Instant*) Target creature gets -1/-1 until end of turn. Draw a card;
* **Aggressive Urge**: (*Instant*) Target creature gets +1/+1 until end of turn. Draw a card;
* **Ancestral Mask**: (*Enchantment*) Enchanted creature gets +2/+2 for each other enchantment on the battlefield;
* **Argothian Enchantress**: (*Creature - [0/1]*) Shroud (This permanent/creature can't be the target of spells or abilities). Whenever you cast an enchantment spell, draw a card;
* **Aura Blast**: (*Instant*) Destroy target enchantment. Draw a card;
* **Barkshell Blessing**: (*Instant*) Target creature gets +2/+2 until end of turn;
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
* **Norwood Ranger**: (*Creature - [1/2]*) "The song of the forest is in perfect harmony. If a single note is out of place, the elves will find its source";
* **Savor The Moment**: (*Sorcery*) Take an extra turn after this one. Skip the untap step of that turn;
* **Volcanic Hammer**: (*Sorcery*) Volcanic Hammer deals 3 damage to target creature or player;
* **World At War**: (*Sorcery*) After the first postcombat main phase this turn, there's an additional combat phase followed by an additional main phase. At the beginning of that combat, untap all creatures that attacked this turn;

# License
The material is available under the [Apache 2.0](https://github.com/FabioDainese/Object-Oriented_Programming/blob/master/LICENSE).
