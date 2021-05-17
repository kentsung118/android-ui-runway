# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5
#指定混淆是采用的算法，后面的参数是一个过滤器，这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*

#混合时不使用大小写混合，混合后的类名为小写,windows下必须使用该选项
-dontusemixedcaseclassnames

#指定不去忽略非公共库的类和成员
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers

#输出详细信息
-verbose
#输出类名->混淆后类名的映射关系
-printmapping map.txt

#不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify

#保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

#避免混淆泛型
-keepattributes Signature

#抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

#保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

