Assignment Legends: Monsters and Heroes

ENVIRONMENT:
Windows 10 Home 21H1 19043.1320
IntelliJ IDEA 2021.2.3 (Ultimate Edition)
Java 17


RUN INSTRUCTIONS:
$ javac *.java
$ java LMaH


NOTES:
- Values in the original txt files are not changed and optimized, which might cause some problems, for example, it is
hard for a hero to defeat a monster.
- Please use positive integer as index of items in market or other scenario.
- Check for inputs is not implemented yet. Please type valid numbers or characters.
- Implemented text colors.
* Perhaps need to change the paths of the configuration files in LMaH.java, but the program runs well in the local machine.



DESCRIPTION (in alphabetical order):
- Armor: class, extends Item
  Attributes of armors.

- Consumable: interface
  Behaviors of consumable items.

- Fight: class
  Behaviors in a fight between heroes and monsters.

- Game: abstract class
  Not implemented. The main class of all types of games.

- Hero: abstract class
  Attributes and behaviors of heroes.

  > Paladin: class, extends Hero
    Specific attributes of paladins.
  > Sorcerer: class, extends Hero
    Specific attributes of sorcerers.
  > Warrior: class, extends Hero
    Specific attributes of warriors.

- HeroesList: class
  Stores lists for each type of heroes. Used to generate heroes randomly.

- Item: class, implements Tradable
  Attributes for all types of items, including spells, potions and equipments.

- LMaH: class, extends RPGame
  The main class of this game.

- Map: class
  Attributes of maps.

  > Block: class
    Basic component in a map. Behaviors when heroes arrive at a block.

- Market: class
  Attributes of market.

- Monster: abstract class
  Attributes of monsters.

  > Dragon: class, extends Monster
    Specific attributes of dragons.

  > Exoskeleton: class, extends Monster
    Specific attributes of exoskeletons.

  > Spirit: class, extends Monster
    Specific attributes of spirits.

- MonstersList: class
  Stores lists for each type of monsters. Used to generate monsters randomly.

- Potion: class, extends Item, implements Consumable
  Attributes and functions of potions.

- Round: class
  Procedure in a single round of fight.

- RPGame: abstract class, extends Game
  The main class of all types of rol-playing games.

- Spell: abstract class, extends Item
  Attributes of spells.

  > FireSpell: class, extends Spell
    Special effects of fire spells.

  > IceSpell: class, extends Spell
      Special effects of ice spells.

  > LightningSpell: class, extends Spell
      Special effects of lightning spells.

- Team: class
  Attributes of a hero team.

- Weapon: class, extends Item
  Attributes of weapons.
