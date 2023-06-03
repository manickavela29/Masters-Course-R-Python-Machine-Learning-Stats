import java.io.IOException;
import java.io.DataOutput;
import java.io.DataInput;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;


public class Map3ForRA extends Mapper<LongWritable,Text,Text,Text>
{
    public void map(LongWritable key,Text value ,Context c) throws IOException,InterruptedException
    {
        String line = value.toString();
        String[] words = line.split("\t");
        
        c.write(new Text(words[0]),new Text(words[1]));

    }
}

