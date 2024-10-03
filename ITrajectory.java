import Geometry.Vector2;

public interface ITrajectory
{
    void Update(float elapsed);

    Vector2 PointAt(float normalizedTime);
}
