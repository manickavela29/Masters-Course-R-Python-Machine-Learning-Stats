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
        Integer sum = 0,count = 0;
        
        if(index.equals(new Text("Error")))
        {
            c.write(new Text("Dimension mismatch Error"),new IntWritable(-1));
        }
        else
        {

            String[] element = index.toString().split(",");

            Integer i = Integer.parseInt(element[0]);
            Integer Vcount = Integer.parseInt(element[1]);
            for(IntWritable val:values)
            {
                sum += val.get();
                count++;
            }

            if(count != Vcount)                                                 //where n(of M) could be less than n(of V)
            {
                c.write(new Text("Dimension mismatch Error"),new IntWritable(-1));
            }
            else
            {
                c.write(new Text(i.toString()),new IntWritable(sum));
            }
            
        }
    }
}   