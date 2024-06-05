package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.game.Handler
import ch.hevs.gdx2d.lib.{GdxGraphics, ScreenManager}
import ch.hevs.gdx2d.main.Main
import ch.hevs.gdx2d.main.Main._
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener


/**
 *
 * The Screen of Main Menu
 *
 */
class MainMenu extends RenderingScreen {

  override def onInit(): Unit = {
    Gdx.input.setInputProcessor(stage)
    var bWith: Int = 180
    var bHeight: Int = 30

    val singlePlayer: TextButton = new TextButton("Single Player", skin)
    singlePlayer.setWidth(bWith)
    singlePlayer.setHeight(bHeight)
    singlePlayer.setPosition(1920/2 - bWith / 2, (1080 * 0.6).toInt)

    stage.addActor(singlePlayer)

    singlePlayer.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        super.clicked(event, x, y)

        if (singlePlayer.isChecked)
          Start()
      }
    })

    val multiPlayer: TextButton = new TextButton("Multiplayer", skin)
    multiPlayer.setWidth(bWith)
    multiPlayer.setHeight(bHeight)
    multiPlayer.setPosition(1920/2 - bWith / 2, (1080 * 0.5).toInt)

    stage.addActor(multiPlayer)

    multiPlayer.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        super.clicked(event, x, y)

        if (multiPlayer.isChecked)
          StartMultiplaying()
      }
    })

    val versusPlayer: TextButton = new TextButton("1 VS 1", skin)
    versusPlayer.setWidth(bWith)
    versusPlayer.setHeight(bHeight)
    versusPlayer.setPosition(1920/2 - bWith / 2, (1080 * 0.4).toInt)

    stage.addActor(versusPlayer)

    versusPlayer.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        super.clicked(event, x, y)

        if (versusPlayer.isChecked)
          StartVersus()
      }
    })
  }

  var time:Float = 0

  override def onGraphicRender(g: GdxGraphics): Unit = {

    g.drawShader(time);
    time += 0.01f

    //gdxGraphics.drawFilledRectangle(1920/2, 1080/2, 1920, 1080, 0, Color.CYAN)
    stage.act()
    stage.draw()
    //g.drawShader(time);
    //time -= 0.01f
    g.drawStringCentered(1080 * 0.9f, "SOS Invader") //rajouter font
    g.drawStringCentered(1080 * 0.8f, "Main Menu", Main.icepixel40)
    g.drawFPS()

    //gdxGraphics.drawCircle(500, 500, 100)

  }

  def Start(): Unit = {
    Handler.Init()
    Handler.InitPlayer(1, false)
    s.transitionTo(1, ScreenManager.TransactionType.SLICE)
  }

  def StartMultiplaying(): Unit = {
    Handler.Init()
    Handler.InitPlayer(2, false)
    s.transitionTo(1, ScreenManager.TransactionType.SLICE)
  }

  def StartVersus(): Unit = {
    Handler.Init()
    Handler.InitPlayer(2, true)
    s.transitionTo(2, ScreenManager.TransactionType.SLICE)
  }

}
