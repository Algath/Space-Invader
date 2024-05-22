package ch.hevs.gdx2d.hello

import ch.hevs.gdx2d.lib.GdxGraphics
import com.badlogic.gdx.{Gdx, Input}

import java.awt.Point

class Player(ID: Int, _position : Point) extends Object with Damage with PV {
  Gdx.input.isKeyJustPressed(Input.Keys.SPACE)
  override var position: Point = _position
  override var velocity: Point = new Point(0,0)

  override var id: Int = ???

  override var pv: Int = ???
  override var maxPV: Int = ???

  override def deplacement(): Unit = ???

  override def onGraphicRender(g: GdxGraphics): Unit = ???

  override def getDamage: Int = ???
}
