import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * bolt处理逻辑，再发送给SuffixBolt
 */
public class UpperBolt extends BaseBasicBolt{


	public void execute(Tuple tuple, BasicOutputCollector collector) {

		String godName = tuple.getString(0);

		//取到value，转成大写再发送出去
		String godName_upper = godName.toUpperCase();

		collector.emit(new Values(godName_upper));
		
	}

	

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
		declarer.declare(new Fields("uppername"));//声明再发送的字段名
	}

}
