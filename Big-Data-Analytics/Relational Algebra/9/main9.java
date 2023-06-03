import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class main9
{
    public static void main(String[] args) throws Exception
    {
        Job j = Job.getInstance();
        j.setJarByClass(main9.class);
        j.setJobName("Relational Algebra");
        j.setMapperClass(MapForRA.class);
        j.setReducerClass(ReduceForRA.class);
        j.setOutputKeyClass(Text.class);
        j.setOutputValueClass(IntWritable.class);
        j.setCombinerClass(Combiner.class);
        FileInputFormat.setInputPaths(j,new Path(args[0]));
        FileOutputFormat.setOutputPath(j,new Path(args[1]));
        j.waitForCompletion(true);      

        Job j2 = Job.getInstance();
        j2.setJarByClass(main9.class);
        j2.setJobName("Relational Algebra 2");
        j2.setMapperClass(Map2ForRA.class);
        j2.setMapOutputKeyClass(IntWritable.class);
        j2.setMapOutputValueClass(Data.class);
        
        j2.setReducerClass(Reduce2ForRA.class);
        j2.setOutputKeyClass(Text.class);
        j2.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(j2,new Path(args[1]));
        FileOutputFormat.setOutputPath(j2,new Path(args[2]));
        j2.waitForCompletion(true); 
    }
}