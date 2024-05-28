package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.hello.{Enemy, Handler, Projectile}
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.{Gdx, Input}

import java.awt.Point
import scala.util.Random

import com.badlogic.gdx.math.Vector2

class Game extends RenderingScreen {
  override def onInit(): Unit = {



  }

  var shaderTime:Float = 0

  override def onGraphicRender(gdxGraphics: GdxGraphics): Unit = {

    //gdxGraphics.drawFilledRectangle(1920/2, 1080/2, 1920, 1080, 0, Color.BLUE)

    gdxGraphics.drawShader(shaderTime);
    shaderTime += 0.01f

    gdxGraphics.drawStringCentered(1080 * 0.8f, "Playing")


    Handler.onGraphicRender(gdxGraphics)

    //Handler.projectile.append(new Projectile(1, new Point(Random.between(0, 1000), Random.between(0, 1000)), 10))


//    if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) { // || Main.ctrl.getPov(Xbox.L_STICK_VERTICAL_AXIS) == PovDirection.north) {
//      Handler.enemy.append(new Enemy(-1, 200, new Point(Random.between(1940, 1941), Random.between(10, 1070))))
//      Handler.enemy.append(new Enemy(-2, 500, new Point(Random.between(1940, 1941), Random.between(10, 1070))))
//      Handler.enemy.append(new Enemy(-3, 1000, new Point(Random.between(1940, 1941), Random.between(10, 1070))))
//    }

    if (Gdx.input.isKeyPressed(Input.Keys.P)) {
      Handler.projectile.append(new Projectile(-1, new Point(Random.between(800, 1800), Random.between(0, 1000)), 10))
    }

    gdxGraphics.drawFilledRectangle(400, 1060, Handler.player.maxPV, 25, 0, Color.GRAY)
    gdxGraphics.drawFilledRectangle(400, 1060, Handler.player.pv, 25,0,  Color.GREEN)


    gdxGraphics.drawFPS()

    //gdxGraphics.drawCircle(500, 500, 100, Color.GOLD)

  }

}
