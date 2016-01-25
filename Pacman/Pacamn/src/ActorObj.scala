import scala.actors._, Actor._

object ActorObj {

  private val GameMode =
    actor {
    
    val done = false
      while (!done) {
        receive {
          case 0 => Game.map.pause = true
          case 1 => Game.map.pause = false
          case 2 => Game.map.inGame = true
          case 3 => Game.map.inGame = false
          case 4 => Game.map.demo = true
          case 5 => Game.map.demo = false
          case 6 => Game.map.replay = true
          case 7 => Game.map.replay = false
        }
      }
    }

  def setPause(pause: Boolean) = {
    if(pause == true)
      GameMode ! 0
    else 
      GameMode ! 1
  }

  def setInGame(inGame: Boolean) = {
      if(inGame == true)
        GameMode ! 2
      else
        GameMode ! 3
  }

  def setDemo(demo: Boolean) = {
      if(demo == true)
        GameMode ! 4
      else
        GameMode ! 5
  }

  def setReplay(replay: Boolean) = {
      if(replay == true)
        GameMode ! 6
      else
        GameMode ! 7
  }
}