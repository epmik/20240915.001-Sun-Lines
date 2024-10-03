import processing.event.KeyEvent;
import processing.event.MouseEvent;
import java.util.Iterator;

public interface ISunSurface extends IGraphics
{
    ISun Add(ISun sun);

    Iterator<ISun> Suns();

    ISunBackground Background();

    ISunBackground Background(ISunBackground background);

    void Setup(ISketch sketch);

    void Update(float elapsed);
    
    void Draw();

    void MousePressed(MouseEvent event);

    void MouseReleased(MouseEvent event);

    void KeyPressed(KeyEvent event);

    void KeyReleased(KeyEvent event);
}
