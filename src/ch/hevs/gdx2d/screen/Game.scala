package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.desktop.Xbox
import ch.hevs.gdx2d.hello.{Enemy, Handler, Main, Projectile}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color

import java.awt.Point
import scala.util.Random

class Game extends RenderingScreen {
  override def onInit(): Unit = {



  }

  override def onGraphicRender(gdxGraphics: GdxGraphics): Unit = {

    gdxGraphics.drawFilledRectangle(1920/2, 1080/2, 1920, 1080, 0, Color.BLUE)
    gdxGraphics.drawStringCentered(1080 * 0.8f, "Playing")


    Handler.onGraphicRender(gdxGraphics)

    //Handler.projectile.append(new Projectile(1, new Point(Random.between(0, 1000), Random.between(0, 1000)), 10))


    if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) { // || Main.ctrl.getPov(Xbox.L_STICK_VERTICAL_AXIS) == PovDirection.north) {
      Handler.enemy.append(new Enemy(-1, 200, new Point(Random.between(1940, 1941), Random.between(10, 1070))))
    }

    if (Gdx.input.isKeyPressed(Input.Keys.P)) {
      Handler.projectile.append(new Projectile(-1, new Point(Random.between(800, 1800), Random.between(0, 1000)), 10))
    }




    gdxGraphics.drawFPS()

    //gdxGraphics.drawCircle(500, 500, 100, Color.GOLD)

  }

}
