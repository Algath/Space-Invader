package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.ParticleSystem.ParticleManager
import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.game.{Bonus_Object, Enemy, Handler}
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.main.Main
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.{Gdx, Input}

import java.awt.Point
import scala.util.Random

/**
 *
 * The Screen of Game in One Player Mode and in Two Players Mode (not Versus Mode)
 *
 */
class Game extends RenderingScreen {
  var count: Int = 0
  var minute: Int = 0
  var sec: Double = 0.0
  var fondGameOver: BitmapImage = new BitmapImage("data/images/fond-game-over.png")

  override def onGraphicRender(g: GdxGraphics): Unit = {

    //gdxGraphics.drawFilledRectangle(1920/2, 1080/2, 1920, 1080, 0, Color.BLUE)

    //g.drawStringCentered(1080 * 0.8f, "Playing")

    g.drawShader(Main.shaderTime)
    Main.shaderTime += 0.01f

    Handler.onGraphicRender(g)

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


//    g.drawFilledRectangle(400, 1060, Handler.playerOne.maxPV, 25, 0, Color.GRAY)
//    g.drawFilledRectangle(400, 1060, Handler.playerOne.pv, 25, 0, Color.GREEN)
//    g.setColor(Color.BLACK)
//    g.drawString(375, 1065, "PV : " + Handler.playerOne.pv)
//    g.setColor(Color.WHITE)


    if (Random.between(1, 600) == 1) {
      Handler.bonusObject.append(new Bonus_Object(3, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    }
    if (Random.between(1, 100000) == 1) { // 10000
      Handler.bonusObject.append(new Bonus_Object(4, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    }
    if (Random.between(1, 1000000) == 1) { // 1000000
      Handler.bonusObject.append(new Bonus_Object(5, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    }

//    import sys.process._

    //    val cpuUsage = "for /f "tokens=3 delims=," %i in ('tasklist /fo csv /nh ^| findstr /c:"CPU"') do @echo %i" !
    //    val cpuUsagePercent = cpuUsage.split("%").head.toDouble / 100
    //    println(s"CPU usage: $cpuUsagePercent")


    // Calcule timer pour Debuggage
    sec += 1 / 60.0
    if (count % 3600 == 0) {
      minute += 1
      sec = 0
    }

    g.drawStringCentered(1080 - 25, "SCORE : " + Handler.pts, Main.icepixel40)

    /// Draw Player One Info
    g.drawString(150, 1080 - 25, "PLAYER ONE", Main.icepixel40, 1)

    g.drawFilledRectangle(400, 1080 - 80, (Handler.playerOne.maxPV * 200 / Handler.playerOne.maxPV), 20, 0, Color.GRAY)
    g.drawFilledRectangle(400 - 100 + (Handler.playerOne.pv * 200 / Handler.playerOne.maxPV) / 2, 1080 - 80 - 5, Handler.playerOne.pv * 200 / Handler.playerOne.maxPV, 10, 0, Color.FOREST)
    g.drawFilledRectangle(400 - 100 + (Handler.playerOne.pv * 200 / Handler.playerOne.maxPV) / 2, 1080 - 80 + 5, Handler.playerOne.pv * 200 / Handler.playerOne.maxPV, 10, 0, Color.GREEN)

    g.drawFilledRectangle(400, 1080 - 100, Handler.playerOne.maxPS * 200 / Handler.playerOne.maxPS, 10, 0, Color.GRAY)
    g.drawFilledRectangle(400 - 100 + (Handler.playerOne.ps * 200 / Handler.playerOne.maxPS) / 2, 1080 - 100 - 2.5f, Handler.playerOne.ps * 200 / Handler.playerOne.maxPS, 5, 0, Color.RED)
    g.drawFilledRectangle(400 - 100 + (Handler.playerOne.ps * 200 / Handler.playerOne.maxPS) / 2, 1080 - 100 + 2.5f, Handler.playerOne.ps * 200 / Handler.playerOne.maxPS, 5, 0, Color.SALMON)

    g.setColor(Color.BLACK)
    g.drawString(400, 1080 - 25, Handler.playerOne.pv + " / " + Handler.playerOne.maxPV, Main.icepixel40, 1)
    g.setColor(Color.WHITE)


    /// Draw Player Two Info
    if(Handler.playerTwo != null){
      g.drawString(1920 - 150, 1080 - 25, "PLAYER TWO", Main.icepixel40, 1)

      g.drawFilledRectangle(1920 - 400, 1080 - 80, (Handler.playerTwo.maxPV * 200 / Handler.playerTwo.maxPV), 20, 0, Color.GRAY)
      g.drawFilledRectangle(1920 - 500 + (Handler.playerTwo.pv * 200 / Handler.playerTwo.maxPV) / 2, 1080 - 80 - 5, Handler.playerTwo.pv * 200 / Handler.playerTwo.maxPV, 10, 0, Color.FOREST)
      g.drawFilledRectangle(1920 - 500 + (Handler.playerTwo.pv * 200 / Handler.playerTwo.maxPV) / 2, 1080 - 80 + 5, Handler.playerTwo.pv * 200 / Handler.playerTwo.maxPV, 10, 0, Color.GREEN)

      g.drawFilledRectangle(1920 - 400, 1080 - 100, Handler.playerTwo.maxPS * 200 / Handler.playerTwo.maxPS, 10, 0, Color.GRAY)
      g.drawFilledRectangle(1920 - 500 + (Handler.playerTwo.ps * 200 / Handler.playerTwo.maxPS) / 2, 1080 - 100 - 2.5f, Handler.playerTwo.ps * 200 / Handler.playerTwo.maxPS, 5, 0, Color.RED)
      g.drawFilledRectangle(1920 - 500 + (Handler.playerTwo.ps * 200 / Handler.playerTwo.maxPS) / 2, 1080 - 100 + 2.5f, Handler.playerTwo.ps * 200 / Handler.playerTwo.maxPS, 5, 0, Color.SALMON)

      g.setColor(Color.BLACK)
      g.drawString(1920 - 400, 1080 - 25, Handler.playerTwo.pv + " / " + Handler.playerTwo.maxPV, Main.icepixel40, 1)
      g.setColor(Color.WHITE)
    }


    /*
    * Gestion conditions du Game over + screen
    * */

    if (Handler.playerOne.pv == 0 && Handler.playerTwo == null || (Handler.playerTwo != null && Handler.playerOne.pv == 0 && Handler.playerTwo.pv == 0)) {
      // explosion + disparition du player
      g.drawAlphaPicture(1920/2, 1080/2, 0.7f, fondGameOver)
      g.drawStringCentered(1080 * 0.9f, "Game Over", Main.optimus150)
      g.drawStringCentered(1080 * 0.7f, "SCORE : " + Handler.pts, Main.icepixel40)
      g.drawStringCentered(1080 * 0.6f, "HIGH SCORE : ", Main.icepixel40)
      g.drawStringCentered(1080 * 0.35f, "Thank you for playing our game!", Main.icepixel40)
      g.drawStringCentered(1080 * 0.2f, "CREDITS : ", Main.icepixel40)
      g.drawStringCentered(1080*0.15f, "Joshua SIEDEL - Maroua Zanad, ISC2 2023-2024", Main.icepixel40)
      //g.drawStringCentered(1080*0.10f, "InfiniteRight ∞", Main.icepixel40)
    }


    if (Main.DEBUG) {
      g.drawFPS()
      g.drawString(1700, 1075, "number of object : " + (Handler.projectile.length + Handler.enemy.length + 1 + Handler.bonusObject.length))
      //      gdxGraphics.drawString(1700, 1060, "CPU usage: " + cpuUsagePercent)
      g.drawString(0, 30, "Timer: " + minute + ":" + sec.toInt)
      // instant death
      if (Gdx.input.isKeyJustPressed(Input.Keys.F4)) {
        Handler.playerOne.pv = 0
        if(Handler.playerTwo != null) Handler.playerTwo.pv = 0
      }
    }

  }

  override def onInit(): Unit = {}
}
