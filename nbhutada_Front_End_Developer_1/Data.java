

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class Data {
  public HashTableMap<String, Entity> players = new HashTableMap<String, Entity>();
  // public HashTableMap<String, Boss> bosses = new HashTableMap<String, Boss>();
  String filename;

  public Data(String filename) {
    this.filename = filename;
  }
  
  /*
   * load data from csv into respective "Entity" objects
   * 
   */
  public void loadHeroes() throws NumberFormatException, Exception {
    // Create placeholders for all the Player's stats
    int curHP = 0;
    String curName = null;
    String curSpecialAttack = null;
    int curDefenseScore = 0;
    int curAttackScore = 0;
    int curMP = 0;
    int curMagic = 0;
    
    // create stream in order to parse the data in csv file
    List<String[]> heroStream =
        Files.lines(Paths.get(filename)).map(line -> line.split(",")).collect(Collectors.toList());
    
    // retrieve the data and put it into respective objects and attributes
    heroStream.remove(0);
    for (String[] newStream : heroStream) {
      curHP = Integer.parseInt(newStream[0]);
      curName = newStream[1];
      curSpecialAttack = newStream[2];
      curDefenseScore = Integer.parseInt(newStream[3]);
      curAttackScore = Integer.parseInt(newStream[4]);
      curMP = Integer.parseInt(newStream[5]);
      curMagic = Integer.parseInt(newStream[6]);
      Entity newHero =
          // new Hero(curHP, curName, curSpecialAttack, curDefenseScore, curAttackScore, curMana);
          new Entity(curName, curHP, curMP, curAttackScore, curDefenseScore, curMagic);
      players.put(curName, newHero);
    }
  }

}
