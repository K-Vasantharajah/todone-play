package controllers

import javax.inject._


import models._
import play.api.libs.json.{JsError, Json}
//import play.api.libs.json.Json
import play.api.mvc._
import todone.data._

@Singleton
class ApiController @Inject() (
    val controllerComponents: ControllerComponents,
    val model: TasksModel
) extends BaseController {
  import JsonFormats._

  def tasks() = Action { implicit request: Request[AnyContent] =>
    Ok(Json.toJson(model.tasks))
  }
  def create() = Action(parse.json) { implicit request =>
    val postResult = request.body.validate[Task]
    postResult.fold(
      errors => BadRequest(JsError.toJson(errors)),
      task => {
        val id = model.create(task)
        Ok(Json.toJson(id))
      }
    )
  }
}
