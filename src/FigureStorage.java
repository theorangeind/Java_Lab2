import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FigureStorage
{
    private List<TriangleEquilateral> listN = new ArrayList<>();
    private List<TrianglePrismEquilateral> listM = new ArrayList<>();

    private static Random random = new Random();

    public void addFigure(TriangleEquilateral triangle)
    {
        listN.add(triangle);
    }

    public void addFigure(TrianglePrismEquilateral prism)
    {
        listM.add(prism);
    }

    public List getFigureList(Class<?> figureClass)
    {
        if(figureClass == TriangleEquilateral.class)
        {
            return listN;
        }
        else if(figureClass == TrianglePrismEquilateral.class)
        {
            return listM;
        }
        else
        {
            System.out.println("Error! Figure storage doesn't contains a class (" + figureClass.toString() + ") !");
            return null;
        }
    }

    public void fillListRandomly(Class<?> figureClass, int count)
    {
        if(figureClass == TriangleEquilateral.class)
        {
            for(int i = 0; i < count; i++)
            {
                TriangleEquilateral tr = getRandomTriangle();
                addFigure(tr);
            }
        }
        else if(figureClass == TrianglePrismEquilateral.class)
        {
            for(int i = 0; i < count; i++)
            {
                TrianglePrismEquilateral pr = getRandomPrism();
                addFigure(pr);
            }
        }
        else
        {
            System.out.println("Error! Figure storage doesn't contains a class (" + figureClass.toString() + ") !");
        }
    }

    public void printList(Class<?> figureClass)
    {
        List list = getFigureList(figureClass);
        if(list == null || list.isEmpty() || !(list.get(0) instanceof IFigure))
        {
            System.out.println("Error! Can't print a figures list!");
        }
        else
        {
            int counter = 0;
            for (Object f : list)
            {
                counter++;
                System.out.println((counter) + ".\n" + ((IFigure)f).getInfo() + "\n");
            }
        }
    }

    public void clear()
    {
        listN.clear();
        listM.clear();
    }

    public static TriangleEquilateral getRandomTriangle()
    {
        return new TriangleEquilateral((float)random.nextInt(20) + 1);
    }

    public static TrianglePrismEquilateral getRandomPrism()
    {
        return new TrianglePrismEquilateral((float)random.nextInt(20) + 1, (float)random.nextInt(20) + 1);
    }

    public void save(String fileName)
    {
        try(BufferedWriter out = new BufferedWriter(new FileWriter(fileName)))
        {
            if (listN != null)
            {
                for (TriangleEquilateral tr : listN)
                {
                    out.write(String.valueOf(tr.sideLength));
                    out.newLine();
                }
                out.write(";");
                out.newLine();
            }
            if(listM != null)
            {
                for (TrianglePrismEquilateral pr : listM)
                {
                    out.write(String.valueOf(pr.sideLength));
                    out.newLine();
                    out.write(String.valueOf(pr.height));
                    out.newLine();
                }
                out.write(";");
                out.newLine();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void load(String fileName)
    {
        this.clear();

        try(Scanner scanner = new Scanner(new FileReader(fileName)))
        {
            while(scanner.hasNextLine())
            {
                try
                {
                    float f = Float.parseFloat(scanner.nextLine());
                    listN.add(new TriangleEquilateral(f));
                }
                catch (NumberFormatException e)
                {
                    break;
                }
            }
            while(scanner.hasNextLine())
            {
                try
                {
                    float s = Float.parseFloat(scanner.nextLine());
                    float h = Float.parseFloat(scanner.nextLine());
                    listM.add(new TrianglePrismEquilateral(s, h));
                }
                catch (NumberFormatException e)
                {
                    break;
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void doFileBackup(String mainFileName)
    {
        File f = new File(mainFileName);
        if(!f.exists())
        {
            return;
        }

        String backupName = "save-";
        backupName += new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new Date());
        backupName += Main.FILES_EXTENSION;

        load(mainFileName);
        save(backupName);
        clear();
    }

    public static File[] getSavesList(String directory, String extension)
    {
        File dir = new File(directory);
        File [] files = dir.listFiles(new FilenameFilter()
        {
            @Override
            public boolean accept(File dir, String name)
            {
                return name.endsWith(extension);
            }
        });
        return files;
    }
}
