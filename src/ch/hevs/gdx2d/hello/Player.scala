package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.{Gdx, Input}

import java.awt.{Point, Rectangle}
import scala.util.Random

class Player(ID: Int, _position : Point, _vie: Int) extends Object with Damage with PV {
  /*
  * ID: 1 = Player 1
  * ID: 2 = Player 2
  * */


  override var position: Point = _position
  override var velocity: Point = new Point(1, 10)

  override var id: Int = ID

  override var pv: Int = _vie
  override var maxPV: Int = _vie

  var count = 0


  override def deplacement(): Unit = {
    // décplacement clavier
    if (Gdx.input.isKeyPressed(Input.Keys.W)) position.setLocation(position.getX, position.getY + velocity.getY)
    else if (Gdx.input.isKeyPressed(Input.Keys.S)) position.setLocation(position.getX, position.getY - velocity.getY)

    //println(velocity.x)

    // déplacement manette stick
//    Xbox.L_STICK_VERTICAL_AXIS match {
//      case -1 => position.setLocation(position.getX + velocity.getX, position.getY)
//      case 1 => position.setLocation(position.getX - velocity.getX, position.getY)
//    }

    // déplacement manette D_Pad
  }

  override def getHitBox(): Rectangle = {
    new Rectangle(position.getX.toInt, position.getY.toInt, 50,50)
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    deplacement()

    if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
      Handler.projectile.append(new Projectile(ID, position.clone().asInstanceOf[Point], getDamage, new Point(40, 0)))
      count = 0
    }
    count += 1


    for (i: Int <- Handler.projectile.indices) {
      try{
        if (Handler.projectile(i).id < 0) {
          if (Handler.projectile(i).getHitBox().intersects(this.getHitBox())) {
            pv -= Handler.projectile(i).damage
            Handler.projectile.remove(i)
          }
        }
      }
      catch {
        case e:IndexOutOfBoundsException => {}
      }
    }

    for(i:Int <- Handler.bonusObject.indices){
      try{
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

    if(pv < 0)
      pv = 0


    g.drawFilledRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0, Color.GREEN)

  }

  override def getDamage: Int = {
    damage
  }

  def setDamage(newDamage: Int): Unit = {
    damage = newDamage
  }

  override var damage: Int = 10
}
