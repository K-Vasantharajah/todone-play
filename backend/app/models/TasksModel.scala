package models

import todone.data.{Id, Project, State, Tag, Tags, Task, Tasks}

import javax.inject._
import scala.collection.concurrent

@Singleton
class TasksModel() {
  val currentId = new java.util.concurrent.atomic.AtomicInteger(6)
  val taskStore = concurrent.TrieMap[Id, Task](
      Id(1) -> Task(
        State.open,
        "Play with the ToDone interface",
        """Right now many things in the application are not working.
          |Our task is to make it work, and the first step to that is
          |finding what is needs to be fixed.""".stripMargin,
        Some(Project("todone")),
        Tags(List(Tag("scalabridge"), Tag("frontend")))
      ),
      Id(2) -> Task(
        State.open,
        "Learn how to use the web developer tools",
        """The web developer tools are one of the most useful tools for
          |debugging problems between the frontend and backend. We need
          |open up the web developer tools and look at the network pane,
          |where we'll find requests from the frontend that the backend
          |is not properly responding to.""".stripMargin,
        Some(Project("todone")),
        Tags(List(Tag("scalabridge"), Tag("frontend")))
      ),
      Id(3) -> Task(
        State.open,
        "Implement functionality to close a completed task",
        """The close button is probably the simplest bit of functionality
          |we can implement. (Hopefully you discovered the close button
          |doesn't work.) Let's do that now! The worksheets have more
          |details.""".stripMargin,
        Some(Project("todone")),
        Tags(List(Tag("scalabridge"), Tag("backend")))
      ),
      Id(4) -> Task(
        State.open,
        "Have a break!",
        "We've done a lot. Time for a break.",
        None,
        Tags(List(Tag("chillout")))

    )
  )

  def nextId(): Id = {
    val id = currentId.getAndIncrement()
    Id(id)
  }

  /** Get all the posts */
  def tasks: Tasks = {
    val sortedTasks: List[(Id, Task)] =
      taskStore.toArray.sortInPlaceBy { case (id, _) => id }.toList
    Tasks(sortedTasks)
  }

  /** Create a new post */
  def create(task: Task): Id = {
    val id = nextId()
    taskStore.addOne(id -> task)
    id
  }
}
