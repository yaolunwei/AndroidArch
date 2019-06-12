## 构建项目
> 用于乐诚组件化项目架构

## 使用

**一、`build.gradle` 添加**

```groovy
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.2.1'
        // 添加脚本依赖
        classpath 'com.bigoat.andrid:arch:0.0.1'
    }
}

......

// 应用配置
apply plugin: 'com.bigoat.andrid.config'

// 全局配置
config {
	// Debug
	debug false
	// 应用ID(必须)
    applicationId 'com.bigoat.demo'
    // 主应用Module(必须)
    mainApp 'app'
}
```

**二、主Module配置**
```groovy
apply plugin: 'com.bigoat.andrid.app'
```
*默认依赖项目所有`Module` and `Library`当`debug true`时*

**三、Module配置**
```groovy
apply plugin: 'cc.lecent.android'
```
*`debug true`我是Module，`debug false`我是Library*

**四、Library配置**
```groovy
apply plugin: 'com.bigoat.andrid.lib'
```

## 说明
**配置：**
```groovy
config {
	// 主Module名称(必须)
	mainApp 'app'
	
	// 是否启用Debug模式(默认false)
	debug false
	
	// 编译版本
	compileSdkVersion 28
	
	// 应用唯一ID(必须)
	applicationId 'com.bigoat.demo'
	
	// 最小支持版本
	minSdkVersion 19
	
	// 最大支持版本
    targetSdkVersion 28

	// 应用版本号
    versionCode 1
    
    // 应用版本号名称
    versionName '1.0'

	// 依赖版本号	
    appcompatVersion '28.0.0'
    constraintVersion '1.1.3'
    junitVersion '4.12'
    arouterApiVersion '1.4.1'
    arouterCompilerVersion '1.2.2'
    lifecycleVersion '1.1.1'
    bbcVersion '1.0.0'
}
```

<br>

**插件：**

在`Module`或者`Library` 引用乐诚插件下方可以添加`android` `dependencies` 用于覆盖全局或增加依赖

例如：
```groovy
apply plugin: 'com.bigoat.andrid'

android {
    compileSdkVersion 29
    defaultConfig {
        minSdkVersion 20
        targetSdkVersion 29
        versionCode 2
        versionName "2.0"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation 'com.bigoat.android:bbc:0.0.1'
}

```

## Roadmap
- 减少配置
- 提高开发调试速度

## 版本发布

#### v1.0.0
```groovy
classpath 'com.bigoat.andrid:arch:0.0.1'
```

- minSdkVersion `19`
- compileSdkVersion `28`
- targetSdkVersion `28`
- appcompatVersion `'28.0.0'`
- constraintVersion `'1.1.3'`
- junitVersion `'4.12'`
- arouterApiVersion `'1.4.1'`
- arouterCompilerVersion `'1.2.2'`
- lifecycleVersion `'1.1.1'`
- bbcVersion `'1.0.0'`
- 默认启用`dataBinding`
- 默认使用 `Java1.8`
- 增加仓库地址 <br>
`maven { url "https://jitpack.io" }`
