# Project Report

## Environment & Tools
Ubuntu 20.04, IntelliJ IDEA 2021.3.1 (Ultimate Edition), OpenJDK 17.0.2, Apache Maven 3.8.5, Git 2.25.1

## Purpose
The purpose of this assignment was to create a solution for a dungeon crawler game,
where 7 classes with driver code and some helper classes are provided from the start
and the remaining 34 required classes need to be built from scratch.

The project requires comprehension of UML diagrams, understanding of OOP principles,
common design patterns, knowledge of many Java interfaces and classes in the standard
library, working with a bigger project with many classes and functionalities, and
implementing algorithms from vague descriptions of end results, usage of unit tests, Maven use
and general problem-solving. The project therefore aimed to solidify a good base knowledge
of Java development.

## Procedures

### StatsManager.java
Declared class final since it is a singleton which will never
be extended, and used to implement notation for interface constants for clarity of intent.

Initialized INSTANCE member with eager initialization.

Initialized stat lists members using the Arrays.asList method.

Implemented required methods according to UML, and added my own private method
getListIndexRange() to segregate the calculation of list indexes from
the getRandom methods.

### BaseStat.java
Defined members and methods according to UML, used the getters getBaseValue()
and chained combinations of others when needed in order to maintain cohesion and
not directly accessing the members, which enables extending classes to access all
members through the getter methods. Figuring out how the different modifiers
were supposed to be calculated was challenging in the beginning.

### Attribute.java
Defined class according to UML, with the constructor calling the superclass constructor.

### Trait.java
Defined class according to UML, with the same functionality as Attribute.

### CombatStat.java
Defined class according to UML, with members attributeReliance and traitReliance
being set in the constructor, and the superclass constructor being used like in
Trait and Attribute with the special case of declaring baseValue as 0.

The calculation of the baseValue is instead handled by overridden getBaseValue()
which uses the specified combination of reliances, and constants which is rounded and
typecasted to an integer value.

### GearManager.java
Declared class final, since it's a singleton which will never be extended.

Initialized INSTANCE member with eager initialization, and the two members
weapons and armorPieces as empty hashmaps which will be initialized later.

The constructor calls the two private methods loadWeaponsToMemberFromXML/
loadArmorToMemberFromXML I added, which are responsible for taking incoming parsed
XML data from weapons.xml/armor.xml, and storing it
as a list of maps, then iterating over each map, extracting the weapon type,
conditionally adding this type as a key to the weapons/armorPieces members,
then filling the list under each key with all weapons of relevant type.

I also added my own private method getListIndexRange(),
getAllWeaponsForRestriction(), getOneHandedWeapons() to segregate 
responsibilities.

Defined all required methods according to UML.

### BaseGear.java
Defined members and methods according to UML, with the constructor initializing all members,
using my private own private method addClassRestrictions() which is responsible
for parsing the list of restrictions, and adding the corresponding class to the restrictions list.
For this I used the Class.forName() method, with the full class path. I had issues
with this first when i did not specify the full path to the relevant class.

Declared methods that are not intended to be overridden, and which function
needs to stay the same for any derivatives, as final.

### Armor.java
Defined members and methods according to UML, with the constructor
initializing members by accessing the correct mapping, and parsing its
value to Integer where needed.

Member trait gets initialized by my own private method createTraitWithRandomBoost()
which returns a Trait of random type and random value.

### Weapon.java
Defined members and methods according to UML, with the constructor
initializing members by accessing the correct mapping, and parsing its
value to Integer where needed.

Member attribute gets initialized by my own private method createAttributeWithRandomBoost()
which returns an Attribute of random type and random value.

### BaseAbility.java
Defined members and methods according to UML, with the constructor
initializing members from parameters.

The calling stack between performAbility(), execute(), the AbilityInfo class and the different attacking functions was very
difficult to understand initially, as well as how return values should be determined, where in the stack they should be analyzed,
and which function held which responsibility. But through study, deduction and logic it was eventually resolved.

### HeavyAttack, FocusedShot, WeaponAttack, Elementalbolt, ElementalBlast, SprayOfArrows, Whirlwind.java
Defined members and methods according to UML, with the constructor
initializing members using the BaseAbility constructor, and values for these as well as return values for
isMagic(), isHeal(), getAmountOfTargets(), execute() and toString() were derived from  the correct constants values as
defined in the instruction table.

