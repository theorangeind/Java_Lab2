import lombok.Getter;

import java.io.Serializable;

public class TriangleEquilateral implements IFigure, Serializable
{
    @Getter
    protected final float sideLength;

    public TriangleEquilateral(float sideLength)
    {
        this.sideLength = sideLength;
    }

    public float getPerimeter()
    {
        return sideLength*3;
    }

    @Override
    public float getSquare()
    {
        return (float)(sideLength * sideLength * Math.sqrt(3) / 4);
    }

    @Override
    public String getInfo()
    {
        return "Side length: " + sideLength + "\nPerimeter: " + getPerimeter() + "\nSquare: " + getSquare();
    }
}
