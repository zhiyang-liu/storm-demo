import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

/**
 * spout组件，获取数据发消息。发到UpperBolt
 */
public class RandomWordSpout extends BaseRichSpout{

	private SpoutOutputCollector collector;

	String[] words = {"iphone","xiaomi","mate","sony","sumsung","moto","meizu"};

	/**
	 * 没有阻塞将一直调用
	 */
	public void nextTuple() {


		Random random = new Random();
		int index = random.nextInt(words.length);

		String godName = words[index];


		//封装成tuple，发送到下一个组件
		collector.emit(new Values(godName));

		Utils.sleep(500);

	}

	//初始化spout，只调一次
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {

		this.collector = collector;
		
		
	}

	//声明spout组件发出去的tuple中的数据字段名
	public void declareOutputFields(OutputFieldsDeclarer declarer) {

		declarer.declare(new Fields("orignname"));//可以写多个字段名，对应values
		
	}

}
