package greencover;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Greencover
 {

  public static class TokenizerMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable>
       {
			
      public void map(LongWritable key, IntWritable value, Context context)throws IOException, InterruptedException
        {
              StringTokenizer itr=new StringTokenizer(value.toString());
               int sum=0;
              while(itr.hasMoreTokens())
               {
                sum+=Integer.parseInt(itr.nextToken());
                }
                 context.write(new IntWritable(sum),new IntWritable(sum));
    }
   		}
      
  public static class IntSumReducer extends Reducer<IntWritable,IntWritable,IntWritable,IntWritable>
   {
    
    public void reduce(IntWritable key,Iterable<IntWritable> values, Context context)throws IOException, InterruptedException
        {
            int sum=0;
            for(IntWritable value:values)
                {
                    sum+=value.get();
                }
            context.write(key, new IntWritable(sum));
        }
  
   }   

  public static void main(String[] args) throws Exception 
  {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "GreenCover");
    job.setJarByClass(Greencover.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(IntWritable.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
   
  }
}