### GroupHeal, FocusedHeal.java
In addition to the process for the abilities above, targetEnemies and baseValue was
negated in the execute() method, in order for these spells to act as heals executed on
the own team. This mechanic caused a lot of confusion initially, with team members healing
enemies to death/draining the life from their own team members. The logic around heals
and targeting was eventually resolved once the project reached a more mature state.

### BaseCharacter.java
Defined members and methods according to UML, with the constructor
initializing members characterStats with the incoming parameter and equipment as
an empty CharacterEquipment - which will be filled by the derivative classes.

The abilities member is fully defined by derivatives using the inherited addAbilities method.

The executeActions() turned out to be a logic intense method with many required actions, and therefore
I wrote two private methods to factor out responsibilities. The method takes the returned queue of
random abilities determined by determineActions(), iterates over them and calls my private method
abilityIsAfforded() to check if the character has enough AP and Energy to execute it. If it can be
executed, it calls my other private method determineAbilityBaseValue which calculates the correct
damage/heal value for the ability based on its type. The ability is then tried to be executed, and if successful
the AP and Energy cost is deducted. If the ability is unsuccessful the abilities iteration is stopped
since all opponents are dead.

The logic for determining base values/registering costs and execution was tricky and caused the game
to become very hard / too easy with the wrong values. After some tweaking, refactoring and close studies of the
requirements I found the correct logic.

The logic for damage/heal registering in registerDamage()/registerHealing() was also
difficult to implement correctly, before it was clear where the negation of healing
damage would occur, and other factors. Some minor bugs in the determining of physical damage
also caused the game to be very hard, which caused some headache before these faulty variables
could be found from debugging. Incorrect logical statements in these functions also caused
characters to get healed if they mitigated more damage than was incoming.

The toString() method also took some laboration in order to achieve the correct
look for the printouts.

Defined my own private method getRandomIndex here as well.

### CharacterEquipment.java
Defined members and methods according to UML, initializing members
directly since no constructor was allowed.

Added two private members maxArmorSlots, maxWeaponSlots
to define how many max slots were available.

The toString() was very challenging, since it required understanding of the
IOhelper.formatAsTable() method and the creation of formatted strings with padding,
and other layout management. I also had trouble with runtime exceptions before understanding
that each row needed the same amount of strings before passing the rows to formatAsTable().

### CharacterStats.java
Defined members and methods according to UML, initializing the member
stats in the constructor using the name lists provided in StatsManager.

Calculating the argument values for these stat initializations without hardcoding
took some afterthought, since traits and combatstats depend on relevant constants.

Defined three private methods for the initialisation of Attributes, Traits and CombatStats which are
called from the constructor, to separate concerns.

used the relevant getters and setters and chained combinations of these when needed
in order to maintain cohesion and not directly accessing the members.

Figuring out the design for base values, static, dynamic and total modifiers proved
difficult and caused many issues before finding the correct combinations.

Like in CharacterEquipment, the toString() method was challenging since it
differed from the CharacterEquipment implementation in that the BaseStat.toString()
could be used more extensively if correctly designed, instead of having to rely
on various getters for the stat information.

### BaseHero.java
Defined members and methods according to UML, initializing the member
characterName from the provided parameter in the constructor, while relying
on the superclass constructor to initialize attributeValues from the provider parameter.

The doTurn() method was tricky to figure out, since it took some trial and error
to figure out how the turn information would be created, passed to the logger and
how the targetEnemies parameter was intended to function for the executeActions() method.
Like stated above, the attack/execution call stack was very confusing.

### Cleric, Ranger, Warrior, Wizard.java
Defined members and methods according to UML, initializing the member
characterName using the superclass constructor with the provided parameter and
the correct hero constant, and also passing the correct constant for attribute values.

The superclass equipHero() method was used to equip the hero on initialization,
by passing a reference to the own class as an argument.

The superclass addAbilities() method was also used to att the relevant abilities
as stated in the instructions.

### BaseEnemy.java
Defined members and methods according to UML, the constructor being
identical to the BaseHero constructor, and equipEnemy() having the same
implementation as BaseHero, excluding the adding of armor.

The doTurn() was also implemented in the same way, with targetEnemies set to
the opposite value.

### SkeletonWarrior, SkeletonArcher, SkeletonMage, LichLord.java
Defined members and methods according to UML, initializing the member
sequenceNumber using the superclass constructor with the provided parameter and
the correct hero enemy, and also passing the correct constant for attribute values.

