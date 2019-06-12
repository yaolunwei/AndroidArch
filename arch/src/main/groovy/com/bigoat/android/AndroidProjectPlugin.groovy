package com.bigoat.android

import org.gradle.api.Plugin
import org.gradle.api.Project


class AndroidProjectPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def config = project['config']

        if (config.debug) {
            project.apply plugin: 'cc.lecent.android.app'
        } else {
            project.apply plugin: 'cc.lecent.android.lib'
        }
    }
}