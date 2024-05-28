package ch.hevs.gdx2d.hello

import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.math.Interpolation
import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.lib.{GdxGraphics, ScreenManager}
import ch.hevs.gdx2d.desktop.{PortableApplication, Xbox}
import ch.hevs.gdx2d.hello.Main.ctrl
import ch.hevs.gdx2d.screen.Game
import ch.hevs.gdx2d.screen.Menu
import com.badlogic.gdx.controllers.{Controller, Controllers, PovDirection}


/**
 * Hello World demo in Scala
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
object Main {

  var ctrl:Controller = null

  def main(args: Array[String]): Unit = {
    new Main
  }
}

class Main extends PortableApplication(1920, 1080) {
  private var imgBitmap: BitmapImage = null

  var s:ScreenManager = new ScreenManager()

  override def onInit(): Unit = {
    setTitle("Hello World - mui 2024")

    // Load a custom image (or from the lib "res/lib/icon64.png")
    imgBitmap = new BitmapImage("data/images/ISC_logo.png")

    s.registerScreen(classOf[Menu])
    s.registerScreen(classOf[Game])

    //if (Controllers.getControllers().size > 0)
      //ctrl = Controllers.getControllers().first();

  }

  /**
   * Some animation related variables
   */
  private var direction: Int = 1
  private var currentTime: Float = 0
  final private val ANIMATION_LENGTH: Float = 2f // Animation length (in seconds)
  final private val MIN_ANGLE: Float = -20
  final private val MAX_ANGLE: Float = 20

  /**
   * This method is called periodically by the engine
   *
   * @param g
   */
  override def onGraphicRender(g: GdxGraphics): Unit = {
    // Clears the screen
    g.clear()
    // Compute the angle of the image using an elastic interpolation
    val t = computePercentage
    val angle: Float = Interpolation.sine.apply(MIN_ANGLE, MAX_ANGLE, t)

    // Draw everything
    //g.drawTransformedPicture(getWindowWidth / 2.0f, getWindowHeight / 2.0f, angle, 0.7f, imgBitmap)
    //g.drawStringCentered(getWindowHeight * 0.8f, "Welcome to gdx2d !")
    //g.drawFPS()
    //g.drawSchoolLogo()

    s.render(g)

    if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
      s.transitionTo(1, ScreenManager.TransactionType.SLICE)
    }

    else if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
      s.transitionTo(0, ScreenManager.TransactionType.SLIDE)
    }

//    if(ctrl.getPov(Xbox.L_STICK_VERTICAL_AXIS) == PovDirection.west) {
//      println("DOWN")
//    }



  }

  /**
   * Compute time percentage for making a looping animation
   *
   * @return the current normalized time
   */
  private def computePercentage: Float = {
    if (direction == 1) {
      currentTime += Gdx.graphics.getDeltaTime
      if (currentTime > ANIMATION_LENGTH) {
        currentTime = ANIMATION_LENGTH
        direction *= -1
      }
    }
    else {
      currentTime -= Gdx.graphics.getDeltaTime
      if (currentTime < 0) {
        currentTime = 0
        direction *= -1
      }
    }
    currentTime / ANIMATION_LENGTH
  }
}
