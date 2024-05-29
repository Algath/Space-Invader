package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.hello.{Bonus_Object, Enemy, Handler, Main}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

import java.awt.Point
import scala.util.Random

class Game extends RenderingScreen {
  var time: Float = 0
  var count: Int = 0

  override def onGraphicRender(gdxGraphics: GdxGraphics): Unit = {

    //gdxGraphics.drawFilledRectangle(1920/2, 1080/2, 1920, 1080, 0, Color.BLUE)

    gdxGraphics.drawShader(time);
    time += 0.01f

    gdxGraphics.drawStringCentered(1080 * 0.8f, "Playing")

    Handler.onGraphicRender(gdxGraphics)

    //Handler.projectile.append(new Projectile(1, new Point(Random.between(0, 1000), Random.between(0, 1000)), 10))

    count += 1
    // || Main.ctrl.getPov(Xbox.L_STICK_VERTICAL_AXIS) == PovDirection.north) {

    if (count % 300 == 0) {
      Handler.enemy.append(new Enemy(-1, 100, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    }
    if (count % 10800 == 0) {
      Handler.enemy.append(new Enemy(-2, 500, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    }
    if (count % 18000 == 0) {
      Handler.enemy.append(new Enemy(-3, 5000, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    }
    if (Random.between(1, 100000) == 1)
      Handler.enemy.append(new Enemy(-2, 500, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    if (Random.between(1, 10000000) == 1)
      Handler.enemy.append(new Enemy(-3, 5000, new Point(Random.between(1940, 1950), Random.between(55, 1025))))


    gdxGraphics.drawFilledRectangle(400, 1060, Handler.player.maxPV, 25, 0, Color.GRAY)
    gdxGraphics.drawFilledRectangle(400, 1060, Handler.player.pv, 25, 0, Color.GREEN)
    gdxGraphics.setColor(Color.BLACK)
    gdxGraphics.drawString(375, 1065, "PV : " + Handler.player.pv)
    gdxGraphics.setColor(Color.WHITE)


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

    if (Main.DEBUG) {
      gdxGraphics.drawFPS()
      gdxGraphics.drawString(1700, 1070, "number of object : " + (Handler.projectile.length + Handler.enemy.length + 1 + Handler.bonusObject.length))
      //      gdxGraphics.drawString(1700, 1060, "CPU usage: " + cpuUsagePercent)
    }

  }

  override def onInit(): Unit = {}
}
