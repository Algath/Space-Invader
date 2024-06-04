package ch.hevs.gdx2d.game

import ch.hevs.gdx2d.controller.ControllerHandler
import ch.hevs.gdx2d.desktop.Xbox
import ch.hevs.gdx2d.game.{Handler, Object}
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.main.{Main}
import com.badlogic.gdx.controllers.PovDirection
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.{Gdx, Input}

import java.awt.{Point, Rectangle}
import scala.util.Random

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


    //println(velocity.x)

    // déplacement manette stick
    //    Xbox.L_STICK_VERTICAL_AXIS match {
    //      case -1 => position.setLocation(position.getX + velocity.getX, position.getY)
    //      case 1 => position.setLocation(position.getX - velocity.getX, position.getY)
    //    }

    // déplacement manette D_Pad
  }

  override def getHitBox(): Rectangle = {
    new Rectangle(position.getX.toInt - 25, position.getY.toInt - 25, 50, 50)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    deplacement()

    // Fire Input Player One
    if(ID == 1){
      if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || (ControllerHandler.ControllerIsNotNull(ControllerHandler.PLAYERONE) && ControllerHandler.isJustPressA(ControllerHandler.PLAYERONE)) || (Gdx.input.isKeyPressed(Input.Keys.SPACE) && Main.DEBUG)) {
        Handler.projectile.append(new Projectile(ID, position.clone().asInstanceOf[Point], getDamage, new Point(40, 0)))

        /// Multi-tire
        Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y + 15), getDamage, new Point(40, 0)))
        Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y - 15), getDamage, new Point(40, 0)))
        Handler.projectile.append(new Projectile(ID, new Point(position.x - 20, position.y + 30), getDamage, new Point(40, 0)))
        Handler.projectile.append(new Projectile(ID, new Point(position.x - 20, position.y - 30), getDamage, new Point(40, 0)))

      }
    }


    // Fire Input Player Two
    if(ID == 2){
      if (Gdx.input.isKeyJustPressed(Input.Keys.ALT_RIGHT) || (ControllerHandler.ControllerIsNotNull(ControllerHandler.PLAYERTWO) && ControllerHandler.isJustPressA(ControllerHandler.PLAYERTWO)) || (Gdx.input.isKeyPressed(Input.Keys.ALT_RIGHT) && Main.DEBUG)) {
        Handler.projectile.append(new Projectile(ID, position.clone().asInstanceOf[Point], getDamage, new Point(40, 0)))

        /// Multi-tire
        Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y + 15), getDamage, new Point(40, 0)))
        Handler.projectile.append(new Projectile(ID, new Point(position.x - 10, position.y - 15), getDamage, new Point(40, 0)))
        Handler.projectile.append(new Projectile(ID, new Point(position.x - 20, position.y + 30), getDamage, new Point(40, 0)))
        Handler.projectile.append(new Projectile(ID, new Point(position.x - 20, position.y - 30), getDamage, new Point(40, 0)))

      }
    }



    for (i: Int <- Handler.projectile.indices) {
      try {
        if (Handler.projectile(i).id < 0) {
          if (Handler.projectile(i).getHitBox().intersects(this.getHitBox())) {
            pv -= Handler.projectile(i).damage
            Handler.projectile.remove(i)
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
              case _ => setDamage(damage * 2)
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


    if(Main.DEBUG)
      g.drawRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0)

    g.drawTransformedPicture(position.getX.toInt, position.getY.toInt, 270, 3, Main.playerImg)

  }

  override def getDamage: Int = {
    damage
  }

  def setDamage(newDamage: Int): Unit = {
    damage = newDamage
  }

  override var damage: Int = 10
}
