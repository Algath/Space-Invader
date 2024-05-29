package ch.hevs.gdx2d.game

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.main.Main
import com.badlogic.gdx.graphics.Color

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

        if (!isVerticalDisplacement) {
          xAdvance += 1
          x -= velocity.x
        }

        if (xAdvance > 50) {
          xAdvance = 0
          isVerticalDisplacement = true
        }
      }
    }
    if (ID == -3) {
      if (position.x > 1800) x += -velocity.x
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

    //if (y > 10) y += -1
    //else if (y == 10 && x != x - 10) x += -1
    //else if (y < 1070) y += 1
    //else if (y == 1070 && x != x - 10) x += -1

    if (isVerticalDisplacement)
      position.setLocation(x + velocity.getX, y + velocity.getY)
    else
      position.setLocation(x + velocity.getX, y)

  }


  override def getHitBox(): Rectangle = {
    ID match {
      case -1 => return new Rectangle(position.getX.toInt - 15, position.getY.toInt - 15, 30, 30)
      case -2 => return new Rectangle(position.getX.toInt - 25, position.getY.toInt - 25, 50, 50)
      case _ => return new Rectangle(position.getX.toInt - 50, position.getY.toInt - 50, 100, 100)
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
            Handler.removeProjectile(i)
          }
        }
      }
      catch {
        case e: IndexOutOfBoundsException => {}
      }

    }

    speed += 1
    println(position.x)

    if (speed % 60 == 0) {
      if (velocity.y > 0) {
        velocity = new Point(velocity.getX.toInt + 1, velocity.getY.toInt + 1)
      } else velocity = new Point(velocity.getX.toInt + 1, velocity.getY.toInt - 1)
    }

    g.drawFilledRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0, Color.ORANGE)

    if (Main.DEBUG)
      g.drawString(position.getX.toInt - 15, position.getY.toInt + 40, "PV : " + pv)
  }

  override def getDamage: Int = {
    ID match {
      case -1 => return 10
      case -2 => return 30
      case _ => return 50
    }
  }

  override def setDamage(newDamage: Int): Unit = damage = 0

  override var damage: Int = 0
}
