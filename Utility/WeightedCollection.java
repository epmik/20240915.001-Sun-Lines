package Utility;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class WeightedCollection<TValue> 
{
    private final NavigableMap<Double, TValue> map = new TreeMap<Double, TValue>();
    private final Random random;
    private double total = 0;

    public WeightedCollection() 
    {
        this(new Random());
    }

    public WeightedCollection(long seed) 
    {
        this(new Random(seed));
    }

    public WeightedCollection(Random random) 
    {
        this.random = random;
    }

    public WeightedCollection<TValue> Add(double weight, TValue result) 
    {
        if (weight <= 0)
            return this;
        
        total += weight;
        
        map.put(total, result);
            
        return this;
    }

    public TValue Next() 
    {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }

    public void Clear() 
    {
        map.clear();
    }
}
