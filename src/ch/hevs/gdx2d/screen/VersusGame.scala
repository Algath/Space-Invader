package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.ParticleSystem.ParticleManager
import ch.hevs.gdx2d.SaveSystem.SaveManager
import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.controller.ControllerHandler
import ch.hevs.gdx2d.game.{Bonus_Object, Enemy, Handler}
import ch.hevs.gdx2d.lib.{GdxGraphics, ScreenManager}
import ch.hevs.gdx2d.main.Main
import ch.hevs.gdx2d.main.Main.s
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color

import java.awt.Point
import scala.util.Random

/**
 *
 * The Screen of Versus Mode
 *
 */
class VersusGame  extends RenderingScreen {
  var count: Int = 0
  var minute: Int = 0
  var sec: Double = 0.0

  override def onGraphicRender(g: GdxGraphics): Unit = {

    Gdx.input.setInputProcessor(null)

    //gdxGraphics.drawFilledRectangle(1920/2, 1080/2, 1920, 1080, 0, Color.BLUE)

    g.drawShader(Main.shaderTime)
    Main.shaderTime += 0.01f

    //g.drawStringCentered(1080 * 0.8f, "Playing")

    Handler.onGraphicRender(g)
    //ParticleManager.UpdatePhysicParticle(g)

    //g.setColor(new Color(128, 128, 128, 0.5f))
    //g.drawFilledRectangle(1920 / 2, 1080 / 2, 1920, 1080, 0);

    /*if (Random.between(1, 600) == 1) {
      Handler.bonusObject.append(new Bonus_Object(3, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    }
    if (Random.between(1, 100000) == 1) { // 10000
      Handler.bonusObject.append(new Bonus_Object(4, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    }
    if (Random.between(1, 1000000) == 1) { // 1000000
      Handler.bonusObject.append(new Bonus_Object(5, new Point(Random.between(1940, 1950), Random.between(55, 1025))))
    }*/

    // Calcule timer pour Debuggage
    /*sec += 1 / 60.0
    if (count % 3600 == 0) {
      minute += 1
      sec = 0
    }*/

    g.drawStringCentered(1080 - 25, "\\VERSUS/", Main.icepixel40)

    /// Draw Player One info
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

    /// Draw Player Two info
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

    if (Handler.playerOne.isDeath() || Handler.playerTwo.isDeath()) {
      // explosion + disparition du player

      /// Check Score and HighScore
      if (Handler.playerTwo != null) {
        if (Handler.score > Handler.highScoreMulti)
          Handler.highScoreMulti = Handler.score
      } else if (Handler.score > Handler.highScore) Handler.highScore = Handler.score

      g.drawAlphaPicture(1920 / 2, 1080 / 2, 0.7f, Main.fondGameOver)

      if(Handler.playerOne.isDeath() && !Handler.playerTwo.isDeath()) {
        g.drawStringCentered(1080 * 0.9f, "WINNER", Main.optimus150)
        g.drawStringCentered(1080 * 0.7f, "Player 2", Main.optimus150)
      } else if(Handler.playerTwo.isDeath() && !Handler.playerOne.isDeath()) {
        g.drawStringCentered(1080 * 0.9f, "WINNER", Main.optimus150)
        g.drawStringCentered(1080 * 0.7f, "Player 1", Main.optimus150)
      } else {
        g.drawStringCentered(1080 * 0.8f, "404 error", Main.optimus150)
        g.drawStringCentered(1080 * 0.65f, "winner not found ?", Main.icepixel40)
      }

      g.drawStringCentered(1080 * 0.40f, "Thank you for playing our game!", Main.icepixel40)
      g.drawStringCentered(1080 * 0.35f, "CREDITS : ", Main.icepixel40)
      g.drawStringCentered(1080 * 0.30f, "Joshua SIEDEL - Maroua Zanad, ISC2 2023-2024", Main.icepixel40)
      g.drawStringCentered(1080 * 0.20f, "Click 'X' to go back to menu", Main.icepixel40)

      if (Gdx.input.isKeyJustPressed(Input.Keys.X) || isSTART_Input()) {
        s.transitionTo(0, ScreenManager.TransactionType.SLICE)
      }
    }


    /// Used in DEBUG Mode
    if (Main.DEBUG) {
      g.drawFPS()
      g.drawString(1700, 1075, "number of object : " + (Handler.projectile.length + Handler.enemy.length + 1 + Handler.bonusObject.length))
      //      gdxGraphics.drawString(1700, 1060, "CPU usage: " + cpuUsagePercent)
      g.drawString(0, 30, "Timer: " + minute + ":" + sec.toInt)
    }

  }

  def isSTART_Input(): Boolean = {

    if (ControllerHandler.isJustPressSTART(ControllerHandler.PLAYERTWO) || ControllerHandler.isJustPressSTART(ControllerHandler.PLAYERONE)) {
      return true
    }

    return false

  }

  override def onInit(): Unit = {}
}
