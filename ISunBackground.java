import processing.event.KeyEvent;
import processing.event.MouseEvent;

public interface ISunBackground extends IGraphics
{
    void Setup();

    void Update(float elapsed);
    
    void Draw();

    void MousePressed(MouseEvent event);

    void MouseReleased(MouseEvent event);

    void KeyPressed(KeyEvent event);

    void KeyReleased(KeyEvent event);
}
