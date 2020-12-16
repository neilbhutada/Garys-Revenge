// --== CS400 File Header Information ==--
// Name: Ryan Almizyed
// Email: almizyed@wisc.edu
// Team: MG
// Role: Test engineer 1
// TA: MG
// Lecturer: Florian
// Notes to Grader:


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * A JUnit5 test suite for P04 Gary's Revenge
 * 
 * @author Ryan Almizyed
 *
 */
class GarysRevengeTester
{
  private final static String filepath = "Players.csv";
  private static Entity ryan;
  private static Entity prannav;
  private static Entity bailey;
  private static Entity gary;
  private static Data data;

  /**
   * Some code to initialize all of the entities before each test
   */
  @BeforeEach
  void intialization()
  {
    try
    {
      data = new Data(filepath);
      data.loadHeroes();
      ryan = data.players.get("Ryan");
      prannav = data.players.get("Prannav");
      bailey = data.players.get("Bailey");
      gary = data.players.get("Gary");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      fail();
    }
  }

  /**
   * Tests that a hero can perform a melee attack on the boss and deal damage.
   */
  @Test
  void heroMeleeAttackTest()
  {

    // Save Boss's health before attack
    int beforeHP = gary.getHealth();

    // Attack
    for (int i = 0; i < 10; i++)
      prannav.meleeAttack(gary);

    // Save Boss's health after attack
    int afterHP = gary.getHealth();

    // check that the HP has decreased
    if(!(afterHP < beforeHP))
      fail();
  }

  /**
   * Tests that a hero can perform a magic attack on the boss, deals damage, and reduces that hero's
   * MP
 * @throws Exception 
   */
  @Test
  void heroMagicAttackTest() throws Exception
  {
    // Save Boss's health before attack
    int beforeHP = gary.getHealth();
    int beforeMP = ryan.getMana();
    // Attack
    for (int i = 0; i < 4; i++)
      ryan.magicAttack(gary);

    // Save Boss's health after attack
    int afterHP = gary.getHealth();
    int afterMP = ryan.getMana();

    // check that the HP & MP have decreased
    if(!(afterHP < beforeHP && afterMP < beforeMP))
      fail();
  }

  /**
   * Tests that a hero can heal themselves (their HP increases and their MP decreases)
 * @throws Exception 
   */
  @Test
  void heroHealTest() throws Exception
  {
    // Damage hero
    gary.meleeAttack(prannav);

    // Save hero's health before heal
    int beforeHP = prannav.getHealth();
    int beforeMP = prannav.getMana();

    // Heal
    for (int i = 0; i < 4; i++)
      prannav.heal(prannav);

    // Save hero's health after heal
    int afterHP = prannav.getHealth();
    int afterMP = prannav.getMana();

    // check that HP has increased and MP has decreased
    if(!(afterHP > beforeHP && afterMP < beforeMP))
      fail();
  }

  /**
   * Tests that a hero can die.
   */
  @Test
  void heroDeathTest()
  {
    // Check that the hero is alive
    if(!bailey.isAlive())
      fail();

    // Kill the hero
    for (int i = 0; i < 500; i++)
      gary.meleeAttack(bailey);

    // Check that the hero is not alive
    if(bailey.isAlive())
      fail();
  }

  /**
   * Tests that the boss can melee attack a particular Hero and deal damage to them
   */
  @Test
  void bossMeleeAttackTest()
  {
    // Save hero's health before attack
    int beforeHP = ryan.getHealth();
    // Attack
    for (int i = 0; i < 4; i++)
      gary.meleeAttack(ryan);

    // Save hero's health after attack
    int afterHP = ryan.getHealth();

    // check whether after HP has not decreased
    if(!(afterHP < beforeHP))
      fail();
  }

  /**
   * Tests that the boss can heal himself
 * @throws Exception 
   */
  @Test
  void bossHealTest() throws Exception
  {
    // Damage boss
    ryan.magicAttack(gary);
    prannav.meleeAttack(gary);

    // Save hero's health before heal
    int beforeHP = gary.getHealth();
    int beforeMP = gary.getMana();

    System.out.println(gary.getMana());
    // Heal
    gary.heal(gary);

    // Save hero's health after heal
    int afterHP = gary.getHealth();
    int afterMP = gary.getMana();

    // check HP has increased and MP has decreased
    if(!(afterHP > beforeHP && afterMP < beforeMP))
      fail();
  }

  /**
   * Tests that the getHealthPercent returns the proper health percentage
   */
  @Test
  void getHealthPercentTest()
  {
    // Full health
    float test = ryan.getHealthPercent();
    float key = (float) ryan.getHealth() / ryan.getMaxHealth();
    if(!(test == key))
      fail();

    // After damage
    gary.meleeAttack(ryan);
    test = ryan.getHealthPercent();
    key = (float) ryan.getHealth() / ryan.getMaxHealth();
    if(!(test == key))
      fail();

  }

}
