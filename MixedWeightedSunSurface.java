import Utility.WeightedCollection;

public class MixedWeightedSunSurface extends AbstractSunSurface
{
    private WeightedCollection<ISun> _weightedSunCollection = new WeightedCollection<ISun>();
    private float DefaultWeight = 100f;
    
    public MixedWeightedSunSurface(int width, int height) 
    {
        super(width, height);
    }

    @Override
    public ISun Add(ISun sun)
    {
        return Add(sun, DefaultWeight);
    }

    public ISun Add(ISun sun, float weight)
    {
        _weightedSunCollection.Add(weight, sun);

        return super.Add(sun);
    }
 
    @Override
    public void Draw()
    {
        _background.Draw();
        
        for (var i = 0; i < _sunArrayList.size(); i++)
        {
            _weightedSunCollection.Next().Draw();
        }
    }
}
