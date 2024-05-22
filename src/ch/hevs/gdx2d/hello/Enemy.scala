package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.lib.GdxGraphics

import java.awt.{Point, Rectangle}

class Enemy(ID: Int, _vie: Int, _position: Point) extends Object with Damage with PV {
  override var velocity: Point = new Point(0, 0)
  override var position: Point = _position

  override var id: Int = ID

  override var pv: Int = _vie
  override var maxPV: Int = _vie


  override def deplacement(): Unit = {
    var x = position.getX
    var y = position.getY

    if (x > 1910) x += -1
    if (y > 10) y += -1
    else if (y == 10 && x != x - 10) x += -1
    else if (y < 1070) y += 1
    else if (y == 1070 && x != x - 10) x += -1

    position.setLocation(x + velocity.getX, y + velocity.getY)
  }


  for (i: Int <- Handler.projectile.indices) {
    /*
    * compare l'ID du projectile (ID > 0)
    * compare si les hitbox se touchent (Handler.projectile(i).getHitBox.intersect(this.getHitBox))
    * si true, perte de point de vie
    * */
    if (Handler.projectile(i).id > 0) {
      if (Handler.projectile(i).getHitBox().intersects(this.getHitBox())) {
        pv -= Handler.projectile(i).damage
        // TODO
        Handler.projectile.remove(i)
      }
    }
  }

  override def getHitBox(): Rectangle = {
    ID match {
      case -1 => return new Rectangle(position.getX.toInt, position.getY.toInt, 30, 30)
      case -2 => return new Rectangle(position.getX.toInt, position.getY.toInt, 50, 50)
      case _ => return new Rectangle(position.getX.toInt, position.getY.toInt, 100, 100)
    }
  }


  override def onGraphicRender(g: GdxGraphics): Unit = {
    /*
    * regarde quel est le projectile fournit et regarde le nombre de dégat qu'il fait
    * modification temps par rapport à l'ID plus tard
    * */
    var count = 0
    if (count == 600) {
      Handler.projectile.append(new Projectile(ID, position, getDamage))
    }
    count += 1
  }

  override def getDamage: Int = {
    ID match {
      case -1 => return 10
      case -2 => return 30
      case _ => return 50
    }
  }
}
