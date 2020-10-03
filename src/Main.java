import java.io.*;
import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main
{
    private static int M, N;
    private static Random random = new Random();
    private static FigureStorage storage = new FigureStorage();

    public static final String MAIN_FILE_NAME = "last_save.lab";
    public static final String FILES_EXTENSION = ".lab";

    public static void main(String[] args)
    {
        //search for existing files
        System.out.println("Available files:");
        File[] saves = FigureStorage.getSavesList(".", FILES_EXTENSION);
        int counter = 0;
        for (File f : saves)
        {
            ++counter;
            System.out.println(counter + ". " + f.getName());
        }

        //do last save backup
        storage.doFileBackup(MAIN_FILE_NAME);

        boolean flag = counter > 0;
        if(!flag)
        {
            flag = true;
            System.out.println("No suitable files found\n");
        }
        else
        {
            System.out.println("\nEnter a number of file to load. Enter '0' to skip.");
            int f = readInteger(0, counter);
            if (f != 0)
            {
                //load file
                storage.load(saves[f - 1].getName());
                flag = false;
            }
            else flag = true;
        }

        if(flag)
        {
            //handle user input
            System.out.println("Enter N value");
            N = readInteger();
            System.out.println("Enter M value");
            M = readInteger();

            //fill storage with figures
            storage.fillListRandomly(TriangleEquilateral.class, N);
            storage.fillListRandomly(TrianglePrismEquilateral.class, M);
        }

        //print figures info
        System.out.println("Triangles list:");
        storage.printList(TriangleEquilateral.class);
        System.out.println("Prisms list:");
        storage.printList(TrianglePrismEquilateral.class);

        //do tasks
        doTask(TriangleEquilateral.class);
        doTask(TrianglePrismEquilateral.class);

        //save data to file
        storage.save(MAIN_FILE_NAME);
        System.out.println("Data saved to file '" + MAIN_FILE_NAME + "'");
    }

    public static void doTask(Class<?> figureClass)
    {
        if(figureClass == TriangleEquilateral.class)
        {
            System.out.println("Processing the Triangles...");
            List<TriangleEquilateral> list = storage.getFigureList(figureClass);
            if(list != null)
            {
                float averageSquare = 0;
                for (TriangleEquilateral tr : list)
                {
                    averageSquare += tr.getSquare();
                }
                averageSquare /= list.size();
                System.out.println("Average square equals " + averageSquare);

                int lowerSquareValuesCount = 0;
                for (TriangleEquilateral tr : list)
                {
                    if (tr.getSquare() < averageSquare) lowerSquareValuesCount++;
                }
                System.out.println("The amount of Triangles with lower square values equals " + lowerSquareValuesCount + "\n");
            }
        }
        else if(figureClass == TrianglePrismEquilateral.class)
        {
            System.out.println("Processing the Prisms...");
            List<TrianglePrismEquilateral> list = storage.getFigureList(figureClass);
            if(list != null)
            {
                float maxVolume = 0;
                TrianglePrismEquilateral maxVolumePrism = null;
                for(TrianglePrismEquilateral pr : list)
                {
                    if (pr.getVolume() > maxVolume) maxVolumePrism = pr;
                }
                if(maxVolumePrism != null) System.out.println("The prism with largest volume is:\n" + maxVolumePrism.getInfo());
                else System.out.println("An error occured");
            }
        }
        else
        {
            System.out.println("Error! No task for class <" + figureClass.toString() + ">");
        }
    }

    public static int readInteger()
    {
        return readInteger(1, 9999);
    }

    public static int readInteger(int minValue, int maxValue)
    {
        Scanner input = new Scanner(System.in);
        while(true)
        {
            try
            {
                int result = Integer.parseInt(input.next());
                if(result >= minValue)
                {
                    if(result <= maxValue) return result;
                    else
                    {
                        System.out.println("Value must be less than " + (maxValue + 1));
                    }
                }
                else
                {
                    System.out.println("Value must be greater than " + (minValue - 1));
                }
            }
            catch (Exception e)
            {
                System.out.println("Enter a number, please");
            }
        }
    }
}
