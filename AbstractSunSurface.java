import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
import Utility.RandomGenerator;
import Utility.Interfaces.IRandomGenerator;

public abstract class AbstractSunSurface implements ISunSurface
{    
    public int Width = 800;
    public int Height = 800;

    public IRandomGenerator RandomGenerator;
    protected long RandomGeneratorSeed;

    protected float _scale = 1f;

    protected ISketch _sketch;
    public PGraphics Graphics;
    protected ArrayList<ISun> _sunArrayList;
    protected ISunBackground _background = null;

    public AbstractSunSurface(int width, int height) 
    {
        super();

        RandomGeneratorSeed = new Date().getTime();

        RandomGenerator = new RandomGenerator(RandomGeneratorSeed);

        Width = width;
        Height = height;

        _scale = width / 800f;

        _sunArrayList = new ArrayList<ISun>();
    }

    public PGraphics Graphics()
    {
        return Graphics;
    }

    public void Graphics(PGraphics graphics)
    {
        Graphics = graphics;
        Width = Graphics.width;
        Height = Graphics.height;
    }

    public ISun Add(ISun sun) 
    {
        _sunArrayList.add(sun);

        return sun;
    }

    public Iterator<ISun> Suns()
    {
        return _sunArrayList.iterator();
    }


    public ISunBackground Background()
    {
        return _background;
    }


    public ISunBackground Background(ISunBackground background)
    {
        _background = background;

        return _background;
    }


    public void Setup(ISketch sketch)
    {
        _sketch = sketch;

        if (Graphics == null)
        {
            Graphics = ((PApplet) _sketch).createGraphics(Width, Height, PConstants.P2D);
            Graphics.smooth(16);
            Graphics.colorMode(PConstants.RGB, 255, 255, 255, 255);
        }
        
        if(_background == null)
        {
            _background = new SunBackgroundVerticalGradiant(Graphics);
        }    
    }


    public void Update(float elapsed)
    {
        _background.Update(elapsed);

        for (ISun sun : _sunArrayList) 
        {
            sun.Update(elapsed);
        }
    }

    
    public void Draw()
    {
        _background.Draw();

        for (ISun sun : _sunArrayList) 
        {
            sun.Draw();
        }
    }


    public void MousePressed(MouseEvent event)
    {
        _background.MousePressed(event);

        for (ISun sun : _sunArrayList) 
        {
            sun.MousePressed(event);
        }
    }


    public void MouseReleased(MouseEvent event)
    {
        _background.MouseReleased(event);

        for (ISun sun : _sunArrayList) 
        {
            sun.MouseReleased(event);
        }
    }


    public void KeyPressed(KeyEvent event)
    {
        _background.KeyPressed(event);

        for (ISun sun : _sunArrayList) 
        {
            sun.KeyPressed(event);
        }
    }


    public void KeyReleased(KeyEvent event)
    {
        _background.KeyReleased(event);

        for (ISun sun : _sunArrayList) 
        {
            sun.KeyReleased(event);
        }
    }

}
