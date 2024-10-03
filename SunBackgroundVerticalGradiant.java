import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class SunBackgroundVerticalGradiant implements ISunBackground 
{
    public int ForeColor;
    public int BackColor;
    public int Alpha = 255;

    public SunBackgroundVerticalGradiant(PGraphics graphics) 
    {
        super();

        Graphics = graphics;

        ForeColor = Graphics.color(255, 210, 0);
        BackColor = Graphics.color(255, 138, 0);
    }

    public PGraphics Graphics;

    public PGraphics Graphics()
    {
        return Graphics;
    }

    public void Graphics(PGraphics graphics)
    {
        Graphics = graphics;
    }

    public void Setup()
    {

    }

    public void Update(float elapsed) 
    {
        
    }
       
    public void Draw()
    {
        DrawVerticalBackgroundGradient(
            Graphics.width,
            Graphics.height,
            ForeColor,
            BackColor);
    }
        
    void DrawVerticalBackgroundGradient(float w, float h, int foreColor, int backColor) 
    {
        Graphics.beginDraw();

        Graphics.noFill();

        if (Alpha < 255)
        {
            Graphics.blendMode(PConstants.ADD);
        }

        for (int i = 0; i <= h; i++) 
        {
            float inter = PApplet.map(i, 0, h, 0, 1);

            int c = Graphics.lerpColor(foreColor, backColor, inter);

            Graphics.stroke(c, Alpha);
            Graphics.line(0, i, w, i);
        }

        if (Alpha < 255)
        {
            Graphics.blendMode(PConstants.BLEND);
        }

        Graphics.endDraw();
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
