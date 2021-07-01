import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;


public class main10
{
    public static void main(String[] args) throws Exception
    {
        Job j = Job.getInstance();
        j.setJarByClass(main10.class);
        j.setJobName("Relational Algebra");
        j.setMapperClass(MapForRA.class);
        j.setReducerClass(ReduceForRA.class);
        j.setOutputKeyClass(Text.class);
        j.setOutputValueClass(Text.class);
        FileInputFormat.setInputPaths(j,new Path(args[0]));
        FileOutputFormat.setOutputPath(j,new Path(args[1]));
        j.waitForCompletion(true);      

        Job j2 = Job.getInstance();
        j2.setJarByClass(main10.class);
        j2.setJobName("Relational Algebra 2");
        j2.setMapperClass(Map2ForRA.class);
        //j2.setMapOutputKeyClass(Text.class);
        //j2.setMapOutputValueClass(Text.class);
        j2.setReducerClass(Reduce2ForRA.class);
        j2.setOutputKeyClass(Text.class);
        j2.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(j2,new Path(args[1]));
        FileOutputFormat.setOutputPath(j2,new Path(args[2]));
        j2.waitForCompletion(true); 

        Job j3 = Job.getInstance();
        j3.setJarByClass(main10.class);
        j3.setJobName("Relational Algebra 3");
        j3.setMapperClass(Map3ForRA.class);
        j3.setMapOutputKeyClass(Text.class);
        j3.setMapOutputValueClass(Text.class);
        j3.setReducerClass(Reduce3ForRA.class);
        j3.setOutputKeyClass(Text.class);
        j3.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(j3,new Path(args[2]));
        FileOutputFormat.setOutputPath(j3,new Path(args[3]));
        j3.waitForCompletion(true); 
    }
}