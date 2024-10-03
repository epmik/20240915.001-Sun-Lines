
import java.util.function.Consumer;

import controlP5.*;
import processing.core.PApplet;

public class Gui 
{
    ControlP5 _gui;

    private int _xOffset = 24;
    private int _yOffset = 24;

    public Gui(ISketch sketch) 
    {
        _gui = new ControlP5((PApplet) sketch);
    }
    
    void AddSlider(Consumer<Float> consumer, Float value, String label, float min, float max, int ticks)
    {
        var slider = _gui
            .addSlider(label)
            .setPosition(_xOffset, _yOffset)
            .setLabel(label)
            .setRange(min, max)
            .setNumberOfTickMarks(ticks)
            .setValue(value);
            
        _yOffset += 24;

        slider.addCallback(new CallbackListener() 
        {
            public void controlEvent(CallbackEvent event) 
            {
                // if (event.getAction() == ControlP5.ACTION_PRESS) 
                // {
                consumer.accept(event.getController().getValue());
                // }
            }
        });
    }    
    
    void AddToggle(Consumer<Boolean> consumer, Boolean value, String label)
    {
        var slider = _gui
            .addToggle(label)
            .setPosition(_xOffset, _yOffset)
            .setLabel(label)
            .setValue(value);
            
        _yOffset += 24;

        slider.addCallback(new CallbackListener() 
        {
            public void controlEvent(CallbackEvent event) 
            {
                // if (event.getAction() == ControlP5.ACTION_PRESS) 
                // {
                consumer.accept(event.getController().getValue() != 0f);
                // }
            }
        });
    }    
}
