// --== CS400 File Header Information ==--
// Name: Bailey Hurlburt
// Email: bhurlburt@wisc.edu
// Team: MG
// Role: Test Engineer
// TA: Harit
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

public class Record <KeyType, ValueType> //This class will contain info about the records to be stored in the Hash Table.
{                                 
    public KeyType key;  // Contain key of the record 
    public ValueType value; // Contain value of the record
    Record(KeyType K , ValueType V)
    {
      this.key = K; 
      this.value = V; 
      
    }


}
