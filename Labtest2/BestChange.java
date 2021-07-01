import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class BestChange
{
    public static void main(String[] args) throws Exception
    {
        Job j = Job.getInstance();
        j.setJarByClass(BestChange.class);
        j.setJobName("Best Change 1");
        j.setMapperClass(Map.class);
        j.setReducerClass(Reduce.class);
        j.setOutputKeyClass(Text.class);            //Map output
        j.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(j,new Path(args[0]));
        FileOutputFormat.setOutputPath(j,new Path(args[1]));
        j.waitForCompletion(true);    


        Job j2 = Job.getInstance();
        j2.setJarByClass(BestChange.class);
        j2.setJobName("Best Change 2");
        j2.setMapperClass(Map2.class);
        //j2.setMapOutputKeyClass(Text.class);
        //j2.setMapOutputValueClass(FloatWritable.class);
        
        j2.setReducerClass(Reduce2.class);
        j2.setOutputKeyClass(Text.class);
        j2.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(j2,new Path(args[1]));
        FileOutputFormat.setOutputPath(j2,new Path(args[2]));
        j2.waitForCompletion(true);
    }
}