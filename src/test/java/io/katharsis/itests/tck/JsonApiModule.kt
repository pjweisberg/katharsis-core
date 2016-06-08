package io.katharsis.itests.tck

import io.katharsis.repository.annotations.JsonApiFindAll
import io.katharsis.repository.annotations.JsonApiFindOne
import io.katharsis.repository.annotations.JsonApiResourceRepository
import io.katharsis.resource.annotations.JsonApiId
import io.katharsis.resource.annotations.JsonApiResource
import io.katharsis.resource.annotations.JsonApiToMany
import io.katharsis.resource.annotations.JsonApiToOne
import java.util.*

fun from(project: Project): ProjectResource {
    return ProjectResource(project.uuid, project.name)
}

fun from(task: Task): TaskResource {

    var project: ProjectResource? = projectResource(task)

    return TaskResource(task.uuid, task.task, project)
}

private fun projectResource(task: Task): ProjectResource? {
    var project: ProjectResource?
    val p = task.project
    if (p == null) {
        project = null;
    } else {
        project = from(p)
    }
    return project
}

@JsonApiResource(type = "tasks")
data class TaskResource(
        @JsonApiId
        var uuid: String = UUID.randomUUID().toString(),
        var task: String = "task",
        @JsonApiToOne
        var project: ProjectResource?
)

@JsonApiResource(type = "project")
data class ProjectResource(
        @JsonApiId
        var uuid: String = UUID.randomUUID().toString(),
        var name: String = "project",
        @JsonApiToMany
        var tasks: List<TaskResource> = ArrayList()
)


@JsonApiResourceRepository(value = TaskResource::class)
class TaskResourceRepository {

    @JsonApiFindAll
    fun findAll(taskRepository: TaskRepository): List<TaskResource> {
        val tasks = taskRepository.findAll()

        return tasks.map { task -> from(task) }
    }

    @JsonApiFindOne
    fun findOne(id: String, taskRepository: TaskRepository): TaskResource {
        val task = taskRepository.findOne(id);
        return from(task);
    }

}
