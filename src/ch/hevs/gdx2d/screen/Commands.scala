package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.lib.{GdxGraphics, ScreenManager}
import ch.hevs.gdx2d.main.Main
import ch.hevs.gdx2d.main.Main.{s, skin, stage}
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class Commands extends RenderingScreen {
  var time: Float = 0
  var clavier: BitmapImage = new BitmapImage("data/images/clavier.png")
  var manette: BitmapImage = new BitmapImage("data/images/manette.png")

  var commandButtons:Stage = new Stage()

  override def onInit(): Unit = {
    Gdx.input.setInputProcessor(stage)
    val bWith: Int = 180
    val bHeight: Int = 30

    val back: TextButton = new TextButton("Back to menu", skin)
    back.setWidth(bWith)
    back.setHeight(bHeight)
    back.setPosition(1920 - bWith / 2, (1080 *0.9f).toInt)

    commandButtons.addActor(back)

    back.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        super.clicked(event, x, y)

        if (back.isChecked)
          Back()
      }
    })
  }

//  var back: MainMenu = new MainMenu

  override def onGraphicRender(g: GdxGraphics): Unit = {
    g.drawShader(time);
    time -= 0.01f

    commandButtons.act()
    commandButtons.draw()

    g.drawStringCentered(1080 * 0.9F, "COMMANDS CLAVIER/MANETTE", Main.icepixel120)

    g.drawPicture(1920*0.8f, 1080*0.8f, clavier)
    g.drawPicture(1920*0.2f, 1080*0.7f, manette)
  }
  def Back(): Unit = {
//    back.onInit()
    s.transitionTo(0, ScreenManager.TransactionType.SLICE)
  }
}
