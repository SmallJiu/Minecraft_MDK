
## 构建并编译
* gradlew.bat build
  * 构建mod并放在 项目文件夹/build/libs/xxxxx.jar
  * 如果执行了 withSourcesJar() 则会另外生成一个 xxxx-sources.jar

## JarInJar
```groovy
jarJar.enabled(true) // 通过此方法开启jij功能
dependencies {
    jarJar("com.tterrag.registrate:Registrate:MC1.20-1.3.3") {
        jarJar.pin(it, "[1.0.0,)") // 设置支持的版本范围，具体请查看相关规范
    }
}
```

## 非 Mod 依赖
* 如果不执行此操作，则在jar不是mod时不会加载到mc中
```groovy
jarJar.enabled(true)
dependencies {
    // ModDevGradle 是 additionalRuntimeClasspath
    additionalRuntimeClasspath("local.libs:jmp123-all:1.0.0")
    
    // ForgeGradle 是 minecraftLibrary
    minecraftLibrary("local.libs:jmp123-all:1.0.0")
}
```
* NeoGradle 有别的写法
```groovy
configurations {
    libraries
    implementation.extendsFrom libraries
}
dependencies {
    libraries 'com.example:example:1.0'
}
runs {
    configureEach {
        dependencies {
            runtime project.configurations.libraries
        }
    }
}
```

* 或者
```groovy
dependencies {
    implementation 'com.example:example:1.0'
}
runs {
    configureEach {
        dependencies {
            runtime 'com.example:example:1.0'
        }
    }
}
```

## 本地依赖
```groovy
repositories {
    // ...
    flatDir {
        dirs("libs")
    }
}
dependencies {
    // 两种都可
    implementation("local.libs:jmp123-all:1.0.0")
    implementation("blank:jmp123-all:1.0.0")
}
```

## 源码Jar
```groovy
java{
    withSourcesJar() // 此代码块可自动生成源码jar
}
sourcesJar { // 如果你有些文件不想放进源码，你可以在此代码块添加对应文件以排除
    from sourceSets.main.java // 只包含 Java 源文件
    include '**/*.java' // 确保只包括 .java 文件
    exclude(
            '**/.cache/**', // 排除 .cache 文件夹
            '**/assets/**', // 排除 assets 文件夹
            '**/data/**'    // 排除 data 文件夹
    )
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE // 排除重复的文件
    archiveClassifier.set('sources') // 设置源代码 JAR 文件的后缀
}
```