The superclass equipEnemy() method was used to equip the hero on initialization,
by passing the names of allowed weapon types as arguments.

The superclass addAbilities() method was also used to att the relevant abilities
as stated in the instructions.

The LichLord was initialized in the same way with the addition of a health boost,
by adjusting the static modifier with the currentHP multiplied with the correct constant.

### ActivityLogger.java
Defined members and methods according to UML, initializing the member INSTANCE as
an eager singleton.

The constructor calls my own private setLogger() method, which initializes the logger member with
the global logger with a custom console handler and a simpleFormatter which
overrides the format() method to return only the logged message.

The different logging methods simply add a color to the logmessage, and
calls performlog(), which logs the message to console and conditionally delays execution
of the game.

The logger took some study to properly implement.

## Discussion
I think the purpose of the assignment was fulfilled. The assignment was challenging, took
some considerable effort both in terms of algorithm design, general design, OOP principles,
design patterns, understanding of the Java standard library and the many interfaces and classes,
as well as strategy for unit testing, Maven configuration, debugging
and problem solving in the big and the small.

After completing this assignment I feel that much Java knowledge and development skills have solidified.
I also took the opportunity to try and refactor code to be as readable as possible, as well as striving for
code reuse and the single responsibility principle.

Some feedback regarding if I am on the right track regarding the readability and "style" of the code would be appreciated!

Some thoughts about implementation details and alternative designs came up during the process which i will
discuss here.

### BaseCharacter.executeActions()
I chose to abort further abilities execution if the opponents are all dead
since it feels logical that the round will then end. This also fixed a bug of multiple "All enemies/heroes are dead..."
messages being printed in succession after all enemies/heroes were dead. It could however be an idea that
heals would be allowed to be conducted even after all enemies are dead. But since all HP is restored between levels, it
feels redundant in my opinion.

### GearManager.LoadArmor/Loadweapons methods
These algorithms are very similar and I tried to build a single method for this action with
generics and other ideas, but was limited by the required type definition of the members which
was strict. So I settled for two different methods, and opted out of overloading for clarity.

### getRandomIndex methods
These methods are identical in many classes, and could be more appropriately implemented
as a public interface, perhaps included in the Randomizer class.
But this was not possible since we were restricted to extending only private
interfaces with our own methods and members.

### CharacterEquipment.java
This was the only class which lacked a constructor, which caused some confusion. Perhaps it could be included in the UML
as an empty constructor for consistency.

### BaseStat.toString() / BaseGear.toString()
These are not designed with the same structure, BaseStat.toString() returns both the name, and all
relevant numbers for the stat, while BaseGear.toString() and it's overridden version in its derivatives
only returns the name of the weapons without any numbers. For consistency, this method could
return the same amount of formatted information as the BaseStat.toString() - which would simplify
the implementation of CharacterEquipment.toString() which depends on this method, just like
CharacterStats.toString() does.

### BaseHero/BaseEnemy.equipHero()
These methods has identical implementations for adding weapons, and could perhaps
be designed as a single method in the superclass BaseCharacter and inherited instead, the BaseHero class
instead overriding it and adding implementations for equipping armor as well. The only issue
would be the differing parameter types, but some form of code reuse would probably be possible.

### CombatStat.getBaseValue()
In CombatStat, it feels a bit weird that the getBaseValue() method gets the complete responsibility
of calculating the base value for the combat stat, and that the actual base value is set to 0 at construction.
This means that combat stats actually "Has a real base value of 0" and the value is calculated is only an
artificial value. This causes some issues with accessing the modifiers for combat stats, since added
combat stat boosts from gear cannot be isolated and displayed since the "Real base value" when accessed always
is 0, and you need to be able to reference the original CombatStat baseValue in order to see the added bonus
from gear, which is not possible if it is always directly calculated in the getBaseValue() method.

### BaseCharacter.registerHealing(), .roundReset()
I opted for setting an upper limit for heals, as well as AP and Energy regen after each round. It feels
more logical to me that the characters can only be healed up to their max HP and only regen up until their max AP/Energy.
Without a roof, the methods getTotalActionPoints(), getTotalHitPoints() and getTotalEnergyLevel() was left
unused, which further prompted me to add this restriction. In order to make the game more winnable the constants
should probably be buffed a few notches. 

### Minor things
In Constants, ANSI_WHITE is actually gray which is weird. Defence/Defense is also spelled inconsistently
which caused some minor headache when looking for constants.




