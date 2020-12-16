//--== CS400 File Header Information ==--
//Name: Prannav Arora
//Email: parora9@wisc.edu
//Team: MG
//TA: Harit
//Lecturer: Gary Dahl
//Notes to Grader: <optional extra notes>



import java.util.Random;

/**
 * Entity class
 * @author Prannav Arora
 *
 */
public class Entity
{
  //Fields
  protected String name;
  protected int HP;
  protected int maxHP;
  protected int MP;
  protected int attack;
  protected int defense;
  protected int magic;
  private int decider = 1; 
  //protected String imagePath;
  
  //Some constants
  public static final int ATTACK_LIMIT = 2;
  
  public static final int MAGIC_LIMIT = 3;
  
  public static final int DEFENSE_LIMIT = 2;
  
  public static final int MANA_MAGIC_ATTACK_COST = 1;
  public static final int MANA_HEAL_COST = 2;
  
  public static final int MINIMUM_ATTACK_DAMAGE = 1;
  
  /**
   * Constructor for Entity
   * @param imagePath
   * @param name
   * @param HP
   * @param strength
   */
  public Entity(String name, int HP, int MP, int attack, int defense, int magic)
  {
    this.name = name;
    this.HP = HP;
    this.maxHP = HP;
    this.MP = MP;
    this.attack = attack;
    this.defense = defense;
    this.magic = magic;
  }
  
  /**
   * Is character alive
   * @return true if HP is higher than 0, false otherwise.
   */
  public boolean isAlive()
  {
    return HP > 0;
  }
  
  /**
   * Getter for health
   * @return HP
   */
  public int getHealth()
  {
    return this.HP;
  }
  
  /**
   * Getter for max health
   * @return maxHP
   */
  public int getMaxHealth()
  {
    return this.maxHP;
  }
  
  /**
   * Gets the entitity's health as a percentage
   * @return the health as a percentage
   */
  public float getHealthPercent()
  {
    return (float)this.HP / (float)this.maxHP;
  }
  
  /**
   * Getter for Mana
   * @return MP
   */
  public int getMana()
  {
    return this.MP;
  }
  
  /**
   * Gets the entity's stats
   * @return int array containing the entity's stats
   */
  public int[] getStats()
  {
    int[] stats = new int[4];
    stats[0] = this.HP;
    stats[1] = this.MP;
    stats[2] = this.attack;
    stats[3] = this.defense;
    return stats;
  }
  
  
  /**
   * Melee attack that the Hero does on a Boss
   * @param target - the Boss we want to do melee attack on
   * @return Boss's new health
   */
  public int meleeAttack(Entity target)
  {
    int damage = 0;
    // the player's attack (level not relevant right now)
    int attackScore = this.attack + randomNum(-1*(Entity.ATTACK_LIMIT), Entity.ATTACK_LIMIT);
    // the enemy's defense (level not relevant right now)
    int defenseScore = target.defense + randomNum(-1*(Entity.DEFENSE_LIMIT), Entity.DEFENSE_LIMIT); 
    if (defenseScore >= attackScore) 
    {
      damage = Entity.MINIMUM_ATTACK_DAMAGE;
    }
    else 
    {
      damage = attackScore - defenseScore;
    }

    target.HP -= damage;
    return target.getHealth();
  }
  
  /**
   * Magic attack that Hero does on Boss
   * @param target - the Boss we want to do a magic attack on
   * @return Boss's new health
   * @throws RuntimeException 
   */
  public int magicAttack(Entity target) throws RuntimeException
  {
    int damage = 0;
    if(this.MP < Entity.MANA_MAGIC_ATTACK_COST) 
    { 
      //updatedMP holds the updated value of MP throughout the game
      throw new RuntimeException("hey you don't have enough MP lol");
    }
    else 
    {
      //MP is a constant variable that holds the max MP a player has
      int magicAttackScore = this.magic + randomNum(-1*(Entity.MAGIC_LIMIT), Entity.MAGIC_LIMIT); 
      int magicDefenseScore = target.magic + randomNum(-1*(Entity.MAGIC_LIMIT), Entity.MAGIC_LIMIT);
      if (magicDefenseScore >= magicAttackScore) 
      {
        damage = Entity.MINIMUM_ATTACK_DAMAGE;
      }
      else 
      {
        damage = magicAttackScore - magicDefenseScore;
      }
      

      this.MP -= Entity.MANA_MAGIC_ATTACK_COST; 
      target.HP -= damage;
    }
    
    return target.getHealth();
  }
  
  /**
   * Hero heals itself/ or another Hero
   * @param target - the person who will be healed
   * @return The new HP of the healed person
   * @throws RuntimeException 
   */
  public int heal(Entity target) throws RuntimeException 
  {
    if(target.MP < Entity.MANA_HEAL_COST) 
    { 
      //updatedMP holds the updated value of MP throughout the game
      throw new RuntimeException("You don't have enough MP");
    }
    else 
    {
      int healAmount = this.defense + (MP + randomNum(-1 * (Entity.DEFENSE_LIMIT), Entity.DEFENSE_LIMIT));
      if(target.HP == this.maxHP) 
      { 
        //if the HP is at it's max, you can't add more HP
        healAmount = 0;
      }
      else if (target.HP == 0)
      {
        throw new RuntimeException("The person you are trying to heal is dead");
      }
      else if(target.HP + healAmount >= this.maxHP)
      {
        //stops the healAmount from going over the max HP
        healAmount = this.maxHP - target.HP;
      }
      

      this.MP -= Entity.MANA_HEAL_COST;
      target.HP += healAmount;
    }
    
    return target.HP;
  }
  
  
  /**
   * Generates a random number between min and max
   * @param min - lower bound
   * @param max - upper bound
   * @return a random int
   */
  private int randomNum(int min, int max)
  {
    Random rand = new Random();
    int arg = max - min + 1;
    int random_num = rand.nextInt(arg) + min;
    
    return random_num;
  }
  
  /**
   * Boss's attack algorithm (sometimes Boss will heal itself)
   * @param target - The Hero that the Boss will attack
   * @return an int array with information for front end
   * @throws Exception
   */
  public String bossNextMove(Entity target) throws Exception
  {
    if (this.HP <= (this.maxHP / 3) && this.MP >= Entity.MANA_HEAL_COST)
    {
      this.heal(this);
      return "heal";
    }
    else if(decider%3 == 0 ) 
    {
      this.magicAttack(target);
      decider++;
      return "magic";
    }
    else
    {
      this.meleeAttack(target);
      decider++;
      return "melee";
    }
  }
}
