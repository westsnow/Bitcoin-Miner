
//client to server
import akka.actor.ActorRef

case class Register(client:ActorRef)
case class Result(str:String, coin:String, actor:ActorRef)

case object StopWork

//server to client
case object ShutDown
case object RegisterAck
case class Workload(preString : String, numOfZeros: Int)
