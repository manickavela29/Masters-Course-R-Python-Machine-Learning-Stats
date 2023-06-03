import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;



public class ReduceMatrixMulti extends  Reducer<Text,IntWritable,Text,IntWritable> 
{
    public void reduce(Text index , Iterable<IntWritable> values ,Context c) 
    throws IOException , InterruptedException
    {
        int sum = 0;    
        for(IntWritable val:values)
        {
            sum += val.get();
            //c.write(key,val);
        }
        c.write(index,new IntWritable(sum));
    }
}   