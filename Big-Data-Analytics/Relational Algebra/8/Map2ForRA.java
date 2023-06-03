import java.io.IOException;
import java.io.DataOutput;
import java.io.DataInput;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;

class Data implements Writable
{
    Text t;
    IntWritable i;
    public Data()
    {
        t = new Text("");
        i = new IntWritable(0);
    }

    public Data(Text t1 ,IntWritable i1)
    {
        t = t1;
        i = i1;
    }

    public Text getText()
    {
        return(t);
    }

    public IntWritable getFrequency()
    {
        return (i);
    }

    public void write(DataOutput d) throws IOException
    {
        t.write(d);
        i.write(d);
    }

    public void readFields(DataInput d) throws IOException
    {
        t.readFields(d);
        i.readFields(d);
    }

    public String toString()        //overwriting just for debugging , else default hash values will come
    {                       
        return (t.toString() + ":" + i.toString());
    }

}

public class Map2ForRA extends Mapper<LongWritable,Text,IntWritable,Data>
{
    public void map(LongWritable key,Text value ,Context c) throws IOException,InterruptedException
    {
            String line = value.toString();
            String[] words = line.split("\t");
            c.write(new IntWritable(1) ,new Data(new Text(words[0]),new IntWritable(Integer.parseInt(words[1]))));
    }
}

