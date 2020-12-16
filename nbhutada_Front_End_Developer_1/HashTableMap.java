// --== CS400 File Header Information ==--
// Name: Bailey Hurlburt
// Email: bhurlburt@wisc.edu
// Team: MG
// Role: Test Engineer
// TA: Harit
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Math;

public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>
{
  int capacity;
  private LinkedList<Record> HashTable[];// This is our Hash Table.

  // Constructor 1
  public HashTableMap(int capacity)
  {
    // This makes sure the maximum elements that Hash Table will store will be depend on the user
    this.capacity = capacity;
    HashTable = new LinkedList[this.capacity];

    // This segment of the code is to declare an hashtable and array of linked lists
    for (int i = 0; i < HashTable.length; i++)
      HashTable[i] = new LinkedList<Record>();
  }

  // Constructor 2
  public HashTableMap()
  {
    this.capacity = 10; // This variable will make sure the maximum elements the HashTable will hold
                        // is 10
    HashTable = new LinkedList[this.capacity];
    for (int i = 0; i < HashTable.length; i++) // This segment of the code is to declare an hash
                                               // table and array of linked lists
    {
      HashTable[i] = new LinkedList<Record>();

    }



  }

  public void clear()
  {
    for (int i = 0; i < capacity; i++) // checks whether key exists in the Hash Table.
    {
      for (int j = 0; j < HashTable[i].size(); j++)
      {
        HashTable[i].remove(j);

      }


    }


  }

  public int size()
  {
    int numberOfElements = 0;

    for (int i = 0; i < capacity; i++) // checks whether key exists in the Hash Table.
    {
      for (int j = 0; j < HashTable[i].size(); j++)
      {
        if((HashTable[i].get(j)) != null)
          numberOfElements++;

      }


    }
    return numberOfElements;

  }

  public ValueType remove(KeyType key)
  {
    int index = Math.abs((key.hashCode()) % capacity); // Calculate index values
    ValueType value;
    for (int i = 0; i < HashTable[index].size(); i++)
    {

      if(HashTable[index].get(i).key == key)
      {
        value = (ValueType) HashTable[index].get(i).value;
        HashTable[index].remove(i);
        return value;

      }


    }

    return null;

  }


  private void resize()
  {
    int numberOfElements = size();
    float loadFactor = (float) numberOfElements / (float) capacity; // Load Factor declaration.
    KeyType key;
    ValueType value;
    int flag = 0;

    Record temp[] = new Record[this.capacity]; // temporary array for all records in the hash table
    if(loadFactor >= 0.80)
    {



      for (int i = 0; i < (capacity); i++) // Goes through the Hash Table.
      {

        for (int j = 0; j < HashTable[i].size(); j++)
        {
          if((HashTable[i].get(j)) != null)
          {
            key = (KeyType) HashTable[i].get(j).key;
            value = (ValueType) HashTable[i].get(j).value;
            temp[flag++] = new Record(key, value); // Stroring all the legitimate records in the
                                                   // array

          }

        }

      }



      capacity = capacity * 2; // increase the capacity by twice
      HashTable = new LinkedList[capacity];
      for (int i = 0; i < HashTable.length; i++) // Declare new Hash Table with twice the size
      {
        HashTable[i] = new LinkedList<Record>();

      }


      for (int i = 0; i < flag; i++)
      {

        put((KeyType) temp[i].key, (ValueType) temp[i].value); // Storing all the values back

      }

    }


  }

  public boolean containsKey(KeyType key)
  {
    KeyType check;
    for (int i = 0; i < this.capacity; i++) // checks whether key exists in the Hash Table.
    {
      for (int j = 0; j < HashTable[i].size(); j++)
      {
        check = (KeyType) (HashTable[i].get(j)).key;
        if(check.equals(key))
        {
          return true; // Key already exists
        }
      }


    }
    return false;
  }


  public boolean put(KeyType key, ValueType value)
  { // Inserts key-value combination into Hash Structure


    int index;
    if(containsKey(key))
      return false;

    index = Math.abs((key.hashCode()) % capacity); // Calculate index values
    HashTable[index].add(new Record(key, value));
    resize();
    return true;

  }

  public ValueType get(KeyType key) throws NoSuchElementException
  {
    int index = Math.abs((key.hashCode()) % capacity); // Calculate index values


    for (int i = 0; i < HashTable[index].size(); i++)
    {

      if(HashTable[index].get(i).key.equals(key))
      {
        return (ValueType) HashTable[index].get(i).value;
      }


    }
    throw new NoSuchElementException("Element not found"); // Throws Exception when element is not
                                                           // found


  }

  public void getAllKeys()
  {
    for (int i = 0; i < HashTable.length; i++)
    {
      for (int j = 0; j < HashTable[i].size(); j++)
      {
        if(HashTable[i].get(j) != null)
          System.out.println(HashTable[i].get(j).key);

      }

    }

  }

}
