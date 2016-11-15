package controllers.Requests

import com.google.inject.Inject
import com.google.inject.name.Named
import models.User
import play.api.mvc.{Request, _}

import scala.concurrent.Future

object AuthenticatedOrAnonymousSearchAllowed extends ActionBuilder[Request] with ActionFilter[Request] {
  def filter[A](request: Request[A]): Future[Option[Result]] = {

    val result = request.session.get("username").map(User.findByEmail) match {
      case Some(username) => None // The user is authenticated.
      case None => shineConfig.getBoolean("authorization_required_to_search") match {
        // The user is not authenticated. Check config.
        case Some(true) =>  Some(Results.Forbidden("Oops, you are not authorized"))   // Auth is required. Reject user.
        case Some(false) => None                                                      // Auth is not required to search. Continue execution.
        case x => Some(Results.Forbidden("Oops, you are not authorized"))             // No value set in config. Reject user.
      }
    }

    Future.successful(result)
  }
}