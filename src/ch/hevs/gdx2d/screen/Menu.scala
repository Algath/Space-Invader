package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.main.Main

class Menu extends RenderingScreen {
  override def onInit(): Unit = {

  }

  var time:Float = 0

  override def onGraphicRender(g: GdxGraphics): Unit = {

    //gdxGraphics.drawFilledRectangle(1920/2, 1080/2, 1920, 1080, 0, Color.CYAN)

    //g.drawShader(time);
    //time -= 0.01f
    g.drawStringCentered(1080 * 0.9f, "SOS Invader") //rajouter font
    g.drawStringCentered(1080 * 0.8f, "Main Menu", Main.icepixel40)

    g.drawFPS()

    //gdxGraphics.drawCircle(500, 500, 100)

  }

}
