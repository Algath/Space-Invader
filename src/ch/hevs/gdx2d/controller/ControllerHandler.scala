package ch.hevs.gdx2d.controller

import ch.hevs.gdx2d.desktop.Xbox
import com.badlogic.gdx.controllers.{Controller, Controllers}

/**
*
* Used for catch controllers input for two players
*
**/
object ControllerHandler {

  var PLAYERONE = 0
  var PLAYERTWO = 1

  var controller:Array[Controller] = Array.ofDim(1)

  var PlayerONE_A_oldpressed:Boolean = false
  var PlayerONE_RTRIGGER_oldpressed: Boolean = false
  var PlayerONE_X_oldpressed: Boolean = false
  var PlayerONE_START_oldpressed: Boolean = false

  if (Controllers.getControllers().size > 0)
    Controllers.getControllers().first();

  for (i:Int <- 0 until Controllers.getControllers().size){

    if(Controllers.getControllers().get(i) != null)
      controller(i) = Controllers.getControllers().get(i)

  }


  // Sert à savoir si une mannette est null
  def ControllerIsNotNull(index:Int): Boolean = {
    if(index >= controller.length || controller(index) == null)
      return false
    return true
  }

  def isJustPressA(index:Int): Boolean = {

    if (index >= controller.length || controller(index) == null)
      return false

    if(controller(index).getButton(Xbox.A) && !PlayerONE_A_oldpressed) {
      PlayerONE_A_oldpressed = true
      return true
    }

    if (!controller(index).getButton(Xbox.A) && PlayerONE_A_oldpressed) {
      PlayerONE_A_oldpressed = false
    }

    return false

  }

  def isJustPressRTRIGGER(index: Int): Boolean = {

    if (index >= controller.length || controller(index) == null)
      return false

    if (controller(index).getButton(Xbox.R_TRIGGER) && !PlayerONE_RTRIGGER_oldpressed) {
      PlayerONE_RTRIGGER_oldpressed = true
      return true
    }

    if (!controller(index).getButton(Xbox.R_TRIGGER) && PlayerONE_RTRIGGER_oldpressed)
      PlayerONE_RTRIGGER_oldpressed = false

    return false

  }

  def isJustPressX(index: Int): Boolean = {

    if (index >= controller.length || controller(index) == null)
      return false

    if (controller(index).getButton(Xbox.X) && !PlayerONE_X_oldpressed) {
      PlayerONE_X_oldpressed = true
      return true
    }

    if (!controller(index).getButton(Xbox.X) && PlayerONE_X_oldpressed)
      PlayerONE_X_oldpressed = false

    return false

  }

  def isJustPressSTART(index: Int): Boolean = {

    if (index >= controller.length || controller(index) == null)
      return false

    if (controller(index).getButton(Xbox.START) && !PlayerONE_START_oldpressed) {
      PlayerONE_START_oldpressed = true
      return true
    }

    if (!controller(index).getButton(Xbox.START) && PlayerONE_START_oldpressed)
      PlayerONE_START_oldpressed = false

    return false

  }

}
