package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.lib.GdxGraphics

import java.awt.Point
import scala.collection.mutable.ArrayBuffer

object Handler {

  var enemy: ArrayBuffer[Enemy] = new ArrayBuffer[Enemy]()
  var projectile: ArrayBuffer[Projectile] = new ArrayBuffer[Projectile]()

  var player:Player = null


  def onGraphicRender(g: GdxGraphics): Unit = {
    for (i: Int <- projectile.indices) {
      projectile(i).onGraphicRender(g)
    }
    for (i: Int <- enemy.indices) {
      enemy(i).onGraphicRender(g)
      if(enemy(i).pv < 0)
        enemy.remove(i)
    }
    player.onGraphicRender(g)
  }
}
