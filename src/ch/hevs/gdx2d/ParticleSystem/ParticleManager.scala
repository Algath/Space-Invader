package ch.hevs.gdx2d.ParticleSystem

import ch.hevs.gdx2d.lib.GdxGraphics
import ch.hevs.gdx2d.lib.physics.PhysicsWorld
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.{Body, World}
import com.badlogic.gdx.utils.Array

import java.awt.Point
import java.util.{Iterator, Random}

object ParticleManager {

  private val rand = new Random()

  var world:World = null
  world = PhysicsWorld.getInstance()
  world.setGravity(new Vector2(0, 0.0f))

  def UpdatePhysicParticle(g: GdxGraphics): Unit = {
    var bodies: Array[Body] = new Array[Body];
    world.getBodies(bodies);

    var it: Iterator[Body] = bodies.iterator();

    while (it.hasNext) {
      var p: Body = it.next();

      if (p.getUserData.isInstanceOf[Particle]) {
        var particle: Particle = p.getUserData.asInstanceOf[Particle]
        particle.step();
        particle.render(g);

        if (particle.shouldbeDestroyed()) {
          particle.destroy();
        }
      }
    }

    PhysicsWorld.updatePhysics(Gdx.graphics.getDeltaTime)
    g.resetBlend();
  }

  def CreateParticles(position:Point, CREATION_RATE: Int = 10, MAX_AGE: Int = 35, FORCE_FACTOR:Int = 1): Unit = {
    var vect: Vector2 = new Vector2(position.x, position.y)
    for (i <- 0 until CREATION_RATE) {
      val c = new Particle(vect, 10, MAX_AGE + rand.nextInt(MAX_AGE / 2))
      // Apply a vertical force with some random horizontal component
      val force = new Vector2
      force.x = rand.nextFloat * 0.00095f * (if (rand.nextBoolean == true) -1 else 1) * FORCE_FACTOR
      force.y = rand.nextFloat * 0.00095f * (if (rand.nextBoolean == true) -1 else 1) * FORCE_FACTOR
      c.applyBodyLinearImpulse(force, vect, true)
    }
  }

}
