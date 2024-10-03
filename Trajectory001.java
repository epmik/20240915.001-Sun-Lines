import Utility.Easing;
import Utility.OpenSimplexSummedNoiseGenerator;
import Utility.RandomGenerator;
import Geometry.Vector2;

public class Trajectory001 implements ITrajectory
{
    public float Radius = 40f;
    public float MaxDeviation = 200f;
    public float NoiseMultiplier = 0.01f;
    public RandomGenerator RandomGenerator;
    public OpenSimplexSummedNoiseGenerator NoiseGenerator;
    public boolean EaseIn = true;

    public Trajectory001()
    {
        RandomGenerator = new RandomGenerator();
        NoiseGenerator = new OpenSimplexSummedNoiseGenerator();
    }

    public void Update(float elapsed)
    {
        
    }
    
    public Vector2 PointAt(float normalizedTime)
    {
        float radius = Radius * normalizedTime;
        float offset = (float)((MaxDeviation * 0.5f) + (NoiseGenerator.Value(radius * NoiseMultiplier) * MaxDeviation));
        float v = (float)NoiseGenerator.Value(radius * NoiseMultiplier);
        float x = (EaseIn ? (float) Easing.InCubic(normalizedTime) : 1f)
                * (offset + (-(MaxDeviation * 0.5f) + (v * MaxDeviation)));
        
        return new Vector2(x, radius);
    }
}


