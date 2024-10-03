import java.util.Date;

import Geometry.Vector2;
import Utility.Easing;
import Utility.RandomGenerator;
import Utility.Interfaces.IRandomGenerator;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class TrajectoryComponent
{
    private ITrajectory _trajectory;
    public float Resolution = 100f;

    public TrajectoryComponent(ITrajectory trajectory) 
    {
        super();

        _trajectory = trajectory;
    }
     
    public void Draw(PGraphics graphics)
    {
        graphics.beginDraw();

        graphics.pushMatrix();
   
        graphics.translate(graphics.width / 2, graphics.height / 2);
        
        for (float i = 0f; i <= 1f; i += 0.01)
        {
          var p = _trajectory.PointAt(i);
    
          graphics.noStroke();
          graphics.fill(0, 0, 255);
          graphics.circle(p.X, p.Y, 20);
    
        }
        
        graphics.popMatrix();

        graphics.endDraw();
    }
}
