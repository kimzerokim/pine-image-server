package quickstart.action

import java.io.File
import java.nio.file.{Paths, Files}
import java.text.SimpleDateFormat
import java.util.Calendar

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
    var fileName: String = "temp"

    paramo[FileUpload]("file") match {
      case Some(file) =>
        val file = param[FileUpload]("file")
        fileName = generatedFileName(file.getFilename)
        val path = "/Users/nhk/" + fileName
        file.renameTo(new File(path))
        respondJson(fileName)

      case None =>
        respondJson("not pine")
    }
  }

  private def generatedFileName(fileName: String): String = (new SimpleDateFormat("yyMMdd_HHmmss_").format(Calendar.getInstance().getTime)
                                                             + java.util.UUID.randomUUID.toString + "_" + fileName)
}
