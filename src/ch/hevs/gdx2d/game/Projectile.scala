package ch.hevs.gdx2d.game

import ch.hevs.gdx2d.game.Object
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.main.Main
import com.badlogic.gdx.graphics.Color

import java.awt.{Point, Rectangle}

class Projectile(ID: Int, _position : Point, _damage: Int, _velocity:Point = new Point(10, 0), versusEnabled:Boolean = false) extends Object {

  override var position: Point = _position
  override var id: Int = ID
  override var velocity: Point = _velocity

  var damage = _damage

  override def deplacement(): Unit = {

    // Si le projectile provient d'un ennemie
    if (ID < 0){
      position.setLocation(position.getX - velocity.getX, position.getY)
    }
    // Si le projectile provient du joueur 1
    else position.setLocation(position.getX + velocity.getX, position.getY)
  }

  override def getHitBox(): Rectangle = {
    id match {
      case -1 => return new Rectangle(position.getX.toInt - 10, position.getY.toInt - 5, 20 , 10)
      case -2 => return new Rectangle(position.getX.toInt - 15, position.getY.toInt - 5, 30, 10)
      case -3 => new Rectangle(position.getX.toInt - 20, position.getY.toInt - 5, 40, 10)
      case _ => return new Rectangle(position.getX.toInt - 10, position.getY.toInt - 5, 20, 10)
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    deplacement()
    if (id != 1 && id != 2) {

      Main.playerBulletImg.mirrorLeftRight()
      Main.superBulletImg.mirrorLeftRight()

      ID match{

        case -1 => g.drawTransformedPicture (position.getX.toInt, position.getY.toInt, 0, 1, Main.playerBulletImg)
        case -2 => g.drawTransformedPicture (position.getX.toInt, position.getY.toInt, 0, 1, Main.superBulletImg)

      }

      Main.playerBulletImg.mirrorLeftRight()
      Main.superBulletImg.mirrorLeftRight()

    }
    else {
      if(versusEnabled && ID == 2) {
        Main.playerBulletImg.mirrorLeftRight()
        g.drawTransformedPicture(position.getX.toInt, position.getY.toInt, 0, 1, Main.playerBulletImg)
        Main.playerBulletImg.mirrorLeftRight()
      }
      else
        g.drawTransformedPicture(position.getX.toInt, position.getY.toInt, 0, 1, Main.playerBulletImg)
    }

    /// Used in DEBUG mode for debugging projectile's hitbox
    if(Main.DEBUG)
      g.drawRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0)

  }
}
