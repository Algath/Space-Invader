package ch.hevs.gdx2d.game

import ch.hevs.gdx2d.ParticleSystem.ParticleManager
import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.main.Main
import com.badlogic.gdx.{Gdx, Input}
import com.badlogic.gdx.graphics.Color

import java.awt.{Point, Rectangle}

/*
* ID :
*
*  -3   - Bullet Boss
*  -2   - Bullet Mini-Boss
*  -1   - Bullet Enemy
*   0   - /
*   1   - Basic Bullet Player 1
*   2   - Basic Bullet Player 2
*   3   - Fireball Player 1
*   4   - FireBall Player 2
*
*/

/**
 * Class of all projectile of the game
 * @param ID
 * @param _position
 * @param _damage
 * @param _velocity
 * @param versusEnabled
 */
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
      case -2 => return new Rectangle(position.getX.toInt - 15, position.getY.toInt - 5, 23, 10)
      case -3 => new Rectangle(position.getX.toInt - 20, position.getY.toInt - 5, 40, 20)
      case 3 => return new Rectangle(position.getX.toInt - 15, position.getY.toInt - 15, 30, 30)
      case 4 => return new Rectangle(position.getX.toInt - 15, position.getY.toInt - 15, 30, 30)
      case _ => return new Rectangle(position.getX.toInt - 10, position.getY.toInt - 5, 20, 10)
    }
  }

  override def onGraphicRender(g: GdxGraphics): Unit = {
    deplacement()
    if (id != 1 && id != 2 && id != 3 && id != 4) {

      Main.playerBulletImg.mirrorLeftRight()
      Main.superBulletImg.mirrorLeftRight()
      Main.soldatBulletImg.mirrorLeftRight()

      ID match{

        case -1 => g.drawTransformedPicture (position.getX.toInt, position.getY.toInt, 0, 1, Main.soldatBulletImg)
        case -2 => g.drawTransformedPicture (position.getX.toInt, position.getY.toInt, 0, 1, Main.superBulletImg)
        case -3 => g.drawTransformedPicture (position.getX.toInt, position.getY.toInt, 0, 1, Main.bossBulletImg)
        case _ => g.drawRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0)

      }

      Main.playerBulletImg.mirrorLeftRight()
      Main.superBulletImg.mirrorLeftRight()
      Main.soldatBulletImg.mirrorLeftRight()

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

    if(ID == 3 || ID == 4) {
      ParticleManager.CreateParticles(position.clone.asInstanceOf[Point], 3, 25, 2)
      ParticleManager.CreateParticles(position.clone.asInstanceOf[Point], 3, 10, 1)
    }

    if(ID == 3 || ID == 4)
      collision()

    /// Used in DEBUG mode for debugging projectile's hitbox
    if (Main.DEBUG) {
      g.drawRectangle(position.getX.toInt, position.getY.toInt, getHitBox().width, getHitBox().height, 0)
    }

  }

  def collision(): Unit = {

    var it: scala.Iterator[Projectile] = Handler.projectile.iterator;

    while(it.hasNext){

      var p:Projectile = it.next()

      if((p.id == 3 || p.id == 4) && p != this && p.getHitBox().intersects(getHitBox())){
        ParticleManager.CreateParticles(position.clone.asInstanceOf[Point], 100, 50, 6)
        //Handler.removeProjectile(it.indexOf(this))
        //Handler.removeProjectile(it.indexOf(p))
      }

    }

  }

}
