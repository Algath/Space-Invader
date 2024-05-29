package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import org.lwjgl.util.WritableColor

import java.awt.geom.Point2D

class Menu extends RenderingScreen {
  override def onInit(): Unit = {

  }

  var time:Float = 0

  override def onGraphicRender(g: GdxGraphics): Unit = {

    //gdxGraphics.drawFilledRectangle(1920/2, 1080/2, 1920, 1080, 0, Color.CYAN)

    //g.drawShader(time);
    //time -= 0.01f

    g.drawStringCentered(1080 * 0.8f, "Main Menu")
    g.drawFPS()

    //gdxGraphics.drawCircle(500, 500, 100)

  }

}
