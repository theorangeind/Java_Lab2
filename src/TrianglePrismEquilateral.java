import lombok.Getter;

import java.io.Serializable;

public class TrianglePrismEquilateral extends TriangleEquilateral implements IFigure, Serializable
{
    @Getter
    protected final float height;

    public TrianglePrismEquilateral(float sideLength, float height)
    {
        super(sideLength);
        this.height = height;
    }

    public float getVolume()
    {
        return getSquare() * height;
    }

    @Override
    public float getSquare()
    {
        return super.getSquare() * 2 + (height * sideLength * 3);
    }

    @Override
    public String getInfo()
    {
        return "Base side length: " + sideLength + "\nHeight: " + height + "\nSurface square: " + getSquare() + "\nVolume: " + getVolume();
    }
}
