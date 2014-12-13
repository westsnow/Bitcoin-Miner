import akka.actor._
import com.typesafe.config.ConfigFactory
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration

object ServerMain {
  

  
  def main(args: Array[String]): Unit = {

    var sec = 30
    var numOfZeros = 5
    if(args.length<1)
      println("the default number of prefix zeros in coin is " + numOfZeros)
    else if(args.length <2){
      println("the default number of prefix zeros in coin is " + numOfZeros)
      println("the default running time is :" + sec + " seconds")
    }
    else{
      numOfZeros = args(0).toInt
      sec = args(1).toInt
      println("the  number of prefix zeros in coin is set to be " + numOfZeros)
      println("the  running time is set to be: " + sec + " seconds")

    }
      
    
    val serverSystem = ActorSystem("serverSys", ConfigFactory.load(ConfigFactory.parseString("""
  akka {
    actor {
      provider = "akka.remote.RemoteActorRefProvider"
    }
    remote {
      enabled-transports = ["akka.remote.netty.tcp"]
      transport = "akka.remote.netty.NettyRemoteTransport"
      netty.tcp {
        hostname = "127.0.0.1"
        port = 11111
      }
    }
    log-dead-letters = off
  }
  """)))

    import serverSystem.dispatcher
    
    
  
    val server = serverSystem.actorOf(Props(classOf[Server], "shuai.wu", numOfZeros, sec), name = "server")

    //to make most use of cup(4 cores) time,start 5 calculation client in each machine
    val innnerClient = serverSystem.actorOf(Props(classOf[InnerClient], server), name = "client")
    val innnerClient2 = serverSystem.actorOf(Props(classOf[InnerClient], server), name = "client2")
    val innnerClient3 = serverSystem.actorOf(Props(classOf[InnerClient], server), name = "client3")
    val innnerClient4 = serverSystem.actorOf(Props(classOf[InnerClient], server), name = "client4")
    val innnerClient5 = serverSystem.actorOf(Props(classOf[InnerClient], server), name = "client5")

    serverSystem.scheduler.scheduleOnce(Duration(sec, TimeUnit.SECONDS), server, StopWork)
    //println(serverSystem)

  }
}
//(Props(classOf[ChatClientActor], server, identity)