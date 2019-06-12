package com.bigoat.android

import org.gradle.api.Plugin
import org.gradle.api.Project

class ConfigExtension {
    // 开发配置
    boolean debug = false

    // 安卓配置
    String applicationId = null
    def mainApp = null
    int minSdkVersion = 19
    int compileSdkVersion = 28
    int targetSdkVersion = 28
    int versionCode = 1
    String versionName = "1.0"


    // 必须依赖
    String appcompatVersion = "28.0.0"
    String constraintVersion = "1.1.3"
    String designVersion = "28.0.0"
    String junitVersion = "4.12"

    String arouterApiVersion = "1.4.1"
    String arouterCompilerVersion = "1.2.2"
    String lifecycleVersion = "1.1.1"

    String bbcVersion = "1.0.0"
    String httpVersion = "1.0.1"

    List<Project> libs = new ArrayList<>()


    @Override
    public String toString() {
        return "ConfigExtension{" +
                "mainApp=" + mainApp +
                ", libs=" + libs +
                ", debug=" + debug +
                ", compileSdkVersion=" + compileSdkVersion +
                ", applicationId='" + applicationId + '\'' +
                ", minSdkVersion=" + minSdkVersion +
                ", targetSdkVersion=" + targetSdkVersion +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", appcompatVersion='" + appcompatVersion + '\'' +
                ", constraintVersion='" + constraintVersion + '\'' +
                ", junitVersion='" + junitVersion + '\'' +
                ", arouterApiVersion='" + arouterApiVersion + '\'' +
                ", arouterCompilerVersion='" + arouterCompilerVersion + '\'' +
                ", lifecycleVersion='" + lifecycleVersion + '\'' +
                ", bbcVersion='" + bbcVersion + '\'' +
                '}'
    }
}

class ConfigPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println "########## 初始化配置插件 ##########\n"

        // 项目配置
        project.subprojects { sub ->
            sub.repositories {
                maven { url "http://maven.jxwd.me/nexus/content/repositories/android-releases" }
                maven { url "https://jitpack.io" }
            }
        }

        project.extensions.create('config', ConfigExtension)

        project.afterEvaluate {
            def config = project['config']

            if (config.applicationId == null) {
                throw new IllegalArgumentException("必须配置应用唯一ID(applicationId). \n eg:\nconfig {\n \tapplicationId 'a.b.c' \n}")
            }

            if(config.mainApp == null) {
                throw new IllegalArgumentException("必须配置主应用(mainApp). \n eg:\nconfig {\n \tmainApp 'app' \n}")
            } else {
                project.subprojects { sub ->
                    if(config.mainApp == sub.name) {
                        config.mainApp = sub
                    } else {
                        config.libs.add(sub)
                    }
                }

                if(!(config.mainApp instanceof Project)) {
                    throw new IllegalArgumentException("请配置正确的主应用(mainApp). \n $config.mainApp is not found")
                }

                println "\n主应用: $config.mainApp.name \n依赖项目: $config.libs \n"

                println config
            }
        }

    }
}