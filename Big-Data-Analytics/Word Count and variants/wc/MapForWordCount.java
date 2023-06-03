import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class MapForWordCount extends MapReduceBase implements Mapper<LongWritable,Text,Text,IntWritable>
{
    IntWritable one = new IntWritable(1);
    public void map(LongWritable key , Text value , OutputCollector<Text,IntWritable> out , Reporter r)
    throws IOException
    {
        String line = value.toString();
        String[] words = line.split(",");
        for(String word : words)
        {
            Text outputkey = new Text(word.toUpperCase().trim());
            out.collect(outputkey,one);
        }
    } 
}