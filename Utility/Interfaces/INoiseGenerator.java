
package Utility.Interfaces;

public interface INoiseGenerator
{
    long Seed();
    long ReSeed();
    long ReSeed(long seed);
    double Value(double x);
    double Value(double x, double y);
    double Value(double x, double y, double z);
    double Value(double x, double y, double z, double w);
}
