

import akka.actor._
import akka.routing.RoundRobinRouter
import scala.collection.mutable.ArrayBuffer

class Server(preFix: String, numOfZeros: Int, sec:Int) extends Actor {
 
  
    println("server started." + self.path + "  " + preFix)
    val maxPreLength = 4

    val algm = new Algorithm()
    val preStrs:ArrayBuffer[String] = algm.generateStrings(maxPreLength)
    
//    val preStrs = Array("000","001","002","003","004","005","006","007","008","009",
//        "010","011","012","013","014","015","016","017","018","019",
//        "020","021","022","023","024","025","026","027","028","029",
//    )
    
    val name = "shuai.wu"
    var clientNum = 0
    var resultNum = 0
    val start: Long = System.currentTimeMillis
 
    
    var clients = ArrayBuffer[ActorRef]()

    def stopAllClients(){
      for( i <- 0 until clients.length){
        clients(i) ! ShutDown
      }
    }
    
    
    def receive = {
      case Register(client)=>
        clients.append(client)
        clientNum += 1
        if( clientNum  > 30 )
          client ! "sorry, there are too many clients"
        else{
          client ! "your are the client No." + clientNum + " please start working"
          client ! Workload(name+preStrs((clientNum - 1)%preStrs.length),numOfZeros)
        }
        //context.stop(self)
      case Result(str,coin,actor)=>
        resultNum += 1
        println(str+"\t" +coin)
        
      case StopWork=>
        stopAllClients()
        println("=======================================================================")
        println("after calculation of " + sec +" seconds with " + clientNum +"  machines" +
            " I have found " + resultNum +" bit coins" +" starting with " + numOfZeros +" 0s")
        println("=======================================================================")
        //context.system.shutdown()
        context stop self
    }
}