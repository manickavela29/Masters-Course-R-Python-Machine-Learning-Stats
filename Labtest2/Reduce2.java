import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class Reduce2 extends Reducer<Text,Text,Text,Text> 
{
    public void reduce(Text hour , Iterable<Text> values ,Context c) 
    throws IOException, InterruptedException   
    {
        

        Float max = 0.0f;
        Float min = 100.0f;

        String MaxHour = "";
        String MinHour = "";

        for(Text val:values)
        {
            String line = val.toString();
            String[] rel = line.split(",");
            Float num = Float.parseFloat(rel[1]);

            if(num > max)
            {
                max = num;
                MaxHour = rel[0];
            }

            if(num < min)
            {
                min = num;
                MinHour = rel[0];
            }

        }

        c.write(new Text("Best Increase : "+MaxHour),new Text(max.toString()));
        c.write(new Text("Worst Fall    : "+MinHour),new Text(min.toString()));

    }
}   