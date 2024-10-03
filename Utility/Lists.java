package Utility;
import java.util.AbstractList;
import Utility.Interfaces.IRandomGenerator;

public class Lists
{
    private static IRandomGenerator RandomGenerator = new RandomGenerator();

    public static <T> T Random(AbstractList<T> array)
    {
      return array.get(RandomGenerator.Value(array.size()));
    }
}
