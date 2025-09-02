package com.android.vurgun.convention.extensions

import com.android.vurgun.convention.utils.GitHooksTask
import org.gradle.api.Project

fun Project.registerPrePushTask() {
    tasks.register("installGitHooks", GitHooksTask::class.java) {
        description = "Copy pre-commit hook from the scripts folder to the .git/hooks folder "
        group = "git-hooks"
        val projectDirectory = rootProject.layout.projectDirectory
        hookSource.set(projectDirectory.file("scripts/pre-commit.sh"))
        hookOutput.set(projectDirectory.file(".git/hooks/pre-commit"))

        doLast {
            println("Git hooks installed")
        }
    }

    tasks.named("preBuild").configure {
        dependsOn("installGitHooks")
    }
}
