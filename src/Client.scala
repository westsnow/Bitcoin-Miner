
import akka.actor._

class Client(server: ActorSelection) extends Actor {
  
  var preStr:String = ""
  var numOfZeros:Int = 1
  val strMaxLength:Int = 9
  var strBuf = new StringBuilder("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  
  
  server ! Register(self)
  
  def startwork()= {
    println("calculating...")
    findStrsOfMaxLength(strMaxLength)
  }
  
  def findStrsOfMaxLength(l:Int) = {
    // each char from 'A' to 'z'
    var i = 1
    for( i <- 1 to l)
      //find all strings with length i
      findStrWithCertainLength(1,i)
   }
  
  
  
  
  def findStrWithCertainLength(start:Int,length:Int):Unit = {
    if( start > length) return
    var ch:Char = 'A'
    while(ch <= 'z'){
      //println(start)
      strBuf.setCharAt(start,ch)
      if( start == length )
        testString(length)
      findStrWithCertainLength(start+1, length)
      ch = (ch.toInt + 1).toChar
    }
  }
  
  def testString(length:Int) = {
    var shaStr = preStr + strBuf.substring(1,length+1)
    //s = preStr + strTmp.subStirng(0,length)
    var coin = SHA256.sha256(shaStr)
    var i = 0
    var testOk = true
    while(i < numOfZeros){
      if(coin.charAt(i) != '0' )
        testOk = false
      i += 1
    }
    if(testOk == true){
      //println(shaStr + "  " + coin)
      server ! Result(shaStr,coin,self)
    }
  }
  
  def receive={
    case Workload(str, zeros)=>
      this.preStr = str
      println("I'll calculate the coins start with prestr: "+str)
      this.numOfZeros = zeros
      startwork()
    case ShutDown=>
      println("server is down, calculation done")
      context stop self
    case s:String=>
      println("from server: " + s)

  }
}