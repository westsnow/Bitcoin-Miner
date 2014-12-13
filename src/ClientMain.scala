import akka.actor._
import com.typesafe.config.ConfigFactory

object ClientMain {

  def main(args: Array[String]): Unit = {
   
    
    var remoteIP = "127.0.0.1"
    if(args.length < 1){
      println("Error:you need to input the server IP to join the cluster")
    }else{
        remoteIP = args(0)
    }
    val clientSystem = ActorSystem("client", ConfigFactory.load(ConfigFactory.parseString("""
   akka {
    actor {
      provider = "akka.remote.RemoteActorRefProvider"
    }
    log-dead-letters = off
  }
  """)))

    //  val clientSystem = ActorSystem("client")
    //al remoteServer = clientSystem.actorFor("akka://server@XXX:5678/user/server")

    //akka.protocol://system@host:1234/user/my/actor/hierarchy/path
    val remotePath = "akka.tcp://serverSys@" + remoteIP +":11111/user/server"
    println(remotePath)

    val remoteServer = clientSystem.actorSelection(remotePath)

    println(remoteServer.pathString)
    val client1 = clientSystem.actorOf(Props(classOf[Client], remoteServer), name = "client1")
    val client2 = clientSystem.actorOf(Props(classOf[Client], remoteServer), name = "client2")
    val client3 = clientSystem.actorOf(Props(classOf[Client], remoteServer), name = "client3")
    val client4 = clientSystem.actorOf(Props(classOf[Client], remoteServer), name = "client4")
    val client5 = clientSystem.actorOf(Props(classOf[Client], remoteServer), name = "client5")


  }
}