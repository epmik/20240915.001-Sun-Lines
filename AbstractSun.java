import java.util.Date;

import Geometry.Vector2;
import Utility.RandomGenerator;
import Utility.Interfaces.IRandomGenerator;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public abstract class AbstractSun implements ISun 
{
    public IRandomGenerator RandomGenerator;
    protected ISketch _sketch;
    protected long _randomGeneratorSeed;

    public Vector2 Position = new Vector2(0, 0);
    public Vector2 Area = new Vector2(800, 800);

    public PGraphics Graphics;

    public PGraphics Graphics()
    {
        return Graphics;
    }

    public void Graphics(PGraphics graphics)
    {
        Graphics = graphics;
    }

    public AbstractSun(int x, int y, int width, int height, PGraphics graphics) 
    {
        super();

        Graphics = graphics;
      
        _randomGeneratorSeed = new Date().getTime();

        RandomGenerator = new RandomGenerator(_randomGeneratorSeed);

        Position.X = x;
        Position.Y = y;
        Area.X = width;
        Area.Y = height;
    }
 
    public void Setup(ISketch sketch)
    {
        _sketch = sketch;

        if (Graphics == null)
        {
            Graphics = ((PApplet)_sketch).createGraphics((int)Area.X, (int)Area.Y, PConstants.P2D);
            Graphics.smooth(16);    
            Graphics.colorMode(PConstants.RGB, 255, 255, 255, 255);
        }
    }

    public void Update(float elapsed) 
    {
    }
     
    public void Draw()
    {
    }

    public void MousePressed(MouseEvent event)
    {
    }

    public void MouseReleased(MouseEvent event)
    {        
    }

    public void KeyPressed(KeyEvent event)
    {        
    }

    public void KeyReleased(KeyEvent event)
    {
    }
}
