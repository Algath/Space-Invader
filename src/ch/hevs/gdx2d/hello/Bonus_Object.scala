package ch.hevs.gdx2d.hello
import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

import java.awt.{Point, Rectangle}

class Bonus_Object(ID: Int, _position : Point) extends Object {
  /*
  * ID: 3 = Récupération PV
  * ID: 4 = Augmentation du nombre de point max
  * ID: 5 = Augmentation des dégats
  * */

  override var position: Point = _position
  override var velocity: Point = new Point(3, 0)
  override var id: Int = ID

  override def deplacement(): Unit = {
    position.setLocation(position.getX - velocity.getX, position.getY)
  }

  override def getHitBox(): Rectangle = {
    id match {
      case 3 => new Rectangle(position.getX.toInt, position.getY.toInt, 20,20)
      case 4 => new Rectangle(position.getX.toInt, position.getY.toInt, 20,20)
      case _ => new Rectangle(position.getX.toInt, position.getY.toInt, 20,20)
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    deplacement()

    id match {
      case 3 => g.drawFilledRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0, Color.YELLOW)
      case 4 => g.drawFilledRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0, Color.ROYAL)
      case _ => g.drawFilledRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0, Color.GOLD)
    }

  }

}
