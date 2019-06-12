package com.bigoat.android

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidProjectPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def config = project['config']

        if (config.debug) {
            project.apply plugin: 'com.bigoat.android.app'
        } else {
            project.apply plugin: 'com.bigoat.android.lib'
        }
    }
}