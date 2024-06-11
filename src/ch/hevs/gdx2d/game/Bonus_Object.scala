package ch.hevs.gdx2d.game

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

import java.awt.{Point, Rectangle}

class Bonus_Object(ID: Int, _position : Point) extends Object {
  /*
  * ID: 3 = Récupération PV
  * ID: 4 = Augmentation du nombre de point max
  * ID: 5 = Augmentation des dégats
  * ID: 6 = +2 arme
  * */

  override var position: Point = _position
  override var velocity: Point = new Point(3, 0) // 3
  override var id: Int = ID

  override def deplacement(): Unit = {
    position.setLocation(position.getX - velocity.getX, position.getY)
  }

  override def getHitBox(): Rectangle = {
    id match {
      case 3 => new Rectangle(position.getX.toInt - 10, position.getY.toInt - 10, 20,20)
      case 4 => new Rectangle(position.getX.toInt - 10, position.getY.toInt - 10, 20,20)
      case 5 => new Rectangle(position.getX.toInt - 10, position.getY.toInt - 10, 20,20)
      case _ => new Rectangle(position.getX.toInt - 10, position.getY.toInt - 10, 20,20)
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    deplacement()

    id match {
      case 3 => g.drawFilledRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0, Color.YELLOW)
      case 4 => g.drawFilledRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0, Color.ROYAL)
      case 5 => g.drawFilledRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0, Color.WHITE)
      case _ => g.drawFilledRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0, Color.WHITE)
    }

  }

}
