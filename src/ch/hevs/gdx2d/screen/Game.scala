package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.hello.{Bonus_Object, Enemy, Handler}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.{Gdx, Input}

import java.awt.Point
import scala.util.Random

class Game extends RenderingScreen {
  override def onInit(): Unit = {



  }

  var time:Float = 0

  override def onGraphicRender(gdxGraphics: GdxGraphics): Unit = {

    //gdxGraphics.drawFilledRectangle(1920/2, 1080/2, 1920, 1080, 0, Color.BLUE)

    gdxGraphics.drawShader(time);
    time += 0.01f

    gdxGraphics.drawStringCentered(1080 * 0.8f, "Playing")

    gdxGraphics.drawString(1700, 1070, "number of object : " + (Handler.projectile.length + Handler.enemy.length + 1 + Handler.bonusObject.length))

    Handler.onGraphicRender(gdxGraphics)

    //Handler.projectile.append(new Projectile(1, new Point(Random.between(0, 1000), Random.between(0, 1000)), 10))


    if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) { // || Main.ctrl.getPov(Xbox.L_STICK_VERTICAL_AXIS) == PovDirection.north) {
      Handler.enemy.append(new Enemy(-1, 200, new Point(Random.between(1940, 1950), Random.between(10, 1070))))
      Handler.enemy.append(new Enemy(-2, 500, new Point(Random.between(1940, 1950), Random.between(10, 1070))))
      Handler.enemy.append(new Enemy(-3, 1000, new Point(Random.between(1940, 1950), Random.between(10, 1070))))
    }

    gdxGraphics.drawFilledRectangle(400, 1060, Handler.player.maxPV, 25, 0, Color.GRAY)
    gdxGraphics.drawFilledRectangle(400, 1060, Handler.player.pv, 25,0,  Color.GREEN)


    if (Random.between(1, 60) == 1) {
      Handler.bonusObject.append(new Bonus_Object(3, new Point(Random.between(1940, 1950), Random.between(10, 1070))))
    }
    if (Random.between(1, 10000) == 1){
      Handler.bonusObject.append(new Bonus_Object(4, new Point(Random.between(1940, 1950), Random.between(10, 1070))))
    }
    if (Random.between(1, 1000000) == 1){
      Handler.bonusObject.append(new Bonus_Object(5, new Point(Random.between(1940, 1950), Random.between(10, 1070))))
    }

    gdxGraphics.drawFPS()

    //gdxGraphics.drawCircle(500, 500, 100, Color.GOLD)

  }

}
