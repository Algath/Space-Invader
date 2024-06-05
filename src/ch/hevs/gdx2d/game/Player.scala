package ch.hevs.gdx2d.game

import ch.hevs.gdx2d.ParticleSystem.ParticleManager
import ch.hevs.gdx2d.controller.ControllerHandler
import ch.hevs.gdx2d.desktop.Xbox
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.main.Main
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.{Gdx, Input}

import java.awt.{Point, Rectangle}
import scala.util.Random

/**
 *
 * It's the Player class.
 * For create new Player, create instance of this and call onGraphicsRendering.
 *
 * @param ID
 * ID of the Player
 * @param _position
 * Spawn Position of the Player
 * @param _vie
 * [Deprectated] The PV of the Player in the Initalization
 * @param versusEnabled
 * Set if the Versus mode is enabled for the Player with ID 2
 */
class Player(ID: Int, _position: Point, _vie: Int, versusEnabled:Boolean = false) extends Object with Damage with PV {
  /*
  * ID: 1 = Player 1
  * ID: 2 = Player 2
  * */


  override var position: Point = _position
  override var velocity: Point = new Point(1, 10)

  override var id: Int = ID

  override var pv: Int = _vie
  override var maxPV: Int = _vie

  var nbreCanon: Int = 1

  override def deplacement(): Unit = {

    // Input Player One
    if(ID == 1){
      if (Gdx.input.isKeyPressed(Input.Keys.W) || (ControllerHandler.ControllerIsNotNull(ControllerHandler.PLAYERONE) && ControllerHandler.controller(ControllerHandler.PLAYERONE).getPov(Xbox.L_STICK_VERTICAL_AXIS) == PovDirection.north))
        position.setLocation(position.getX, position.getY + velocity.getY)
      else if (Gdx.input.isKeyPressed(Input.Keys.S) || (ControllerHandler.ControllerIsNotNull(ControllerHandler.PLAYERONE) && ControllerHandler.controller(ControllerHandler.PLAYERONE).getPov(Xbox.L_STICK_VERTICAL_AXIS) == PovDirection.south))
        position.setLocation(position.getX, position.getY - velocity.getY)
    }

    // Input Player Two
    if(ID == 2){
      if (Gdx.input.isKeyPressed(Input.Keys.UP) || (ControllerHandler.ControllerIsNotNull(ControllerHandler.PLAYERTWO) &&
            ControllerHandler.controller(ControllerHandler.PLAYERTWO).getPov(Xbox.L_STICK_VERTICAL_AXIS) == PovDirection.north))
        position.setLocation(position.getX, position.getY + velocity.getY)
      else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || (ControllerHandler.ControllerIsNotNull(ControllerHandler.PLAYERTWO) && ControllerHandler.controller(ControllerHandler.PLAYERTWO).getPov(Xbox.L_STICK_VERTICAL_AXIS) == PovDirection.south))
        position.setLocation(position.getX, position.getY - velocity.getY)
    }

