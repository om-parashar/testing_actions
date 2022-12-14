#!/usr/bin/env kscript

//KOTLIN_OPTS -cp ecs-deployer.jar
//COMPILER_OPTS -cp ecs-deployer.jar

import `in`.porter.utilities.ecsdeployer.dsl.*

val env = args[0]

println("_*_*\n_*_*__\n*_*(*_)_\n*(_*_(_*_\n(_*_(_\n*_(_*_(_\n*_(_*_(_\n*_(*(_*_\n(_*_(_*\n_(_*_(_*\n_(_*_(_\n*_(_*_\n(_*_(_\n*(_*(\n__*$env")

//INCLUDE components.kt

val taskDesiredCount =
  when (env) {
    "prod" -> 2
    else -> 1
  }

updateService {
  cluster = if (env == "prod") "ecs-prod-private-v2" else "ecs-$env-private"

  name = appName
  task = serverTask

  desiredCount = taskDesiredCount

  killTasks = env == "staging"
}
