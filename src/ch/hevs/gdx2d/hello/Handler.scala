package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.lib.GdxGraphics

import java.awt.Point
import scala.collection.mutable.ArrayBuffer

object Handler {

  var enemy: ArrayBuffer[Enemy] = new ArrayBuffer[Enemy]()
  var projectile: ArrayBuffer[Projectile] = new ArrayBuffer[Projectile]()

  var player:Player = null

  var removedEnemy:Int = 0

  def onGraphicRender(g: GdxGraphics): Unit = {

    for (i: Int <- projectile.indices) {
      projectile(i).onGraphicRender(g)
    }

    removedEnemy = 0
    for (i: Int <- enemy.indices) {
      enemy(i - removedEnemy).onGraphicRender(g)
      if(enemy(i - removedEnemy).pv < 0)
        removeEnemy(i)
    }

    player.onGraphicRender(g)
  }


  def removeProjectile(index:Int): Unit = {
    projectile.remove(index)
  }

  def removeEnemy(index:Int): Unit = {
    enemy.remove(index)
    removedEnemy += 1
  }


}
