package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.hello.{Handler, Projectile}
import ch.hevs.gdx2d.lib.GdxGraphics
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

    Handler.projectile.append(new Projectile(1, new Point(Random.between(0, 1000), Random.between(0, 1000))))


    gdxGraphics.drawFPS()

    //gdxGraphics.drawCircle(500, 500, 100, Color.GOLD)

  }

}
