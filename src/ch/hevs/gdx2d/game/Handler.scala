package ch.hevs.gdx2d.game

import ch.hevs.gdx2d.lib.GdxGraphics

import java.awt.Point
import scala.collection.mutable.ArrayBuffer

object Handler {

  var enemy: ArrayBuffer[Enemy] = new ArrayBuffer[Enemy]()
  var projectile: ArrayBuffer[Projectile] = new ArrayBuffer[Projectile]()
  var bonusObject: ArrayBuffer[Bonus_Object] = new ArrayBuffer[Bonus_Object]()

  var playerOne: Player = null
  var playerTwo: Player = null

  var removedEnemy: Int = 0
  var removedProjectile: Int = 0
  var removedBonusObject: Int = 0

  var pts:Int = 0

  def Init(): Unit = {
    enemy = new ArrayBuffer[Enemy]()
    projectile = new ArrayBuffer[Projectile]()
    bonusObject = new ArrayBuffer[Bonus_Object]()
    pts = 0
  }
  
  def InitPlayer(playerNumber:Int, versusEnabled:Boolean): Unit = {

    playerOne = null
    playerTwo = null

    if(playerNumber == 1)
      playerOne = new Player(1, new Point(100, 1080 / 2), 200)
    else {
      if(versusEnabled){
        playerOne = new Player(1, new Point(100, 1080 / 2), 500, true)
        playerTwo = new Player(2, new Point(1920 - 100, 1080 / 2), 200, true)
      }
      else{
        playerOne = new Player(1, new Point(300, 1080 / 3), 200)
        playerTwo = new Player(2, new Point(100, (1080 / 3) * 2), 200)
      }
    }
  }

  def onGraphicRender(g: GdxGraphics): Unit = {

    removedProjectile = 0
    for (i: Int <- projectile.indices) {
      projectile(i - removedProjectile).onGraphicRender(g)
      if (projectile(i - removedProjectile).position.x > 1940 || projectile(i - removedProjectile).position.x < -10)
        removeProjectile(i - removedProjectile)
    }

    removedEnemy = 0
    for (i: Int <- enemy.indices) {
      enemy(i - removedEnemy).onGraphicRender(g)
      if (enemy(i - removedEnemy).pv <= 0) {
        pts += enemy(i).getPts()
        if (enemy(i).id == 2)
          bonusObject.append(new Bonus_Object(6, new Point (enemy(i).position.x, enemy(i).position.y)))
        removeEnemy(i)
      }
    }

    removedBonusObject = 0
    for (i: Int <- bonusObject.indices) {
      bonusObject(i - removedBonusObject).onGraphicRender(g)
      if (bonusObject(i - removedBonusObject).position.x < -50)
        removeBonusObject(i)
    }

    if(playerOne.pv > 0)
      playerOne.onGraphicRender(g)

    if(playerTwo != null)
      if(playerTwo.pv > 0)
        playerTwo.onGraphicRender(g)

  }


  def removeProjectile(index: Int): Unit = {
    projectile.remove(index)
    removedProjectile += 1
  }

  def removeEnemy(index: Int): Unit = {
    enemy.remove(index)
    removedEnemy += 1
  }

  def removeBonusObject(index: Int): Unit = {
    bonusObject.remove(index)
    removedBonusObject += 1
  }


}
