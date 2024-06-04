package ch.hevs.gdx2d.main

import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.controller.ControllerHandler
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.{GdxGraphics, ScreenManager}
import ch.hevs.gdx2d.main.Main._
import ch.hevs.gdx2d.screen.{Game, Menu, VersusGame}
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.math.{Interpolation, Vector2}
import com.badlogic.gdx.{Gdx, Input}


/**
 * Hello World demo in Scala
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
object Main {

  var DEBUG: Boolean = false
  var playerBulletImg: BitmapImage = null
  var playerImg: BitmapImage = null
  var icepixel40: BitmapFont = null
  var optimus150: BitmapFont = null
  var s: ScreenManager = new ScreenManager()

  def main(args: Array[String]): Unit = {
    new Main
  }
}

class Main extends PortableApplication(1920, 1080) {
  private var imgBitmap: BitmapImage = null

  override def onInit(): Unit = {
    setTitle("Hello World - mui 2024")

    // Load a custom image (or from the lib "res/lib/icon64.png")
    imgBitmap = new BitmapImage("data/images/ISC_logo.png")
    playerBulletImg = new BitmapImage("data/images/Bullet.png")
    playerImg = new BitmapImage("data/images/Ship.png")

    s.registerScreen(classOf[Menu])
    s.registerScreen(classOf[Game])
    s.registerScreen(classOf[VersusGame])

    s.activateScreen(0)

    val icePixelF: FileHandle = Gdx.files.internal("data/fonts/ice_pixel-7.ttf")
    val optimusF: FileHandle = Gdx.files.internal("data/fonts/OptimusPrinceps.ttf")

    var generator = new FreeTypeFontGenerator(icePixelF)
    val parameter = new FreeTypeFontGenerator.FreeTypeFontParameter
    parameter.size = generator.scaleForPixelHeight(40)
    parameter.color = Color.WHITE
    parameter.borderColor = Color.BLUE
    parameter.borderWidth = 3
    parameter.shadowOffsetY = -8
    parameter.shadowColor = Color.LIGHT_GRAY
    icepixel40 = generator.generateFont(parameter)
    generator.dispose()

    generator = new FreeTypeFontGenerator(optimusF)
    parameter.size = generator.scaleForPixelHeight(150)
    parameter.color = Color.RED
    parameter.borderColor = Color.BROWN
    optimus150 = generator.generateFont(parameter)
    generator.dispose()

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

    if(Gdx.input.isKeyJustPressed(Input.Keys.F1) || (ControllerHandler.ControllerIsNotNull(ControllerHandler.PLAYERONE) && (ControllerHandler.isJustPressRTRIGGER(ControllerHandler.PLAYERONE)))) {
      if(Main.DEBUG) DEBUG = false
      else DEBUG = true
    }

    //g.setShader("data/shaders/stars.fp")
    //g.getShaderRenderer().setUniform("mouse", new Vector2(0, 10))

    //    if(ctrl.getPov(Xbox.L_STICK_VERTICAL_AXIS) == PovDirection.west) {
    //      println("DOWN")
    //    }


    //println(ControllerHandler.controller(ControllerHandler.PLAYERONE).getAxis(Xbox.L_TRIGGER))

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
