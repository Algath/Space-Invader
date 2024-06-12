package ch.hevs.gdx2d.SaveSystem

import com.badlogic.gdx.math.Vector2

import java.io.{BufferedOutputStream, BufferedReader, BufferedWriter, File, FileNotFoundException, FileOutputStream, FileReader, FileWriter, PrintStream}
import java.nio.file
import javax.tools.StandardJavaFileManager.PathFactory
import scala.reflect.io.Path


/**
 *
 * The SaveManager, used to save highscores in save.txt
 *
 */
object SaveManager {

  def ReadSave(): Vector2 = {

    var path:File = new File("save")
    var save:File = new File(path + "/save.txt")

    var result:Vector2 = Vector2.Zero

    /// If the folder not exist
    if(!path.exists()) {
      if(path.mkdir())
        println("[SYSTEM] New Folder Has Created !")
    }

    /// If the Save file not exist
    if(!save.exists()){

      if(save.createNewFile())
        println("[SYSTEM] New Save File Has Created !")

      val f = new PrintStream(new BufferedOutputStream(new FileOutputStream("save/save.txt")))

      f.println("0")
      f.println("0")
      f.close()

      result.x = 0
      result.y = 0

    }
    else{
      val reader: BufferedReader = new BufferedReader(new FileReader("save/save.txt"))

      result.x = Integer.parseInt(reader.readLine())
      result.y = Integer.parseInt(reader.readLine())

      println("[SYSTEM] Save Readed With Success !")

      reader.close()

    }

    return result

  }

  def WriteSave(highScore:Int, highScoreMulti:Int): Unit = {

    var path: File = new File("save")
    var save: File = new File(path + "/save.txt")

    /// If the folder not exist
    if (!path.exists()) {
      if (path.mkdir())
        println("[SYSTEM] New Folder Has Created !")
    }

    /// If the Save file not exist
    if (!save.exists()) {

      if (save.createNewFile())
        println("[SYSTEM] New Save File Has Created !")

    }

    val f = new PrintStream(new BufferedOutputStream(new FileOutputStream("save/save.txt")))

    f.println(highScore)
    f.println(highScoreMulti)
    f.close()

  }

}
