import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;




public class Map extends Mapper<LongWritable,Text,Text,Text>
{
    
    public void map(LongWritable key , Text value ,Context c)
    throws IOException , InterruptedException   
    {
        String line = value.toString();
        String[] rel = line.split(",");
        Integer num = Integer.parseInt(rel[0]);
        Float val = Float.parseFloat(rel[1]);
        
        if(num == 0)
        {
            num = num + 1;
            c.write(new Text(num.toString()),new Text("B,"+val.toString()));       // 0 will go with next(1) for reduce
        }
        else if(num == 23)
        {
            c.write(new Text(num.toString()),new Text("B,"+val.toString()));
        }
        else
        {
            c.write(new Text(num.toString()),new Text("A,"+val.toString()));
            num++;
            c.write(new Text(num.toString()),new Text("B,"+val.toString()));
        }
        

    } 
}