import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class Reduce2 extends Reducer<Text,Text,Text,Text> 
{
    public void reduce(Text word , Iterable<Text> values ,Context c) 
    throws IOException, InterruptedException   
    {
        

        Float max = 0.0f;
        String Mquarter = "";
        for(Text val:values)
        {
            String line = val.toString();
            String[] rel = line.split(",");
            Float num = Float.parseFloat(rel[1]);
            if(num > max)
            {
                max = num;
                Mquarter = rel[0];
            }
            //c.write(word,val);
        }

        c.write(new Text(Mquarter),new Text(max.toString()));

        /*Integer count = 0;
        Float avg = 0.0f;
        for(FloatWritable val:values)
        {
            avg = avg + val.get();
            count++;
            //c.write(word,val);
        }
        avg = avg/count;
        c.write(quarter,new FloatWritable(avg));
        */
    }
}   