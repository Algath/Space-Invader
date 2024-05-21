package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.lib.GdxGraphics

import java.awt.Rectangle
import java.awt.geom.Point2D

/**
 * Moi
 * */
class Enemy (ID: Int, vie: Int, _position: Point2D) extends Object {

  override def deplacement(): Unit = {

  }

  override var position: Point2D = _position


  for(i:Int <- 0 until Handler.projectile.length) {
    if(Handler.projectile(i) == null){

    }
  }

  override def getHitBox(): Rectangle = {

    return null
  }

  override var velocity: Point2D = null

  override def onGraphicRender(g: GdxGraphics): Unit = {

  }

}
