import scala.collection.mutable.ArrayBuffer

class Algorithm {
  
  def generateStrings(length:Int):ArrayBuffer[String]={
    var strs:ArrayBuffer[String] = ArrayBuffer[String]()
    var strBuf = new StringBuilder("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    findStrWithCertainLength(strs,strBuf, 1,length)
    return strs
  }
  
  def findStrWithCertainLength(strs:ArrayBuffer[String],strBuf:StringBuilder,start:Int,length:Int):Unit = {
    if( start > length) return
    var ch:Char = '0'
    while(ch <= '9'){
      strBuf.setCharAt(start,ch)
      if( start == length )
        strs.append(strBuf.substring(1,length+1))
      findStrWithCertainLength(strs,strBuf,start+1, length)
      ch = (ch.toInt + 1).toChar
    }
  }
}