package ch.hevs.gdx2d.game

trait Damage {
  def getDamage: Int

  def setDamage(newDamage: Int): Unit

  var damage: Int
}
