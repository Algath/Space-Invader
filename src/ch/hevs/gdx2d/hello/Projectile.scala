package ch.hevs.gdx2d.hello
import ch.hevs.gdx2d.lib.GdxGraphics

import java.awt.Rectangle
import java.awt.geom.Point2D

class Projectile extends Object{

  override var position: Point2D = ???
  override def deplacement(): Unit = ???

  override def getHitBox(): Rectangle = {
    return new Rectangle(position.getX.toInt, position.getY.toInt, 1000, 1000)

  }

  override var velocity: Point2D = ???

  override def onGraphicRender(g: GdxGraphics): Unit = {


  }

}
