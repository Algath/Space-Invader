package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.lib.GdxGraphics

import scala.collection.mutable.ArrayBuffer

object Handler {

  var enemy: ArrayBuffer[Enemy] = new ArrayBuffer[Enemy]()
  var projectile: ArrayBuffer[Projectile] = new ArrayBuffer[Projectile]()
  var bonusObject: ArrayBuffer[Bonus_Object] = new ArrayBuffer[Bonus_Object]()

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
    for (i: Int <- bonusObject.indices) {
      bonusObject(i).onGraphicRender(g)
    }

    player.onGraphicRender(g)
  }


  def removeProjectile(index:Int): Unit = {
    projectile.remove(index)
  }


}
