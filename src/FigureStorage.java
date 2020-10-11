import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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

    public void serialize(String fileName)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fos);

            out.writeObject(listN);
            out.writeChars(";");
            out.writeObject(listM);

            out.close();
            fos.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void deserialize(String fileName)
    {
        try
        {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream input = new ObjectInputStream(fis);

            this.listN = (ArrayList<TriangleEquilateral>)input.readObject();
            input.readChar();
            this.listM = (ArrayList<TrianglePrismEquilateral>)input.readObject();

            input.close();
            fis.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void serializeFastJSON(String fileName)
    {
        try
        {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(JSON.toJSONString(this.listN) + "\n");
            bw.write(JSON.toJSONString(this.listM));
            bw.close();
            fw.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void deserializeFastJSON(String fileName)
    {
        try
        {
            Scanner scanner = new Scanner(new FileReader(fileName));
            this.clear();
            ArrayList<JSONObject> JSONlist = JSON.parseObject(scanner.nextLine(), ArrayList.class);
            for (JSONObject obj : JSONlist)
            {
                this.listN.add(new TriangleEquilateral(obj.getIntValue("sideLength")));
            }
            JSONlist = JSON.parseObject(scanner.nextLine(), ArrayList.class);
            for (JSONObject obj : JSONlist)
            {
                this.listM.add(new TrianglePrismEquilateral(obj.getIntValue("sideLength"), obj.getIntValue("height")));
            }
            scanner.close();
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

        deserialize(mainFileName);
        serialize(backupName);
        System.out.println("Created last save backup file '" + backupName + "'");
        this.clear();
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
