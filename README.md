# MVVMDataBinding
1)、运用结构化和模块化构建项目；
    运用了MVVM设计模式；
    运用网络框架 okhttp3+rxjava2+retrofit2;
    多渠道打包技术，配置三款不同类型的APK；

2）、多环境打包设置
      productFlavors {
             //APP1
             develop {
                 dimension "versionCode"
                 buildConfigField "int", "ENV_TYPE", "1"

                 applicationId rootProject.ext["develop"]["application_id"]
                 versionCode rootProject.ext["develop"]["version_code"]
                 versionName rootProject.ext["develop"]["version_name"]
             }
             //APP2
             check {
                 dimension "versionCode"
                 buildConfigField "int", "ENV_TYPE", "2"

                 applicationId rootProject.ext["check"]["application_id"]
                 versionCode rootProject.ext["check"]["version_code"]
                 versionName rootProject.ext["check"]["version_name"]

             }
             //APP3
             product {
                 dimension "versionCode"
                 buildConfigField "int", "ENV_TYPE", "3"
                 applicationId rootProject.ext["product"]["application_id"]
                 versionCode rootProject.ext["product"]["version_code"]
                 versionName rootProject.ext["product"]["version_name"]
             }
         }
         applicationId、versionCode 和 versionName 决定了不同的 ID 和版本号在bank.gradle中获得。

3)、代报配置
     //自定义生成的apk的地址及名称
        def apkName
        /**
         * 配置秘钥
         */
        signingConfigs {
            release {
                keyAlias keystoreProperties['keyAlias']
                keyPassword keystoreProperties['keyPassword']
                storeFile file(keystoreProperties['storeFile'])
                storePassword keystoreProperties['storePassword']
            }
        }

        buildTypes {
            release {
                //更改AndroidManifest.xml中预先定义好占位符信息
                //manifestPlaceholders = [app_icon: "@drawable/icon"]
                // 不显示Log
                buildConfigField "boolean", "LOG_DEBUG", "false"
                //Zipalign优化
                zipAlignEnabled true
                // 移除无用的resource文件
                shrinkResources false
                //混淆
                minifyEnabled false
                // proguard-android.txt 表示 Android 系统为我们提供的默认混淆规则文件
                // proguard-rules.pro 则是我们想要自定义的混淆规则
                proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
                debuggable false //禁止使用dug调试
                apkName = "element.apk"
                //签名
                signingConfig signingConfigs.release
            }

            debug {
                signingConfig signingConfigs.release
                //给applicationId添加后缀“.debug”
                applicationIdSuffix ".debug"
                //manifestPlaceholders = [app_icon: "@drawable/launch_beta"]
                buildConfigField "boolean", "LOG_DEBUG", "true"
                minifyEnabled false
                debuggable true
                apkName = "element.apk"
            }


            debugUat {
                signingConfig signingConfigs.release
                //给applicationId添加后缀“.debugUat”
                applicationIdSuffix ".debugUat"
                // 显示Log
                buildConfigField "boolean", "LOG_DEBUG", "true"
                debuggable true
                shrinkResources false
                apkName = "element.apk"

            }
        }

        android.applicationVariants.all { variant ->
            def buildType = variant.buildType.name
            def productFlavor = variant.flavorName

            resValue "string", "app_client", rootProject.ext[productFlavor]["app_client"]
            resValue "string", "mall_base_url", rootProject.ext[productFlavor][buildType]
            resValue "string", "base_url", rootProject.ext[productFlavor][buildType]
            buildConfigField "String", "QQ_APPID", rootProject.ext[productFlavor]["qq_appid_app_key"]
            buildConfigField "String", "APP_ID", rootProject.ext[productFlavor]["app_id_app_key"]

            //配置参数信息 和 productFlavors中的不能同时存在
            if (buildType.contains("debug")) {
                variant.mergedFlavor.manifestPlaceholders = [app_name        : rootProject.ext[productFlavor]["app_name"],
                                                             app_icon        : rootProject.ext[productFlavor]["app_icon"],
                                                             app_package     : rootProject.ext[productFlavor]["application_package"],
                                                             "APP_ID"        : rootProject.ext[productFlavor]["app_id_app_key"],

                                                             GETUI_APP_ID    : rootProject.ext[productFlavor]["getui_app_id_debug"],
                                                             GETUI_APP_KEY   : rootProject.ext[productFlavor]["getui_app_key_debug"],
                                                             GETUI_APP_SECRET: rootProject.ext[productFlavor]["getui_app_secret_debug"],]
            } else {
                variant.mergedFlavor.manifestPlaceholders = [app_name        : rootProject.ext[productFlavor]["app_name"],
                                                             app_icon        : rootProject.ext[productFlavor]["app_icon"],
                                                             app_package     : rootProject.ext[productFlavor]["application_package"],
                                                             "APP_ID"        : rootProject.ext[productFlavor]["app_id_app_key"],

                                                             GETUI_APP_ID    : rootProject.ext[productFlavor]["getui_app_id_debug"],
                                                             GETUI_APP_KEY   : rootProject.ext[productFlavor]["getui_app_key_debug"],
                                                             GETUI_APP_SECRET: rootProject.ext[productFlavor]["getui_app_secret_debug"],]
            }

            variant.outputs.all {
                if (outputFileName.endsWith('.apk')) {
                    //这里使用之前定义apk文件名称
                    outputFileName = buildTime() + "_" + apkName
                }
            }
        }
     配置了三种环境release、debug和debugUat。apkName是三种环境下的不同包名，app_name是APP的名字，
     app_icon是APP的icon等在bank.gradle中获得配置信息。



















