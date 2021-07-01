import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class ReduceForWordCount2 extends Reducer<IntWritable,Data,Text,IntWritable>
{
    public void reduce(IntWritable key, Iterable<Data> values , Context c) throws IOException , InterruptedException
    {
        int max = 0;
        Text word = new Text("");
        for(Data d : values)
        {
            if(d.getFrequency().get() > max)
            {
                max = d.getFrequency().get();
                word.set(d.getText());
            }
        }
        c.write(word,new IntWritable(max));

    }
}