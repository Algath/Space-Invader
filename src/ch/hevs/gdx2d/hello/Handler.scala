package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.graphics.Color

import scala.collection.mutable.ArrayBuffer

object Handler {

  var enemy:ArrayBuffer[Enemy] = new ArrayBuffer[Enemy]()
  var projectile:ArrayBuffer[Projectile] = new ArrayBuffer[Projectile]()


  def onGraphicRender(g: GdxGraphics): Unit = {


    for (i: Int <- 0 until projectile.length) {

      projectile(i).onGraphicRender(g)

    }

    for (i: Int <- 0 until enemy.length) {

      enemy(i).onGraphicRender(g)

    }

  }


}
