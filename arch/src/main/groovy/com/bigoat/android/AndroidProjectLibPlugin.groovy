package com.bigoat.android

import org.gradle.api.Plugin
import org.gradle.api.Project


class AndroidProjectLibPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "########## 配置安卓库($project.name) ##########\n"

        project.apply plugin: 'com.android.library'

        def config = project['config']
        def android = project.android

        // 安卓版本配置
        android.compileSdkVersion config.compileSdkVersion
        android.defaultConfig {
            minSdkVersion config.minSdkVersion
            targetSdkVersion config.targetSdkVersion
            versionCode config.versionCode
            versionName config.versionName
            testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

            javaCompileOptions {
                annotationProcessorOptions {
                    arguments = [ AROUTER_MODULE_NAME : project.name ]
                }
            }
        }

        android.buildTypes {
            release {
                minifyEnabled false
                proguardFiles android.getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
                buildConfigField "Boolean", "IS_MODULE", "${config.debug}"
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
            sourceCompatibility org.gradle.api.JavaVersion.VERSION_1_8
            targetCompatibility org.gradle.api.JavaVersion.VERSION_1_8
        }

        // 配置通用依赖
        project.dependencies {
            implementation project.fileTree(dir: 'libs', include: ['*.jar'])

            implementation "com.android.support:appcompat-v7:$config.appcompatVersion"
            implementation "com.android.support.constraint:constraint-layout:$config.constraintVersion"

            // 集成阿里路由: https://github.com/alibaba/ARouter/blob/master/README_CN.md
            implementation "com.alibaba:arouter-api:$config.arouterApiVersion"
            annotationProcessor "com.alibaba:arouter-compiler:$config.arouterCompilerVersion"

            implementation "cc.lecent:bbc:$config.bbcVersion"

            implementation "cc.lecent.component:http:$config.httpVersion"

            testImplementation "junit:junit:$config.junitVersion"
        }
    }
}