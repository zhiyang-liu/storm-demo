import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;

public class SuffixBolt extends BaseBasicBolt{
	
	FileWriter fileWriter = null;
	
	//只调一次
	@Override
	public void prepare(Map stormConf, TopologyContext context) {

		try {
			//每台机器上都会产生一个文件
			fileWriter = new FileWriter("/home/hadoop/stormresult/"+UUID.randomUUID());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	

	public void execute(Tuple tuple, BasicOutputCollector collector) {

		String upper_name = tuple.getString(0);
		String suffix_name = upper_name + "_itisok";



		try {
			fileWriter.write(suffix_name);
			fileWriter.write("\n");
			fileWriter.flush();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}



	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {

		
	}

}
