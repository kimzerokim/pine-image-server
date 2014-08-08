package quickstart.action

import java.io.File
import java.nio.file.{Paths, Files}

import io.netty.handler.codec.http.multipart.FileUpload

import xitrum.{SkipCsrfCheck, Action}
import xitrum.annotation.{POST, GET}

@GET("upload/:name")
class Upload extends Action with SkipCsrfCheck {
  def execute() {
    val name = param("name")
    val existTrue = Files.exists(Paths.get("/Users/Andy/"+name))
    if(existTrue)
      respondFile("/Users/Andy/"+name)
    else
      respondJson("not pine")
  }
}

@POST("upload")
class HelloAction extends Action with SkipCsrfCheck {
  def execute() {
    paramo[FileUpload]("file") match {
      case Some(file) =>
        val file = param[FileUpload]("file")
        val path = "/Users/Andy/" + file.getFilename
        file.renameTo(new File(path))

      case None =>
        flash("Please upload a nonempty file")
    }
    respondJson("pine")
  }
}
