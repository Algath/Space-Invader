package ch.hevs.gdx2d.game

import ch.hevs.gdx2d.lib.GdxGraphics

import java.awt.{Point, Rectangle}

abstract class Object {

  var position:Point
  var velocity:Point
  var id: Int

  def deplacement(): Unit

  def getHitBox(): Rectangle = {
    return new Rectangle(position.getX.toInt, position.getY.toInt, 100, 100)
  }

  def onGraphicRender(g: GdxGraphics): Unit


}
