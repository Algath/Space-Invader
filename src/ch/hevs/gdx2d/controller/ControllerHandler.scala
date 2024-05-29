package ch.hevs.gdx2d.controller

import ch.hevs.gdx2d.desktop.Xbox
import com.badlogic.gdx.controllers.{Controller, Controllers}

object ControllerHandler {

  var PLAYERONE = 0
  var PLAYERTWO = 1

  var controller:Array[Controller] = Array.ofDim(1)

  var PlayerONE_A_pressed:Boolean = false
  var PlayerONE_A_oldpressed:Boolean = false

  if (Controllers.getControllers().size > 0)
    Controllers.getControllers().first();

  for (i:Int <- 0 until Controllers.getControllers().size){

    if(Controllers.getControllers().get(i) != null)
      controller(i) = Controllers.getControllers().get(i)

  }


  // Sert à savoir si une mannette est null
  def ControllerIsNotNull(index:Int): Boolean = {
    if(controller(index) == null)
      return false
    return true
  }

  def isJustPressA(index:Int): Boolean = {

    if (controller(index) == null)
      return false

    if(controller(PLAYERONE).getButton(Xbox.A) && !PlayerONE_A_oldpressed) {
      PlayerONE_A_oldpressed = true
      return true
    }

    if (!controller(PLAYERONE).getButton(Xbox.A) && PlayerONE_A_oldpressed) {
      PlayerONE_A_pressed = false
      PlayerONE_A_oldpressed = false
    }

    return false

  }

  def UpdatePressButton(): Unit = {

    if (controller(PLAYERONE) == null){
      if (!controller(PLAYERONE).getButton(Xbox.A)) {
        PlayerONE_A_pressed = false
        PlayerONE_A_oldpressed = false
      }
    }

    println(PlayerONE_A_pressed + " / " + PlayerONE_A_oldpressed)

  }


}
