package ch.hevs.gdx2d.main

import ch.hevs.gdx2d.ParticleSystem.ParticleManager
import ch.hevs.gdx2d.ParticleSystem.ParticleManager.world
import ch.hevs.gdx2d.components.audio.MusicPlayer
import ch.hevs.gdx2d.components.bitmaps.BitmapImage
import ch.hevs.gdx2d.controller.ControllerHandler
import ch.hevs.gdx2d.desktop.PortableApplication
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import ch.hevs.gdx2d.lib.{GdxGraphics, ScreenManager}
import ch.hevs.gdx2d.main.Main._
import ch.hevs.gdx2d.screen.{Commands, Game, MainMenu, VersusGame}
//import com.badlogic.gdx.Graphics.DisplayMode
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Pixmap.{Blending, Format}
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.{Color, Pixmap}
import com.badlogic.gdx.math.{Interpolation, Vector2}
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils._
import com.badlogic.gdx.{Files, Gdx, Input}
import org.lwjgl.opengl.Display

import java.nio.ByteBuffer

/**
 * SOS Invader
 * / InfiniteRight ∞ SIEDEL™
 * @author ZANAD Maroua
 * @author SIEDEL Joshua
 * @version 1.0
 */
object Main {

  var DEBUG: Boolean = false

  var bannerImg: BitmapImage = null
  var playerBulletImg: BitmapImage = null
  var soldatBulletImg: BitmapImage = null
  var bossBulletImg: BitmapImage = null
  var superBulletImg: BitmapImage = null
  var playerImg: BitmapImage = null
  var bossImg: BitmapImage = null
  var miniBossImg: BitmapImage = null
  var fondGameOver: BitmapImage = null
  var soldatImg: BitmapImage = null
  var recupImg: BitmapImage = null
  var augmentImg: BitmapImage = null
  var degatImg: BitmapImage = null
  var armeImg: BitmapImage = null
  var playerDebugImg: BitmapImage = null
  var enemyDebugImg: BitmapImage = null
  var projectileDebugImg: BitmapImage = null
  var ISC_logo: BitmapImage = null

  var icepixel40: BitmapFont = null
  var icepixel120: BitmapFont = null
  var optimus150: BitmapFont = null

  var boom:MusicPlayer = null
  var music:MusicPlayer = null

  var s: ScreenManager = new ScreenManager()
  var skin: Skin = null

  var shaderTime:Float = 0

  def main(args: scala.Array[String]): Unit = {
    new Main
  }
}

class Main extends PortableApplication(1920, 1080) {

  override def onInit(): Unit = {
    setTitle("SOS INVADERS - ERROR 404")
    setIcons(scala.Array("res/icon16x16.png", "res/icon64x64.png"))

    // Load a custom image (or from the lib "res/lib/icon64.png")
    ISC_logo = new BitmapImage("data/images/ISC_logo_8bit.png")
    bannerImg = new BitmapImage("data/images/Banner.png")
    playerBulletImg = new BitmapImage("data/images/Bullet.png")
    superBulletImg = new BitmapImage("data/images/Bullet_2.png")
    soldatBulletImg = new BitmapImage("data/images/Bullet_3.png")
    bossBulletImg = new BitmapImage("data/images/Bullet_4.png")
    playerImg = new BitmapImage("data/images/Ship.png")
    bossImg = new BitmapImage("data/images/Boss.png")
    miniBossImg = new BitmapImage("data/images/mini-boss.png")
    fondGameOver = new BitmapImage("data/images/fond-game-over.png")
    miniBossImg = new BitmapImage("data/images/mini-boss-v3.png")
    soldatImg = new BitmapImage("data/images/soldat-v2.png")
    recupImg = new BitmapImage("data/images/recup_pv-v2.png")
    augmentImg = new BitmapImage("data/images/augm_pv.png")
    degatImg = new BitmapImage("data/images/degat_plus.png")
    armeImg = new BitmapImage("data/images/arme_plus.png")
    playerDebugImg = new BitmapImage("data/images/Player-Debug.png")
    enemyDebugImg = new BitmapImage("data/images/Ennemie-Debug.png")
    projectileDebugImg = new BitmapImage("data/images/Projectile_Debug.png")

    skin = new Skin(Gdx.files.internal("data/ui/uiskin.json"))

    boom = new MusicPlayer("data/sounds/boom.wav")
    music = new MusicPlayer("data/music/SOS Invaders.wav")

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

    //music.loop()

    s.render(g)

    ParticleManager.UpdatePhysicParticle(g)

    if(Gdx.input.isKeyJustPressed(Input.Keys.F1) || ControllerHandler.isJustPressRTRIGGER(ControllerHandler.PLAYERONE)) {
      if(Main.DEBUG) DEBUG = false
      else DEBUG = true
    }

    if(Gdx.input.isKeyJustPressed(Input.Keys.F12))
      s.transitionTo(0, ScreenManager.TransactionType.SLIDE)

  }

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

  def setIcons(paths: scala.Array[String]): Unit = {
    val icons: scala.Array[ByteBuffer] = new scala.Array(paths.length)
    for ((path: String, i: Int) <- paths.zipWithIndex) {
      var pixmap: Pixmap = new Pixmap(Gdx.files.getFileHandle(path, Files.FileType.Internal))
      if (pixmap.getFormat != Format.RGBA8888) {
        val rgba: Pixmap = new Pixmap(pixmap.getWidth, pixmap.getHeight, Format.RGBA8888)
        rgba.setBlending(Blending.None)
        rgba.drawPixmap(pixmap, 0, 0)
        pixmap.dispose()
        pixmap = rgba
      }
      icons(i) = ByteBuffer.allocateDirect(pixmap.getPixels.limit())
      icons(i).put(pixmap.getPixels).flip()
      pixmap.dispose()
    }
    Display.setIcon(icons)
  }

}