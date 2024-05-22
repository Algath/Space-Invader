package ch.hevs.gdx2d.hello
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

import java.awt.{Point, Rectangle}

class Projectile(ID: Int, _position : Point, _damage: Int) extends Object {

  override var position: Point = _position

  var damage = _damage

  override def deplacement(): Unit = {
    if (ID < 0){
      position.setLocation(position.getX - velocity.getX, position.getY)
    }
    else position.setLocation(position.getX + velocity.getX, position.getY)
  }

  override def getHitBox(): Rectangle = {
    id match {
      case -1 => return new Rectangle(position.getX.toInt, position.getY.toInt, 30 , 30)
      case -2 => return new Rectangle(position.getX.toInt, position.getY.toInt, 50, 50)
      case _ => return new Rectangle(position.getX.toInt, position.getY.toInt, 100, 100)
    }
  }

  override var velocity: Point = new Point(10, 0)

  override def onGraphicRender(g: GdxGraphics): Unit = {
    deplacement()
    g.drawFilledRectangle(position.getX.toInt, position.getY.toInt, 20, 10, 0, Color.RED)

  }

  override var id: Int = ID
}
