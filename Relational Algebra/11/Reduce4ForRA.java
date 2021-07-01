import java.io.IOException;
import java.util.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce4ForRA extends Reducer<Text,Text,Text,Text>
{
    public void reduce(Text key, Iterable<Text>  values , Context c) throws IOException , InterruptedException
    {
        int max = 0 ;
        String  track = "";
        for(Text val:values)
        {
            String t = val.toString();
            String[] rel = t.split(",");
            if(max < Integer.parseInt(rel[1]))
            {
                max = Integer.parseInt(rel[1]);
                track = rel[0];
            }
        }
        
        c.write(new Text(track),new Text(Integer.toString(max)));
        
        //c.write(new Text("checked") , new Text("Here"));
    }
}