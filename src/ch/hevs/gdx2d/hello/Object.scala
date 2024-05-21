package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.lib.GdxGraphics

import java.awt.Rectangle
import java.awt.geom.Point2D

abstract class Object {

  var position:Point2D
  var velocity:Point2D

  def deplacement(): Unit

  def getHitBox(): Rectangle = {
    return new Rectangle(position.getX.toInt, position.getY.toInt, 100, 100)
  }

  def onGraphicRender(g: GdxGraphics): Unit


}
