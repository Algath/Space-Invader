package ch.hevs.gdx2d.screen

import ch.hevs.gdx2d.components.screen_management.RenderingScreen
import ch.hevs.gdx2d.game.Handler
import ch.hevs.gdx2d.lib.{GdxGraphics, ScreenManager}
import ch.hevs.gdx2d.main.Main
import ch.hevs.gdx2d.main.Main
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.{Skin, TextButton}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.{InputEvent, Stage}

class Menu extends RenderingScreen {
  override def onInit(): Unit = {
   /*var stage = new Stage()
    Gdx.input.setInputProcessor(stage)

    var skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"))
    var singlePlayer: TextButton = new TextButton("Single Player", skin)
    singlePlayer.setWidth(180)
    singlePlayer.setHeight(30)
    singlePlayer.setPosition(Gdx.graphics.getWidth/2 - 180 / 2, (Gdx.graphics.getHeight * 0.6).toInt)

    stage.addActor(singlePlayer)

    singlePlayer.addListener(new ClickListener() {
      override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
        super.clicked(event, x, y)

        //if (singlePlayer.isChecked)
          //Start()
      }
    })*/

    Thread.sleep(5000)

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

//  def Start(): Unit = {
//    Handler.Init()
//    Handler.InitPlayer(1, false)
//    s.transitionTo(1, ScreenManager.TransactionType.SLICE)
//  }
//
//  def StartMultiplaying(): Unit = {
//    Handler.Init()
//    Handler.InitPlayer(2, false)
//    s.transitionTo(1, ScreenManager.TransactionType.SLICE)
//  }
//
//  def StartVersus(): Unit = {
//    Handler.Init()
//    Handler.InitPlayer(2, true)
//    s.transitionTo(2, ScreenManager.TransactionType.SLICE)
//  }

}
