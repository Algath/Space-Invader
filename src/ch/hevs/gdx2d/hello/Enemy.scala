package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.lib.GdxGraphics

import java.awt.{Point, Rectangle}

/**
 * Moi
 * */
class Enemy(ID: Int, vie: Int, _position: Point) extends Object with Damage {
  override var velocity: Point = new Point(0, 0)

  override def deplacement(): Unit = {
    var x = position.getX
    var y = position.getY

    if (x > 1920) x += -1
    if (y > 10) y += -1
    else if (y == 10 && x != x - 10) x += -1
    else if (y < 1070) y += 1
    else if (y == 1070 && x != x - 10) x += -1

    position.setLocation(x + velocity.getX, y + velocity.getY)
  }

  override var position: Point = _position


  for (i: Int <- Handler.projectile.indices) {
    /*
    * compare l'ID du projectile (ID > 0)
    * compare si les hitbox se touchent (Handler.projectile(i).getHitBox.intersect(this.getHitBox))
    * si true, perte de point de vie
    * */
    if (Handler.projectile(i) == null) {

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
    * */
  }

  override var id: Int = ID

  override def getDamage: Int = {
    ID match {
      case -1 => 10
      case -2 => 30
      case _ => 50
    }
  }
}
