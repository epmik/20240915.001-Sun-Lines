
package Utility.Interfaces;

public interface IRandomGenerator
{
    long Seed();
    long ReSeed();
    long ReSeed(long seed);
    float Single();
    float Single(float max);
    float Single(float min, float max);
    double Double();
    double Double(double max);
    double Double(double min, double max);
    int Int();
    int Int(int max);
    int Int(int min, int max);
    double Value();
    double Value(double max);
    double Value(double min, double max);
    float Value(float max);
    float Value(float min, float max);
    int Value(int max);
    int Value(int min, int max);
    boolean Boolean();
}
