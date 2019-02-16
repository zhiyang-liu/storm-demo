import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

/**
 * topo，提交集群将一直在执行，除非终止；   类似mr的job
 */
public class TopoMain {

	
	public static void main(String[] args) throws Exception {

		/**
		 * 打包上传执行
		 */
		TopologyBuilder builder = new TopologyBuilder();
		

		//parallelism_hint
		//setNumTasks(8)
		//给spout起个id，并发度4，并发度指的是excutor数量
		builder.setSpout("randomspout", new RandomWordSpout(), 4);//.setNumTasks(8);//指定task数量，意味着每个线程执行两个任务


		//UpperBolt指定并发度4，指定接收randomspout，随机分组
		//shuffleGrouping两层含义，1、组件从哪里接收tuple，分组策略为随机分组
		builder.setBolt("upperbolt", new UpperBolt(), 4).shuffleGrouping("randomspout");

		//几个suffixbolt并发，即几个excutor，setNumTasks(8)几个task就有几个文件产生(8个文件)，不设置task就是默认的excutor数量，1个excutor1个task
		builder.setBolt("suffixbolt", new SuffixBolt(), 6).shuffleGrouping("upperbolt").setNumTasks(9);

		StormTopology demotop = builder.createTopology();
		

		Config conf = new Config();
		//worker数量
		conf.setNumWorkers(4);
		conf.setDebug(true);
		//acker ： Storm为了保证每条数据成功被处理,实现至少一次语义，通过Storm的ACK机制可以对spout产生的每一个tuple进行跟踪;
		conf.setNumAckers(0);

		StormSubmitter.submitTopology("demotopo", conf, demotop);
		
	}
}
