package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import org.lwjgl.util.WritableColor

import java.awt.geom.Point2D

class Menu extends RenderingScreen {
  override def onInit(): Unit = {

  }

  override def onGraphicRender(gdxGraphics: GdxGraphics): Unit = {

    gdxGraphics.drawFilledRectangle(1920/2, 1080/2, 1920, 1080, 0, Color.CYAN)

    gdxGraphics.drawStringCentered(1080 * 0.8f, "Main Menu")
    gdxGraphics.drawFPS()

    //gdxGraphics.drawCircle(500, 500, 100)

  }

}
