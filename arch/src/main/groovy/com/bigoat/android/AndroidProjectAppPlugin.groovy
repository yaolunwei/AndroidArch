package com.bigoat.android

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidProjectAppPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "########## 配置安卓应用($project.name) ##########\n"

        project.apply plugin: 'com.android.application'

        def config = project['config']
        def android = project.android

        // 安卓版本配置
        android.compileSdkVersion config.compileSdkVersion
        android.defaultConfig {
            if(project == config.mainApp) {
                applicationId config.applicationId
            } else {
                applicationId "$config.applicationId.$project.name"
            }
            minSdkVersion config.minSdkVersion
            targetSdkVersion config.targetSdkVersion
            versionCode config.versionCode
            versionName config.versionName
            testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

            if (config.arouterApiVersion != null && config.arouterCompilerVersion != null) {
                javaCompileOptions {
                    annotationProcessorOptions {
                        arguments = [ AROUTER_MODULE_NAME : project.name ]
                    }
                }
            }

        }

        android.buildTypes {
            release {
                minifyEnabled false
                buildConfigField "Boolean", "IS_MODULE", "${config.debug}"
                proguardFiles android.getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            }

            debug {
                proguardFiles android.getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
                buildConfigField "Boolean", "IS_MODULE", "${config.debug}"
            }
        }

        // 设置数据绑定
        android.dataBinding{
            enabled = true
        }

        // 使用java 8编译
        android.compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }

        // 配置通用依赖
        project.dependencies {
            implementation project.fileTree(dir: 'libs', include: ['*.jar'])

            testImplementation "junit:junit:$config.junitVersion"

            implementation "com.android.support:appcompat-v7:$config.appcompatVersion"

            implementation "com.android.support.constraint:constraint-layout:$config.constraintVersion"

            implementation "com.android.support:design:$config.designVersion"

            if (config.bbcVersion != null) {
                implementation "com.bigoat.android:bbc:$config.bbcVersion"
            }

            if (config.uiVersion != null) {
                implementation "com.bigoat.android:ui:$config.uiVersion"
            }

            if (config.arouterApiVersion != null && config.arouterCompilerVersion != null) {
                // 集成阿里路由: https://github.com/alibaba/ARouter/blob/master/README_CN.md
                implementation "com.alibaba:arouter-api:$config.arouterApiVersion"
                annotationProcessor "com.alibaba:arouter-compiler:$config.arouterCompilerVersion"
            }

            // ViewModel and LiveData: https://developer.android.com/topic/libraries/architecture/adding-components
            implementation "android.arch.lifecycle:extensions:$config.lifecycleVersion"

            if(project == config.mainApp && !config.debug) {
                config.libs.each {
                    implementation it
                }
            }
        }

    }
}
