import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main
{
    /*
    10. Создать класс равносторонний треугольник, член класса – длина стороны. Предусмотреть в
    классе методы вычисления и вывода сведений о фигуре – периметр, площадь. Создать производный
    класс – правильная треугольная призма с высотой H, добавить в класс метод определения объема
    фигуры, перегрузить методы расчета площади и вывода сведений о фигуре. Написать программу,
    демонстрирующую работу с классом: дано N треугольников и M призм. Найти количество треугольников,
    у которых площадь меньше средней площади треугольников, и призму с наибольшим объемом.
    */

    private static int M, N;
    private static List<TriangleEquilateral> listN = new ArrayList<>();
    private static List<TrianglePrismEquilateral> listM = new ArrayList<>();
    private static Random random = new Random();

    public static void main(String[] args)
    {
        System.out.println("Enter N value");
        N = readInteger();
        System.out.println("Enter M value");
        M = readInteger();

        //triangles creating and processing
        System.out.println("Processing the Triangles...");
        float averageSquare = 0;
        for(int i = 0; i < N; i++)
        {
            TriangleEquilateral tr = getRandomTriangle();
            System.out.println(i + 1 + ".\n" + tr.getInfo() + "\n");
            averageSquare += tr.getSquare();
            listN.add(tr);
        }
        averageSquare /= N;
        System.out.print("Average square equals " + averageSquare + "\nThe amount of Triangles with lower square values equals ");
        int lowerSquareValuesCount = 0;
        for (TriangleEquilateral tr : listN)
        {
            if(tr.getSquare() < averageSquare) lowerSquareValuesCount++;
        }
        System.out.println(lowerSquareValuesCount + "\n");

        //prisms creating and processing
        System.out.println("Processing the Prisms...");
        float maxVolume = 0;
        TrianglePrismEquilateral maxVolumePrism = null;
        for(int i = 0; i < N; i++)
        {
            TrianglePrismEquilateral pr = getRandomPrism();
            System.out.println(i + 1 + ".\n" + pr.getInfo() + "\n");
            if(pr.getVolume() > maxVolume) maxVolumePrism = pr;
            listM.add(pr);
        }
        System.out.println("The prism with largest volume is:\n" + maxVolumePrism.getInfo());
    }

    public static TriangleEquilateral getRandomTriangle()
    {
        return new TriangleEquilateral((float)random.nextInt(20) + 1);
    }

    public static TrianglePrismEquilateral getRandomPrism()
    {
        return new TrianglePrismEquilateral((float)random.nextInt(20) + 1, (float)random.nextInt(20) + 1);
    }

    public static int readInteger()
    {
        Scanner input = new Scanner(System.in);
        while(true)
        {
            try
            {
                int result = Integer.parseInt(input.next());
                if(result > 0) return result;
                else
                {
                    System.out.println("Value must be > 0");
                }
            }
            catch (Exception e)
            {
                System.out.println("Enter a number, please");
            }
        }
    }
}
