import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce3ForRA extends Reducer<Text,Text,Text,Text>
{
    public void reduce(Text key, Iterable<Text> values , Context c) throws IOException , InterruptedException
    {
        Set<String> Myset = new HashSet<String>();
        Integer count = 0;
        for(Text val:values)
        {
            String t = val.toString();
            String[] rel = t.split(",");
            for(int i = 0 ; i < rel.length ; i++)
            {
                Myset.add(rel[i]);
            }
        }
        Myset.remove(key.toString());
        count = Myset.size();
        String mylist =  String.join(",",Myset);
        //c.write(key , new Text(mylist));
        c.write(key,new Text(Integer.toString(count)));
    }
}