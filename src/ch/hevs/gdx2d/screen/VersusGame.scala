package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.game.{Bonus_Object, Enemy, Handler}
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.main.Main
import com.badlogic.gdx.graphics.Color

import java.awt.Point
import scala.util.Random

class VersusGame  extends RenderingScreen {
  var time: Float = 0
  var count: Int = 0
  var minute: Int = 0
  var sec: Double = 0.0

  override def onGraphicRender(g: GdxGraphics): Unit = {

    //gdxGraphics.drawFilledRectangle(1920/2, 1080/2, 1920, 1080, 0, Color.BLUE)

    g.drawShader(time);
    time += 0.01f

    //g.drawStringCentered(1080 * 0.8f, "Playing")

    Handler.onGraphicRender(g)


    //g.setColor(new Color(128, 128, 128, 0.5f))
    //g.drawFilledRectangle(1920 / 2, 1080 / 2, 1920, 1080, 0);

    if (Random.between(1, 600) == 1) {
      Handler.bonusObject.append(new Bonus_Object(3, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    }
    if (Random.between(1, 100000) == 1) { // 10000
      Handler.bonusObject.append(new Bonus_Object(4, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    }
    if (Random.between(1, 1000000) == 1) { // 1000000
      Handler.bonusObject.append(new Bonus_Object(5, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    }

    import sys.process._

    //    val cpuUsage = "for /f "tokens=3 delims=," %i in ('tasklist /fo csv /nh ^| findstr /c:"CPU"') do @echo %i" !
    //    val cpuUsagePercent = cpuUsage.split("%").head.toDouble / 100
    //    println(s"CPU usage: $cpuUsagePercent")


    // Calcule timer pour Debuggage
    sec += 1 / 60.0
    if (count % 3600 == 0) {
      minute += 1
      sec = 0
    }

    //g.drawStringCentered(1080 - 25, "SCORE : " + Handler.pts, Main.icepixel40)

    g.drawString(150, 1080 - 25, "PLAYER ONE", Main.icepixel40, 1)

    g.drawFilledRectangle(400, 1080 - 80, 200, 20, 0, Color.GRAY)
    g.drawFilledRectangle(400 - (Handler.playerOne.maxPV - Handler.playerOne.pv * 200 / Handler.playerOne.maxPV) / 2, 1080 - 80 - 5, Handler.playerOne.pv * 200 / Handler.playerOne.maxPV, 10, 0, Color.FOREST)
    g.drawFilledRectangle(400 - (Handler.playerOne.maxPV - Handler.playerOne.pv * 200 / Handler.playerOne.maxPV) / 2, 1080 - 80 + 5, Handler.playerOne.pv * 200 / Handler.playerOne.maxPV, 10, 0, Color.GREEN)

    g.setColor(Color.BLACK)
    g.drawString(400, 1080 - 25, Handler.playerOne.pv + " / " + Handler.playerOne.maxPV, Main.icepixel40, 1)
    g.setColor(Color.WHITE)

    g.drawString(1920 - 150, 1080 - 25, "PLAYER TWO", Main.icepixel40, 1)

    g.drawFilledRectangle(1920 - 400, 1080 - 80, 200, 20, 0, Color.GRAY)
    g.drawFilledRectangle(1920 - 400  - (Handler.playerTwo.maxPV - Handler.playerTwo.pv * 200 / Handler.playerTwo.maxPV) / 2, 1080 - 80 - 5, Handler.playerTwo.pv * 200 / Handler.playerTwo.maxPV, 10, 0, Color.FOREST)
    g.drawFilledRectangle(1920 - 400  - (Handler.playerTwo.maxPV - Handler.playerTwo.pv * 200 / Handler.playerTwo.maxPV) / 2, 1080 - 80 + 5, Handler.playerTwo.pv * 200 / Handler.playerTwo.maxPV, 10, 0, Color.GREEN)

    g.setColor(Color.BLACK)
    g.drawString(1920 - 400, 1080 - 25, Handler.playerTwo.pv + " / " + Handler.playerTwo.maxPV, Main.icepixel40, 1)
    g.setColor(Color.WHITE)


    if (Main.DEBUG) {
      g.drawFPS()
      g.drawString(1700, 1070, "number of object : " + (Handler.projectile.length + Handler.enemy.length + 1 + Handler.bonusObject.length))
      //      gdxGraphics.drawString(1700, 1060, "CPU usage: " + cpuUsagePercent)
      g.drawString(0, 30, "Timer: " + minute + ":" + sec.toInt)
    }

  }

  override def onInit(): Unit = {}
}