import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class MapForWordCount extends Mapper<LongWritable,Text,Text,IntWritable>
{
    static IntWritable one = new IntWritable(1);
    public void map(LongWritable key , Text value , Context c)
    throws IOException , InterruptedException  
    {
        String line = value.toString();
        String[] words = line.split(",");
        for(String word : words)
        {
            Text outputkey = new Text(word.toUpperCase().trim());
            c.write(outputkey,one);
        }
    } 
}