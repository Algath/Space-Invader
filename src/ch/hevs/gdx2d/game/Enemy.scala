package ch.hevs.gdx2d.game

import ch.hevs.gdx2d.ParticleSystem.ParticleManager
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.main.Main

import java.awt.{Point, Rectangle}
import scala.util.Random

class Enemy(ID: Int, _vie: Int, _position: Point) extends Object with Damage with PV {
  /*
  * ID: -1 = Soldat
  * ID: -2 = Mini-boss
  * ID: -3 = Boss
  * */

  override var velocity: Point = new Point(3, 3)
  override var position: Point = _position

  override var id: Int = ID

  override var pv: Int = _vie
  override var maxPV: Int = _vie

  var count = 0
  var xAdvance: Int = 0;
  var isVerticalDisplacement: Boolean = false
  var speed: Int = 0


  override def deplacement(): Unit = {
    var x = position.getX
    var y = position.getY

    if (ID != -3) {
      if (position.x > 1860) x -= velocity.x
      else {

        if(position.x > 960){

          if (position.y < 10 + getHitBox().height / 2) { // monte
            y = 10 + getHitBox().height / 2
            isVerticalDisplacement = false
            velocity.y *= -1
          }

          if (position.y > 1070 - getHitBox().height / 2) { // descend
            y = 1070 - getHitBox().height / 2
            isVerticalDisplacement = false
            velocity.y *= -1
          }

          if (!isVerticalDisplacement) { // début déplacement x
            xAdvance += 1
            x -= velocity.x
          }

          if (xAdvance > 50 || position.x < 960) { // avance de 50 clocks
            xAdvance = 0
            isVerticalDisplacement = true
          }

        }

        else{

          position.x = 960
          isVerticalDisplacement = true

          if (position.y < 10 + getHitBox().height / 2) { // monte
            y = 10 + getHitBox().height / 2
            velocity.y *= -1
          }

          if (position.y > 1070 - getHitBox().height / 2) { // descend
            y = 1070 - getHitBox().height / 2
            velocity.y *= -1
          }

        }
        

      }
    }
    if (ID == -3) {
      if (position.x > 1800) x -= velocity.x
      else {
        isVerticalDisplacement = true
        if (position.y < 10 + getHitBox().height / 2) {
          y = 10 + getHitBox().height / 2
          isVerticalDisplacement = false
          velocity.y *= -1
        }
        if (position.y > 1070 - getHitBox().height / 2) {
          y = 1070 - getHitBox().height / 2
          isVerticalDisplacement = false
          velocity.y *= -1
        }
      }
    }

    if (isVerticalDisplacement)
      position.setLocation(x, y + velocity.getY)
    else
      position.setLocation(x, y)
  }


  override def getHitBox(): Rectangle = {
    ID match {
      case -1 => new Rectangle(position.getX.toInt - 15, position.getY.toInt - 15, 45, 40)
      case -2 => new Rectangle(position.getX.toInt - 25, position.getY.toInt - 25, 75, 60)
      case _ => new Rectangle(position.getX.toInt - 50, position.getY.toInt - 50, 100, 110) // 120
    }
  }


  override def onGraphicRender(g: GdxGraphics): Unit = {
    /*
    * regarde quel est le projectile fournit et regarde le nombre de dégat qu'il fait
    * modification temps par rapport à l'ID plus tard
    * */

    ID match {
      case -1 => if (count > 50) {
        Handler.projectile.append(new Projectile(ID, position.clone.asInstanceOf[Point], getDamage))
        count = 0
      }
      case -2 => if (count > 40) {
        Handler.projectile.append(new Projectile(ID, position.clone.asInstanceOf[Point], getDamage))
        count = 0
      }
      case _ => if (count > 50) {
        Handler.projectile.append(new Projectile(ID, position.clone.asInstanceOf[Point], getDamage))
        count = 0
      }
    }

    count += Random.between(1, 5)

    deplacement()

    for (i: Int <- Handler.projectile.indices) {
      /*
      * compare l'ID du projectile (ID > 0)
      * compare si les hitbox se touchent (Handler.projectile(i).getHitBox.intersect(this.getHitBox))
      * si true, perte de point de vie
      * */

      try {
        if (Handler.projectile(i).id > 0) {
          if (Handler.projectile(i).getHitBox().intersects(this.getHitBox())) {
            pv -= Handler.projectile(i).damage
            ParticleManager.CreateParticles(Handler.projectile(i).position.clone.asInstanceOf[Point])
            Handler.removeProjectile(i)
            if (Main.boom.isPlaying())
              Main.boom.stop()
            Main.boom.play()
          }
        }
      }
      catch {
        case e: IndexOutOfBoundsException => {}
      }

    }

    speed += 1

    if (speed % 3600 == 0 && speed < 18000) {
      if (velocity.y > 0) {
        velocity = new Point(velocity.getX.toInt + 1, velocity.getY.toInt + 1)
      } else velocity = new Point(velocity.getX.toInt + 1, velocity.getY.toInt - 1)
    }

    if (ID == -3){
      g.drawTransformedPicture(position.getX.toInt, position.getY.toInt, 0, 1, Main.bossImg)
    } else if (ID == -2) {
      g.drawTransformedPicture(position.getX.toInt, position.getY.toInt, 0, 0.5f, Main.miniBossImg)
    } else g.drawTransformedPicture(position.getX.toInt, position.getY.toInt, 0, 0.5f, Main.soldatImg)

    if (Main.DEBUG)
      g.drawString(position.getX.toInt - 15, position.getY.toInt + 40, "PV : " + pv)

    /// Used in DEBUG Mode for debug hitbox
    if (Main.DEBUG)
      g.drawRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0)
      g.drawTransformedPicture(position.getX.toInt, position.getY.toInt, 0, 0.7f, Main.enemyDebugImg)
  }

  override def getDamage: Int = {
    ID match {
      case -1 => 10
      case -2 => 30
      case _ => 50
    }
  }

  override def setDamage(newDamage: Int): Unit = damage = 0

  override var damage: Int = 0


  def getPts(): Int = {
    ID match{
      case -1 => 10
      case -2 => 25
      case -3 => 50
    }
  }
}
