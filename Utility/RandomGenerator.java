package Utility;

import java.util.Date;

import Utility.Interfaces.IRandomGenerator;
  
public class RandomGenerator implements IRandomGenerator
{
    private long _seed;
    private java.util.Random _random;
 
    public RandomGenerator()
    {
      this(new java.util.Date().getTime());
    }
  
    public RandomGenerator(long seed)
    {
      _seed = seed;
      _random = new java.util.Random(_seed);
    }
    
    public long Seed()  
    {
      return _seed;
    }
  
  public long ReSeed()
  {
    return ReSeed(new Date().getTime());
  }
  
   public long ReSeed(long seed)
   {
    // if(seed != _seed)
    // {
      _seed = seed;
      _random.setSeed(_seed);
    //  }     
     return _seed;
   }
    
    public float Single()
    {
      return (float)_random.nextDouble();
    }
    
    public float Single(float max)
    {
        return Value(0f, max);
    }
    
    public float Single(float min, float max)
    {
        return (min + (float)_random.nextDouble() * (max - min));
    }
    
    public double Double()
    {
      return _random.nextDouble();
    }
    
    public double Double(double max)
    {
        return Value(0f, max);
    }
    
    public double Double(double min, double max)
    {
        return (min + _random.nextDouble() * (max - min));
    }
    
    public int Int()
    {
      return _random.nextInt();
    }
    
    public int Int(int max)
    {
        return Int(0, max);
    }
    
    public int Int(int min, int max)
    {
      return (int)(min + _random.nextDouble() * (max - min));
    }
    
    public double Value()
    {
        return _random.nextDouble();
    }
    
    public float Value(float max)
    {
        return Value(0f, max);
    }
    
    public float Value(float min, float max)
    {
        return (float)(min + _random.nextDouble() * (max - min));
    }
    
    public double Value(double max)
    {
        return Value(0, max);
    }
    
    public double Value(double min, double max)
    {
        return min + _random.nextDouble() * (max - min);
    }
    
    public int Value(int max)
    {
        return Value(0, max);
    }
    
    public int Value(int min, int max)
    {
        return (int)(min + _random.nextDouble() * (max - min));
    }
    
    public boolean Boolean()
    {
        return Value(0, 2) == 0;
    }
}