    if (position.y < 55) position.y = 55
    if (position.y > 1025) position.y = 1025

  }

  /// Get the HitBox of Player
  override def getHitBox(): Rectangle = {
    new Rectangle(position.getX.toInt - 25, position.getY.toInt - 25, 50, 50)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {

    deplacement()

    // Fire Input Player One
    if(ID == 1){
      if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || (ControllerHandler.ControllerIsNotNull(ControllerHandler.PLAYERONE) && ControllerHandler.isJustPressA(ControllerHandler.PLAYERONE)) || (Gdx.input.isKeyPressed(Input.Keys.SPACE) && Main.DEBUG)) {
//        Handler.projectile.append(new Projectile(ID, position.clone().asInstanceOf[Point], getDamage, new Point(40, 0)))

        /// Multi-tire
        nbreCanon match {
          case 1 => Handler.projectile.append(new Projectile(ID, position.clone().asInstanceOf[Point], getDamage, new Point(40, 0)))
          case 2 =>
            Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y + 15), getDamage, new Point(40, 0)))
            Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y - 15), getDamage, new Point(40, 0)))
          case 3 =>
            Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y + 15), getDamage, new Point(40, 0)))
            Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y - 15), getDamage, new Point(40, 0)))
            Handler.projectile.append(new Projectile(ID, new Point(position.x - 20, position.y + 30), getDamage, new Point(40, 0)))
            Handler.projectile.append(new Projectile(ID, new Point(position.x - 20, position.y - 30), getDamage, new Point(40, 0)))
        }
      }
    }


    // Fire Input Player Two
    if(ID == 2){

      if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || (ControllerHandler.ControllerIsNotNull(ControllerHandler.PLAYERTWO) && ControllerHandler.isJustPressA(ControllerHandler.PLAYERTWO)) || (Gdx.input.isKeyPressed(Input.Keys.ENTER) && Main.DEBUG)) {

        if(versusEnabled){
          Handler.projectile.append(new Projectile(ID, position.clone().asInstanceOf[Point], getDamage, new Point(-40, 0), true))

          /// Multi-tire
          nbreCanon match {
            case 1 => Handler.projectile.append(new Projectile(ID, position.clone().asInstanceOf[Point], getDamage, new Point(-40, 0), true))
            case 2 => Handler.projectile.append(new Projectile(ID, new Point(position.x + 10, position.y + 15), getDamage, new Point(-40, 0), true))
              Handler.projectile.append(new Projectile(ID, new Point(position.x + 10, position.y - 15), getDamage, new Point(-40, 0), true))
            case 3 => Handler.projectile.append(new Projectile(ID, new Point(position.x + 10, position.y + 15), getDamage, new Point(-40, 0), true))
              Handler.projectile.append(new Projectile(ID, new Point(position.x + 10, position.y - 15), getDamage, new Point(-40, 0), true))
              Handler.projectile.append(new Projectile(ID, new Point(position.x + 20, position.y + 30), getDamage, new Point(-40, 0), true))
              Handler.projectile.append(new Projectile(ID, new Point(position.x + 20, position.y - 30), getDamage, new Point(-40, 0), true))
          }
        }
        else{
          Handler.projectile.append(new Projectile(ID, position.clone().asInstanceOf[Point], getDamage, new Point(40, 0)))

          /// Multi-tire
          nbreCanon match {
            case 1 => Handler.projectile.append(new Projectile(ID, position.clone().asInstanceOf[Point], getDamage, new Point(40, 0)))
            case 2 => Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y + 15), getDamage, new Point(40, 0)))
              Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y - 15), getDamage, new Point(40, 0)))
            case 3 => Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y + 15), getDamage, new Point(40, 0)))
              Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y - 15), getDamage, new Point(40, 0)))
              Handler.projectile.append(new Projectile(ID, new Point(position.x - 20, position.y + 30), getDamage, new Point(40, 0)))
              Handler.projectile.append(new Projectile(ID, new Point(position.x - 20, position.y - 30), getDamage, new Point(40, 0)))
            case _ => Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y + 15), getDamage, new Point(40, 0)))
              Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y - 15), getDamage, new Point(40, 0)))
              Handler.projectile.append(new Projectile(ID, new Point(position.x - 20, position.y + 30), getDamage, new Point(40, 0)))
              Handler.projectile.append(new Projectile(ID, new Point(position.x - 20, position.y - 30), getDamage, new Point(40, 0)))
          }
        }

      }
    }

    for (i: Int <- Handler.projectile.indices) {
      try {
        if (Handler.projectile(i).id < 0) {
          if (Handler.projectile(i).getHitBox().intersects(this.getHitBox())) {
            pv -= Handler.projectile(i).damage
            ParticleManager.CreateParticles(Handler.projectile(i).position.clone.asInstanceOf[Point])
            Handler.projectile.remove(i)
            if (pv <= 0)
              ParticleManager.CreateParticles(position.clone.asInstanceOf[Point], 50, 100, 3)
          }
        }

        else if (versusEnabled && Handler.projectile(i).id != ID) {
          if (Handler.projectile(i).getHitBox().intersects(this.getHitBox())) {
            pv -= Handler.projectile(i).damage / 4
            ParticleManager.CreateParticles(Handler.projectile(i).position.clone.asInstanceOf[Point])
            Handler.projectile.remove(i)
            if (pv <= 0)
              ParticleManager.CreateParticles(position.clone.asInstanceOf[Point], 50, 100, 3)
          }
        }

      }
      catch {
        case e: IndexOutOfBoundsException => {}
      }
    }

    for (i: Int <- Handler.bonusObject.indices) {
      try {
        if (Handler.bonusObject(i).id > 2) {
          if (Handler.bonusObject(i).getHitBox().intersects(this.getHitBox())) {
            Handler.bonusObject(i).id match {
              case 3 => {
                pv += Random.between(20, 201)
                if (pv > maxPV) pv = maxPV
                Handler.removeBonusObject(i)
              }
              case 4 => maxPV += 50
                Handler.removeBonusObject(i)
              case 5 => setDamage(damage * 2)
                Handler.removeBonusObject(i)
              case _ => nbreCanon += 1
                println(nbreCanon)
                if (nbreCanon > 3) nbreCanon = 3
                Handler.removeBonusObject(i)
            }
          }
        }
      }
      catch {
        case e: IndexOutOfBoundsException => {}
      }

    }

    if (pv < 0)
      pv = 0

    if (position.y < 55) position.y = 55
    if (position.y > 1025) position.y = 1025

    /// Used in DEBUG Mode for debug hitbox
    if(Main.DEBUG)
      g.drawRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0)

    /// Draw the player on the screen
    if(versusEnabled && ID == 2) {

      Main.playerImg.mirrorUpDown()
      g.drawTransformedPicture(position.getX.toInt, position.getY.toInt, 270, 1, Main.playerImg)
      Main.playerImg.mirrorUpDown()
    }
    else{
      g.drawTransformedPicture(position.getX.toInt, position.getY.toInt, 270, 1, Main.playerImg)
    }
  }

  override def getDamage: Int = {
    damage
  }

  def setDamage(newDamage: Int): Unit = {
    damage = newDamage
  }

  override var damage: Int = 10
}
