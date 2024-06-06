package ch.hevs.gdx2d.main

import ch.hevs.gdx2d.ParticleSystem.ParticleManager
import ch.hevs.gdx2d.ParticleSystem.ParticleManager.world
import ch.hevs.gdx2d.SaveSystem.SaveManager
import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.controller.ControllerHandler
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.{GdxGraphics, ScreenManager}
import ch.hevs.gdx2d.main.Main._
import ch.hevs.gdx2d.screen.{Commands, Game, MainMenu, VersusGame}
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.math.{Interpolation, Vector2}
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils._
import com.badlogic.gdx.{Gdx, Input}

/**
 * SOS Invader
 *
 * @author Pierre-André Mudry (mui)
 * @version 1.0
 */
object Main {

  var DEBUG: Boolean = false
  var playerBulletImg: BitmapImage = null
  var superBulletImg: BitmapImage = null
  var playerImg: BitmapImage = null
  var bossImg: BitmapImage = null
  var miniBossImg: BitmapImage = null
  var icepixel40: BitmapFont = null
  var icepixel120: BitmapFont = null
  var optimus150: BitmapFont = null
  var s: ScreenManager = new ScreenManager()
  var stage: Stage = null
  var skin: Skin = null

  var shaderTime:Float = 0

  def main(args: scala.Array[String]): Unit = {
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
    superBulletImg = new BitmapImage("data/images/Bullet_2.png")
    playerImg = new BitmapImage("data/images/Ship.png")
    bossImg = new BitmapImage("data/images/Boss.png")
    miniBossImg = new BitmapImage("data/images/mini-boss.png")
    stage = new Stage()
    skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"))

    /// Read Save for High Score
    SaveManager.ReadSave()
    SaveManager.WriteSave(10, 2000)

    s.registerScreen(classOf[MainMenu])
    s.registerScreen(classOf[Game])
    s.registerScreen(classOf[VersusGame])
    s.registerScreen(classOf[Commands])
    world = PhysicsWorld.getInstance()
    world.setGravity(new Vector2(0, -0.0f))

    s.registerScreen(classOf[MainMenu]) // 0
    s.registerScreen(classOf[Game]) // 1
    s.registerScreen(classOf[VersusGame]) // 2
    s.registerScreen(classOf[Commands]) // 3

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

    parameter.size = generator.scaleForPixelHeight(120)
    icepixel120 = generator.generateFont(parameter)
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
  var Inited:Boolean = false;

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

    if(!Inited){
      g.setShader("data/shaders/stars.fp")
      g.getShaderRenderer().setUniform("mouse", new Vector2(0, 10))
      Inited = true
    }


    // Draw everything
    //g.drawTransformedPicture(getWindowWidth / 2.0f, getWindowHeight / 2.0f, angle, 0.7f, imgBitmap)
    //g.drawStringCentered(getWindowHeight * 0.8f, "Welcome to gdx2d !")
    //g.drawFPS()
    //g.drawSchoolLogo()

    s.render(g)

    ParticleManager.UpdatePhysicParticle(g)

    if(Gdx.input.isKeyJustPressed(Input.Keys.F1) || (ControllerHandler.ControllerIsNotNull(ControllerHandler.PLAYERONE) && (ControllerHandler.isJustPressRTRIGGER(ControllerHandler.PLAYERONE)))) {
      if(Main.DEBUG) DEBUG = false
      else DEBUG = true
    }

    if(Gdx.input.isKeyJustPressed(Input.Keys.F12))
      s.transitionTo(0, ScreenManager.TransactionType.SLIDE)

    //    if(ctrl.getPov(Xbox.L_STICK_VERTICAL_AXIS) == PovDirection.west) {
    //      println("DOWN")
    //    }


    //println(ControllerHandler.controller(ControllerHandler.PLAYERONE).getAxis(Xbox.L_TRIGGER))

  }


  /*private def UpdatePhysicParticle(): Unit = {
    var  bodies:Array[Body] = Array[Body];
    world.getBodies(bodies);

    Iterator < Body > it = bodies.iterator();


    while (it.hasNext()) {
      Body p = it.next();

      if (p.getUserData() instanceof Particle) {
        Particle particle = (Particle) p
      .getUserData();
        particle.step();
        particle.render(g);

        if (particle.shouldbeDestroyed()) {
          particle.destroy();
        }
      }
    }

    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime());
  }*/


  /**
   * Compute time percentage for making a looping animation
   *
   * @return the current normalized time
   */
  private def computePercentage = {
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
