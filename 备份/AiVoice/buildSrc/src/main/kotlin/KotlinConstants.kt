object KotlinConstants {
    //kotlin版本
    const val kotlin_version = "1.3.72"
    //gradle版本
    const val build_gradle = "4.0.1"
}
object APPConfig{
    //依赖版本
    const val compileSdkVersion = 30;
    //编译工具
    const val buildToolsVersion = "30.0.2"
    //包名
    const val applicationId =  "com.example.aivoiceapp"
    //最小SDK
    const val minSdkVersion = 21
    //当前基于SDK
    const val targetSdkVersion = 30
    //版本编码
    const val versionCode = 1
    //版本名称
    const val versionName = "1.0"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}
object DependenciesConfig{
    const val STD_LAB = "org.jetbrains.kotlin:kotlin-stdlib:${KotlinConstants.kotlin_version}"
    const val CORE_KTX = "androidx.core:core-ktx:1.2.0"
    const val APPCOMPAT = "androidx.appcompat:appcompat:1.1.0"
    const val MATERIAL = "com.google.android.material:material:1.1.0"
    const val CONSTRAINTLAYOUT = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val JUNIT = "junit:junit:4.12"
    const val EXT_JUNIT = "androidx.test.ext:junit:1.1.1"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.2.0"
    //EventBus
    const val EVENT_BUS = "org.greenrobot:eventbus:3.2.0"
    //ARouter
    const val AROUTER = "com.alibaba:arouter-api:1.5.1"
    const val AROUTER_COMPILER = "com.alibaba:arouter-compiler:1.5.1"
    const val AROUTER_REGISTER = "com.alibaba:arouter-register:1.0.2"
    //RecyclerView
    const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:1.2.0-alpha03"

}
//Module配置
object ModuleConfig{
    //是否App
    const val isApp = false
    //包名
    const val MODULE_APP_MANAGER = "com.example.module_app_manager"
    const val MODULE_CONSTELLATION = "com.example.module_constellation"
    const val MODULE_DEVELOPER = "com.example.module_developer"
    const val MODULE_JOKE = "com.example.module_joke"
    const val MODULE_MAP = "com.example.module_map"
    const val MODULE_SETTING = "com.example.module_setting"
    const val MODULE_VOICE_SETTING = "com.example.module_voice_setting"
    const val MODULE_WEATHER = "com.example.module_weather"

}