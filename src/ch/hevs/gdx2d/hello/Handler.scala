package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.lib.GdxGraphics

import java.awt.Point
import scala.collection.mutable.ArrayBuffer

object Handler {

  var enemy: ArrayBuffer[Enemy] = new ArrayBuffer[Enemy]()
  var projectile: ArrayBuffer[Projectile] = new ArrayBuffer[Projectile]()
  var bonusObject: ArrayBuffer[Bonus_Object] = new ArrayBuffer[Bonus_Object]()

  var player:Player = null

  var removedEnemy:Int = 0
  var removedProjectile:Int = 0

  def Init(): Unit = {

    enemy = new ArrayBuffer[Enemy]()
    projectile = new ArrayBuffer[Projectile]()
    bonusObject = new ArrayBuffer[Bonus_Object]()
    player = new Player(1, new Point(100, 500), 200)
  }

  def onGraphicRender(g: GdxGraphics): Unit = {

    removedProjectile = 0
    for (i: Int <- projectile.indices) {
      projectile(i - removedProjectile).onGraphicRender(g)
      if(projectile(i - removedProjectile).id > 0) {
        if(projectile(i - removedProjectile).position.x > 1940)
          removeProjectile(i - removedProjectile )
      }

      else if (projectile(i - removedProjectile).id < 0) {
        if (projectile(i - removedProjectile).position.x < -10)
          removeProjectile(i - removedProjectile)
      }
    }

    removedEnemy = 0
    for (i: Int <- enemy.indices) {
      enemy(i - removedEnemy).onGraphicRender(g)
      if(enemy(i - removedEnemy).pv < 0)
        removeEnemy(i)
    }

    for (i: Int <- bonusObject.indices) {
      bonusObject(i).onGraphicRender(g)
    }

    player.onGraphicRender(g)
  }


  def removeProjectile(index:Int): Unit = {
    projectile.remove(index)
    removedProjectile += 1
  }

  def removeEnemy(index:Int): Unit = {
    enemy.remove(index)
    removedEnemy += 1
  }

  def removeBonusObject(index:Int): Unit = {
    bonusObject.remove(index)
  }


}
