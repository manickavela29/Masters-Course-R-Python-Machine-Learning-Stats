import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;



public class ReduceMatrixMulti extends  Reducer<Text,IntWritable,Text,IntWritable> 
{

    static List<Integer> V = new ArrayList<>();
    protected void setup(Context C) throws IOException,InterruptedException
    {
        Path pt=new Path("hdfs:/tmp/V.txt");//Location of file in HDFS
        FileSystem fs = FileSystem.get(new Configuration());
        BufferedReader br=new BufferedReader(new InputStreamReader(fs.open(pt)));
        String line;
        line=br.readLine();
        while (line != null)
        {
            System.out.println(V);
            V.add(Integer.parseInt(line.split(",")[1]));
            line=br.readLine();
        }
    }

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